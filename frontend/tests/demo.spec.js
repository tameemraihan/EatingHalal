import { test, expect } from "@playwright/test";
test("Search, assessment, personal CRUD and admin review", async ({ page }) => {
  await page.goto("/");
  await expect(
    page.getByText("Classroom demonstration", { exact: true }),
  ).toBeVisible();
  await page.getByLabel("Password", { exact: true }).fill("demo-password");
  await page.getByRole("button", { name: "Sign in", exact: true }).click();
  await expect(page.getByText("Signed in as")).toBeVisible();
  await page.getByLabel("Product name", { exact: true }).fill("Demo Oat Drink");
  await page.getByRole("button", { name: "Search", exact: true }).click();
  await page.getByRole("button", { name: "View assessment" }).click();
  await expect(
    page.getByRole("region", { name: "Assessment details" }),
  ).toContainText("Not a real dietary assessment");
  if (await page.getByRole("button", { name: "Clear list" }).isVisible())
    await page.getByRole("button", { name: "Clear list" }).click();
  await page.getByRole("button", { name: "Add to shopping list" }).click();
  await expect(page.getByRole("status")).toHaveText(
    "Product added to your shopping list.",
  );
  await page.getByLabel("Quantity for Demo Oat Drink").fill("3");
  await page.getByRole("button", { name: "Save quantity" }).click();
  await expect(page.getByRole("status")).toHaveText("Quantity saved.");
  await page.reload();
  await expect(page.getByLabel("Quantity for Demo Oat Drink")).toHaveValue("3");
  await page.screenshot({
    path: "../docs/assets/images/local-crud-demo.png",
    fullPage: true,
  });
  await page.getByRole("button", { name: "Sign out" }).click();
  await page.getByLabel("Username", { exact: true }).fill("other");
  await page.getByLabel("Password", { exact: true }).fill("demo-password");
  await page.getByRole("button", { name: "Sign in", exact: true }).click();
  await expect(
    page.getByText("Your shopping list is empty.", { exact: true }),
  ).toBeVisible();
  await page.getByRole("button", { name: "Sign out" }).click();
  await page.getByLabel("Username", { exact: true }).fill("demo");
  await page.getByLabel("Password", { exact: true }).fill("demo-password");
  await page.getByRole("button", { name: "Sign in", exact: true }).click();
  await expect(page.getByLabel("Quantity for Demo Oat Drink")).toHaveValue("3");
  await page.getByRole("button", { name: "Remove", exact: true }).click();
  await expect(
    page.getByText("Your shopping list is empty.", { exact: true }),
  ).toBeVisible();
  await page.getByRole("button", { name: "Sign out" }).click();
  await page.getByLabel("Username", { exact: true }).fill("admin");
  await page.getByLabel("Password", { exact: true }).fill("demo-password");
  await page.getByRole("button", { name: "Sign in", exact: true }).click();
  await page.getByRole("button", { name: "Find assessments" }).click();
  await expect(
    page.getByRole("heading", { name: "Stored assessment administration" }),
  ).toBeVisible();
  await expect(
    page.getByText("Demo Gelatine Sweets", { exact: true }),
  ).toBeVisible();
  await page.getByRole("button", { name: "Sign out" }).click();
  await page
    .getByLabel("Product name", { exact: true })
    .fill("Missing classroom snack");
  await page.getByRole("button", { name: "Search", exact: true }).click();
  await page.getByRole("button", { name: "Upload a packaging photo" }).click();
  await expect(
    page.getByText(
      "OCR processing is not available yet. Uploads will not produce an assessment.",
    ),
  ).toBeVisible();
  await page.screenshot({
    path: "../docs/assets/images/local-photo-pending.png",
    fullPage: true,
  });
});
test("Mobile layout fits the viewport", async ({ page }) => {
  await page.setViewportSize({ width: 390, height: 844 });
  await page.goto("/");
  await expect(
    page.getByRole("heading", { name: "Search products" }),
  ).toBeVisible();
  expect(
    await page.evaluate(
      () => document.documentElement.scrollWidth <= window.innerWidth,
    ),
  ).toBeTruthy();
  await page.screenshot({
    path: "../docs/assets/images/local-mobile-demo.png",
    fullPage: true,
  });
});
