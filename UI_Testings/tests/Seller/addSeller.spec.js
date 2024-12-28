import { test, expect } from "@playwright/test";

test("add seller and capture notification", async ({ page }) => {
  await page.goto("http://localhost:3000");

  const addButton = await page.locator("button:has(span.anticon-plus)");
  await addButton.click();

  await page.getByLabel("Seller Name").click();
  await page.getByLabel("Seller Name").fill("Kavindu Kavishka");
  await page.getByLabel("Email").click();
  await page.getByLabel("Email").fill("kavindu123@gmail.com");
  await page.getByLabel("Contact").click();
  await page.getByLabel("Contact").fill("0778854691");
  await page.getByLabel("Address").click();
  await page
    .getByLabel("Address")
    .fill("46F, Rukgaha Road, Arukgoda, Alubomulla");

  await page.locator('input[type="radio"][value="Active"]').click();

  const submitButton = await page.locator(
    'button[type="submit"]:has-text("Add Seller")'
  );
  await expect(submitButton).toBeVisible({ timeout: 10000 });
  await submitButton.click();

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
