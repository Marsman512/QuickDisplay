package com.marsman512.quickDisplay;

/**
 * A class containing the initial setup for an OpenGL context.
 * There is no need for getters or setters here. Pretend it's a C-style struct with some extra functions.
 * @author Marsman512
 * @since August 21, 2019
 */

public class ContextSetup {
	// The OpenGL version.
	public int majorVersion;
	public int minorVersion;
	
	// Whether or not the profile is core or compatibility
	public boolean coreProfile = false;
	
	// Whether or not legacy functions should be loaded. If false, legacy functions may load.
	public boolean forwardCompat = false;
	
	/**
	 * Whether or not the OpenGL context should perform debug checks.
	 * If true you may also set a debug callback. 
	 */
	public boolean debugContext = false;
	
	public ContextSetup(int major, int minor) {
		this.majorVersion = major;
		this.minorVersion = minor;
	}
	
	public ContextSetup setCore(boolean value) {
		this.coreProfile = value;
		return this;
	}
	
	public ContextSetup setForwardCompate(boolean value) {
		this.forwardCompat = value;
		return this;
	}
	
	public ContextSetup setDebugMode(boolean value) {
		this.debugContext = value;
		return this;
	}
	
	public boolean cameBefore(ContextSetup other) {
		if(this.majorVersion < other.majorVersion)
			return true;
		else if(this.majorVersion == other.majorVersion && this.minorVersion < other.minorVersion)
			return true;
		return false;
	}
	
	public boolean cameAfter(ContextSetup other) {
		if(this.majorVersion > other.majorVersion)
			return true;
		else if(this.majorVersion == other.majorVersion && this.minorVersion > other.minorVersion)
			return true;
		return false;
	}
	
	public boolean canBeCore() {
		if(this.majorVersion > 3)
			return true;
		else if(this.majorVersion == 3 && this.minorVersion >= 2)
			return true;
		return false;
	}
	
	public boolean canBeForwardCompat() {
		if(this.majorVersion > 3)
			return true;
		else if(this.majorVersion == 3 && this.minorVersion >= 1)
			return true;
		return false;
	}
}
