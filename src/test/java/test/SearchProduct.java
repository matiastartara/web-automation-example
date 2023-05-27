package test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;

public class SearchProduct extends BaseTest {

    @Test
    public void searchProductTest() {

        ExtentTest test = extent.createTest("Search product in searchbox",
                "Search product and check details");
        testThread.set(test);
        test.log(Status.INFO, "Open url");

        //Search product and check details
        var home = new HomePage(driver);
        var product = "iPod Touch";

        test.log(Status.INFO, "Search "+ product +" product");
        home.search(product);
        ProductPage productPage = new ProductPage(driver);

        Assert.assertEquals(productPage.getSubtitle(),product);
        Assert.assertTrue(driver.getCurrentUrl().contains("route=product/product&product_id="));

    }
}
