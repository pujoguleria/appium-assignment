package com.rbc.tests;

import java.io.IOException;

import com.rbc.tests.Utilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.rbc.tests.TestConfiguration;

public class TestListener implements ITestListener{

	AndroidDriver<AndroidElement> driver;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	String screenShotsDir;

	@Override
	public void onTestStart(ITestResult result) {
		logger.info("Listener onTestStart");
		try {
			TestConfiguration config = TestConfiguration.getInstance();
			this.driver = config.getDriver();
			this.screenShotsDir = config.getProperties().getProperty("screenShotsDir");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailure(ITestResult result) {

		String s = result.getName();
		try {
			Utilities.generateScreenshot(driver, s, screenShotsDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {
		logger.info("onStart");
	}

	@Override
	public void onFinish(ITestContext context) {
		logger.info("onFinish");
	}
}
