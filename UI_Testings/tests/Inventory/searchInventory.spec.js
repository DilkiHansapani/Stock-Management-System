import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");

  // Navigate to Inventory
  await page
    .locator("div")
    .filter({ hasText: /^Inventories$/ })
    .click();

  // Search for "Seller J"
  await page.getByPlaceholder("Search Sellers,Materials,").fill("Seller j");
  await page.getByRole("button", { name: "Search" }).click();

  // Verify seller cell visibility
  const sellerCell = page.getByRole("cell", { name: /Seller J/i }); // Case-insensitive partial match
  await expect(sellerCell).toBeVisible({ timeout: 10000 }); // Increase timeout

  // Clear search and perform the next search
  await page.getByRole("button", { name: "close-circle" }).click();
  await page.getByPlaceholder("Search Sellers,Materials,").fill("Linen");
  await page.getByRole("button", { name: "Search" }).click();

  // Verify material cell visibility
  const materialCell = page.getByRole("cell", { name: /Linen-lin001/i });
  await expect(materialCell).toBeVisible({ timeout: 10000 });

  // Clear search and perform the next search
  await page.getByRole("button", { name: "close-circle" }).click();
  await page.getByPlaceholder("Search Sellers,Materials,").fill("tshirt");
  await page.getByRole("button", { name: "Search" }).click();

  // Verify category cell visibility
  const categoryCell = page.getByRole("cell", { name: /Tshirt/i });
  await expect(categoryCell).toBeVisible({ timeout: 10000 });
});
