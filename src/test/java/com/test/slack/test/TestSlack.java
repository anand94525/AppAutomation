package com.test.slack.test;

import java.io.File;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.framework.core.DriverManager;
import com.framework.core.ServerManager;
import com.framework.core.android.AndroidADBUtil;
import com.framework.core.android.AndroidBaseTest;
import com.test.slack.screens.LoginScreen;
import com.test.slack.screens.SettingsScreen;

public class TestSlack extends AndroidBaseTest {
	
	@BeforeClass
	public void startServer() {
		ServerManager.startServer();
		System.out.println("abc");
	}
	
	@AfterClass
	public void stopServer() {
		DriverManager.quitDriver();
		ServerManager.startServer();
	}

	@Test
	public void testLogin1() {
		System.out.println("ulalalalalal..................................................................................");
	}
	//TODO : Plan to add data providers to login data with positive and negative values.
	@Test
	public void testLogin() {
		this.initDriver();
		
		LoginScreen login = new LoginScreen();
		login = login.clickSignInButtion().clickSignInManuallyButton().enterTeamUrl("slacktestautomation").tapNextButtonFromEnterTeamUrlScreen().enterEmail("kumar.anand94525@gmail.com").tapNextButtonFromEnterEmailScreen().enterPassword("anandkumar").tapNextButtonFromEnterPasswordScreen();
	
		SoftAssert sAssert = new SoftAssert();
		
		sAssert.assertEquals(login.getTeamName(), "slacktestautomation", "After Login => Team name was not displayed.");
		sAssert.assertEquals(login.getWelcomeText(), "Slack is a messaging app for groups of people who work together. You can send updates, share files, and organize conversations so that everyone is in the loop.", "After Login => Welcome text was not displayed.");
		
		sAssert.assertAll();
	}
	
	@Test(dependsOnMethods = "testLogin")
	public void testEditProfile() {
		this.initDriver(true);
		
		SettingsScreen settings = new SettingsScreen();
		String name = "Anandk" + new Random().nextInt(1000);
		settings = settings.openSettingsOptions().openSetting().tapEditProfile().tapDisplayName().enterNewName(name).saveEditProfile();
	
		Assert.assertEquals(settings.getUpdatedName(), name, "Profile Screen => New name was not present on the profile screen.");
	}
	
	@Test
	public void testAppVersion() {
		Assert.assertEquals(AndroidADBUtil.getAppVersionName(), "2.69.0", "App version failed.");
	}
	
	@Test
	public void pullApkFromDeviceToMachine() {
		//Plan to change below path as a variable
		String filePath = "/Users/vikasthange/Desktop/test_folder";
		AndroidADBUtil.getApkFileToMachine(filePath);
		Assert.assertTrue(new File("/Users/vikasthange/Desktop/test_folder/base.apk").exists(), "File was not present");
	}
	
}