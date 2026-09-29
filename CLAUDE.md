# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A Java 17 + Selenium WebDriver UI automation suite (TestNG, Page Object Model) targeting a demo e-commerce site (OpenCart-style: register, search product flows). Reporting via ExtentReports; execution can be local, headless, or against a Selenium Grid via Docker Compose.

## Setup

Environment property files are gitignored except `*.example.properties`. Before running anything the first time:

```bash
cp src/main/resources/environment/dev.example.properties src/main/resources/environment/dev.properties
```

Fill in `url`, `username`, `password` in `dev.properties`. CI generates this file from GitHub Secrets (see `.github/workflows/ci-workflow.yml`) rather than committing it.

## Common commands

```bash
# Compile + download deps
mvn clean test-compile

# Run the full suite (failsafe, uses src/test/java/suite/allTests.xml)
mvn verify -PallTests

# Run a single test class via surefire (surefire is skip=true by default, so it must be overridden)
mvn test -Dtest=RegisterTest -Dsurefire.skip=false

# Selenium Grid for remote/parallel execution
docker-compose up -d      # hub at http://localhost:4444
docker-compose down

# Standalone containerized run
docker build -t web-automation-tests .
docker run --rm web-automation-tests
```

There is no separate lint step; `mvn clean test-compile` is the fastest correctness check.

Tests can also be run from IntelliJ by right-clicking a suite XML under `src/test/java/suite/` (requires the TestNG plugin).

### Choosing what runs

Execution is controlled by TestNG suite XML parameters, not Java code:

- `environment` — selects `src/main/resources/environment/<value>.properties` (via AEONBITS Owner, see `Environment.java`)
- `browser` — `chrome` | `firefox` | `edge`
- `headlessMode` — `true`/`false`
- `type` — `local` or `remote` (remote connects to `http://localhost:4444/wd/hub`, i.e. requires `docker-compose up -d` first)

Suite files: `allTests.xml` (full suite, headless chrome), `allTests-parallel.xml`, `allTestsRemote.xml` (Grid), plus single-test suites `registerUserTest.xml` / `searchProductTest.xml`. `mvn verify -PallTests` is hardwired to `allTests.xml` via the `maven-failsafe-plugin` config in `pom.xml`; to run a different suite XML, invoke TestNG directly or from the IDE.

## Architecture

**Page Object Model**, split across `src/main/java` (framework + pages, reusable) and `src/test/java` (test cases, suite XML):

- `pages/BasePage.java` — every page extends this. Constructor calls `PageFactory.initElements`, and it exposes wrapped `click`/`type`/`getText` helpers that wait via a shared 60s `WebDriverWait` (click retries once on `StaleElementReferenceException`). New page classes should build on these helpers rather than calling Selenium's raw `WebElement` methods, to keep waiting/retry behavior consistent.
- `pages/*Page.java`, `pages/NavigationBar.java` — one class per page/component, elements declared with `@FindBy`, actions return `this` for a fluent chain (see `RegisterPage.java`).
- `test/BaseTest.java` — `@BeforeMethod` reads the `environment`/`browser`/`headlessMode`/`type` TestNG parameters, builds the `Environment` config via `ConfigFactory`, gets a driver via `Driver.get(...)`, navigates to `e.url()`. `@AfterMethod` quits the driver and reports pass/fail/skip to ExtentReports (with a screenshot on failure).
- `test/*Test.java` — extend `BaseTest`, drive pages through their fluent API, assert with TestNG `Assert`. Each test creates its own `ExtentTest` via `extent.createTest(...)` and logs steps with `test.log(Status.INFO, ...)`.
- `utils/Driver.java` — `ThreadLocal<WebDriver>` factory. One driver per thread, supports parallel suite execution. `type=remote` builds a `RemoteWebDriver` against the Grid hub; `type=local` builds a local `ChromeDriver`/`FirefoxDriver`/`EdgeDriver`. Browser config always goes through `*Options` classes (shared by local and remote), and headless flags are applied only when `headlessMode=true` (Chromium browsers use `--headless=new`, Firefox uses `-headless`). Edge is local-only. Selenium Manager handles driver binaries automatically — no WebDriverManager dependency.
- `utils/Environment.java` — AEONBITS Owner `Config` interface; `${env}` is bound at runtime via `ConfigFactory.setProperty("env", environment)` before `ConfigFactory.create(Environment.class)`.
- `utils/ExtentReport.java` — `@BeforeSuite`/`@AfterSuite` lifecycle for the HTML report (timestamped file under `reports/`), `ThreadLocal<ExtentTest>` for parallel-safe test logging, and the failure screenshot capture helper.
- `utils/ElementUtils.java`, `GridUtils.java`, `ExcelUtils.java`, `WaitUtils.java`, `PropertiesReader.java` — assorted helpers (e.g. `ExcelUtils` reads test data from `src/main/resources/users.xlsx`; `GridUtils` reads generic HTML `<table>` grids by row/col).

