package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ElementUtils;
import utils.WaitUtils;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(name = "search")
    private WebElement searchBox;

    @FindBy(css = ".dropdown-menu.autocomplete h4 a")
    private List<WebElement> searchResults;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void search(String text){
        type(searchBox,text);
        WaitUtils.waitForNotEmptyList(driver, By.cssSelector(".dropdown-menu.autocomplete h4 a"));
        ElementUtils.findFirstElementByText(searchResults,text).click();
    }

}
