import { readFile, writeFile } from "node:fs/promises";
import os from "node:os";
import path from "node:path";
const base = process.env.DEMO_URL || "http://127.0.0.1:8080";
const stateFile =
  process.env.RESTART_CHECK_FILE ||
  path.join(os.tmpdir(), "eatinghalal-restart-check.json");
let cookies = new Map();
let session;
async function request(route, options = {}) {
  const headers = {
    ...options.headers,
    Cookie: [...cookies].map(([key, value]) => key + "=" + value).join("; "),
  };
  if (options.method && session)
    headers[session.csrfHeader] = session.csrfToken;
  const response = await fetch(base + route, {
    ...options,
    headers,
    redirect: "manual",
  });
  for (const cookie of response.headers.getSetCookie()) {
    const [pair] = cookie.split(";");
    const index = pair.indexOf("=");
    cookies.set(pair.slice(0, index), pair.slice(index + 1));
  }
  if (!response.ok) throw new Error(route + " returned " + response.status);
  return response.status === 204 ? null : response.json();
}
session = await request("/api/session");
await request("/api/login", {
  method: "POST",
  headers: { "Content-Type": "application/x-www-form-urlencoded" },
  body: new URLSearchParams({ username: "demo", password: "demo-password" }),
});
session = await request("/api/session");
if (process.argv[2] === "create") {
  const results = await request("/api/products?query=Demo%20Oat%20Drink");
  const productId = results.products[0].id;
  const existing = await request("/api/me/shopping-list/entries");
  let entry = existing.find((entry) => entry.product.id === productId);
  if (!entry)
    entry = await request("/api/me/shopping-list/entries", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ productId, quantity: 1 }),
    });
  await request("/api/me/shopping-list/entries/" + entry.id, {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ quantity: 7 }),
  });
  await writeFile(stateFile, JSON.stringify({ id: entry.id, quantity: 7 }));
  console.log(
    "Created quantity 7. Stop and restart the server with the same PostgreSQL data directory, then run check.",
  );
} else if (process.argv[2] === "check") {
  const expected = JSON.parse(await readFile(stateFile, "utf8"));
  const entries = await request("/api/me/shopping-list/entries");
  const actual = entries.find((entry) => entry.id === expected.id);
  if (!actual || actual.quantity !== expected.quantity)
    throw new Error("Persistence check failed after restart.");
  console.log(
    "PASS: the same entry and quantity 7 survived a full application/PostgreSQL restart.",
  );
} else throw new Error("Use create before restart, then check after restart.");
