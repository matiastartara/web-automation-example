package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected WebDriverWait wait;
    protected WebDriver driver;
    protected static Logger logger = LogManager.getLogger();

    public BasePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    public void click(WebElement element){
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();

        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        }
    }

    public void type(WebElement e, String text) {
        wait.until(ExpectedConditions.visibilityOf(e));
        e.clear();
        e.sendKeys(text);
    }

    public String getText(By cssSelector){
        WebElement e = driver.findElement(cssSelector);
        wait.until(ExpectedConditions.visibilityOf(e));
        return e.getText();
    }

    public boolean isElementPresent(By by, WebDriver driver) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
