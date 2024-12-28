import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");
  await page
    .locator("div")
    .filter({ hasText: /^Inventories$/ })
    .click();
  await page.locator(".ant-select-selection-item").first().click();
  await page.getByText("Seller J").click();
  await page
    .locator(
      "div:nth-child(2) > .ant-row > div:nth-child(2) > .ant-form-item-control-input > .ant-form-item-control-input-content > .ant-select > .ant-select-selector > .ant-select-selection-wrap > .ant-select-selection-item"
    )
    .click();
  await page.getByText("Linen-lin001").click();
  await page
    .locator(
      "div:nth-child(3) > .ant-row > div:nth-child(2) > .ant-form-item-control-input > .ant-form-item-control-input-content > .ant-select > .ant-select-selector > .ant-select-selection-wrap > .ant-select-selection-item"
    )
    .click();
  await page.getByText("Tshirt").click();
  await page.getByPlaceholder("Enter buying price").click();
  await page.getByPlaceholder("Enter buying price").fill("700");
  await page.getByPlaceholder("Enter profit percentage").click();
  await page.getByPlaceholder("Enter profit percentage").fill("20");
  await page.getByPlaceholder("Enter quantity").click();
  await page.getByPlaceholder("Enter quantity").fill("9");
  await page.getByRole("button", { name: "Add Inventory" }).click();

  try {
    const successNotification = await page.waitForSelector(
      ".ant-message-notice-success",
      { timeout: 30000 }
    );
    await expect(successNotification).toBeVisible();
  } catch (error) {
    console.log("Error:", error);
    await page.screenshot({ path: "screenshot.png" });
  }
});
