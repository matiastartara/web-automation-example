package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    private By subTitle = By.cssSelector("h1.h3");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getSubtitle() {
        return getText(subTitle);
    }

}
