package com.marsman512.quickDisplay.input;

public class KeyEvent {
	public final int
		type,
		keycode;
	
	public KeyEvent(int type, int keycode) {
		this.type = type;
		this.keycode = keycode;
	}
}
