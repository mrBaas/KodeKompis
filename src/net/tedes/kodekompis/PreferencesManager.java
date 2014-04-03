package net.tedes.kodekompis;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

	public static final String PREFS_NAME = "Tedespreferanser";
	
	//This one is only for string variables. Make new ones as needed.
	public static String getString(Context context, String prefname) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String value = prefs.getString(prefname, "");
		return value;
	}
	
	public static int getInt(Context context, String prefname) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		int value = prefs.getInt(prefname, 0);
		return value;
	}

	//This one is only for string variables. Make new ones as needed.
	public static void setString(Context context, String prefname, String value) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(prefname, value);
	    editor.commit();
	}
	
	public static void setInt(Context context, String prefname, int value) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putInt(prefname, value);
	    editor.commit();
	}
	
}
