import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");

  await page.getByPlaceholder("Search Sellers, Emails...").click();
  await page.getByPlaceholder("Search Sellers, Emails...").fill("Indrani");
  await page.getByRole("button", { name: "Search" }).click();

  const nameCell = page
    .getByRole("cell", { name: "indrani", exact: true })
    .nth(1);
  await expect(nameCell).toBeVisible();

  const emailCell = page
    .getByRole("cell", { name: "indrani123@gmail.com" })
    .nth(1);
  await expect(emailCell).toBeVisible();
});
