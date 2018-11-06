package com.framework.core.android;

import java.util.List;

import com.framework.common.CommandRunner;

public class AndroidADBUtil {
	
	private static final String CMD_APP_VERSION_NAME = "adb shell dumpsys package com.Slack | grep versionName";
	private static final String GET_APK_PATH = "adb shell pm path com.Slack";
	private static final String PULL_APK_TO_MACHINE = "adb pull %s %s";
	
	public static String getAppVersionName() {
		String appVersion = "";
		List<String> appVersionList = CommandRunner.exec(CMD_APP_VERSION_NAME);
		if(appVersionList.size() > 0) {
			appVersion = CommandRunner.exec(CMD_APP_VERSION_NAME).get(0).replace("versionName=", "").trim();
		}
		return appVersion;
	}
	
	public static void getApkFileToMachine(String whereToPull) {
		String apkPath = CommandRunner.exec(GET_APK_PATH).get(0).replace("package:", "");
		CommandRunner.exec(String.format(PULL_APK_TO_MACHINE, apkPath, whereToPull));
	}
	
}
