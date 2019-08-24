package com.marsman512.quickDisplay.input;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import com.marsman512.quickDisplay.DisplayManager;

import org.lwjgl.system.MemoryStack;
import java.nio.DoubleBuffer;

/**
 * A simple utility for managing mouse inputs
 * @author Marsman512
 * @since August 22, 2019
 */

public class Mouse {
	private static double mouseX, mouseY;
	private static double lastX, lastY;
	
	private static GLFWCursorPosCallbackI cursorPosCB = (long window, double x, double y) -> {
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
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			DoubleBuffer x = stack.callocDouble(1);
			DoubleBuffer y = stack.callocDouble(1);
			
			glfwGetCursorPos(windowID, x, y);
			
			mouseX = x.get(0);
			mouseY = y.get(0);
			lastX = mouseX;
			lastY = mouseY;
		}
		
	}
	
	/**
	 * INTERNAL METHOD!
	 * DO NOT USE!
	 */
	public static void update() {
		lastX = mouseX;
		lastY = mouseY;
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
