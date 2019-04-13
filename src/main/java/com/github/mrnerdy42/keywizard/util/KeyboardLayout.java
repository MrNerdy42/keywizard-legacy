package com.github.mrnerdy42.keywizard.util;

import static org.lwjgl.input.Keyboard.*;
import static com.github.mrnerdy42.keywizard.util.KeyHelper.STD_WIDTH;
import static com.github.mrnerdy42.keywizard.util.KeyHelper.STD_HEIGHT;

public enum KeyboardLayout {
	QWERTY(new int[][][]{
		{ {KEY_Q, STD_WIDTH, STD_HEIGHT} }
	});
	
	private int[][][] rows;
	KeyboardLayout(int[][][] rows){
		this.rows = rows;
	}


}
