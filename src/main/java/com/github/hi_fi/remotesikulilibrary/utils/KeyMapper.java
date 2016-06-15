package com.github.hi_fi.remotesikulilibrary.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.sikuli.script.Key;

/**
 * Maps keys given as strings to Sikuli keys
 * @author hifi
 *
 */
public class KeyMapper {
	
	private static final Map<String, String> keyMap;
	static {
		Map<String, String> keys = new HashMap<String, String>();
		keys.put("CTRL", Key.CTRL);
		keys.put("ALT", Key.ALT);
		keys.put("TAB", Key.TAB);
		keys.put("SHIFT", Key.SHIFT);
		keys.put("HOME", Key.HOME);
		keyMap = Collections.unmodifiableMap(keys);
	}
	
	/**
	 * 
	 * @param key Name of special key to check
	 * @return either special key or key itself if no special key found.
	 */
	public static String getKey(String key) {
		if (keyMap.containsKey(key.toUpperCase())) {
			return keyMap.get(key.toUpperCase());
		} else {
			return key;
		}
	}

}
