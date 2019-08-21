package com.marsman512.quickDisplay;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWVidMode;

/**
 * This class holds the initial setup for the main window.
 * Pretend it's a C-style struct. There is no need for getters or setters in this class!
 * @author Marsman512
 * @since August 21, 2019
 */

public class DisplaySetup {
	public int width, height;
	public String title;
	
	public boolean vsync = true;
	public boolean resizable = false;
	
	public DisplaySetup(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
}
