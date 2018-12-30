package com.github.mrnerdy42.keywizard.handlers;

import org.lwjgl.input.Keyboard;

import com.github.mrnerdy42.keywizard.KeyWizard;
import com.github.mrnerdy42.keywizard.gui.GuiControlsPlusKeyWiz;
import com.github.mrnerdy42.keywizard.gui.GuiKeyWizard;

import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

	private Minecraft client  = FMLClientHandler.instance().getClient();
	
    @SubscribeEvent
    public void guiInit(GuiOpenEvent e) {
        if (e.getGui() instanceof GuiControls && !(e.getGui() instanceof GuiControlsPlusKeyWiz)) {
            e.setGui(new GuiControlsPlusKeyWiz(e.getGui(), this.client.gameSettings));
            System.out.println("Overriding");
        }
    }
}
