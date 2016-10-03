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
		keys.put("WIN", Key.WIN);
		keys.put("META", Key.META);
		keys.put("CMD", Key.CMD);
		keys.put("ALTGR", Key.ALTGR);
		keys.put("INSERT", Key.INSERT);
		keys.put("DELETE", Key.DELETE);
		keys.put("BACKSPACE", Key.BACKSPACE);
		keys.put("ENTER", Key.ENTER);
		keys.put("ESC", Key.ESC);
		keys.put("END", Key.END);
		keys.put("LEFT", Key.LEFT);
		keys.put("RIGHT", Key.RIGHT);
		keys.put("DOWN", Key.DOWN);
		keys.put("UP", Key.UP);
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
