package com.github.mrnerdy42.keywizard.util;

import com.github.mrnerdy42.keywizard.gui.GuiKeyWizard;
import com.github.mrnerdy42.keywizard.gui.GuiKeyboard;
import static org.lwjgl.input.Keyboard.*;

public class KeyboardFactory {
	public static GuiKeyboard makeKeyboard(KeyboardLayout layout, GuiKeyWizard parent, double x, double y, double width, double height) {
		switch(layout) {
		case QWERTY:
			return makeQwertyKeyboard(parent, x, y, width, height);
		case NUMPAD:
			return null;
		default:
			return null;
		}
	}
	
	private static GuiKeyboard makeQwertyKeyboard(GuiKeyWizard parent, double x, double y, double width, double height) {
		GuiKeyboard kb = new GuiKeyboard(parent, x, y);
		double currentX = 0;
		double currentY = 0;
		
		double keyWidth = width/12-5;
		double keyHeight = 15;
		double keySpacing = 5;
		
		currentX = addHorizontalRow(kb, KEY_F1, KEY_F10, 0, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX + keySpacing, currentY, keyWidth, keyHeight, KEY_F11);
		kb.addKey(currentX + keyWidth + (keySpacing*2), currentY, keyWidth, keyHeight, KEY_F12);
		
		keyWidth = width/19;
		currentY += keyHeight + 5;
		kb.addKey(0, currentY, keyWidth, keyHeight, KEY_GRAVE);
		currentX = keyWidth+keySpacing;
		currentX = addHorizontalRow(kb, KEY_1, KEY_EQUALS, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX + keySpacing, currentY, width - (currentX + keySpacing)-5, keyHeight, KEY_BACK);
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
		return startX + (width * (endCode-startCode + 1) + spacing * (endCode-startCode));
	}

}
