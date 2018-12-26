package com.github.mrnerdy42.keywizard.handlers;

import net.minecraft.client.gui.GuiControls;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

	    @SubscribeEvent
	    public void guiInit(GuiOpenEvent e) {
	        if (e.getGui() instanceof GuiControls && !(e.getGui() instanceof GuiControlsPlusKeyWiz)) {
	            //e.setGui();
	        }
	    }



	}