### Data flow for a new test

1. Add/extend a page class in `pages/` with `@FindBy` locators and fluent action methods built on `BasePage` helpers.
2. Add a test class in `test/` extending `BaseTest`, using the page's fluent API and TestNG `Assert`.
3. Register it in a suite XML (or rely on `allTests.xml`'s `<package name="test.*"/>` to pick it up automatically) and set the four required parameters (`environment`, `browser`, `headlessMode`, `type`).

## Selenium rules for agents

Based on Selenium's [AI agents guidance](https://www.selenium.dev/documentation/ai_agents/). Selenium Java **4.44.0** (see `pom.xml`); the Grid images in `docker-compose.yml` are pinned to the same version, so bump both together.

- **Docs first:** check https://www.selenium.dev/documentation/ (index for agents: https://www.selenium.dev/llms.txt) before using an API you're unsure about. Don't copy Selenium 3 era patterns from memory.
- **Drivers:** never add WebDriverManager, hard-code driver paths, or call `System.setProperty("webdriver.*.driver", ...)`. Selenium Manager resolves drivers.
- **Browser config:** only `ChromeOptions`/`FirefoxOptions`/`EdgeOptions`, including for `RemoteWebDriver`. Never `DesiredCapabilities` or raw capability maps.
- **Waits:** never `Thread.sleep`, never `implicitlyWait` (it would mix with the explicit waits in `BasePage`/`WaitUtils`). Wait for a specific condition; raise a timeout only after identifying which condition isn't being met.
- **Locators:** prefer `id`/`name`, then CSS on stable attributes. No absolute XPath, no auto-generated class names. Verify new locators against the live site with the Selenium MCP server (`.mcp.json`) before writing them into a Page Object.
- **Browser logs / network:** use WebDriver BiDi, not Chrome DevTools Protocol.
- **Grid:** Grid 4 only (`hub`/`node`/`standalone`), no Grid 3 role syntax.
- **Verification loop for a new/changed test:** run it alone (`mvn test -Dtest=<Class> -Dsurefire.skip=false`), re-run it a few times to check it isn't flaky, then review the diff for sleeps, driver paths, and absolute XPath.
- **Public repo:** never commit URLs, credentials, or real user data. They live only in the gitignored `*.properties` files and GitHub Secrets.

## Reports

- ExtentReports HTML: `reports/report_<timestamp>.html`
- Failsafe/Surefire HTML: `target/failsafe-reports/emailable-report.html`, `target/surefire-reports/index.html`
- Failure screenshots: `<user.dir>/screenshots/` (path is built with a Windows-style `\` separator in `ExtentReport.captureScreen`, so on macOS/Linux this resolves to a literal `screenshots\<name>` file inside the working directory rather than a nested folder)

## CI

`.github/workflows/ci-workflow.yml` runs on push/PR to `master`: installs JDK 17 + Chrome + Firefox, materializes `dev.properties` from GitHub Secrets (`DEV_URL`, `DEV_USERNAME`, `DEV_PASSWORD`), runs `mvn verify -PallTests`, and uploads the failsafe emailable report as an artifact.
