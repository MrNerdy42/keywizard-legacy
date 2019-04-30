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
	
	private double yOffset = 0;

	public GuiKeyboard(GuiKeyWizard parent, double x, double y, double scaleFactor) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.scaleFactor = scaleFactor;
	}
	
	
	public void draw(Minecraft mc, int mouseX, int mouseY) {
	}
	
	public void addKey(GuiKeyboardKey k) {
	}
}
