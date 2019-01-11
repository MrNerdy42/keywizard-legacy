package com.github.mrnerdy42.keywizard;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@Config(modid=KeyWizard.MODID)
public class KeyWizardConfig {
	
	@Comment({"If true, keyboard wizard will be accecible through a button in the controls gui.",
		     "If false, keyboard wizard is accecible through a keybinding (dafualt:F7)",
	         "Note: this option is ignored if Controlling is installed."})
	@RequiresMcRestart
	public static boolean openFromControlsGui = false;

}
