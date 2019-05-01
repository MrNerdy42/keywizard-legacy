package com.github.mrnerdy42.keywizard.util;

import com.github.mrnerdy42.keywizard.gui.GuiKeyWizard;
import com.github.mrnerdy42.keywizard.gui.GuiKeyboard;
import static org.lwjgl.input.Keyboard.*;

public class KeyboardFactory {
	public static GuiKeyboard makeKeyboard(KeyboardLayout layout, GuiKeyWizard parent, double x, double y, double scaleFactor) {
		switch(layout) {
		case QWERTY:
			return makeQwertyKeyboard(parent, x, y, scaleFactor);
		case NUMPAD:
			return null;
		default:
			return null;
		}
	}
	
	private static GuiKeyboard makeQwertyKeyboard(GuiKeyWizard parent, double x, double y, double scaleFactor) {
		GuiKeyboard kb = new GuiKeyboard(parent, x, y, scaleFactor);
		kb.addKey(0, 0, 20, 20, KEY_A);
		return kb;
	}

}
