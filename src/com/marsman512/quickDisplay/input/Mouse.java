package com.marsman512.quickDisplay.input;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import com.marsman512.quickDisplay.DisplayManager;

/**
 * A simple utility for managing mouse inputs
 * @author Marsman512
 * @since August 22, 2019
 */

public class Mouse {
	private static double mouseX, mouseY;
	private static double lastX, lastY;
	private static boolean firstMouse = true;
	
	private static GLFWCursorPosCallbackI cursorPosCB = (long window, double x, double y) -> {
		if(firstMouse) {
			lastX = x;
			lastY = y;
			firstMouse = false;
		} else {
			lastX = mouseX;
			lastY = mouseY;
		}
		
		mouseX = x;
		mouseY = y;
	};
	
	/**
	 * INTERNAL METHOD!
	 * DO NOT USE!
	 */
	public static void init() {
		long windowID = DisplayManager.getWindowID();
		glfwSetCursorPosCallback(windowID, cursorPosCB);
	}
	
	/**
	 * INTERNAL METHOD!
	 * DO NOT USE!
	 */
	public static void update() {
		
	}
	
	public static double getX() {
		return mouseX;
	}
	
	public static double getY() {
		return mouseY;
	}
	
	public static double getDX() {
		return mouseX - lastX;
	}
	
	public static double getDY() {
		return mouseY - lastY;
	}
}
