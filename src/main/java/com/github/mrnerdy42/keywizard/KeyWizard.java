package com.github.mrnerdy42.keywizard;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;

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
	public static final String VERSION = "1.12.2-1.5.6";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public static Configuration config;
	public static boolean openFromControlsGui;
	
	private static String[] conflictingMods = {"controlling"};
	
	@Instance
	public static KeyWizard instance = new KeyWizard();
	
	private ClientEventHandler clientEventHandler = new ClientEventHandler();
	
    @EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		LOGGER.log(Level.INFO, "Let's do some keyboard magic!");
		config = new Configuration(e.getSuggestedConfigurationFile());

	}
	
	@EventHandler
    public void init(FMLInitializationEvent e) {
		ClientEventHandler.register();
    	MinecraftForge.EVENT_BUS.register(this.clientEventHandler);
    	
    	openFromControlsGui = config.getBoolean("openFromControlsGui", Configuration.CATEGORY_GENERAL, false, null);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	boolean flag = false;
    	if(openFromControlsGui) {
    		LOGGER.log(Level.WARN, "Controls gui override enabled, this may cause problems with other mods");
    		for(String id:conflictingMods) {
    			if(Loader.isModLoaded(id)) {
    				flag = true;
    				break;
    			}
    		}
        	if(flag) {
        		LOGGER.log(Level.WARN, "Conflicting mod detected, controls gui override may not work");
        	}
    	}
    }
}
