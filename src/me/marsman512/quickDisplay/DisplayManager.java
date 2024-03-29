package me.marsman512.quickDisplay;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.opengl.GL11C.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import java.nio.IntBuffer;
import org.lwjgl.system.MemoryStack;

import me.marsman512.quickDisplay.input.Keyboard;
import me.marsman512.quickDisplay.input.Mouse;

import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;

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
	private static boolean windowResized = false;
	
	private static GLCapabilities glCaps;
	private static DisplayProps displayProps = new DisplayProps();
	
	private static GLFWWindowCloseCallbackI windowCloseCB = (long window) -> {
		closeRequested = true;
	};
	
	private static GLFWWindowSizeCallbackI windowSizeCB = (long window, int width, int height) -> {
		displayProps.setWidth(width);
		displayProps.setHeight(height);
		
		windowResized = true;
	};
	
	private static GLFWFramebufferSizeCallbackI framebufferSizeCB = (long window, int width, int height) -> {
		displayProps.setFramebufferWidth(width);
		displayProps.setFramebufferHeight(height);
		
		if(displayProps.getAutoCorrectFramebufferSize())
			glViewport(0, 0, width, height);
	};
	
	/**
	 * Initializes GLFW
	 */
	public static void init(DisplaySetup setup, ContextSetup ctx, PixelFormat pxl) {
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
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		// Hints for the OpenGL context.
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, ctx.majorVersion);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, ctx.minorVersion);
		glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, ctx.debugContext ? GLFW_TRUE : GLFW_FALSE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, ctx.canBeForwardCompat() && ctx.forwardCompat ? GLFW_TRUE : GLFW_FALSE);
		
		if(ctx.canBeCore())
			glfwWindowHint(GLFW_OPENGL_PROFILE, ctx.coreProfile ? GLFW_OPENGL_CORE_PROFILE : GLFW_OPENGL_COMPAT_PROFILE);
		else
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_ANY_PROFILE);
		
		// Pixel format hints
		glfwWindowHint(GLFW_RED_BITS, pxl.redBits);
		glfwWindowHint(GLFW_GREEN_BITS, pxl.greenBits);
		glfwWindowHint(GLFW_BLUE_BITS, pxl.blueBits);
		glfwWindowHint(GLFW_ALPHA_BITS, pxl.alphaBits);
		glfwWindowHint(GLFW_DEPTH_BITS, pxl.depthBits);
		glfwWindowHint(GLFW_STENCIL_BITS, pxl.stencilBits);
		glfwWindowHint(GLFW_SAMPLES, pxl.samples);
		
		// Create the window
		windowID = glfwCreateWindow(setup.width, setup.height, setup.title, 0, 0);
		if(windowID == 0L)
			throw new RuntimeException("Failed to create the window!");
		
		// Get the window's real properties
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer width = stack.callocInt(1);
			IntBuffer height = stack.callocInt(1);
			
			IntBuffer framebufferWidth = stack.callocInt(1);
			IntBuffer framebufferHeight = stack.callocInt(1);
			
			glfwGetWindowSize(windowID, width, height);
			glfwGetFramebufferSize(windowID, framebufferWidth, framebufferHeight);
			
			displayProps.setWidth(width.get(0));
			displayProps.setHeight(height.get(0));
			
			displayProps.setFramebufferWidth(framebufferWidth.get(0));
			displayProps.setFramebufferHeight(framebufferHeight.get(0));
			
			displayProps.setAutocorrectFB(setup.autoCorrectFramebufferSize);
		}
		
		// Create the OpenGL context
		glfwMakeContextCurrent(windowID);
		glCaps = GL.createCapabilities();
		glViewport(0, 0, displayProps.getFramebufferWidth(), displayProps.getFramebufferHeight());
		
		// Enable vsync if you want it
		glfwSwapInterval(setup.vsync ? 1 : 0);
		
		// Initialize the callbacks.
		glfwSetWindowCloseCallback(windowID, windowCloseCB);
		glfwSetWindowSizeCallback(windowID, windowSizeCB);
		glfwSetFramebufferSizeCallback(windowID, framebufferSizeCB);
		
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
		// Clear the previous frame's input cache.
		Keyboard.update();
		Mouse.update();
		
		// Check for events.
		glfwPollEvents();
		
		// Swap the frame buffers
		glfwSwapBuffers(windowID);
	}
	
	/**
	 * Make the window visible on the main display and initialize inputs
	 */
	public static void show() {
		glfwShowWindow(windowID);
		
		// Initialize input classes
		Keyboard.init();
		Mouse.init();
	}
	
	/**
	 * Returns true if the user tried to close the window.
	 */
	public static boolean windowShouldClose() {
		// We have the close request cached from a callback,
		// as calling native code over and over will slow the JVM down.
		return closeRequested;
	}
	
	/**
	 * Centers the window on the primary monitor
	 */
	public static void centerOnDisplay() {
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		int windowX = (videoMode.width()  - displayProps.getWidth())  / 2;
		int windowY = (videoMode.height() - displayProps.getHeight()) / 2;
		
		glfwSetWindowPos(windowID, windowX, windowY);
	}
	
	/**
	 * Centers the window on the workspace of the primary monitor
	 */
	public static void centerOnWorkspace() {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer x = stack.callocInt(1);
			IntBuffer y = stack.callocInt(1);
			IntBuffer width = stack.callocInt(1);
			IntBuffer height = stack.callocInt(1);
			
			glfwGetMonitorWorkarea(glfwGetPrimaryMonitor(), x, y, width, height);
			
			int windowX = ((width.get(0) - displayProps.getWidth()) / 2) + x.get(0);
			int windowY = ((height.get(0) - displayProps.getHeight()) / 2) + y.get(0);
			
			glfwSetWindowPos(windowID, windowX, windowY);
		}
	}
	
	public static DisplayProps getDisplayProps() {
		return displayProps;
	}
	
	public static GLCapabilities getGLCaps() {
		return glCaps;
	}
	
	/**
	 * Returns whether or not the window has been resized since the last time this function was called
	 */
	public static boolean windowResized() {
		boolean toReturn = windowResized;
		windowResized = false;
		return toReturn;
	}
	
	/**
	 * Returns the GLFW window handle.
	 * Not meant for public use.
	 * USE AT YOUR OWN DISCRETION!
	 */
	public static long getWindowID() {
		return windowID;
	}
}
