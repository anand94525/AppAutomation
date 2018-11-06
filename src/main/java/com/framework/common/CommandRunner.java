package com.framework.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandRunner {
	public static List<String> exec(String command) {
		List<String> output = new ArrayList<String>();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        try {
			while ((line = reader.readLine()) != null) {
				output.add(line);
			}
		} catch (IOException e) {
		}
        
        return output;
    }

}
