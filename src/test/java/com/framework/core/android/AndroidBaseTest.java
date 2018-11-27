package com.framework.core.android;

import java.util.Map;
import java.util.Optional;

import com.apps_auto.mobile.framework.config.Constants;
import com.framework.core.BaseTest;
import com.framework.core.MobileTypes;
import com.framework.core.PropertiesManager;
import com.test.screens.HomeScreen;
import com.test.screens.OnBoardingAlertScreen;
import com.framework.core.PropertiesManager;

import io.appium.java_client.android.AndroidDriver;

public class AndroidBaseTest extends BaseTest {
	
	private void setAppiumSessionInfo() {
		System.out.println("Altering appium session...");
		final Map<String, String> capabilityProps = PropertiesManager.getCapability("android_caps.properties");
		String adbPort = Optional.ofNullable(System.getenv("ADB_PORT")).orElse(capabilityProps.get("systemPort"));
		//String adbPort = "8201";
				
		//System.out.println("Setting adb port to " + adbPort);
		System.setProperty("capability.systemPort", adbPort);
		if (Constants.ANDROID_ID() != null) {
			//System.out.println("Setting adb udid " + Constants.ANDROID_ID());
			System.setProperty("capability.udid", Constants.ANDROID_ID());//emulator-5556
			//System.setProperty("capability.udid", "emulator-5556");
		}
	}


	protected void initDriver() {
		setAppiumSessionInfo();
		this.initDriver(MobileTypes.ANDROID);
		this.handleAlerts();
		
		//Wait for weather data
		new HomeScreen().waitForWeatherData(20);
	}
	

	protected void handleAlerts() {
		try {
			Thread.sleep(5000);
			OnBoardingAlertScreen onBoardingAlertScreen = new OnBoardingAlertScreen();
			onBoardingAlertScreen.clickGotIt();
			Thread.sleep(2000);
			
		} catch (Exception e) {
		}
	}
	
	protected void initDriver(boolean reset) {
		System.setProperty("NoReset", "true");
		this.initDriver(MobileTypes.ANDROID);
	}

	@Override
	public AndroidDriver<?> driver() {
		return ((AndroidDriver<?>)super.driver());
	}
}
