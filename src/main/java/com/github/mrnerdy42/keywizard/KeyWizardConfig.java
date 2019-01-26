package com.github.mrnerdy42.keywizard;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@Config(modid=KeyWizard.MODID )
public class KeyWizardConfig {
	
	@Comment({"If true, keyboard wizard will be accessible through a button in the controls gui.",
		     "as well as through the a keybinding. (default:F7)",
	         "Note: this option is ignored if Controlling is installed."})
	@RequiresMcRestart
	public static boolean openFromControlsGui = true;

}
