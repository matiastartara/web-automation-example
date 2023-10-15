package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class Driver {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static WebDriver get(String browser,Boolean headlessMode,String type) throws MalformedURLException {

        browser = Optional.ofNullable(browser).orElse("chrome").toLowerCase();

        if (driver.get() == null) {

            switch (browser) {
                case "chrome":
                    if (type.equals("remote")) {
                        //Using docker compose
                        DesiredCapabilities chromeCapabilities = new DesiredCapabilities();
                        chromeCapabilities.setCapability("browserName", "chrome");
                        driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeCapabilities));
                    } else {
                        //Type local
                        ChromeOptions chromeOptions = new ChromeOptions();
                        if (headlessMode) {
                            chromeOptions.addArguments("--start-maximized");
                            chromeOptions.addArguments("--headless=new");
                            chromeOptions.addArguments("--disable-gpu");
                            chromeOptions.addArguments("--no-sandbox");
                            chromeOptions.addArguments("--disable-dev-shm-usage");
                            chromeOptions.addArguments("--allow-insecure-localhost");
                        }

                        driver.set(new ChromeDriver(chromeOptions));
                        //driver.set(WebDriverManager.chromedriver().capabilities(chromeOptions).create());
                    }
                    break;

                case "firefox":
                    if (type.equals("remote")) {
                        //Using docker compose
                        DesiredCapabilities firefoxCapabilities = new DesiredCapabilities();
                        firefoxCapabilities.setCapability("browserName", "firefox");
                        driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), firefoxCapabilities));
                    } else {
                        //Type local
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        if (headlessMode) {
                            firefoxOptions.addArguments("--start-maximized");
                            firefoxOptions.addArguments("--headless=new");
                            firefoxOptions.addArguments("--disable-gpu");
                            firefoxOptions.addArguments("--no-sandbox");
                        }

                        driver.set(new FirefoxDriver(firefoxOptions));
                        //driver.set(WebDriverManager.firefoxdriver().capabilities(firefoxOptions).create());
                    }
                    break;

                case "edge":
                    //Type local
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (headlessMode) {
                        edgeOptions.addArguments("--headless=new");
                        edgeOptions.addArguments("--start-maximized");
                        edgeOptions.addArguments("--disable-gpu");
                        edgeOptions.addArguments("--no-sandbox");
                    }

                    //driver.set(WebDriverManager.edgedriver().capabilities(edgeOptions).create());
                    driver.set(new EdgeDriver(edgeOptions));
                    break;

                default:
                    throw new IllegalArgumentException("Browser [" + browser + "] is NOT supported");
            }

        }

        return driver.get();
    }

    public static void quit() {
        driver.get().quit();
        driver.remove();
    }
}
