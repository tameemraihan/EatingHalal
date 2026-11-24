import { defineConfig } from "@playwright/test";
export default defineConfig({
  testDir: "./tests",
  workers: 1,
  use: {
    channel: "chrome",
    launchOptions: { slowMo: 250 },
    baseURL: "http://127.0.0.1:8080",
    viewport: { width: 1280, height: 900 },
    video: { mode: "on", size: { width: 1280, height: 900 } },
  },
  reporter: [["list"], ["html", { open: "never" }]],
});
