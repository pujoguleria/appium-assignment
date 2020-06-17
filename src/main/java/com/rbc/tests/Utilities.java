package com.rbc.tests;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

public class Utilities {

	private static final Logger logger = LoggerFactory.getLogger(Utilities.class);

	public static AndroidElement scrollToText(AndroidDriver<AndroidElement> driver, String visibleText) {
		return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\""+visibleText+"\").instance(0))");
	}

	public static AndroidElement getElementContainingText(AndroidDriver<AndroidElement> driver,String text){

		return driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\""+text+"\").instance(0)");
	}

	public static List<AndroidElement> findElementsContainingText(AndroidDriver<AndroidElement> driver, String text){
		return driver.findElements(By.linkText(text));
	}

	public static void scrollVertically(AndroidDriver<AndroidElement> driver, int beginY, int endY, int delaySeconds, int x){
		TouchAction touchAction = new TouchAction(driver);
		touchAction.press(PointOption.point(x, beginY))
				.moveTo(PointOption.point(x, endY))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(delaySeconds)))
				.release().perform();
	}



	public static void generateScreenshot(AndroidDriver<AndroidElement> driver, String filename, String screenShotsDir) throws IOException
	{
		File targetDir = Paths.get(System.getProperty("user.dir"), screenShotsDir).toFile();
		if(!targetDir.exists())
			targetDir.mkdirs();

		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File targetFile = Paths.get(targetDir.getPath(), filename +".png").toFile();
		logger.info("Saving screenshot to {}", targetFile.getPath());
		FileUtils.copyFile(srcFile, targetFile);
	}


}
