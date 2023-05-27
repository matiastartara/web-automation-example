package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GridUtils {

    public static WebElement getElementAt(int row, int col, String p, String p2, WebDriver driver) {
        return driver.findElement(By.xpath("//table/tbody/tr[" + row + "]/td[" + col + "]"));
    }

    public static int getRowCount(WebDriver driver) {
        return driver.findElements(By.xpath("//table/tbody/tr")).size();
    }

    public static int getColCount(WebDriver driver) {
        WebElement ToGetColumns = driver.findElement(By.xpath("//table/tbody/tr"));
        List<WebElement> TotalColsList = ToGetColumns.findElements(By.tagName("td"));
        return TotalColsList.size();
    }

}
