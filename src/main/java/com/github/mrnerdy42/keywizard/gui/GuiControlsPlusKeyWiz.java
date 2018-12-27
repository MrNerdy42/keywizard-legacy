package com.github.mrnerdy42.keywizard.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.resources.I18n;

public class GuiControlsPlusKeyWiz extends GuiControls{
	
	private GuiButton keywizardButton;

	public GuiControlsPlusKeyWiz(GuiScreen screen, GameSettings settings) {
		super(screen, settings);
	}
	
//	@Override
//	public void initGui() {
//		super.initGui();
//		
//		this.keywizardButton = new GuiButton(0, this.height/2, this.width/2, 100, 20, I18n.format("options.OpenKeyWizard"));
//		this.buttonList.add(this.keywizardButton);
//		System.out.println("Hello World!");
//	}
}