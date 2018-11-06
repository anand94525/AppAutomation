package com.framework.core.android;

import com.framework.core.BaseTest;
import com.framework.core.MobileTypes;
import io.appium.java_client.android.AndroidDriver;

public class AndroidBaseTest extends BaseTest {

	public void initDriver() {
		this.initDriver(MobileTypes.ANDROID);
	}
	
	public void initDriver(boolean reset) {
		System.setProperty("NoReset", "true");
		this.initDriver(MobileTypes.ANDROID);
	}

	@Override
	public AndroidDriver<?> driver() {
		return ((AndroidDriver<?>)super.driver());
	}
}
