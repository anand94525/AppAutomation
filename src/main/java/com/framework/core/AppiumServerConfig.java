package com.framework.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import com.google.common.io.Resources;

public class AppiumServerConfig {

	private final Properties props = new Properties();
	private static final String APPIUM_HUB_URL = "/wd/hub";
	private static final String CAPABILITY_APPIUM_PREFIX = "appium.";
	private static final String APPIUM_HOST = CAPABILITY_APPIUM_PREFIX + "host";
	private static final String APPIUM_PORT = CAPABILITY_APPIUM_PREFIX + "port";
	private static final String CAPABILITY_PROP_PREFIX = CAPABILITY_APPIUM_PREFIX + "server.";
	private static final String APPIUM_SERVER_NODE_PATH = CAPABILITY_PROP_PREFIX + "node.path";
	private static final String APPIUM_SERVER_NODE_JSFILE = CAPABILITY_PROP_PREFIX + "node.jsfile";
	private static final String APPIUM_SERVER_LOG = CAPABILITY_PROP_PREFIX + "log";
	public static final String APPIUM_SERVER_LOG_LEVEL = CAPABILITY_PROP_PREFIX + "log_level";
	private static final String APPIUM_PORT_KEY = "APPIUM_PORT";

	private AppiumServerConfig() {
	}
	public static AppiumServerConfig get(String configFile) {
		final AppiumServerConfig serverConfig = new AppiumServerConfig();
		try {
			serverConfig.props.load(Resources.getResource(configFile).openStream());
			final Properties sysProps = System.getProperties();
			final Set<Object> keys = sysProps.keySet();
			for (final Object key : keys) {
				if (key.toString().startsWith(CAPABILITY_APPIUM_PREFIX)) {
					serverConfig.props.put(key.toString(), sysProps.get(key));
				}
			}
		} catch (final IOException e) {
			throw new RuntimeException("Failed to load Appium server capabilities file", e);
		}
		return serverConfig;
	}
	public String getAppiumServerNodePath() {
		return this.props.getProperty(APPIUM_SERVER_NODE_PATH);
	}
	public String getAppiumServerLogLevel() {
		return this.props.getProperty(APPIUM_SERVER_LOG_LEVEL);
	}
	public String getAppiumServerLogFilePath() {
		return this.props.getProperty(APPIUM_SERVER_LOG);
	}
	public String getAppiumServerNodeJsFilePath() {
		return this.props.getProperty(APPIUM_SERVER_NODE_JSFILE);
	}
	public String getAppiumHubUrlString() {
		return this.getAppiumHost() + ":" + this.getPort() + APPIUM_HUB_URL;
	}
	public URL getAppiumHubURL() {
		URL appiumUrl = null;
		try {
			appiumUrl = new URL(this.getAppiumHubUrlString());
		} catch (final MalformedURLException e) {
			System.out.println("Invalid appium url: " + this.getAppiumHubUrlString());
		}
		return appiumUrl;
	}
	public String getAppiumHost() {
		return this.props.getProperty(APPIUM_HOST);
	}
	public String getPort() {
		String str = Optional.ofNullable(System.getenv(APPIUM_PORT_KEY)).orElse(this.props.getProperty(APPIUM_PORT));
		//String str = "4727";
		//System.out.println("Running appium on port: " + str);
		return str;
	}
	@Override
	public String toString() {
		return this.props.toString();
	}
}
