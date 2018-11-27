package com.framework.core.android;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AndroidDeviceManager {
	
	private static final Map<String, Boolean> DEVICES = new ConcurrentHashMap<>();
	
	static {
		List<String> deviceIds = AndroidADBUtil.getDeviceList();
		deviceIds.forEach(i -> {
			DEVICES.put(i, false);
		});
	}

	private AndroidDeviceManager() {
	}
	
	public static String getAFreeDeviceId() {
		List<String> freeDevices = DEVICES.entrySet().stream().filter(map -> map.getValue().equals(false)).map(map -> map.getKey()).collect(Collectors.toList());
		return freeDevices.get(0);
	}
	
	public static void main(String[] args) {
		getAFreeDeviceId();
	}
	
}
