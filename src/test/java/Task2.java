import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class Task2 {

    WebDriver driver = new ChromeDriver();

    @Test
    void testInternationalisation() throws InterruptedException {

        driver.manage().window().maximize();
        Thread.sleep(1000);
        driver.get("https://www.ibar.az/az");
        Thread.sleep(1000);
        closeStartPopUpIfExist("default");
        WebElement webElementEn = driver.findElement(By.cssSelector("body > div.not_low_content.lang_nup_default > div.total_wrap_all > div.main_page_wrap > footer > div.footer_right.clear > div > ul.langs > li:nth-child(1) > a"));
        webElementEn.click();
        closeStartPopUpIfExist("en");
        assertEquals("https://www.ibar.az/en/", driver.getCurrentUrl());
        driver.close();
    }

    private void closeStartPopUpIfExist(String languageCode) {
        try {
            WebElement startPopUpCloseButton = driver.findElement(By.cssSelector("body > div.not_low_content.lang_nup_" + languageCode + " > div.total_wrap_all > div.lightbox > div > img"));
            startPopUpCloseButton.click();
        } catch (NoSuchElementException e) {
            System.out.println("Pop Up not found. Go on!");
        }
    }

    @Test
    void testGlobalQuickSearch() throws InterruptedException {
        driver.manage().window().maximize();
        driver.get("https://www.ibar.az/en");
        closeStartPopUpIfExist("en");
        WebElement webElement = driver.findElement(By.cssSelector("body > div.not_low_content.lang_nup_en > div.total_wrap_all > div.main_page_wrap > div > div > div > div > div.search_input > div > input"));
        Thread.sleep(300);
        webElement.sendKeys("American");
        webElement.sendKeys(Keys.SPACE);
        Thread.sleep(700);
        webElement.sendKeys("Exp");
        Thread.sleep(800);
        webElement.sendKeys("ress");
        Thread.sleep(5000);
        List<WebElement> list = driver.findElements(By.className("card_left"));
        assertEquals(2, list.size());
        driver.close();
    }

    @Test
    void testFormInputValidation() throws InterruptedException {
        driver.manage().window().maximize();
        driver.get("https://www.ibar.az/en");
        closeStartPopUpIfExist("en");
        Thread.sleep(2000);
        WebElement webElement = driver.findElement(By.cssSelector("body > div.not_low_content.lang_nup_en > div.total_wrap_all > div.main_page_wrap > div > div > div > div > div.search_results > div.bf_cont > div > div.br_col_before > div > a"));
        webElement.click();
        Thread.sleep(500);
        webElement = driver.findElement(By.id("submitButton"));
        webElement.click();
        Thread.sleep(500);
        List<WebElement> list = driver.findElements(By.className("error"));
        assertEquals(4, list.size());
        driver.close();
    }
}
