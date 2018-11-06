package com.framework.lib.enums;

public enum Platform {
	IOS(""), ANDROID("android_caps.properties");
	String capsFile;
	Platform(String capsFile) {
		this.capsFile = capsFile;
	}

	public String getCapabilityFile() {
		return this.capsFile;
	}
}
