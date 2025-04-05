package test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.NavigationBar;
import pages.RegisterPage;
import utils.ElementUtils;
import utils.ExcelUtils;

import java.io.File;
import java.io.IOException;

public class RegisterTest extends BaseTest {
    @Test
    public void registerUserTest() throws IOException {

        ExtentTest test = extent.createTest("Register new user ","Complete registration form and submit");
        testThread.set(test);
        test.log(Status.INFO, "Open url");

        //Read name and lastName from xlsx file
        File file = new File("src/main/resources/users.xlsx");
        String name = ExcelUtils.read(file.getAbsolutePath(), 1, 0);
        String lastName = ExcelUtils.read(file.getAbsolutePath(), 1, 1);

        //Go to Register section
        test.log(Status.INFO, "Click on My Account -> Register");
        var navBar = new NavigationBar(driver);
        navBar.selectMenuOption("My account")
              .selectMenuItem("Register");

        //Register new user
        test.log(Status.INFO,"Complete registration form");


        var registerPage = new RegisterPage(driver);
        var user = ElementUtils.generateString(10);
        var mail = ElementUtils.generateString(8);
        registerPage.completeFirstName(name)
                    .completeLastName(lastName)
                    .completeEmail(user+"@"+mail+".com")
                    .completePhone("123456")
                    .completePassword("passwd")
                    .confirmPassword("passwd")
                    .clickOnAgreeCheck()
                    .clickOnContinue();

        Assert.assertEquals(driver.getTitle(),"Your Account Has Been Created!");
        Assert.assertTrue(driver.getCurrentUrl().contains("route=account/success"));
    }

}
