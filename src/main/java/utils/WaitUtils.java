package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public final class WaitUtils {

    private static final int maxWait = 40;
    protected static Logger logger = LogManager.getLogger();

    public static void waitForElementClickable(WebDriver driver, WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWait));
        wait.until(ExpectedConditions.elementToBeClickable(e));
    }

    public static boolean fluentWait(WebDriver driver, WebElement element) {
        boolean webElementPresence = false;
        try {
            Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(NoSuchElementException.class)
                    .withTimeout(Duration.ofSeconds(60));
            fluentWait.until(ExpectedConditions.visibilityOf(element));

            if (element.isDisplayed())
                webElementPresence = true;

        } catch (Exception e) {
            logger.info("Error waiting for element : " + e.getMessage());
            throw e;
        }

        return webElementPresence;
    }

    public static boolean waitToContainElement(WebDriver driver, final List<WebElement> elements, final String optionText) {
        return new WebDriverWait(driver, Duration.ofSeconds(maxWait)).until((ExpectedCondition<Boolean>)
                driver1 -> elements.stream().anyMatch(x -> x.getText().equals(optionText)));
    }

    public static boolean waitForNotEmptyList(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWait));
            wait.until((ExpectedCondition<Boolean>) driver1 -> {
                int elementCount = driver1.findElements(locator).size();
                return elementCount >= 1;
            });
            return true;
        } catch (Exception e) {
            logger.info("Error waiting for not empty list : " + e.getMessage());
            return false;
        }
    }

    public static void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = driver1 -> ((JavascriptExecutor) driver1)
                .executeScript("return document.readyState").equals("complete");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWait));
        wait.until(pageLoadCondition);
    }

    public static void waitForEmptyList(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWait));
        wait.until((ExpectedCondition<Boolean>) driver1 -> {
            int elementCount = driver1.findElements(locator).size();
            return elementCount == 0;
        });
    }

    public static void waitToContainXElements(WebDriver driver, By selector, int count) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWait));
        wait.until((ExpectedCondition<Boolean>) driver1 -> {
            int elementCount = driver1.findElements(selector).size();
            if (elementCount >= count)
                return true;
            else
                return false;
        });
    }

}
