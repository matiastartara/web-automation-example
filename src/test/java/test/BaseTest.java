package test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utils.Driver;
import utils.Environment;
import utils.ExtentReport;

import java.net.MalformedURLException;

public class BaseTest extends ExtentReport {
    protected static Logger logger = LogManager.getLogger();
    protected static Environment e;
    protected WebDriver driver;

    @BeforeMethod
    @Parameters({"environment", "browser", "headlessMode","type"})
    public void setup(String environment, String browser, Boolean headlessMode,String type) throws MalformedURLException {
        ConfigFactory.setProperty("env", environment);
        e = ConfigFactory.create(Environment.class);
        driver = Driver.get(browser, headlessMode,type);
        driver.navigate().to(e.url());
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        Driver.quit();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws Exception {

        if (result.getStatus() == ITestResult.FAILURE) {
            getTest().fail(MarkupHelper.createLabel(result.getName() + "Case Failed", ExtentColor.RED));
            String imagePath = captureScreen(driver, result);
            getTest().addScreenCaptureFromPath(imagePath);
            getTest().fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            getTest().pass(MarkupHelper.createLabel(result.getName() + "Case Success", ExtentColor.GREEN));
        } else {
            getTest().skip(MarkupHelper.createLabel(result.getName() + "Case Success", ExtentColor.YELLOW));
            getTest().skip(result.getThrowable());
        }
    }

}
