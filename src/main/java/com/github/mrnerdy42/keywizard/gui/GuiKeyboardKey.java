package com.github.mrnerdy42.keywizard.gui;

import net.minecraft.client.Minecraft;

public class GuiKeyboardKey extends FloatGui{
	public double x;
	public double y;
	public double width;
	public double height;
	
	protected boolean hovered;
	
	public GuiKeyboardKey(double x, double y, double width, double height, int keyCode) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void drawKey(Minecraft mc, double mouseX, double mouseY ) {
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		drawRect(this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, 0);
		if(this.hovered) {
			drawNoFillRect(this.x, this.y, this.x + this.width, this.y + this.height, 0xFF00FF00);
		}else {
			drawNoFillRect(this.x, this.y, this.x + this.width, this.y + this.height, 0xFFFFFFFF);
		}
	}
	
	protected void drawNoFillRect(double left, double top, double right, double bottom, int color) {
		drawHorizontalLine(left, right, top, color);
		drawHorizontalLine(left, right, bottom, color);
		drawVerticalLine(left, top, bottom, color);
		drawVerticalLine(right, top, bottom, color);
	}
}