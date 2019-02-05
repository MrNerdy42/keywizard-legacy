package com.github.mrnerdy42.keywizard;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.RequiresMcRestart;

@Config(modid=KeyWizard.MODID )
public class KeyWizardConfig {
	
	@Comment({"If true, keyboard wizard will be accessible through a button in the controls gui.",
		     "as well as through the a keybinding. (default:F7)",
	         "Note: this option may do weird things if another mod",
	         "that overrides the controls gui is installed."})
	@RequiresMcRestart
	public static boolean openFromControlsGui = true;

}
