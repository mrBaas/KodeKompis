package net.tedes.kodekompis;

import android.content.Context;
import android.content.SharedPreferences;

//DONT DEAD, OPEN INSIDE.
//..bruk internal storage istedet.

public class PreferencesManager {

	public static final String PREFS_NAME = "Tedespreferanser";
	
	// Restore preferences
	public static void getPreferences(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
		String Uname = prefs.getString("username", "");
		String pass = prefs.getString("password", "");

	}
	
}
