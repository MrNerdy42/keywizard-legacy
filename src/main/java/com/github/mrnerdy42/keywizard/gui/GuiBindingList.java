package com.github.mrnerdy42.keywizard.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.github.mrnerdy42.keywizard.util.KeybindUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.GuiScrollingList;

public class GuiBindingList extends GuiScrollingList {
	
	public boolean filtered = false;
	
	private GuiKeyWizard parent;
	private KeyBinding[] bindings;
	private KeyBinding selectedKeybind;
	private int selectedKeybindId;

	public GuiBindingList(GuiKeyWizard parent, int left, int bottom, int width, int height, int entryHeight) {
		     //Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight, int screenWidth, int screenHeight
		super(parent.getClient(), width, height, bottom - height, bottom, left, entryHeight, parent.width, parent.height);
		
		this.parent = parent;
		this.bindings = KeybindUtils.ALL_BINDINGS;
		this.selectKeybind(0);
	}

	@Override
	protected int getSize() {
		return bindings.length;
	}

	@Override
	protected void elementClicked(int index, boolean doubleClick) {
		this.selectKeybind(index);
	}

	@Override
	protected boolean isSelected(int index) {
		if (this.selectedKeybindId == index) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void drawBackground() {

	}

	@Override
	protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotHeight, Tessellator tess) {
		FontRenderer fontRender = this.parent.getFontRenderer();
		KeyBinding currentBinding = this.bindings[slotIdx];
		
		fontRender.drawString(I18n.format(currentBinding.getKeyDescription()), this.left + 3 , slotTop, 0xFFFFFF);
		if ( currentBinding.getKeyCode() == 0 || KeybindUtils.getConficts(currentBinding) > 0) {
			fontRender.drawString(currentBinding.getDisplayName(), this.left + 3, slotTop + fontRender.FONT_HEIGHT + 2, 0x993333);
		} else if(!currentBinding.isSetToDefaultValue()){
			fontRender.drawString(currentBinding.getDisplayName(), this.left + 3, slotTop + fontRender.FONT_HEIGHT + 2, 0x339933);
		} else {
			fontRender.drawString(currentBinding.getDisplayName(), this.left + 3, slotTop + fontRender.FONT_HEIGHT + 2, 0x999999);
		}

	}
	
	protected void updateList(){
		
		String filterExp = "(?i).*" + this.parent.getSearchText() + ".*";
		
		if (this.parent.getSearchText().equals("")) {
			this.filtered = false;
		} else {
			this.filtered = true;
		}
		this.bindings = this.getBindings(this.parent.getSelectedCategory(), filterExp);
		
//		if (this.bindings.length != 0)
//			this.selectKeybind(0);
	}
	
	private void selectKeybind(int id){
		this.selectedKeybindId = id;
		this.selectedKeybind = this.bindings[id];
		this.parent.setSelectedKeybind(this.selectedKeybind);
	}
	
	private KeyBinding[] getBindings(String category, String filterExp) {
		KeyBinding[] bindings = KeybindUtils.ALL_BINDINGS;
		
		switch (category) {
		case "categories.all":
			break;
		case "categories.conflicts":
			bindings = Arrays.stream(bindings).filter(binding -> KeybindUtils.getConficts(binding) >= 1 && binding.getKeyCode() != 0).toArray(KeyBinding[]::new);
			break;
		case "categories.unbound":
			bindings = Arrays.stream(bindings).filter(binding -> binding.getKeyCode() == 0).toArray(KeyBinding[]::new);
			break;
		default:
			bindings = Arrays.stream(bindings).filter(binding -> binding.getKeyCategory() == category).toArray(KeyBinding[]::new);
			break;
		}
		bindings = Arrays.stream(bindings).filter(binding -> I18n.format(binding.getKeyDescription()).toLowerCase().matches(filterExp)).toArray(KeyBinding[]::new);
		return bindings;
	}
	
	public KeyBinding getSelectedKeybind(){
		return this.selectedKeybind;
	}

}
