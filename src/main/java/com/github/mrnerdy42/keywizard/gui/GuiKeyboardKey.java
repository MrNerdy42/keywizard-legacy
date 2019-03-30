package com.github.mrnerdy42.keywizard.gui;


public class GuiKeyboardKey extends FloatGui{
	public double x;
	public double y;
	public double width;
	public double height;
	
	public GuiKeyboardKey(double x, double y, double width, double height, int keyCode) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw() {
		drawRect(this.x, y, this.x + this.width, this.y + this.height, 0xFFFFFFFF);
	}
}