package com.angelcalvo.superpalitos;

import java.util.HashMap;
import java.util.Map;

public class ConfManager {
	public static final String SOUND_OPT = "sound";
	public static final String LANG_OPT = "language";
	public static final String P1_COLOR = "player1.color";
	public static final String P2_COLOR = "player2.color";
	public static final String GAME_MODE = "game.mode";
	
	private Map<String, String> options;
	
	public ConfManager() {
		options = new HashMap<String, String>();
	}
	
	public void save(String option, String value) {
		options.put(option, value);
	}
	public void save(String option, boolean value) {
		save(option, Boolean.toString(value));
	}
	
	public String get(String option) {
		return options.get(option);
	}
	
	public boolean getBoolean(String option) {
		if(!options.containsKey(option)) {
			return false;
		}
		return options.get(option).equalsIgnoreCase("true");
	}
}
