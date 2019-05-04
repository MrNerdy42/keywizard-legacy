package com.github.mrnerdy42.keywizard.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.github.mrnerdy42.keywizard.util.KeyHelper;
import com.github.mrnerdy42.keywizard.util.KeybindUtils;
import com.github.mrnerdy42.keywizard.util.KeyboardLayout;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.SoundEvents;

public class GuiKeyboard extends FloatGui{

	public double anchorX;
	public double anchorY;
	public GuiKeyWizard parent;
	
	protected HashMap<Integer, GuiKeyboardKey> keyList = new HashMap<>();
	
	private double scaleFactor;

	public GuiKeyboard(GuiKeyWizard parent, double anchorX, double anchorY) {
		this.parent = parent;
		this.anchorX = anchorX;
		this.anchorY = anchorY;
	}


	public void draw(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		for(GuiKeyboardKey k:this.keyList.values()) {
			k.drawKey(mc, mouseX, mouseY, partialTicks);
		}
	}
	
	public void mouseClicked(Minecraft mc, int mouseX, int mouseY, int button) {
		for(GuiKeyboardKey k:this.keyList.values()) {
			k.mouseClicked(mc, mouseX, mouseY, button);
		}
	}

	public void addKey(double xIn, double yIn, double width, double height, int keyCode) {
		this.keyList.put(new Integer(keyCode), new GuiKeyboardKey(this, xIn, yIn, width, height, keyCode));
	}
	
	public void disableKey(int keyCode) {
		this.keyList.get(keyCode).enabled = false;
	}
	
	public void enableKey(int keyCode) {
		this.keyList.get(keyCode).enabled = true;
	}
	
	/**
	 * Returns the width of the keyboard. Currently unused.
	 * @return the width of the keyboard
	 */
	public double width() {
		double width = 0;
		for(GuiKeyboardKey k:this.keyList.values()) {
			if (k.absX() + k.width > width) {
				width = k.absX() + k.width;
			}
		}
		return width;
	}
	
	public double getScaleFactor () {
		return this.scaleFactor;
	}
	
	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
		for(GuiKeyboardKey k:this.keyList.values()) {
			k.width = k.width*scaleFactor;
			k.height = k.height*scaleFactor;
			k.x = k.x*scaleFactor;
			k.y = k.y*scaleFactor;
		}
	}

	private class GuiKeyboardKey extends FloatGui{
		public GuiKeyboard keyboard;
		public double x;
		public double y;
		public double width;
		public double height;
		public boolean enabled = true;

		public int keyCode;
		public String displayString;

		protected boolean hovered;
		protected int numBindings;
		

		public GuiKeyboardKey(GuiKeyboard keyboard, double x, double y, double width, double height, int keyCode) {
			this.keyboard = keyboard;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.keyCode = keyCode;
			this.displayString = KeyHelper.translateKey(this.keyCode);
		}

		public void drawKey(Minecraft mc, double mouseX, double mouseY, float partialTicks) {
			this.hovered = mouseX >= this.absX() && mouseY >= this.absY() && mouseX < this.absX() + this.width && mouseY < this.absY() + this.height;
			this.numBindings = KeybindUtils.getNumBindings(this.keyCode, parent.getActiveModifier());
			int color = 0;
			if (this.enabled) {
				if (this.hovered) {
					color = 0xFFAAAAAA;
					if(this.numBindings == 1) {
						color = 0xFF00AA00;
					} else if (this.numBindings > 1) {
						color = 0xFFAA0000;
					}
				}else {
					color = 0xFFFFFFFF;
					if(this.numBindings == 1) {
						color = 0xFF00FF00;
					} else if (this.numBindings > 1) {
						color = 0xFFFF0000;
					}
				}
			} else {
				color = 0xFF555555;
			}
			
			drawNoFillRect(this.absX(), this.absY(), this.absX() + this.width, this.absY() + this.height, color);
			this.drawCenteredString(this.keyboard.parent.getFontRenderer(), this.displayString, (float)(this.absX()+(this.width+2)/2.0F), (float)(this.absY()+(this.height-6)/2.0F), color & 0x00FFFFFF);
		}

		protected void drawNoFillRect(double left, double top, double right, double bottom, int color) {
			drawHorizontalLine(left, right, top, color);
			drawHorizontalLine(left, right, bottom, color);
			drawVerticalLine(left, top, bottom, color);
			drawVerticalLine(right, top, bottom, color);
		}
		
		public void mouseClicked(Minecraft mc, int mouseX, int mouseY, int button) {
			if(mouseX >= this.absX() && mouseX < this.absX() + this.width && mouseY >= this.absY() && mouseY < this.absY() + this.height && button == 0 && this.enabled) {
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				parent.getSelectedKeybind().setKeyModifierAndCode(parent.getActiveModifier(), this.keyCode);
				mc.gameSettings.setOptionKeyBinding(parent.getSelectedKeybind(), this.keyCode);
				KeyBinding.resetKeyBindingArrayAndHash();
				
			}
		}
		
		public double absX() {
			return this.keyboard.anchorX + this.x;
		}
		public double absY() {
			return this.keyboard.anchorY + this.y;
		}
	}
	
}
