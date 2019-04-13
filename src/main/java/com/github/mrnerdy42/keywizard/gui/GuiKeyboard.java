package com.github.mrnerdy42.keywizard.gui;

import java.util.ArrayList;

import com.github.mrnerdy42.keywizard.util.KeyboardLayout;

import net.minecraft.client.Minecraft;

public class GuiKeyboard extends FloatGui{
	
	public double x;
	public double y;
	public double scaleFactor;
	public GuiKeyWizard parent;
	
	protected ArrayList<GuiKeyboardKey> keyList = new ArrayList<>();
	protected GuiKeyRow[] rows;
	protected KeyboardLayout layout;

	public GuiKeyboard(GuiKeyWizard parent, KeyboardLayout layout, double x, double y, double scaleFactor) {
		this.parent = parent;
		this.layout = layout;
		this.x = x;
		this.y = y;
		this.scaleFactor = scaleFactor;
	}
	
	public void init() {
		double keyX = this.x;
		double keyY = this.y;
		this.keyList.add(new GuiKeyboardKey(this, this.x, this.y, 15.0, 15.0, 30));
	}
	
	public void draw(Minecraft mc, int mouseX, int mouseY) {
		for(GuiKeyboardKey k:this.keyList) {
			k.drawKey(mc, mouseX, mouseY);
		}
	}
	
	public void addKey(GuiKeyboardKey k) {
		this.keyList.add(k);
	}
	
	private class GuiKeyRow{
		private ArrayList<GuiKeyboardKey> keys = new ArrayList<GuiKeyboardKey>();
		
		private GuiKeyboard keyboard;
		private int xOffset;
		private int yOffset;
		
		private int xSpacing;
		
		private GuiKeyRow(GuiKeyboard keyboard, int yOffset, int xSpacing) {
			this.keyboard = keyboard;
			this.xOffset = 0;
			this.yOffset = yOffset;
			this.xSpacing=xSpacing;
		}
		
		private void addKey(int[] keyIn) {
			this.keys.add(new GuiKeyboardKey(this.keyboard, this.keyboard.x + this.xOffset, this.keyboard.y + this.yOffset, keyIn[0], keyIn[1], keyIn[3]));
		}
		
	}

}
