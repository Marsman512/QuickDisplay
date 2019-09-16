package me.marsman512.quickDisplay;

public class DisplayProps {
	/** The size of the window */
	private int width, height;
	
	/** The size of the framebuffer */
	private int framebufferWidth, framebufferHeight;
	
	/** Determines whether or not the context should automatically resize when the window does */
	private boolean autocorrectfb;
	
	void setWidth(int width) {
		this.width = width;
	}

	void setHeight(int height) {
		this.height = height;
	}

	void setFramebufferWidth(int framebufferWidth) {
		this.framebufferWidth = framebufferWidth;
	}

	void setFramebufferHeight(int framebufferHeight) {
		this.framebufferHeight = framebufferHeight;
	}
	
	void setAutocorrectFB(boolean value) {
		this.autocorrectfb = value;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getFramebufferWidth() {
		return framebufferWidth;
	}
	
	public int getFramebufferHeight() {
		return framebufferHeight;
	}
	
	public boolean getAutoCorrectFramebufferSize() {
		return this.autocorrectfb;
	}
}
