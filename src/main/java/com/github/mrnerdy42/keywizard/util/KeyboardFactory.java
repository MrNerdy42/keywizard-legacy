package com.github.mrnerdy42.keywizard.util;

import com.github.mrnerdy42.keywizard.gui.GuiKeyWizard;
import com.github.mrnerdy42.keywizard.gui.GuiKeyboard;
import static org.lwjgl.input.Keyboard.*;

public class KeyboardFactory {
	public static GuiKeyboard makeKeyboard(KeyboardLayout layout, GuiKeyWizard parent, double x, double y) {
		switch(layout) {
		case QWERTY:
			return makeQwertyKeyboard(parent, x, y);
		case NUMPAD:
			return null;
		default:
			return null;
		}
	}
	
	private static GuiKeyboard makeQwertyKeyboard(GuiKeyWizard parent, double x, double y) {
		GuiKeyboard kb = new GuiKeyboard(parent, x, y);
		kb.addKey(0, 0, 10, 10, KEY_A);
		kb.addKey(15, 0, 10, 10, KEY_B);
		return kb;
	}

}
