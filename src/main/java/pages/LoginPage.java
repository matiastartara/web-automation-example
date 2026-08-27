package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    @FindBy(id = "input-email")
    private WebElement email;

    @FindBy(id = "input-password")
    private WebElement password;

    @FindBy(css = "[value='Login']")
    private WebElement loginBtn;

    private By errorMessage = By.cssSelector(".alert-danger");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage completeEmail(String text) {
        type(email, text);
        return this;
    }

    public LoginPage completePassword(String text) {
        type(password, text);
        return this;
    }

    public LoginPage clickOnLogin() {
        click(loginBtn);
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("route=account/account"),
                ExpectedConditions.presenceOfElementLocated(errorMessage)
        ));
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
