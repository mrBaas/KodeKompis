package net.tedes.kodekompis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class ActivityMain extends FragmentActivity {

	@Override
	protected void onRestart(){
		super.onRestart();
		selectActivity();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_main);
				
		//Chooses between First Launch, Regular Launch, or Failed Launch mode
		selectActivity();
	}
	
    private void selectActivity(){
    	
    	//FOR DEBUG PURPOSES: EVERY (Tedes.DEBUG_RESET) LOADS, RESET TO FIRST TIME USE
    	int debugcounter = PreferencesManager.getInt(getBaseContext(), "debugcounter");
    	Toast.makeText(getBaseContext(), "debugcounter: "+debugcounter+" (reset on "+Tedes.DEBUG_RESET+")", Toast.LENGTH_LONG).show();
    	if(debugcounter > 9){
    		InternalStorage.deleteList(getBaseContext());
    		InternalStorage.deletePassword(getBaseContext());
    		PreferencesManager.setInt(getBaseContext(), "debugcounter", 1);
    		PreferencesManager.setInt(getBaseContext(), "startcounter", 0);
    		PreferencesManager.setInt(getBaseContext(), "failedlogins", 0);
    		PreferencesManager.setInt(getBaseContext(), "failedloginsN", 0);
    		Toast.makeText(getBaseContext(), "Debug: Reset system", Toast.LENGTH_LONG).show();
    	} else {
    		PreferencesManager.setInt(getBaseContext(), "debugcounter", ++debugcounter);
    	}
    	//END DEBUG PURPOSES
    	
    	
    	//Check how many times the application has been launched by correct password.
		int startcounter = PreferencesManager.getInt(getBaseContext(), "startcounter");
		Log.d("Martin", "startcounter: "+startcounter);
		
		if(startcounter == 0) {
			//First Launch. Provide welcome info and offer to create new password.
			Toast.makeText(getBaseContext(), "first launch: welcome and make password", Toast.LENGTH_LONG).show();
			Intent i = new Intent(this, ActivityStartFirst.class);
			startActivity(i);
			
		} else {
			//Check how many consecutive failed logins
			int failedlogins = PreferencesManager.getInt(getBaseContext(), "failedlogins");
			Log.d("Martin", "check failed logins on start: "+failedlogins);
			
			if(failedlogins >= Tedes.FAILED_LOGINS_MAX) {
				//Failed Start due to too many wrong attempts. Punish by timer on next attempt.
				Toast.makeText(getBaseContext(), "failed launch: too many failed logins ("+failedlogins+")", Toast.LENGTH_LONG).show();
				Intent i = new Intent(this, ActivityStartFailed.class);
				startActivity(i);
			} else {
				//Regular Start. Offer CodePanel to user to enter password.
				Toast.makeText(getBaseContext(), "normal launch: enter password for access", Toast.LENGTH_LONG).show();
				Intent i = new Intent(this, ActivityStartRegular.class);
				startActivity(i);
			}
		}
    }
}
