import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");
  await page
    .locator("div")
    .filter({ hasText: /^Product$/ })
    .click();
  await page.getByPlaceholder("Category Type").click();
  await page.getByPlaceholder("Category Type").fill("Tshirt");
  await page.getByRole("button", { name: "Add Category" }).nth(1).click();

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
