package com.marsman512.quickDisplay.input;

import static org.lwjgl.glfw.GLFW.*;
import com.marsman512.quickDisplay.DisplayManager;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;

import org.lwjgl.system.MemoryStack;
import java.nio.DoubleBuffer;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * A simple utility for managing mouse inputs
 * @author Marsman512
 * @since August 22, 2019
 */

public class Mouse {
	// Constants
	
	/**
	 * Mouse button identifier
	 */
	public static final int
		LEFT_BUTTON   = GLFW_MOUSE_BUTTON_LEFT   ,
		RIGHT_BUTTON  = GLFW_MOUSE_BUTTON_RIGHT  ,
		MIDDLE_BUTTON = GLFW_MOUSE_BUTTON_MIDDLE ;
	
	/**
	 * 
	 */
	public static final int
		BUTTON_PRESSED  = GLFW_PRESS  ,
		BUTTON_RELEASED = GLFW_RELEASE;
	
	// Mouse movement
	private static double mouseX, mouseY;
	private static double lastX, lastY;
	
	private static GLFWCursorPosCallbackI cursorPosCB = (long window, double x, double y) -> {
		mouseX = x;
		mouseY = y;
	};
	
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
	
	// Button events
	private static final Queue<MouseButtonEvent> buttonEvents = new ArrayBlockingQueue<>(12);
	
	private static GLFWMouseButtonCallbackI mouseButtonCB = (long window, int button, int action, int mods) -> {
		MouseButtonEvent buttonEvent = new MouseButtonEvent(button, action);
		buttonEvents.add(buttonEvent);
	};
	
	public static boolean hasButtonEvents() {
		return !buttonEvents.isEmpty();
	}
	
	public static MouseButtonEvent getButtonEvent() {
		return buttonEvents.poll();
	}
	
	// Scrolling
	private static double currentScrollPos = 0.0d;
	
	private static GLFWScrollCallbackI scrollCB = (long window, double xoffset, double yoffset) -> {
		currentScrollPos += yoffset;
	};
	
	public static double getScrollPos() {
		return currentScrollPos;
	}
	
	// Internal methods
	
	/**
	 * INTERNAL METHOD!
	 * DO NOT USE!
	 */
	public static void init() {
		long windowID = DisplayManager.getWindowID();
		glfwSetCursorPosCallback(windowID, cursorPosCB);
		glfwSetMouseButtonCallback(windowID, mouseButtonCB);
		glfwSetScrollCallback(windowID, scrollCB);
		
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
		// Update mouse position variables
		lastX = mouseX;
		lastY = mouseY;
		
		// Clear event cache
		buttonEvents.clear();
	}
}
