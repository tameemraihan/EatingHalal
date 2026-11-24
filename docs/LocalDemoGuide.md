# Local Demo Guide

## Prerequisites

Java 17 or newer, Node.js/npm, and a modern browser. Compilation targets Java 17. The implementation was verified using the installed Java 25 runtime. The native demo/test helper uses real PostgreSQL binaries downloaded through Maven. On this Apple Silicon machine the library used its x86_64 PostgreSQL binary through existing emulation. Supported systems without those binaries/emulation should use an installed PostgreSQL instance or Docker instead.

## Start without Docker

From the repository root:

~~~sh
sh scripts/run-local-demo.sh
~~~

Open http://127.0.0.1:8080. Local accounts are demo, other and admin, each with password demo-password. These are synthetic development accounts and are created only in demo mode. Stop with Ctrl+C. The helper reuses its PostgreSQL data directory; set DEMO_DATABASE_DIRECTORY to an explicit local directory if desired. The default is under the operating system's temporary directory, so use a chosen persistent directory/Compose volume for longer-term data retention. Do not start two helpers against the same data directory simultaneously.

## Alternative: PostgreSQL through Docker

Copy .env.example to .env, choose a local DATABASE_PASSWORD, then run docker compose up -d database. Build the frontend and copy frontend/dist into backend/src/main/resources/static. Run the backend with DATABASE_PASSWORD set and SPRING_PROFILES_ACTIVE=demo. The JDBC URL/user default to localhost:5432/eatinghalal and eatinghalal. The Compose route was supplied but not exercised in this environment.

## Frontend development

~~~sh
cd frontend
npm ci
npm run dev
~~~

Vite proxies /api to the running backend on port 8080. For the integrated demo, run the production build through the startup script instead.

## Product data

External lookup is off by default for deterministic local demos. Set OFF_ENABLED=true to enable Open Food Facts fallback when no local product matches. OFF_USER_AGENT can supply an application/contact identifier. Imported records retain barcode, source URL and retrieval time. Provider ingredients/labels are not confirmed EatingHalal verdicts. Read the [Open Food Facts API requirements](https://openfoodfacts.github.io/openfoodfacts-server/api/) and [data license](https://world.openfoodfacts.org/data); the UI includes attribution. No images are submitted to Open Food Facts by this implementation.

## Demonstrate CRUD

1. Sign in as demo.
2. Search Demo Oat Drink and open its stored assessment.
3. Add it to the list: Create.
4. View the list: Read.
5. Set quantity to 3 and save: Update.
6. Refresh, or restart the server, and verify the quantity remains.
7. Sign in as other and verify that list is empty.
8. Return to demo and remove the product: Delete.
9. Sign in as admin to search/review stored assessments.
10. Search a missing product and open the packaging panel: it clearly reports OCR as unimplemented.

Synthetic verdicts demonstrate the software, not real halal certification. The admin use case remains read-only.

## Run verification

~~~sh
cd backend
./mvnw clean test
~~~

This starts an isolated real PostgreSQL server, calls HTTP endpoints and writes target/cucumber-report.html, target/cucumber-report.json and Surefire reports. Six pending scenarios remain visible/skipped: four OCR flows, automatic assessment generation and the photo-navigation Cucumber scenario. The frontend navigation is checked separately in the browser test.

With the integrated demo running:

~~~sh
cd frontend
npm ci
npx playwright install ffmpeg
npm run test:e2e
~~~

Browser checks use installed Google Chrome and produce screenshots/videos. Install Chrome or adjust the configured Playwright channel for your system.

Validate documentation with python3 scripts/check-documentation.py. Generate UML sources with python3 scripts/generate-diagrams.py. Render sources with PlantUML (headless Java; layout uses Smetana and does not require Graphviz). The supplied PNGs are ready for the architecture document. The [recording script](../scripts/record-cucumber.mjs) captures a real running Maven/Cucumber process; it requires the frontend dependencies and video encoder. Its default Maven command is the project's wrapper.
