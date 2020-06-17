package com.rbc.tests;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.rbc.tests.ui.AppUIControls;
import com.rbc.tests.ui.HamburgerMenu;

import java.io.IOException;
import java.time.Duration;

public class AmazonKindleAvailabilityTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private AndroidDriver<AndroidElement> driver;

    @BeforeClass
    public void setUp() throws IOException {
        logger.info("Initializing tests...");
        TestConfiguration testConfiguration = TestConfiguration.getInstance();
        this.driver = testConfiguration.getDriver();
    }

    public void tearDown() throws IOException {
        logger.info("Tear down...");
        TestConfiguration.getInstance().getDriver().quit();
    }

    @Test
    public void testKindleAvailable() throws InterruptedException, IOException {

        AppUIControls appUIControls = new AppUIControls(driver);
        logger.info("Click skip Sign In button");
        appUIControls.getSkipSignInButton().click();

        HamburgerMenu hamburgerMenu = appUIControls.getHamburgerMenu();
        WebDriverWait wait = new WebDriverWait(driver, 50);

        wait.until(ExpectedConditions.elementToBeClickable(hamburgerMenu.getMainMenuIcon()));
        logger.info("Click hamburger menu");
        hamburgerMenu.getMainMenuIcon().click();

        wait.until(ExpectedConditions.elementToBeClickable(hamburgerMenu.getItemSettings()));

        logger.info("Scrolling to Settings and click");
        hamburgerMenu.getItemSettings().click();

        logger.info("Click Country & Language button");
        hamburgerMenu.getItemSettingsCountryLanguage().click();

        logger.info("Click Country & Region button");
        Thread.sleep(5000);
        appUIControls.getButtonCountryRegion().click();

        logger.info("Click region Canada");
        AndroidElement regionCanadaButton = appUIControls.getButtonRegionCanada();
        wait.until(ExpectedConditions.elementToBeClickable(regionCanadaButton));
        Thread.sleep(2000);
        regionCanadaButton.click();

        logger.info("Click Done");
        AndroidElement doneButton = appUIControls.getButtonDone();
        wait.until(ExpectedConditions.elementToBeClickable(doneButton));
        doneButton.click();

        logger.info("Click hamburger menu");
        hamburgerMenu.getMainMenuIcon().click();

        logger.info("Click Shop By Department");
        hamburgerMenu.getItemShopByDepartment().click();
        logger.info("Click Electronics");
        hamburgerMenu.getItemElectronics().click();

        Utilities.scrollToText(driver,"Top rated");
        logger.info("Click text 'See more'");

        AndroidElement topRated = appUIControls.getLabelTopRated();
        int seeMoreX = topRated.getRect().getX() + topRated.getRect().getWidth()/2;
        int seeMoreY = topRated.getRect().getY() - topRated.getRect().getHeight();

        TouchAction touchAction = new TouchAction(driver);
        touchAction.tap(PointOption.point(seeMoreX, seeMoreY)).perform();
        Thread.sleep(5000);

        logger.info("Scroll to 'eBook Readers'");
        Utilities.scrollToText(driver, "Radio Communication");
        appUIControls.getEbooksReaders().click();

        logger.info("Scroll to 'Top rated'");
        Utilities.scrollToText(driver, "See more");
        logger.info("Click first item in list");

        appUIControls.getEReadersListFirstItem().click();
        String availabilityText = "";
        try{
            Thread.sleep(10000);
            Utilities.scrollToText(driver,"Add to Cart");
            touchAction.press(PointOption.point( 150,930))
                    .moveTo(PointOption.point(190,732))
                    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                    .release().perform();

            availabilityText = appUIControls.getStockAvailabilityText().getText();
            logger.info("Available text {}", availabilityText);
        }catch (Exception ex){
            logger.info("'Add to Cart' button not found, might be out of stock");
        }
        Assert.assertTrue(availabilityText.toLowerCase().contains("in stock"), "");
    }

}


