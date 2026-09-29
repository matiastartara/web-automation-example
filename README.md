# Web UI Automation Example 🚀

![Java 17](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Maven 3.6+](https://img.shields.io/badge/Maven-3.6+-C71A36?style=for-the-badge&logo=apachemaven)
![Selenium](https://img.shields.io/badge/Selenium-4.44.0-43B02A?style=for-the-badge&logo=selenium)
![TestNG](https://img.shields.io/badge/TestNG-7.7.0-FF6C37?style=for-the-badge&logo=testng)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker)

A robust web UI automation testing project built in **Java 17** using **Selenium WebDriver**, **TestNG**, and the **Page Object Model (POM)** design pattern. It integrates **ExtentReports** for interactive HTML reporting, dynamic environment management via **AEONBITS Owner**, and flexible execution locally or in parallel using **Docker & Selenium Grid**.

---

## 📋 Table of Contents

- [Key Features](#-key-features)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Installation & Setup](#-installation--setup)
- [Environments Management](#-environments-management)
- [Test Execution](#-test-execution)
  - [1. From IDE (IntelliJ IDEA)](#1-from-ide-intellij-idea)
  - [2. Command Line (Maven / Failsafe & Surefire)](#2-command-line-maven--failsafe--surefire)
  - [3. Using Docker Compose (Selenium Grid)](#3-using-docker-compose-selenium-grid)
  - [4. Using Dockerfile (Standalone Container)](#4-using-dockerfile-standalone-container)
- [Execution Reports](#-execution-reports)
- [AI-Assisted Exploration (Selenium MCP)](#-ai-assisted-exploration-selenium-mcp)

---

## ✨ Key Features

* **Page Object Model (POM):** Clean, scalable architecture separating page elements and actions from test logic.
* **Built-in Selenium Manager:** Automatic driver binary management (Chrome, Firefox) with no need for external `WebDriverManager` dependencies.
* **Multi-Browser & Headless Execution:** Out-of-the-box support for Chrome, Firefox, and Edge in both standard and headless modes.
* **Local & Remote Grid Execution:** Configurable to run locally or against a remote Grid infrastructure via Docker Compose.
* **Surefire & Failsafe 3.x:** Configured build plugins for continuous integration (CI/CD) test execution.
* **Detailed HTML Reporting:** Automated execution logs and automatic screenshot capturing on test failure using ExtentReports.

---

## 📁 Project Structure

```text
web-automation-example/
├── Dockerfile                   # Docker image configuration for CI containerized runs
├── docker-compose.yml           # Selenium Grid setup (Hub + Chrome & Firefox Nodes)
├── pom.xml                      # Maven dependencies and plugin configurations
├── README.md                    # Project documentation
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── pages/           # Page Object classes (LoginPage, RegisterPage, HomePage, etc.)
│   │   │   └── utils/           # Utilities (Driver, ExtentReport, Environment)
│   │   └── resources/
│   │       └── environment/     # Environment configurations (dev.example.properties template)
│   └── test/
│       └── java/
│           ├── suite/           # TestNG XML suites (allTests.xml, loginTest.xml, allTestsRemote.xml)
│           └── test/            # Test classes (BaseTest, LoginTest, RegisterTest, SearchProduct)
└── reports/                     # Generated ExtentReports output
```

---

## 💻 Prerequisites

Ensure you have the following installed on your local machine:

* **Java JDK:** 17 LTS
* **Apache Maven:** 3.6.3 or higher
* **IDE:** IntelliJ IDEA with the **TestNG** plugin installed
* **Browsers:** Google Chrome, Mozilla Firefox, or Microsoft Edge
* **Docker & Docker Compose:** (Optional, for containerized or distributed runs)

---

## 🚀 Installation & Setup

1. **Clone the repository:**

   ```bash
   git clone https://github.com/tu-usuario/web-automation-example.git
   cd web-automation-example
   ```

2. **Configure environment properties:**

   Copy the example environment configuration template into your local `dev.properties` file:

   ```bash
   cp src/main/resources/environment/dev.example.properties src/main/resources/environment/dev.properties
   ```

3. **Compile the project and download dependencies:**

   ```bash
   mvn clean test-compile
   ```

---

## ⚙️ Environments Management

Environment properties are managed dynamically using the **AEONBITS Owner** library.

To prevent committing sensitive credentials to version control, environment files under `src/main/resources/environment/*.properties` are ignored by Git (except `*.example.properties` templates).

1. Template file: `src/main/resources/environment/dev.example.properties`
2. Create your local file: `src/main/resources/environment/dev.properties` (or `prod.properties`)
3. Fill in your target application URL, username, and password.

> `username`/`password` must match a **registered account** on the target site — `LoginTest` reads them via `Environment` (`e.username()` / `e.password()`) to run the positive login scenario, so no credentials are hardcoded in test code.

To set the targeted environment during test execution, update the `environment` parameter in your TestNG XML suite file:

```xml
<parameter name="environment" value="dev"/>
```

---

## 🧪 Test Execution

### 1. From IDE (IntelliJ IDEA)

1. Open `src/test/java/suite/`.
2. Right-click on `allTests.xml` (or a single-test suite: `loginTest.xml`, `registerUserTest.xml`, `searchProductTest.xml`).
3. Click **Run '.../allTests.xml'**.

---

### 2. Command Line (Maven / Failsafe & Surefire)

The project uses **`maven-failsafe-plugin`** in the `allTests` profile for integration testing.

#### Run Full Test Suite (`maven-failsafe-plugin`):

```bash
mvn verify -PallTests
```

#### Run a specific test class (`maven-surefire-plugin`):

```bash
mvn test -Dtest=RegisterTest -Dsurefire.skip=false
```

---

### 3. Using Docker Compose (Selenium Grid)

Run tests in parallel across distributed Chrome and Firefox nodes:

1. **Start Selenium Grid:**

   ```bash
   docker-compose up -d
   ```

   *Selenium Hub console will be accessible at `http://localhost:4444`.*

2. **Execute tests targeting the Grid:**

   Set the `type` parameter to `remote` in `allTestsRemote.xml` and run:

   ```xml
   <parameter name="type" value="remote"/>
   ```

3. **Stop Grid containers:**

   ```bash
   docker-compose down
   ```

---

### 4. Using Dockerfile (Standalone Container)

Build and run tests inside an isolated Docker container:

1. **Build Docker image:**

   ```bash
   docker build -t web-automation-tests .
   ```

2. **Run container:**

   ```bash
   docker run --rm web-automation-tests
   ```

---

## 📊 Execution Reports

After test execution completes, reports are generated in the following locations:

1. **ExtentReports (Interactive HTML):**
   Path: `reports/ExtentReport.html` (includes pass/fail status, execution logs, and embedded failure screenshots).

2. **Failsafe / TestNG Reports:**
   Path: `target/failsafe-reports/emailable-report.html` & `target/surefire-reports/index.html`.

---

## 🤖 AI-Assisted Exploration (Selenium MCP)

This repo ships a `.mcp.json` at the root that wires up the [`@angiejones/mcp-selenium`](https://github.com/angiejones/mcp-selenium) MCP server:

```json
{
  "mcpServers": {
    "selenium": {
      "command": "npx",
      "args": ["-y", "@angiejones/mcp-selenium@latest"]
    }
  }
}
```

It lets an MCP-compatible AI client (e.g. Claude Code) drive a real, visible browser session interactively — navigating pages, clicking, typing, and reading the `accessibility://current` resource to get real element locators. This is **exploration tooling, not part of the automated suite**: it's how new Page Objects get their locators discovered/verified against the live site before the actual TestNG test is written (e.g. `LoginPage`/`LoginTest` were built this way). It's never invoked by `mvn verify` or CI.

* No manual install needed — `npx -y @angiejones/mcp-selenium@latest` fetches and runs the server on demand.
* Your MCP client needs to approve/enable the `selenium` server once (in Claude Code this is tracked in `.claude/settings.local.json`; run `claude mcp list` to check connection status).

### Basic usage (from Claude Code)

1. **Start a browser:** ask Claude to open a session (`start_browser`, e.g. Chrome, non-headless so you can watch it).
2. **Navigate:** point it at the page you want to inspect (`navigate` to a URL).
3. **Read the locators:** have it read the `accessibility://current` resource — this returns the accessibility tree with roles, names, and ids, which is far more reliable than guessing CSS/XPath by eye.
4. **Interact to confirm the flow:** use `interact`/`send_keys` to click and type through the flow (e.g. fill a login form) and check the resulting page/title/URL — this validates the locators actually work before they go into a Page Object.
5. **Close the session** once you've got what you need (`close_session`).

This flow follows Selenium's official [AI agents guidance](https://www.selenium.dev/documentation/ai_agents/). The project-specific rules agents must follow (no `Thread.sleep`/implicit waits, `*Options` instead of `DesiredCapabilities`, locator preferences, verification loop) are in [`CLAUDE.md`](CLAUDE.md#selenium-rules-for-agents).

Then take the confirmed locators and write the real `pages/*Page.java` + `test/*Test.java` classes by hand — the MCP session itself is throwaway, nothing it does gets committed.