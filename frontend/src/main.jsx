import React, { useEffect, useState } from "react";
import { createRoot } from "react-dom/client";
import "./styles.css";

let csrf = null;
async function request(path, options = {}) {
  const headers = { ...options.headers };
  if (options.method && options.method !== "GET" && csrf)
    headers[csrf.csrfHeader] = csrf.csrfToken;
  const response = await fetch(path, {
    ...options,
    headers,
    credentials: "same-origin",
  });
  if (response.status === 204) return null;
  const data = await response.json().catch(() => ({}));
  if (!response.ok)
    throw new Error(
      data.message ||
        (response.status === 401
          ? "Please sign in."
          : response.status === 403
            ? "You do not have permission for this action."
            : "The request could not be completed."),
    );
  return data;
}
const json = (body) => ({
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify(body),
});

function Entry({ entry, onSave, onRemove, busy }) {
  const [quantity, setQuantity] = useState(entry.quantity);
  useEffect(() => setQuantity(entry.quantity), [entry.quantity]);
  return (
    <li className="entry">
      <div>
        <strong>{entry.product.name}</strong>
        <p className="muted">
          {entry.verdict}
          {entry.needsReview ? " · Status changed — review this product" : ""}
        </p>
      </div>
      <form
        onSubmit={(event) => {
          event.preventDefault();
          onSave(entry.id, Number(quantity));
        }}
      >
        <label>
          Quantity for {entry.product.name}
          <input
            aria-label={"Quantity for " + entry.product.name}
            type="number"
            min="1"
            max="999"
            required
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
          />
        </label>
        <button disabled={busy}>Save quantity</button>
      </form>
      <button
        className="quiet"
        disabled={busy}
        onClick={() => onRemove(entry.id)}
      >
        Remove
      </button>
    </li>
  );
}

