package com.marsman512.quickDisplay;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;

import org.lwjgl.glfw.GLFWWindowCloseCallbackI;

/**
 * The main class of the whole library.
 * It manages GLFW and the window.
 * @author Marsman512
 * @since August 21, 2019
 */

public class DisplayManager {
	private DisplayManager() {}
	
	private static long windowID = 0L;
	private static boolean initialized = false;
	private static boolean closeRequested = false;
	
	private static GLFWWindowCloseCallbackI windowCloseCB = (long window) -> {
		closeRequested = true;
	};
	
	/**
	 * Initializes GLFW
	 */
	public static void init(int width, int height, String title) {
		if(initialized)
			return;
		initialized = true;
		
		if(!glfwInit())
			throw new IllegalStateException("Failed to initialize GLFW!");
		
		windowID = glfwCreateWindow(width, height, title, 0, 0);
		if(windowID == 0L)
			throw new RuntimeException("Failed to create the window!");
		
		glfwSetWindowCloseCallback(windowID, windowCloseCB);
	}
	
	/**
	 * Terminates GLFW
	 */
	public static void cleanUp() {
		if(!initialized)
			return;
		initialized = false;
		
		glfwFreeCallbacks(windowID);
		glfwDestroyWindow(windowID);
		
		glfwTerminate();
	}
	
	/**
	 * Check for window events and swap the window's frame buffer
	 */
	public static void update() {
		glfwPollEvents();
		glfwSwapBuffers(windowID);
	}
	
	/**
	 * Returns true if the user tried to close the window.
	 */
	public static boolean windowShouldClose() {
		return closeRequested;
	}
}
