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
	public static void init(DisplaySetup setup) {
		// Don't call this function twice.
		if(initialized)
			return;
		initialized = true;
		
		// Initialize GLFW
		if(!glfwInit())
			throw new IllegalStateException("Failed to initialize GLFW!");
		
		// Reset the window hints, just in case.
		glfwDefaultWindowHints();
		
		// Hints for the window itself.
		glfwWindowHint(GLFW_RESIZABLE, setup.resizable ? GLFW_TRUE : GLFW_FALSE);
		
		// Create the window
		windowID = glfwCreateWindow(setup.width, setup.height, setup.title, 0, 0);
		if(windowID == 0L)
			throw new RuntimeException("Failed to create the window!");
		
		// Initialize the callbacks.
		glfwSetWindowCloseCallback(windowID, windowCloseCB);
	}
	
	/**
	 * Terminates GLFW
	 */
	public static void cleanUp() {
		// Don't call this function twice.
		if(!initialized)
			return;
		initialized = false;
		
		// Cleanup memory associated with the window.
		glfwFreeCallbacks(windowID);
		glfwDestroyWindow(windowID);
		
		// Terminate GLFW.
		glfwTerminate();
	}
	
	/**
	 * Check for window events and swap the window's frame buffer
	 */
	public static void update() {
		// Check for events.
		glfwPollEvents();
		
		// Swap the frame buffers
		glfwSwapBuffers(windowID);
	}
	
	/**
	 * Returns true if the user tried to close the window.
	 */
	public static boolean windowShouldClose() {
		// We have the close request cached from a callback,
		// as calling native code over and over will slow the JVM down.
		return closeRequested;
	}
}
