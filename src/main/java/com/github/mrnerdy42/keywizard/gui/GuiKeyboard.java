package com.github.mrnerdy42.keywizard.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public class GuiKeyboard extends FloatGui{
	
	public double x;
	public double y;
	public double scaleFactor;
	public GuiKeyWizard parent;
	
	protected ArrayList<GuiKeyboardKey> keyList = new ArrayList<>();
	protected GuiKeyRow[] rowList;

	public GuiKeyboard(GuiKeyWizard parent, double x, double y, double scaleFactor) {
		this.x = x;
		this.y = y;
		this.scaleFactor = scaleFactor;
		this.parent = parent;
		

	}
	
	public void init() {
		this.keyList.add(new GuiKeyboardKey(this.x, this.y, 20.0, 20.0, 30));
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
		private GuiKeyRow(int start, int end, int width, int height, int spacing) {
			
		}
		private GuiKeyRow(int[] keys, int[] widths, int[] spacings ) {
			
		}
	}

}
