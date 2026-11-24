const { test, expect } = require("@playwright/test");

function trackProblems(page) {
  const problems = [];
  page.on("console", (msg) => {
    if (msg.type() === "error") problems.push(`console.error: ${msg.text()}`);
  });
  page.on("pageerror", (err) => problems.push(`uncaught: ${err.message}`));
  page.on("requestfailed", (req) =>
    problems.push(`request failed: ${req.url()} (${req.failure()?.errorText})`)
  );
  page.on("response", (res) => {
    if (res.status() >= 400) problems.push(`HTTP ${res.status()}: ${res.url()}`);
  });
  return problems;
}

async function openPresentation(page) {
  await page.goto("/");
  await page.waitForFunction(
    () => window.Reveal && window.Reveal.isReady && window.Reveal.isReady()
  );
}

test("loads without errors, missing files or failed requests", async ({ page }) => {
  const problems = trackProblems(page);
  await openPresentation(page);
  await page.waitForLoadState("networkidle");
  expect(problems).toEqual([]);
});

test("reveal.js and the plugins are loaded", async ({ page }) => {
  await openPresentation(page);
  const globals = await page.evaluate(() => ({
    reveal: typeof window.Reveal,
    notes: typeof window.RevealNotes,
    highlight: typeof window.RevealHighlight,
    fontAwesome: typeof window.FontAwesome,
  }));
  expect(globals).toEqual({
    reveal: "function",
    notes: "function",
    highlight: "function",
    fontAwesome: "object",
  });
});

test("the slides from reveal.slides are rendered and known to Reveal", async ({ page }) => {
  await openPresentation(page);
  const info = await page.evaluate(() => ({
    sections: document.querySelectorAll("#slides > section").length,
    horizontal: window.Reveal.getHorizontalSlides().length,
    total: window.Reveal.getTotalSlides(),
    present: document.querySelectorAll(".slides section.present").length,
  }));
  expect(info.sections).toBeGreaterThan(0);
  expect(info.horizontal).toBe(info.sections);
  expect(info.total).toBeGreaterThanOrEqual(info.sections);
  expect(info.present).toBeGreaterThan(0);
});

test("the first slide is visible", async ({ page }) => {
  await openPresentation(page);
  await expect(page.locator(".slides section.present").first()).toBeVisible();
});

test("keyboard navigation moves through the slides", async ({ page }) => {
  await openPresentation(page);
  const horizontal = await page.evaluate(() => window.Reveal.getHorizontalSlides().length);
  test.skip(horizontal < 2, "needs at least two top-level slides");

  expect(await page.evaluate(() => window.Reveal.getIndices().h)).toBe(0);
  await page.keyboard.press("ArrowRight");
  await page.waitForFunction(() => window.Reveal.getIndices().h === 1);
  await page.keyboard.press("ArrowLeft");
  await page.waitForFunction(() => window.Reveal.getIndices().h === 0);
});

test("the location hash follows the current slide (option :hash true)", async ({ page }) => {
  await openPresentation(page);
  const horizontal = await page.evaluate(() => window.Reveal.getHorizontalSlides().length);
  test.skip(horizontal < 2, "needs at least two top-level slides");

  await page.keyboard.press("ArrowRight");
  await page.waitForFunction(() => window.location.hash.length > 1);
  const hash = await page.evaluate(() => window.location.hash);

  await page.goto("/" + hash);
  await page.waitForFunction(
    () => window.Reveal && window.Reveal.isReady && window.Reveal.isReady()
  );
  expect(await page.evaluate(() => window.Reveal.getIndices().h)).toBe(1);
});

test("Font Awesome icons are turned into SVGs", async ({ page }) => {
  await openPresentation(page);
  await page.waitForFunction(
    () => document.querySelectorAll("#slides i[class*='fa-']").length === 0
  );
  expect(await page.locator("#slides i[class*='fa-']").count()).toBe(0);
  expect(await page.locator("#slides svg .missing").count()).toBe(0);
});

test("pressing s opens the speaker notes window", async ({ page }) => {
  await openPresentation(page);
  const [popup] = await Promise.all([
    page.waitForEvent("popup"),
    page.keyboard.press("s"),
  ]);
  await popup.waitForLoadState("domcontentloaded");
  await expect(popup.locator("#current-slide")).toBeAttached();
  await expect(popup.locator("#timer, .timer").first()).toBeAttached();
});
