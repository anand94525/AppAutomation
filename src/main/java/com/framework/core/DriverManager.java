package com.framework.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.framework.lib.enums.Platform;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class DriverManager {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ServerManager server;
	
	private DriverManager() {
	}
	public static WebDriver getInstance() {
		if (driver.get() == null) {
			new RuntimeException("Driver has not been initilized");
		}
		return driver.get();
	}
	
	
	public static void setServer(ServerManager server) {
		DriverManager.server = server;
	}
	
	public static void setInstance(WebDriver driver) {
		DriverManager.driver.set(driver);
	}
	
	protected static void createDriver(Platform platform) {
		final Map<String, String> capabilityProps = PropertiesManager.getCapability(platform.getCapabilityFile());
		DesiredCapabilities capabilities = new DesiredCapabilities(capabilityProps);
		
		final AppiumServerConfig serverConfig = AppiumServerConfig.get(platform.getCapabilityFile());
		final ServerManager server = new ServerManager(serverConfig);
		
		final boolean serverStarted = server.startServer();
		if (serverStarted) {
			DriverManager.setServer(server);
		}
		if (!(server.isServerRunning())) {
			throw new RuntimeException("Appium server was not running and failed to start appium server, Can not continue test execution");
		}
		
		switch (platform) {
			case ANDROID: 
				//capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, System.getenv("SYSTEM_PORT"));
				//capabilities.setCapability(MobileCapabilityType.UDID, ANDROID_ID());
					driver.set(new AndroidDriver<MobileElement>(serverConfig.getAppiumHubURL(), capabilities));
				break;
			case IOS:
				break;
			default :
				break;
		}
	}
	
	public static void stopServer() {
		if(server != null) {
			server.stopServer();
		}
	}
	
	private static int getSystemPort() {
		int port = -1;
		try {
			port = new ServerSocket(0).getLocalPort();
		} catch (IOException e) {}
		return port;
	}
	
	public static void quitDriver() {
		if(driver.get() != null) {
			driver.get().quit();
		}
	}
}
