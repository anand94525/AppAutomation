package com.framework.core;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/**
 * @author anand shukla
 */
public abstract class BaseTest {

	@BeforeSuite
	public void beforeSuite() {
	}
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {}

	@AfterMethod()
	public void logTestMethodEnded() {}


	@AfterClass(alwaysRun = true)
	public void afterClass() {}

	protected void initDriver(MobileTypes type) {
		DriverManager.setInstance(type.getDriverInstance());
	}

	public WebDriver driver() {
		return DriverManager.getInstance();
	}

	protected void closeSession() {
		if (this.driver() != null) {
			try {
				this.driver().quit();
			} catch (WebDriverException ex) {}
		}
	}

	public static void wait(int seconds) {
		wait(seconds, TimeUnit.SECONDS);
	}

	public static void wait(int time, TimeUnit timeUnit) {
		try {
			timeUnit.sleep(time);
		} catch (final InterruptedException e) {
		}
	}
}