function App() {
  const [session, setSession] = useState(null);
  const [username, setUsername] = useState("demo");
  const [password, setPassword] = useState("");
  const [query, setQuery] = useState("");
  const [search, setSearch] = useState(null);
  const [selected, setSelected] = useState(null);
  const [entries, setEntries] = useState([]);
  const [adminQuery, setAdminQuery] = useState("");
  const [assessments, setAssessments] = useState(null);
  const [photoOpen, setPhotoOpen] = useState(false);
  const [photo, setPhoto] = useState(null);
  const [busy, setBusy] = useState(false);
  const [error, setError] = useState("");
  const [notice, setNotice] = useState("");
  async function refreshSession() {
    const current = await request("/api/session");
    csrf = current;
    setSession(current);
    if (current.username)
      setEntries(await request("/api/me/shopping-list/entries"));
    else setEntries([]);
    return current;
  }
  useEffect(() => {
    refreshSession().catch((e) => setError(e.message));
  }, []);
  async function run(action) {
    setBusy(true);
    setError("");
    setNotice("");
    try {
      await action();
    } catch (e) {
      setError(e.message);
    } finally {
      setBusy(false);
    }
  }
  async function refreshList() {
    setEntries(await request("/api/me/shopping-list/entries"));
  }
  function login(event) {
    event.preventDefault();
    run(async () => {
      await request("/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({ username, password }),
      });
      await refreshSession();
      setPassword("");
      setNotice("Signed in.");
    });
  }
  function logout() {
    run(async () => {
      await request("/api/logout", { method: "POST" });
      setAssessments(null);
      await refreshSession();
      setNotice("Signed out.");
    });
  }
  function doSearch(event) {
    event.preventDefault();
    run(async () => {
      setSearch(
        await request("/api/products?query=" + encodeURIComponent(query)),
      );
      setSelected(null);
    });
  }
  function select(product) {
    run(async () => {
      setSelected(null);
      setSelected(await request("/api/products/" + product.id + "/assessment"));
    });
  }
  function add(productId) {
    run(async () => {
      await request("/api/me/shopping-list/entries", {
        method: "POST",
        ...json({ productId, quantity: 1 }),
      });
      await refreshList();
      setNotice("Product added to your shopping list.");
    });
  }
  function save(id, quantity) {
    run(async () => {
      await request("/api/me/shopping-list/entries/" + id, {
        method: "PATCH",
        ...json({ quantity }),
      });
      await refreshList();
      setNotice("Quantity saved.");
    });
  }
  function remove(id) {
    run(async () => {
      await request("/api/me/shopping-list/entries/" + id, {
        method: "DELETE",
      });
      await refreshList();
      setNotice("Product removed.");
    });
  }
  function clear() {
    run(async () => {
      await request("/api/me/shopping-list/entries", { method: "DELETE" });
      await refreshList();
      setNotice("Shopping list cleared.");
    });
  }

  return (
    <>
      <header>
        <a className="brand" href="/">
          EatingHalal<span>Packaged food, clearer choices</span>
        </a>
        <span className="eyebrow">University project</span>
      </header>
      <main>
        <section className="intro">
          <p className="eyebrow">Understand what is in your food</p>
          <h1>
            Find a product.
            <br />
            Review the evidence.
          </h1>
          <p>
            Search packaged food, read a stored assessment and keep your own
            shopping list.
          </p>
        </section>
        {session?.demo && (
          <aside className="demo-note">
            <strong>Classroom demonstration</strong>
            <p>
              The demo products and verdicts are synthetic. Live verdict
              generation and OCR are not implemented.
            </p>
          </aside>
        )}
        <div className="feedback" aria-live="polite">
          {error && (
            <p role="alert" className="error">
              {error}
            </p>
          )}
          {notice && <p role="status">{notice}</p>}
          {busy && <p>Working…</p>}
        </div>
        <div className="layout">
          <div>
            <section className="panel">
              <h2>Search products</h2>
              <form className="search" onSubmit={doSearch}>
                <label htmlFor="query">Product name</label>
                <div className="inline">
                  <input
                    id="query"
                    required
                    maxLength="100"
                    placeholder="Try Demo Oat Drink"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                  />
                  <button disabled={busy}>Search</button>
                </div>
              </form>
              {search && (
                <>
                  <p className="muted">{search.sourceMessage}</p>
                  {search.products.length === 0 ? (
                    <p>No matching product was found.</p>
                  ) : (
                    <ul className="products">
                      {search.products.map((product) => (
                        <li key={product.id}>
                          <div>
                            <strong>{product.name}</strong>
                            <p className="muted">
                              {product.brand} · {product.packageDescription}
                            </p>
                            <small>{product.source}</small>
                          </div>
                          <button
                            className="quiet"
                            disabled={busy}
                            onClick={() => select(product)}
                          >
                            View assessment
                          </button>
                        </li>
                      ))}
                    </ul>
                  )}
                  {search.photoUploadOffered && (
                    <button
                      className="quiet"
                      onClick={() => setPhotoOpen(true)}
                    >
                      Upload a packaging photo
                    </button>
                  )}
                </>
              )}
            </section>
            {selected && (
              <section className="panel" aria-label="Assessment details">
                <p className="eyebrow">Stored assessment</p>
                <h2>{selected.product.name}</h2>
                <span className={"verdict " + selected.verdict.toLowerCase()}>
                  {selected.verdict}
                </span>
                <p>{selected.justification}</p>
                <p className="muted">
                  Evidence source: {selected.product.source}. Assessed{" "}
                  {new Date(selected.assessedAt).toLocaleDateString()}.
                </p>
                {selected.product.sourceUrl && (
                  <a
                    href={selected.product.sourceUrl}
                    target="_blank"
                    rel="noreferrer"
                  >
                    View product data source
                  </a>
                )}
                <div>
                  <button
                    disabled={
                      busy ||
                      !session?.username ||
                      selected.verdict !== "Halal" ||
                      entries.some(
                        (entry) => entry.product.id === selected.product.id,
                      )
                    }
                    onClick={() => add(selected.product.id)}
                  >
                    Add to shopping list
                  </button>
                </div>
                {!session?.username && (
                  <p className="muted">Sign in to save products.</p>
                )}
              </section>
            )}
            {photoOpen && (
              <section className="panel">
                <h2>Packaging photo</h2>
                <p>
                  OCR processing is not available yet. Uploads will not produce
                  an assessment.
                </p>
                <form
                  onSubmit={(e) => {
                    e.preventDefault();
                    run(async () => {
                      const body = new FormData();
                      body.append("photo", photo);
                      await request("/api/photos", { method: "POST", body });
                    });
                  }}
                >
                  <label>
                    Packaging image
                    <input
                      type="file"
                      accept="image/jpeg,image/png,image/webp"
                      required
                      onChange={(e) => setPhoto(e.target.files[0])}
                    />
                  </label>
                  <button disabled={busy || !photo}>Check availability</button>
                </form>
              </section>
            )}
          </div>
          <div>
            <section className="panel">
              <h2>{session?.username ? "Your account" : "Sign in"}</h2>
              {session?.username ? (
                <>
                  <p>
                    Signed in as <strong>{session.username}</strong>.
                  </p>
                  <button className="quiet" disabled={busy} onClick={logout}>
                    Sign out
                  </button>
                </>
              ) : (
                <form onSubmit={login}>
                  <label>
                    Username
                    <input
                      autoComplete="username"
                      required
                      value={username}
                      onChange={(e) => setUsername(e.target.value)}
                    />
                  </label>
                  <label>
                    Password
                    <input
                      type="password"
                      autoComplete="current-password"
                      required
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                    />
                  </label>
                  <button disabled={busy || !session}>Sign in</button>
                </form>
              )}
              {session?.demo && (
                <p className="muted">
                  Local demo accounts: demo, other, admin.
                  <br />
                  Password: demo-password.
                </p>
              )}
            </section>
            <section className="panel">
              <div className="section-heading">
                <h2>Shopping list</h2>
                {entries.length > 0 && (
                  <button className="quiet" disabled={busy} onClick={clear}>
                    Clear list
                  </button>
                )}
              </div>
              {!session?.username ? (
                <p className="muted">Sign in to view your personal list.</p>
              ) : entries.length === 0 ? (
                <p>Your shopping list is empty.</p>
              ) : (
                <ul className="entries">
                  {entries.map((entry) => (
                    <Entry
                      key={entry.id}
                      entry={entry}
                      busy={busy}
                      onSave={save}
                      onRemove={remove}
                    />
                  ))}
                </ul>
              )}
            </section>
          </div>
        </div>
        {session?.admin && (
          <section className="panel">
            <h2>Stored assessment administration</h2>
            <p className="muted">
              Read-only review for authorised administrators.
            </p>
            <form
              className="inline"
              onSubmit={(e) => {
                e.preventDefault();
                run(async () =>
                  setAssessments(
                    await request(
                      "/api/admin/assessments?query=" +
                        encodeURIComponent(adminQuery),
                    ),
                  ),
                );
              }}
            >
              <label>
                Assessment search
                <input
                  value={adminQuery}
                  onChange={(e) => setAdminQuery(e.target.value)}
                />
              </label>
              <button disabled={busy}>Find assessments</button>
            </form>
            {assessments &&
              (assessments.length === 0 ? (
                <p>No matching assessments.</p>
              ) : (
                <ul className="products">
                  {assessments.map((a) => (
                    <li key={a.id}>
                      <div>
                        <strong>{a.product.name}</strong>
                        <p>
                          {a.verdict} · {a.justification}
                        </p>
                      </div>
                      <button
                        className="quiet"
                        onClick={() =>
                          run(async () =>
                            setSelected(
                              await request("/api/admin/assessments/" + a.id),
                            ),
                          )
                        }
                      >
                        Review details
                      </button>
                    </li>
                  ))}
                </ul>
              ))}
          </section>
        )}
        <footer>
          External product records:{" "}
          <a href="https://world.openfoodfacts.org">Open Food Facts</a>,
          licensed under{" "}
          <a href="https://opendatacommons.org/licenses/odbl/1-0/">ODbL</a>.
          Provider labels are evidence, not verified EatingHalal verdicts.
        </footer>
      </main>
    </>
  );
}
createRoot(document.getElementById("root")).render(<App />);
