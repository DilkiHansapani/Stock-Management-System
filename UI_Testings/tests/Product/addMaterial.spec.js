import { test, expect } from "@playwright/test";

test("test", async ({ page }) => {
  await page.goto("http://localhost:3000/");

  await page
    .locator("div")
    .filter({ hasText: /^Product$/ })
    .click();

  await page.getByPlaceholder("Material Name").click();
  await page.getByPlaceholder("Material Name").fill("Linen");
  await page.getByRole("button", { name: "Add Category" }).first().click();

  const errorMessage = page.getByText("Please input material type!");
  await expect(errorMessage).toBeVisible();

  await page.getByPlaceholder("Material Type").click();
  await page.getByPlaceholder("Material Type").fill("lin001");
  await page.getByRole("button", { name: "Add Category" }).first().click();

  const notification = page.locator(".ant-notification-notice");
  await expect(notification).toBeVisible();
});
