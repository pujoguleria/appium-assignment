package com.rbc.tests.ui;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class AppUIControls {

	AndroidDriver<AndroidElement> driver;
	HamburgerMenu hamburgerMenu;

	public AppUIControls(AndroidDriver<AndroidElement> driver)
	{
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id ="com.amazon.mShop.android.shopping:id/skip_sign_in_button")
	private AndroidElement signInButton;

	@AndroidFindBy(xpath = "//android.view.View[contains(@text,'CDN$')]")
	private AndroidElement eReadersListFirstItem;

	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'In Stock')]")
	private AndroidElement stockAvailabilityText;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='eBook Readers & Accessories']")
	private AndroidElement ebooksReaders;

	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Canada\").instance(0)")
	private AndroidElement buttonRegionCanada;

	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Country/Region:\").instance(0)")
	private AndroidElement buttonCountryRegion;

	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Done\").instance(0)")
	private AndroidElement buttonDone;

	@AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Top rated\").instance(0)")
	private AndroidElement labelTopRated;


	public HamburgerMenu getHamburgerMenu() {
		if(hamburgerMenu == null)
			hamburgerMenu = new HamburgerMenu(driver);
		return hamburgerMenu;
	}

	public AndroidElement getSkipSignInButton() {
		return signInButton;
	}

	public AndroidElement getEReadersListFirstItem() {
		return eReadersListFirstItem;
	}

	public AndroidElement getStockAvailabilityText() {
		return stockAvailabilityText;
	}

	public AndroidElement getEbooksReaders() {
		return ebooksReaders;
	}

	public AndroidElement getButtonRegionCanada(){
		return buttonRegionCanada;
	}

	public AndroidElement getButtonCountryRegion(){
		return buttonCountryRegion;
	}

	public AndroidElement getButtonDone() {
		return buttonDone;
	}

	public AndroidElement getLabelTopRated() {
		return labelTopRated;
	}

}
