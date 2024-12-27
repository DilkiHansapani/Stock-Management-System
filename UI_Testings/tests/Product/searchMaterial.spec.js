import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");
  await page
    .locator("div")
    .filter({ hasText: /^Product$/ })
    .click();
  await page.getByPlaceholder("Search Materials").click();
  await page.getByPlaceholder("Search Materials").fill("linen");
  await page.getByRole("button", { name: "Search" }).first().click();

  const linenCell = page.getByRole("cell", { name: "Linen" });
  await expect(linenCell).toBeVisible();
});
