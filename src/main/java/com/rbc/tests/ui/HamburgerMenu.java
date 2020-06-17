package com.rbc.tests.ui;

import com.rbc.tests.Utilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;


public class HamburgerMenu {

	AndroidDriver<AndroidElement> driver;
	public HamburgerMenu(AndroidDriver<AndroidElement> driver)
	{
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/chrome_action_bar_burger_icon")
	private AndroidElement hamburgerMenu;

	public AndroidElement getItemShopByDepartment(){
		return Utilities.getElementContainingText(driver,"Shop by Department");
	}

	public AndroidElement getItemSettings(){
		return Utilities.scrollToText(driver,"Settings");
	}

	public AndroidElement getItemElectronics(){
		return Utilities.getElementContainingText(driver,"Electronics");
	}

	public AndroidElement getItemSettingsCountryLanguage(){
		return Utilities.getElementContainingText(driver,"Country & Language");
	}

	public AndroidElement getMainMenuIcon() {
		return hamburgerMenu;
	}
}
