package com.github.mrnerdy42.keywizard;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.github.mrnerdy42.keywizard.handlers.ClientEventHandler;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = KeyWizard.MODID, name = KeyWizard.MODNAME, version = KeyWizard.VERSION, useMetadata = true, acceptedMinecraftVersions = "1.12 1.12.1 1.12.2", clientSideOnly = true)
public class KeyWizard {
	
	public static final String MODID = "keywizard";
	public static final String MODNAME = "Keyboard Wizard";
	public static final String VERSION = "1.12.2-1.5.3";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	@Instance
	public static KeyWizard instance = new KeyWizard();
	
	private ClientEventHandler clientEventHandler = new ClientEventHandler();
	
    @EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		LOGGER.log(Level.INFO, "Let's do some keyboard magic!");
	}
	
	@EventHandler
    public void init(FMLInitializationEvent e) {
		//KeyBinding keyOpenKeyWizard = new KeyBinding("keywizard.keybind.openKeyboardWizard", Keyboard.KEY_F7, "key.categories.misc");
		//ClientRegistry.registerKeyBinding(keyOpenKeyWizard);
    	MinecraftForge.EVENT_BUS.register(this.clientEventHandler);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    }


}
