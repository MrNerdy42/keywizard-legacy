package com.github.mrnerdy42.keywizard.util;

import static org.lwjgl.input.Keyboard.*;

import scala.actors.threadpool.Arrays;

import static com.github.mrnerdy42.keywizard.util.KeyHelper.STD_WIDTH;
import static com.github.mrnerdy42.keywizard.util.KeyHelper.STD_HEIGHT;

public enum KeyboardLayout {
	QWERTY(new int[][][]{
		{ {KEY_TAB, 25, 10},{KEY_Q, STD_WIDTH, STD_HEIGHT},{KEY_W, STD_WIDTH, STD_HEIGHT},
		  {KEY_E, STD_WIDTH, STD_HEIGHT},{KEY_R, STD_WIDTH, STD_HEIGHT},{KEY_T, STD_WIDTH, STD_HEIGHT},
		  {KEY_Y, STD_WIDTH, STD_HEIGHT}, {KEY_U, STD_WIDTH, STD_HEIGHT},{KEY_I, STD_WIDTH, STD_HEIGHT},
		  {KEY_O, STD_WIDTH, STD_HEIGHT},{KEY_P, STD_WIDTH, STD_HEIGHT},{KEY_LBRACKET, STD_WIDTH, STD_HEIGHT},
		  {KEY_RBRACKET, STD_WIDTH, STD_HEIGHT},{KEY_BACKSLASH, STD_WIDTH, STD_HEIGHT} }
	});
	
	private int[][][] rows;
	KeyboardLayout(int[][][] rows){
		this.rows = rows;
	}
	
	public int numRows() {
		return this.rows.length;
	}
	
	public int rowLength(int row) throws IndexOutOfBoundsException{
		return this.rows[row].length;
	}
	
	public int[] getKey(int row, int index) throws IndexOutOfBoundsException{
		return this.rows[row][index].clone();
	}
	
}
