package com.github.mrnerdy42.keywizard;

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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.github.mrnerdy42.keywizard.handlers.BindingHandler;
import com.github.mrnerdy42.keywizard.handlers.GuiHandler;

import net.minecraftforge.common.MinecraftForge;

@Mod(modid = KeyWizard.MODID, name = KeyWizard.MODNAME, version = KeyWizard.VERSION, useMetadata = true, clientSideOnly = true)
public class KeyWizard {
	
	public static final String MODID = "keywizard";
	public static final String MODNAME = "Keyboard Wizard";
	public static final String VERSION = "1.10.2-1.4.5";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	@Instance
	public static KeyWizard instance = new KeyWizard();
	
	private BindingHandler handler = new BindingHandler();
	
	@EventHandler
    public void init(FMLInitializationEvent e) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(KeyWizard.instance, new GuiHandler());
    	BindingHandler.register();
    	MinecraftForge.EVENT_BUS.register(handler);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    }

    @EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		LOGGER.log(Level.INFO, "Let's do some keyboard magic!");
	}
}
