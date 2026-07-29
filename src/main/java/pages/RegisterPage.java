package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ElementUtils;

public class RegisterPage extends BasePage {
    @FindBy(id = "input-lastname")
    private WebElement lastName;

    @FindBy(id = "input-firstname")
    private WebElement firstName;

    @FindBy(id = "input-email")
    private WebElement email;

    @FindBy(id = "input-telephone")
    private WebElement phone;

    @FindBy(id = "input-password")
    private WebElement password;

    @FindBy(id = "input-confirm")
    private WebElement confirmPassword;

    @FindBy(css = "[value='Continue']")
    private WebElement continueBtn;

    @FindBy(id = "input-agree")
    private WebElement agreeCheck;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage clickOnAgreeCheck() {
        click(ElementUtils.getParent(agreeCheck,driver));
        return this;
    }

    public RegisterPage completeFirstName(String text) {
        type(firstName, text);
        return this;
    }

    public RegisterPage completeLastName(String text) {
        type(lastName, text);
        return this;
    }

    public RegisterPage completeEmail(String text) {
        type(email, text);
        return this;
    }

    public RegisterPage completePhone(String text) {
        type(phone, text);
        return this;
    }

    public RegisterPage completePassword(String text) {
        type(password, text);
        return this;
    }

    public RegisterPage confirmPassword(String text) {
        type(confirmPassword, text);
        return this;
    }

    public RegisterPage clickOnContinue() {
        click(continueBtn);
        return this;
    }
}
