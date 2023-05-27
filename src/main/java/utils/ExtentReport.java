package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExtentReport {

    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ThreadLocal<ExtentTest> testThread = new InheritableThreadLocal<>();

    public synchronized ExtentTest getTest() {
        return testThread.get();
    }

    @BeforeSuite
    public void reportSetup() {
        String formatedDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String workingDir = System.getProperty("user.dir");
        String dir = workingDir + "/reports/report_" + formatedDate + ".html";
        htmlReporter = new ExtentHtmlReporter(dir);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    public static synchronized String captureScreen(WebDriver driver, ITestResult result) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
        Date date = new Date();
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "\\screenshots\\" + result.getName() + dateFormat.format(date) + ".png";
        File destination = new File(path);
        FileUtils.copyFile(src, destination);
        return path;
    }

    @AfterSuite
    public void afterMethod() {
        extent.flush();
    }
}
