package com.github.mrnerdy42.keywizard.handlers;

import org.lwjgl.input.Keyboard;

import com.github.mrnerdy42.keywizard.KeyWizard;
import com.github.mrnerdy42.keywizard.KeyWizardConfig;
import com.github.mrnerdy42.keywizard.gui.GuiKeyWizard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {
	
	public static KeyBinding keyOpenKeyWizard = new KeyBinding(I18n.format(KeyWizard.MODID + ".keybind.openKeyboardWizard"), Keyboard.KEY_F7, "key.categories.misc");

	private Minecraft client  = FMLClientHandler.instance().getClient();
	
	public static void register() {
		ClientRegistry.registerKeyBinding(keyOpenKeyWizard);
	}
	
    @SuppressWarnings("unchecked")
	@SubscribeEvent
    public void guiInit(InitGuiEvent e) {
    	if (e.getGui() instanceof GuiControls && KeyWizardConfig.openFromControlsGui) {
    		int width = e.getGui().width;
    		int buttonY = 0;
    		for(GuiButton b: e.getButtonList()) {
    			if(b.id == 200) {
    				b.width = 100;
    				buttonY = b.y;
    				b.x = width / 2 + 60;
    			}
    			if(b.id == 201) {
    				b.width = 100;
    			}
    		}
    		e.getButtonList().add(new GuiButton(203, width / 2 - 50, buttonY, 100, 20, I18n.format("gui.openKeyWiz")));
    	}
        /*    	
        if (e.getGui() instanceof GuiControls && !(e.getGui() instanceof GuiControlsPlusKeyWiz) && KeyWizardConfig.openFromControlsGui) {
           e.setGui(new GuiControlsPlusKeyWiz(this.client, client.currentScreen, this.client.gameSettings));
        }
        */
    }
    
    @SubscribeEvent
    public void keyPressed(KeyInputEvent e) {
    	if (keyOpenKeyWizard.isPressed()) {
    		this.client.displayGuiScreen(new GuiKeyWizard(this.client, null));
    	}
    }
}
