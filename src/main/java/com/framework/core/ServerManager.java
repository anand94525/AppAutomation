package com.framework.core;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.ServerArgument;

public class ServerManager {
	private AppiumDriverLocalService service;
	AppiumServerConfig appiumServerConfigs;

	public ServerManager(AppiumServerConfig appiumServerConfigs) {
		this.appiumServerConfigs=appiumServerConfigs;
	}

	public boolean startServer() {
		boolean serverStarted = false;
		final String hubUrl = this.appiumServerConfigs.getAppiumHubUrlString();
		try {
			if (!ServerManager.isServerRunning(hubUrl)) {
				URL url = null;
				url = new URL(hubUrl);
				System.out.println("Starting Appium server via Local Service");
				final AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder()
						.usingDriverExecutable(new File(this.appiumServerConfigs.getAppiumServerNodePath())).withAppiumJS(new File(this.appiumServerConfigs.getAppiumServerNodeJsFilePath()))
						.withLogFile(new File(this.appiumServerConfigs.getAppiumServerLogFilePath()))
						.withArgument(GeneralServerFlag.LOG_LEVEL, this.appiumServerConfigs.getAppiumServerLogLevel())
						.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
						//.withArgument(RelaxedSecurityFlag.RELAXED_SECURITY)
						.withIPAddress(url.getHost())
						.usingPort(url.getPort());
				this.service = AppiumDriverLocalService.buildService(appiumServiceBuilder);
				final long startTime = Calendar.getInstance().getTimeInMillis();
				System.out.println("Starting server at " + Calendar.getInstance().getTime());
				this.service.start();
				System.out.println("Started appium server in " + TimeUnit.MILLISECONDS.toSeconds(Calendar.getInstance().getTimeInMillis() - startTime) + " seconds");

				serverStarted = true;
			} else {
				System.out.println("Appium server already running at: " + hubUrl);
			}
		} catch (final MalformedURLException e) {
			System.out.println("Invalid Appium hub URL: " + hubUrl);
		}
		return serverStarted;
	}

	public void stopServer() {
		if (this.service != null) {
			this.service.stop();
			System.out.println("Appium server stopped at " + Calendar.getInstance().getTime());
		}
	}

	public boolean isServerRunning() {
		return isServerRunning(this.appiumServerConfigs.getAppiumHubUrlString());
	}
	public static boolean isServerRunning(String hubUrl) {
		HttpURLConnection openConnection;
		try {
			final URL url = new URL(hubUrl + "/status");
			openConnection = (HttpURLConnection) url.openConnection();
			openConnection.setConnectTimeout(5000);
			openConnection.connect();
			final boolean serverRunningOk = openConnection.getResponseMessage().equalsIgnoreCase("ok");
			return serverRunningOk;
		} catch (final Exception ex) {
			//Do Nothing
		}
		return false;
	}

	private enum RelaxedSecurityFlag implements ServerArgument {
		RELAXED_SECURITY("--relaxed-security");

		private final String arg;

		RelaxedSecurityFlag(String arg) {
	        this.arg = arg;
	    }

	    @Override
	    public String getArgument() {
	        return this.arg;
	    }
	}
}
