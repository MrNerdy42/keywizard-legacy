package com.github.mrnerdy42.keywizard.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class GuiKeyboard extends FloatGui{
	
	public int x;
	public int y;
	public int scaleFactor;
	
	private GuiKeyRow[] rows;

	public GuiKeyboard(int x, int y, int scaleFactor) {
		this.x = x;
		this.y = y;
		this.scaleFactor = scaleFactor;
	}
	
	public void draw() {
		
	}
	
	private class GuiKeyRow{
		private GuiKeyRow(int start, int end, int width, int height, int spacing) {
			
		}
		private GuiKeyRow(int[] keys, int[] widths, int[] spacings ) {
			
		}
	}
	
	private class GuiKeyboardKey extends FloatGui{
		
	}
}
