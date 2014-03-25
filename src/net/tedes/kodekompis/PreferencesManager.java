package net.tedes.kodekompis;

import android.content.Context;
import android.content.SharedPreferences;

//DONT DEAD, OPEN INSIDE.
//..bruk internal storage istedet.

public class PreferencesManager {

	public static final String PREFS_NAME = "Tedespreferanser";
	
	//This one is only for string variables. Make new ones as needed.
	public static String getPreferences(Context context, String prefname) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
		String value = prefs.getString(prefname, "");
		return value;
	}

	//This one is only for string variables. Make new ones as needed.
	public static void setPreferences(Context context, String prefname, String value) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(prefname, value);
	    editor.commit();
	}
	
}
