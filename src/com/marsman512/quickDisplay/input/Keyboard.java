package com.marsman512.quickDisplay.input;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import com.marsman512.quickDisplay.DisplayManager;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * A simple utility for managing keyboard input
 * @author Marsman512
 * @since August 22, 2019
 */

public class Keyboard {
	private static Queue<KeyEvent> keyEvents = new ArrayBlockingQueue<KeyEvent>(100);
	private static boolean[] pressedKeys = new boolean[GLFW_KEY_LAST + 1];
	
	private static GLFWKeyCallbackI keyCB = (long window, int key, int scancode, int action, int mods) -> {
		KeyEvent currentEvent = new KeyEvent(action, key);
		keyEvents.add(currentEvent);
		
		pressedKeys[key] = (action == GLFW_RELEASE) ? false : true;
	};
	
	public static boolean hasKeyEvents() {
		return !keyEvents.isEmpty();
	}
	
	public static KeyEvent getKeyEvent() {
		return keyEvents.poll();
	}
	
	public static boolean isKeyPressed(int key) {
		return pressedKeys[key];
	}
	
	/**
	 * INTERNAL METHOD!
	 * DO NOT USE!
	 */
	public static void init() {
		glfwSetKeyCallback(DisplayManager.getWindowID(), keyCB);
	}
	
	/**
	 * INTERNAL METHOD!
	 * DO NOT USE!
	 */
	public static void update() {
		keyEvents.clear();
	}
}
