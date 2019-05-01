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
		double currentX = 0;
		double currentY = 0;
		currentX = addHorizontalRow(kb, KEY_F1, KEY_F10, currentX, currentY, 20, 15, 5);
		kb.addKey(currentX + 5, currentY, 20, 15, KEY_F11);
		kb.addKey(currentX + 30, currentY, 20, 15, KEY_F12);
		return kb;
	}
	
	/**
	 * Adds a uniformly spaced row to the keyboard it is passed.
	 * @param kb
	 * @param startCode
	 * @param endCode
	 * @param startX
	 * @param y
	 * @param width
	 * @param height
	 * @param spacing
	 * @return x position of left edge of the last key added
	 */
	private static double addHorizontalRow(GuiKeyboard kb, int startCode, int endCode, double startX, double y, double width, double height, double spacing) {
		double currentX = startX;
		for(int i=startCode; i<=endCode; i++) {
			kb.addKey(currentX, y, width, height, i);
			currentX += width + spacing;
		}
		return width * (endCode-startCode + 1) + spacing * (endCode-startCode);
	}

}
