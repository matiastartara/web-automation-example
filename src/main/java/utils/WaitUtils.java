package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public final class WaitUtils {

    private static final int maxWait = 40;

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
            throw e;
        }

        return webElementPresence;
    }

    public static boolean waitToContainElement(WebDriver driver, final List<WebElement> elements, final String optionText) {
        return new WebDriverWait(driver, Duration.ofSeconds(maxWait)).until((ExpectedCondition<Boolean>)
                driver1 -> elements.stream().anyMatch(x -> x.getText().equals(optionText)));
    }

    public static void waitForNotEmptyList(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxWait));
        wait.until((ExpectedCondition<Boolean>) driver1 -> {
            int elementCount = driver1.findElements(locator).size();
            return (elementCount >= 1);
        });
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
