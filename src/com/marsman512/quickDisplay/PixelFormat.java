package com.marsman512.quickDisplay;

/**
 * Initial pixel format data for the OpenGL context.
 * Please pretend it is a C-style struct!
 * @author Marsman512
 * @since August 22, 2019
 */

public class PixelFormat {
	public int 
		redBits = 8,
		greenBits = 8,
		blueBits = 8,
		alphaBits = 8,
		depthBits = 24,
		stencilBits = 8,
		samples = 1;
}
