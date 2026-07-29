package utils;

import org.apache.commons.codec.binary.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

public final class ElementUtils {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static void javascriptClick(WebElement element, WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public static void actionClick(WebElement element, WebDriver driver) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click(element).perform();
    }

    public static boolean verifyCssPropertyForElement(WebElement element, String cssProperty, String actualValue) {
        boolean result = false;
        String actualClassProperty = element.getCssValue(cssProperty);
        if (actualClassProperty.contains(actualValue)) {
            result = true;
        }
        return result;
    }

    public static void selectByText(WebElement element, String text) {
        Select selectElement = new Select(element);
        selectElement.selectByVisibleText(text);
    }

    public static void selectByIndex(WebElement element, int index) {
        Select selectElement = new Select(element);
        selectElement.selectByIndex(index);
    }

    public static void selectByValue(WebElement element, String value) {
        Select selectElement = new Select(element);
        selectElement.selectByValue(value);
    }

    public static void executeJavaScript(String js, WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript(js);
    }

    public static String getValue(WebElement element, WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return (String) executor.executeScript("return arguments[0].value", element);
    }

    public static void scrollToElement(WebElement element, WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void moveToElement(WebElement element, WebDriver driver) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public static WebElement getNextSibling(WebElement element, WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return (WebElement) executor.executeScript("return arguments[0].nextSibling;", element);
    }

    public static WebElement getPreviousSibling(WebElement element, WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return (WebElement) executor.executeScript("return arguments[0].previousSibling;", element);
    }

    public static WebElement getParent(WebElement element, WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return (WebElement) executor.executeScript("return arguments[0].parentElement;", element);
    }

    public static String generateString(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return builder.toString();
    }

    public static int getRandomNumber() {
        Random random = new Random();
        return (10 + random.nextInt(90));
    }

    public static WebElement findFirstElementByAttribute(List<WebElement> webElements, String attribute, String value) {
        return webElements
                .stream()
                .filter(webElement -> webElement.getAttribute(attribute).equals(value))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No WebElement found containing attribute " + value));
    }

    public static WebElement findFirstElementByText(List<WebElement> webElements, String text) {
        return webElements
                .stream()
                .filter(webElement -> StringUtils.equals(webElement.getText(), text))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No WebElement found containing " + text));
    }

    public static String getDomAttribute(WebDriver driver, By by, String attribute) {
        WebElement element = driver.findElement(by);
        return element.getDomAttribute(attribute);
    }

    public static String getDomProperty(WebDriver driver, By by, String property) {
        WebElement element = driver.findElement(by);
        return element.getDomProperty(property);
    }

}
