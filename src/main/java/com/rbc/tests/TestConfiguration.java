package com.rbc.tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestConfiguration {

	private static TestConfiguration testConfiguration;
	private AndroidDriver<AndroidElement>  driver;
	private Properties properties;
	private static final Logger logger = LoggerFactory.getLogger(TestConfiguration.class);

	public static TestConfiguration getInstance() throws IOException {
		if(testConfiguration == null)
			testConfiguration = new TestConfiguration();
		return testConfiguration;
	}

	private TestConfiguration() throws IOException {
		this.driver = initDriver();
	}

	public AndroidDriver<AndroidElement> getDriver() {
		return driver;
	}

	public Properties getProperties() {
		return properties;
	}

	private AndroidDriver<AndroidElement> initDriver() throws IOException {
		loadConfigurationProperties();
		checkRequirements();
		checkIfAppiumIsRunning();
		return capabilities();
	}

	private void loadConfigurationProperties() throws IOException{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		properties = new Properties();
		try(InputStream resourceStream = classLoader.getResourceAsStream("global.properties")) {
			properties.load(resourceStream);
			logger.info("Loaded global.properties...");
		}
	}

	private void checkRequirements(){

		String androidHome = System.getenv("ANDROID_SDK_ROOT");
		String androidSdkRoot = null;
		if(androidHome == null) {
			androidSdkRoot = System.getenv("ANDROID_HOME");
			if (androidSdkRoot == null)
				throw new IllegalArgumentException("Environment variable ANDROID_HOME or ANDROID_SDK_ROOT is not defined or set for current user");
			else
				androidHome = androidSdkRoot;
		}
		if(!new File(androidHome).exists())
			throw  new IllegalArgumentException("Environment variable ANDROID_HOME or ANDROID_SDK_ROOT is not setup correctly");

		logger.info("ANDROID_HOME is set to {}", androidHome);
	}

	public File getFileResource(String name){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource(name);
		if(url == null)
			return null;
		return new File(url.getFile());
	}

	public void checkIfAppiumIsRunning()
	{
		boolean isRunning = checkIfProcessIsListeningLocally(4723);
		if(isRunning)
			logger.info("Appium is running...");
		else
			logger.warn("Appium server is not running...");
	}

	private boolean checkIfProcessIsListeningLocally(int port) {
		try (Socket ignored = new Socket("localhost", port)) {
			return true;
		} catch (IOException ignored) {
			return false;
		}
	}

	public AndroidDriver<AndroidElement> capabilities() throws IOException
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		String device = properties.getProperty("deviceName");
		String server = properties.getProperty("appiumServer");
		logger.info("deviceName is {}", device);
		logger.info("Server {}", server);
		File app = getFileResource(properties.getProperty("app"));
		assert app != null;
		String appPath = app.getAbsolutePath();
		logger.info("app path is {}", appPath);

		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,properties.getProperty("automationName", "uiautomator2"));
		capabilities.setCapability("appPackage", properties.getProperty("appPackage"));
		capabilities.setCapability("appActivity",properties.getProperty("appActivity"));
		capabilities.setCapability("adbExecTimeout", properties.getProperty("adbExecTimeout","25000"));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,properties.getProperty("platformVersion","11.0"));
		capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		URL appiumEndpoint = new URL(  server + "/wd/hub");
		driver = new AndroidDriver<>(appiumEndpoint, capabilities);

		driver.manage().timeouts().implicitlyWait(Long.parseLong(properties.getProperty("driverImplicitWaitSeconds","20")), TimeUnit.SECONDS);
		logger.info("Driver is ready...");
		return driver;
	}
}
