package com.github.mrnerdy42.keywizard.handlers;

import org.lwjgl.input.Keyboard;

import com.github.mrnerdy42.keywizard.KeyWizard;
import com.github.mrnerdy42.keywizard.gui.GuiKeyWizard;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;

@SideOnly(Side.CLIENT)
public class BindingHandler {

	private Minecraft client  = FMLClientHandler.instance().getClient();
	private KeyBinding[] keyBinds = this.client.gameSettings.keyBindings;
	public static KeyBinding OpenKeyGui;
	
	public static void register(){
		OpenKeyGui = new KeyBinding("keywizard.keybind.openKeyboardWizard", Keyboard.KEY_F7, "key.categories.misc");
		ClientRegistry.registerKeyBinding(OpenKeyGui);

	}
    
	@SubscribeEvent
	public void OnOpenKeyGuiPress(KeyInputEvent e){
		if (OpenKeyGui.isPressed()){
			client.displayGuiScreen(new GuiKeyWizard());
		}
	}
}
