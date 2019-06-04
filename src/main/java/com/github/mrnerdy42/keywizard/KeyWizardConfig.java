package com.github.mrnerdy42.keywizard;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;

@Config(modid=KeyWizard.MODID )
public class KeyWizardConfig {
	
	@Comment({"If true, keyboard wizard will be accessible through a button in the controls gui.",
		     "as well as through the a keybinding. (default:F7)",
	         "Note: this option may do weird things if another mod",
	         "that overrides the controls gui is installed."})
	public static boolean openFromControlsGui = true;
	@Comment("The number of mouse buttons to show (default:5).")
	@RangeInt(min=3)
	public static int maxMouseButtons = 5;

}
