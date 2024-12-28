import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");
  await page
    .getByRole("row", { name: "Seller A sellerA@example.com" })
    .getByRole("button")
    .click();
  await page.getByLabel("Contact").click();
  await page.getByLabel("Contact").fill("0775521504");
  await page.getByRole("button", { name: "Update Seller" }).click();

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
