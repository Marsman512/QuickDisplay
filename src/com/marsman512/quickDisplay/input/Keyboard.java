package com.marsman512.quickDisplay.input;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import com.marsman512.quickDisplay.DisplayManager;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Keyboard {
	private static Queue<KeyEvent> keyEvents = new ArrayBlockingQueue<KeyEvent>(100);
	
	private static GLFWKeyCallbackI keyCB = (long window, int key, int scancode, int action, int mods) -> {
		KeyEvent currentEvent = new KeyEvent(action, key);
		keyEvents.add(currentEvent);
	};
	
	public static boolean hasKeyEvents() {
		return !keyEvents.isEmpty();
	}
	
	public static KeyEvent getKeyEvent() {
		return keyEvents.poll();
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
