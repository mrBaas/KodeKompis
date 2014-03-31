package net.tedes.kodekompis;

public final class Tedes {

	//How many reloads of ActivityMain will trigger reset of all important variables
	public static final int DEBUG_RESET = 10;
	
	//Amount of failed attempts required before punishment by timer
	public static final int FAILED_LOGINS_MAX = 3;
	
	//Seconds to wait when failing too many times and being punished by timer
	public static final int FAILED_LOGINS_WAIT = 30; 
	
	//Names of extra arguments to pass into StartFirst page Fragments
	public static final String EXTRA_START_FIRST_TEXTSOURCE = "startfirst_textsource";
	public static final String EXTRA_START_FIRST_IMGSOURCE = "startfirst_imgsource";
}
