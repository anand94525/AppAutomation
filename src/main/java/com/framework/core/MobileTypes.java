package com.framework.core;

import org.openqa.selenium.WebDriver;
import com.framework.lib.enums.Platform;
/**
 */
public enum MobileTypes implements DriverInstance<WebDriver> {
	
	ANDROID {
		@Override
		public WebDriver getDriverInstance() {
			DriverManager.createDriver(Platform.ANDROID);
			return DriverManager.getInstance();
		}
	},
	IOS {
		@Override
		public WebDriver getDriverInstance() {
			DriverManager.createDriver(Platform.IOS);
			return DriverManager.getInstance();
		}
	};
}
