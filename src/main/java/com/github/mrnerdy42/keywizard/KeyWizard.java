package com.github.mrnerdy42.keywizard;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.mrnerdy42.keywizard.handlers.ClientEventHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = KeyWizard.MODID, name = KeyWizard.MODNAME, version = KeyWizard.VERSION, useMetadata = true, acceptedMinecraftVersions = "1.12 1.12.1 1.12.2", clientSideOnly = true)
public class KeyWizard {
	
	public static final String MODID = "keywizard";
	public static final String MODNAME = "Keyboard Wizard";
	public static final String VERSION = "1.12.2-1.7.3";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	private static String[] conflictingMods = {"controlling"};
	
	@Instance
	public static KeyWizard instance = new KeyWizard();
	
	private ClientEventHandler clientEventHandler = new ClientEventHandler();
	
    @EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		LOGGER.log(Level.INFO, "Let's do some keyboard magic!");
	}
	
	@EventHandler
    public void init(FMLInitializationEvent e) {
		ClientEventHandler.register();
    	MinecraftForge.EVENT_BUS.register(this.clientEventHandler);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    	boolean flag = false;
    	if(KeyWizardConfig.openFromControlsGui) {
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
