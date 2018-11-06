package com.framework.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.framework.lib.enums.Platform;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class DriverManager {

	private static WebDriver driver;
	private DriverManager() {
	}
	public static WebDriver getInstance() {
		if (driver == null) {
			new RuntimeException("Driver has not been initilized");
		}
		return driver;
	}
	public static void setInstance(WebDriver driver) {
		DriverManager.driver = driver;
	}
	
	protected static void createDriver(Platform platform) {
		ServerManager.startServer();
		
		final Map<String, String> capabilityProps = PropertiesManager.getCapability(platform.getCapabilityFile());
		DesiredCapabilities capabilities = new DesiredCapabilities(capabilityProps);
		
		String noReset = System.getProperty("NoReset");
		if(noReset != null) {
			capabilities.setCapability(MobileCapabilityType.NO_RESET, Boolean.valueOf(noReset));
			capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
		}
		switch (platform) {
			case ANDROID: 
				try {
					driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
				} catch (MalformedURLException e1) {}
				break;
			case IOS:
				break;
			default :
				break;
		}
	}
	
	public static void quitDriver() {
		if(driver != null) {
			driver.quit();
		}
	}
}
