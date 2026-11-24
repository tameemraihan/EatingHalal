import { chromium } from "../frontend/node_modules/playwright/index.mjs";
import { spawn } from "node:child_process";
import { mkdir, writeFile } from "node:fs/promises";
import path from "node:path";
import { fileURLToPath } from "node:url";

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), "..");
const evidence = path.join(root, "docs/submissions/week-08/evidence");
await mkdir(evidence, { recursive: true });
const browser = await chromium.launch({ channel: "chrome" });
const context = await browser.newContext({
  viewport: { width: 1280, height: 900 },
  recordVideo: {
    dir: path.join(evidence, ".recording"),
    size: { width: 1280, height: 900 },
  },
});
const page = await context.newPage();
await page.setContent(
  '<html><head><style>body{background:#14251d;color:#dbe8dc;font:15px/1.45 monospace;margin:28px}h1{font:600 25px system-ui}p{color:#b8cdbd}pre{white-space:pre-wrap;overflow-wrap:anywhere}#status{color:#dbedab}</style></head><body><h1>EatingHalal · Cucumber running against real PostgreSQL</h1><p>Live Maven output. Six scenarios remain explicitly pending; they are not passing implementation evidence.</p><p id="status">Starting test process…</p><pre id="output"></pre></body></html>',
);
const args = ["-B", "-ntp"];
if (process.env.MAVEN_REPOSITORY)
  args.push("-Dmaven.repo.local=" + process.env.MAVEN_REPOSITORY);
args.push("test");
const processHandle = spawn(process.env.MAVEN_COMMAND || "./mvnw", args, {
  cwd: path.join(root, "backend"),
});
let output = "";
for (const stream of [processHandle.stdout, processHandle.stderr])
  stream.on("data", (chunk) => {
    output += chunk.toString();
  });
const timer = setInterval(
  () =>
    page
      .evaluate((text) => {
        document.querySelector("#output").textContent = text;
        window.scrollTo(0, document.body.scrollHeight);
      }, output.slice(-9000))
      .catch(() => {}),
  250,
);
const exitCode = await new Promise((resolve, reject) => {
  processHandle.on("error", reject);
  processHandle.on("close", resolve);
});
clearInterval(timer);
await page.evaluate(
  ({ text, code }) => {
    document.querySelector("#output").textContent = text;
    document.querySelector("#status").textContent =
      "Maven exited with code " +
      code +
      ". See the report for executed and excluded scenarios.";
    window.scrollTo(0, document.body.scrollHeight);
  },
  { text: output.slice(-9000), code: exitCode },
);
await page.waitForTimeout(2000);
await page.screenshot({
  path: path.join(root, "backend/target/cucumber-results.png"),
  fullPage: true,
});
const video = page.video();
await context.close();
await video.saveAs(path.join(evidence, "cucumber-running.webm"));
await browser.close();
await writeFile(path.join(root, "backend/target/cucumber-output.txt"), output);
if (exitCode !== 0)
  throw new Error(
    "Cucumber failed; recording saved, but this is not passing evidence.",
  );
console.log("Captured the Cucumber video; full generated reports remain under backend/target.");
