package com.github.mrnerdy42.keywizard.util;

import static org.lwjgl.input.Keyboard.KEY_APOSTROPHE;
import static org.lwjgl.input.Keyboard.KEY_BACKSLASH;
import static org.lwjgl.input.Keyboard.KEY_COMMA;
import static org.lwjgl.input.Keyboard.KEY_EQUALS;
import static org.lwjgl.input.Keyboard.KEY_F1;
import static org.lwjgl.input.Keyboard.KEY_F10;
import static org.lwjgl.input.Keyboard.KEY_F11;
import static org.lwjgl.input.Keyboard.KEY_F12;
import static org.lwjgl.input.Keyboard.KEY_F2;
import static org.lwjgl.input.Keyboard.KEY_F3;
import static org.lwjgl.input.Keyboard.KEY_F4;
import static org.lwjgl.input.Keyboard.KEY_F5;
import static org.lwjgl.input.Keyboard.KEY_F6;
import static org.lwjgl.input.Keyboard.KEY_F8;
import static org.lwjgl.input.Keyboard.KEY_F9;
import static org.lwjgl.input.Keyboard.KEY_GRAVE;
import static org.lwjgl.input.Keyboard.KEY_LBRACKET;
import static org.lwjgl.input.Keyboard.KEY_LCONTROL;
import static org.lwjgl.input.Keyboard.KEY_MINUS;
import static org.lwjgl.input.Keyboard.KEY_PERIOD;
import static org.lwjgl.input.Keyboard.KEY_RBRACKET;
import static org.lwjgl.input.Keyboard.KEY_RCONTROL;
import static org.lwjgl.input.Keyboard.KEY_SEMICOLON;
import static org.lwjgl.input.Keyboard.KEY_SLASH;
import static org.lwjgl.input.Keyboard.getKeyName;

import java.util.HashMap;

public class KeyHelper {
	
	private static final HashMap<Integer, String> FUNCTION_KEYS = new HashMap();
	static{
		FUNCTION_KEYS.put(KEY_F1, "F1");
		FUNCTION_KEYS.put(KEY_F2, "F2");
		FUNCTION_KEYS.put(KEY_F3, "F3");
		FUNCTION_KEYS.put(KEY_F4, "F4");
		FUNCTION_KEYS.put(KEY_F5, "F5");
		FUNCTION_KEYS.put(KEY_F6, "F6");
		FUNCTION_KEYS.put(KEY_F6, "F7");
		FUNCTION_KEYS.put(KEY_F8, "F8");
		FUNCTION_KEYS.put(KEY_F9, "F9");
		FUNCTION_KEYS.put(KEY_F10, "F10");
		FUNCTION_KEYS.put(KEY_F11, "F11");
		FUNCTION_KEYS.put(KEY_F12, "F12");
	}
	
	/**
	 * Translate the LWJGL key name to a symbol or shortened version of the name
	 * 
	 * @param id
	 *            the LWJGL code of the key to translate
	 */
	public static String translateKey(int id) {
		String keyName;
		if (FUNCTION_KEYS.containsKey(id)) {
			return FUNCTION_KEYS.get(id);
		}
		switch (id) {
		case KEY_MINUS:
			keyName = "-";
			break;
		case KEY_EQUALS:
			keyName = "=";
			break;
		case KEY_LBRACKET:
			keyName = "[";
			break;
		case KEY_RBRACKET:
			keyName = "]";
			break;
		case KEY_SEMICOLON:
			keyName = ";";
			break;
		case KEY_COMMA:
			keyName = ",";
			break;
		case KEY_PERIOD:
			keyName = ".";
			break;
		case KEY_APOSTROPHE:
			keyName = "'";
			break;
		case KEY_SLASH:
			keyName = "/";
			break;
		case KEY_BACKSLASH:
			keyName = "\\";
			break;
		case KEY_GRAVE:
			keyName = "`";
			break;
		case KEY_LCONTROL:
			keyName = "LCTRL";
			break;
		case KEY_RCONTROL:
			keyName = "RCTRL";
			break;
		default:
			keyName = getKeyName(id);
			break;
		}
		return keyName;
	}
}
