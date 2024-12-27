import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");
  await page
    .locator("div")
    .filter({ hasText: /^Inventories$/ })
    .click();
  await page.getByText("2", { exact: true }).click();
  await page
    .getByRole("row", { name: "Seller J Linen-lin001 Tshirt", timeout: 50000 })
    .getByRole("button", { timeout: 50000 })
    .click();

  await page.getByLabel("Bulk Quantity").click();
  await page.getByLabel("Bulk Quantity").fill("10");
  await page.getByLabel("Sale").check();
  await page.getByLabel("Sale Percentage").click();
  await page.getByLabel("Sale Percentage").fill("10");
  await page.getByRole("button", { name: "Update", exact: true }).click();

  try {
    const successNotification = await page.waitForSelector(
      ".ant-message-notice-success",
      { timeout: 20000 }
    );
    await expect(successNotification).toBeVisible();
  } catch (error) {
    console.log("Error:", error);
    await page.screenshot({ path: "screenshot.png" });
  }
});
