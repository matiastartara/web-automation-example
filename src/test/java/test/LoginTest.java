package test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NavigationBar;

public class LoginTest extends BaseTest {

    @Test
    public void loginWithValidCredentialsTest() {

        ExtentTest test = extent.createTest("Login with valid  credentials",
                "Login using a registered email/password and land on My Account");
        testThread.set(test);
        test.log(Status.INFO, "Open url");

        test.log(Status.INFO, "Click on My Account -> Login");
        var navBar = new NavigationBar(driver);
        navBar.selectMenuOption("My account")
              .selectMenuItem("Login");

        test.log(Status.INFO, "Complete login form with valid credentials");
        var loginPage = new LoginPage(driver);
        loginPage.completeEmail(e.username())
                  .completePassword(e.password())
                  .clickOnLogin();

        Assert.assertEquals(driver.getTitle(), "My Account");
        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/account"));
    }

    @Test
    public void loginWithInvalidCredentialsTest() {

        ExtentTest test = extent.createTest("Login with invalid credentials",
                "Login using a wrong password and check the error message");
        testThread.set(test);
        test.log(Status.INFO, "Open url");

        test.log(Status.INFO, "Click on My Account -> Login");
        var navBar = new NavigationBar(driver);
        navBar.selectMenuOption("My account")
              .selectMenuItem("Login");

        test.log(Status.INFO, "Complete login form with an invalid password");
        var loginPage = new LoginPage(driver);
        loginPage.completeEmail(e.username())
                  .completePassword("wrongPassword")
                  .clickOnLogin();

        Assert.assertEquals(loginPage.getErrorMessage(), "Warning: No match for E-Mail Address and/or Password.");
        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/login"));
    }
}
