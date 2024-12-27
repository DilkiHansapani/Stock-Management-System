import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");
  await page
    .locator("div")
    .filter({ hasText: /^Items$/ })
    .click();
  await page
    .getByRole("button", { name: "file-text Generate Reports" })
    .click();
  await page
    .locator("div")
    .filter({ hasText: /^Select Report Type$/ })
    .nth(2)
    .click();
  await page.getByText("Soldout Items base on sellers").click();
  await page
    .getByText("Seller NameCountSeller D40Seller M30Seller A23Seller B201")
    .click();
  const downloadPromise = page.waitForEvent("download");
  await page.getByRole("button", { name: "Generate PDF" }).click();
  const download = await downloadPromise;
  const download1Promise = page.waitForEvent("download");
  await page.getByRole("button", { name: "Generate Excel" }).click();
  const download1 = await download1Promise;
});
