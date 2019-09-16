package me.marsman512.quickDisplay.input;

/**
 * Contains information about mouse button events, such a a click.
 * @author Marsman512
 */

public class MouseButtonEvent {
	public final int
		button, type;
	
	public MouseButtonEvent(int button, int type) {
		this.button = button;
		this.type = type;
	}
}
