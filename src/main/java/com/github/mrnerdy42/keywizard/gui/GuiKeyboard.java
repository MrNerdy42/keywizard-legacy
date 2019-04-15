package com.github.mrnerdy42.keywizard.gui;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.mrnerdy42.keywizard.util.KeyboardLayout;

import net.minecraft.client.Minecraft;

public class GuiKeyboard extends FloatGui{
	
	public double x;
	public double y;
	public double scaleFactor;
	public GuiKeyWizard parent;
	
	protected GuiKeyRow[] rows;
	protected KeyboardLayout layout;

	public GuiKeyboard(GuiKeyWizard parent, KeyboardLayout layout, double x, double y, double scaleFactor) {
		this.parent = parent;
		this.layout = layout;
		this.x = x;
		this.y = y;
		this.scaleFactor = scaleFactor;
		this.rows = new GuiKeyRow[layout.numRows()];
	}
	
	public void init() {
		for(int r=0;r<this.layout.numRows(); r++) {
			this.rows[r] = new GuiKeyRow(this, r*);
			for(int k=0;k<this.layout.rowLength(r);k++) {
				this.
			}
		}
	}
	
	public void draw(Minecraft mc, int mouseX, int mouseY) {
	}
	
	public void addKey(GuiKeyboardKey k) {
	}
	
	private class GuiKeyRow{
		private ArrayList<GuiKeyboardKey> keys = new ArrayList<GuiKeyboardKey>();
		
		private GuiKeyboard keyboard;
		
		private int width=0;
		private int heigh=0;
		
		private int xOffset = 0;
		private int yOffset;
		
		private int xSpacing;
		
		private GuiKeyRow(GuiKeyboard keyboard, int yOffset, int xSpacing) {
			this.keyboard = keyboard;
			this.yOffset = yOffset;
			this.xSpacing=xSpacing;
	
		}
		
		private void addKey(int[] keyIn) {
			this.keys.add(new GuiKeyboardKey(this.keyboard, this.keyboard.x + this.xOffset, this.keyboard.y + this.yOffset, keyIn[0], keyIn[1], keyIn[3]));
		}
		
	}

}
