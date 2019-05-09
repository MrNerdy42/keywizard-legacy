package com.github.mrnerdy42.keywizard.util;

import static org.lwjgl.input.Keyboard.*;

import com.github.mrnerdy42.keywizard.gui.GuiKeyWizard;
import com.github.mrnerdy42.keywizard.gui.GuiKeyboard;

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
		
		double keySpacing = 5;
		double keyWidth = width/12-keySpacing;
		double keyHeight = height;
	
		currentX = addHorizontalRow(kb, KEY_F1, KEY_F10, 0, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX + keySpacing, currentY, keyWidth, keyHeight, KEY_F11);
		kb.addKey(currentX + keyWidth + (keySpacing*2), currentY, keyWidth, keyHeight, KEY_F12);
		
		currentY += keyHeight + keySpacing;
		keyWidth = width/15-keySpacing;
		kb.addKey(0, currentY, keyWidth, keyHeight, KEY_GRAVE);
		currentX = keyWidth+keySpacing;
		currentX = addHorizontalRow(kb, KEY_1, KEY_EQUALS, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX + keySpacing, currentY, keyWidth*2+5, keyHeight, KEY_BACK);
		
		currentY += keyHeight + keySpacing;
		kb.addKey(0, currentY, keyWidth*2+keySpacing, keyHeight, KEY_TAB);
		currentX = keyWidth*2+keySpacing*2;
		currentX = addHorizontalRow(kb, KEY_Q, KEY_RBRACKET, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX + keySpacing, currentY, keyWidth, keyHeight, KEY_BACKSLASH);
		
		currentY += keyHeight + keySpacing;
		kb.addKey(0, currentY, keyWidth*2+keySpacing, keyHeight, KEY_CAPITAL);
		currentX = keyWidth*2+keySpacing*2;
		currentX = addHorizontalRow(kb, KEY_A, KEY_APOSTROPHE, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX + keySpacing, currentY, keyWidth*2+keySpacing, keyHeight, KEY_RETURN);
		
		currentY += keyHeight + keySpacing;
		kb.addKey(0, currentY, keyWidth*2+keySpacing, keyHeight, KEY_LSHIFT);
		currentX = keyWidth*2+keySpacing*2;
		currentX = addHorizontalRow(kb, KEY_Z, KEY_SLASH, currentX, currentY, keyWidth, keyHeight, keySpacing);
		kb.addKey(currentX + keySpacing, currentY, keyWidth*3+keySpacing*2, keyHeight, KEY_RSHIFT);
		
		currentY += keyHeight + keySpacing;
		keyWidth = width/7-keySpacing;
		currentX = addHorizontalRow(kb, new int[] {KEY_LCONTROL,KEY_LMETA,KEY_LMENU,KEY_SPACE,KEY_RMENU,KEY_RMETA,KEY_RCONTROL}, 0, currentY, keyWidth, keyHeight, keySpacing);
		
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
	
	private static double addHorizontalRow(GuiKeyboard kb, int[] keys, double startX, double y, double width, double height, double spacing) {
		double currentX = startX;
		for(int k:keys) {
			kb.addKey(currentX, y, width, height, k);
			currentX += width + spacing;
		}
		return startX + (width * (keys.length) + spacing * (keys.length));
	}

}
