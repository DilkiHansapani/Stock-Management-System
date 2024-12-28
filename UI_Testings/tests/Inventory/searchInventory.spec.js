import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");

  await page
    .locator("div")
    .filter({ hasText: /^Inventories$/ })
    .click();

  await page.getByPlaceholder("Search Sellers,Materials,").fill("Seller j");
  await page.getByRole("button", { name: "Search" }).click();

  const sellerCell = page.getByRole("cell", { name: /Seller J/i });
  await expect(sellerCell).toBeVisible({ timeout: 40000 });

  await page.getByRole("button", { name: "close-circle" }).click();
  await page.getByPlaceholder("Search Sellers,Materials,").fill("Linen");
  await page.getByRole("button", { name: "Search" }).click();

  const materialCell = page.getByRole("cell", { name: /Linen-lin001/i });
  await expect(materialCell).toBeVisible({ timeout: 10000 });

  await page.getByRole("button", { name: "close-circle" }).click();
  await page.getByPlaceholder("Search Sellers,Materials,").fill("tshirt");
  await page.getByRole("button", { name: "Search" }).click();

  const categoryCell = page.getByRole("cell", { name: /Tshirt/i });
  await expect(categoryCell).toBeVisible({ timeout: 10000 });
});
