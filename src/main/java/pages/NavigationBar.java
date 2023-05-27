package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ElementUtils;

import java.util.List;

public class NavigationBar extends BasePage {

    @FindBy(css = "nav.hoverable > div > ul.navbar-nav > li.nav-item > a > div > span")
    private List<WebElement> menuBar;

    @FindBy(css = "nav.hoverable > div > ul.navbar-nav > li.nav-item.show > ul span.title")
    private List<WebElement> menuItem;

    public NavigationBar(WebDriver driver) {
        super(driver);
    }

    public NavigationBar selectMenuOption(String option) {
       WebElement e = ElementUtils.findFirstElementByText(menuBar,option);
       ElementUtils.moveToElement(e,driver);
        return this;
    }

    public NavigationBar selectMenuItem(String item){
        WebElement e = ElementUtils.findFirstElementByText(menuItem,item);
        e.click();
        return this;
    }

}
