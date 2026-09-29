package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class Driver {

    private static final String GRID_URL = "http://localhost:4444/wd/hub";

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static WebDriver get(String browser,Boolean headlessMode,String type) throws MalformedURLException {

        browser = Optional.ofNullable(browser).orElse("chrome").toLowerCase();

        if (driver.get() == null) {

            switch (browser) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headlessMode) {
                        chromeOptions.addArguments("--start-maximized");
                        chromeOptions.addArguments("--headless=new");
                        chromeOptions.addArguments("--disable-gpu");
                        chromeOptions.addArguments("--no-sandbox");
                        chromeOptions.addArguments("--disable-dev-shm-usage");
                        chromeOptions.addArguments("--allow-insecure-localhost");
                    }

                    if (type.equals("remote")) {
                        //Using docker compose
                        driver.set(new RemoteWebDriver(new URL(GRID_URL), chromeOptions));
                    } else {
                        //Type local
                        driver.set(new ChromeDriver(chromeOptions));
                    }
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headlessMode) {
                        //Firefox uses -headless; --headless=new is a Chromium-only flag
                        firefoxOptions.addArguments("-headless");
                    }

                    if (type.equals("remote")) {
                        //Using docker compose
                        driver.set(new RemoteWebDriver(new URL(GRID_URL), firefoxOptions));
                    } else {
                        //Type local
                        driver.set(new FirefoxDriver(firefoxOptions));
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
