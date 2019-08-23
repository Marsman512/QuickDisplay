package com.marsman512.quickDisplay.input;

/**
 * Represents a keyboard input event
 * @author Marsman512
 * @since August 22, 2019
 */

public class KeyEvent {
	public final int
		type,
		keycode;
	
	public KeyEvent(int type, int keycode) {
		this.type = type;
		this.keycode = keycode;
	}
}
