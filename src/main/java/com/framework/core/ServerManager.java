package com.framework.core;

import io.appium.java_client.service.local.AppiumDriverLocalService;

public class ServerManager {

	// Tutorial link - http://www.automationtestinghub.com/3-ways-to-start-appium-server-from-java/
	private static AppiumDriverLocalService service = null;
	
	private ServerManager() {
	}
	
	public static void startServer() {
		service = AppiumDriverLocalService.buildDefaultService();
		service.start();
	}
	
	public static void stopServer() {
		if(service != null && service.isRunning()) {
			service.stop();
		}
	}
}
