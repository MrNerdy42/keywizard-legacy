package com.github.mrnerdy42.keywizard.handlers;

import net.minecraftforge.fml.common.network.IGuiHandler;

import com.github.mrnerdy42.keywizard.gui.GuiKeyWizard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
	
	public static final int KEY_GUI_ID = 0;

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == KEY_GUI_ID)
		    return new GuiKeyWizard();
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	    return null;
	}

}
