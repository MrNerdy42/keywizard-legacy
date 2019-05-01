package com.github.mrnerdy42.keywizard.gui;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.mrnerdy42.keywizard.util.KeyHelper;
import com.github.mrnerdy42.keywizard.util.KeyboardLayout;

import net.minecraft.client.Minecraft;

public class GuiKeyboard extends FloatGui{

	public double x;
	public double y;
	public double scaleFactor;
	public GuiKeyWizard parent;
	
	protected ArrayList<GuiKeyboardKey> keyList;

	public GuiKeyboard(GuiKeyWizard parent, double x, double y, double scaleFactor) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.scaleFactor = scaleFactor;
	}


	public void draw(Minecraft mc, int mouseX, int mouseY) {
		for(GuiKeyboardKey k:this.keyList) {
			k.drawKey(mc, mouseX, mouseY);
		}
	}

	public void addKey(double xIn, double yIn, double width, double height, int keyCode) {
		this.keyList.add(new GuiKeyboardKey(this, this.x + xIn, this.y + yIn, width, height, keyCode));
	}

	private class GuiKeyboardKey extends FloatGui{
		public GuiKeyboard keyboard;
		public double x;
		public double y;
		public double width;
		public double height;

		public int keyCode;
		public String displayString;

		protected boolean hovered;

		public GuiKeyboardKey(GuiKeyboard keyboard, double x, double y, double width, double height, int keyCode) {
			this.keyboard = keyboard;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.keyCode = keyCode;
			this.displayString = KeyHelper.translateKey(this.keyCode);
		}

		public void drawKey(Minecraft mc, double mouseX, double mouseY) {
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			if(this.hovered) {
				drawNoFillRect(this.x, this.y, this.x + this.width, this.y + this.height, 0xFF00FF00);
			}else {
				drawNoFillRect(this.x, this.y, this.x + this.width, this.y + this.height, 0xFFFFFFFF);
			}
			this.drawCenteredString(this.keyboard.parent.getFontRenderer(), this.displayString, (float)(this.x+(this.width+2)/2.0F), (float)(this.y+(this.height-7)/2.0F), 0xFFFFFF);
		}

		protected void drawNoFillRect(double left, double top, double right, double bottom, int color) {
			drawHorizontalLine(left, right, top, color);
			drawHorizontalLine(left, right, bottom, color);
			drawVerticalLine(left, top, bottom, color);
			drawVerticalLine(right, top, bottom, color);
		}
	}
	
}
