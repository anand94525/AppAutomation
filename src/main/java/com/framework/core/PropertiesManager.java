package com.framework.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.io.Resources;

public class PropertiesManager {

	public static final String CAPABILITY_PROP_PREFIX = "capability.";

	private static Properties props;
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesManager.class);

	private static Properties getProperties(String propFile) {
		if ((props == null) || props.isEmpty()) {
			try {
				props = new Properties();
				props.load(Resources.getResource(propFile).openStream());
			} catch (final IOException e) {
				LOGGER.error("Failed to read appium properties, skippping application launch");
				throw new RuntimeException("Failed to read appium properties", e);
			}
		}
		return props;
	}

	public static Map<String, String> getCapability(String capsFileName) {
		if (capsFileName == null) {
			return null;
		}
		final Map<String, String> map = new HashMap<String, String>();
		Properties props = getProperties(capsFileName);
		for (final String name : props.stringPropertyNames()) {
			if (name.startsWith(CAPABILITY_PROP_PREFIX)) {
				map.put(name.substring(CAPABILITY_PROP_PREFIX.length()), props.getProperty(name));
			}
		}
		
		// Replace with system properties
				props = System.getProperties();
				for (final String name : props.stringPropertyNames()) {
					if (name.startsWith(CAPABILITY_PROP_PREFIX)) {
						map.put(name.substring(CAPABILITY_PROP_PREFIX.length()), props.getProperty(name));
					}
				}
		return map;
	}
}
