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
    	int debugcounter = ManagePreferences.getInt(getBaseContext(), Tedes.DEBUG_COUNTER);
    	Toast.makeText(getBaseContext(), "debugcounter: "+debugcounter+" (reset on "+Tedes.DEBUG_RESET+")", Toast.LENGTH_LONG).show();
    	if(debugcounter > 9){
    		ManageStorage.deleteList(getBaseContext());
    		ManageStorage.deletePassword(getBaseContext());
    		ManagePreferences.setInt(getBaseContext(), Tedes.DEBUG_COUNTER, 1);
    		ManagePreferences.setInt(getBaseContext(), Tedes.START_COUNTER, 0);
    		ManagePreferences.setInt(getBaseContext(), Tedes.DATABOLK_COUNTER, 0);
    		ManagePreferences.setInt(getBaseContext(), Tedes.FAILED_LOGINS, 0);
    		ManagePreferences.setInt(getBaseContext(), Tedes.FAILED_LOGINS_ITERATOR, 0);
    		Toast.makeText(getBaseContext(), "Debug: Reset system", Toast.LENGTH_LONG).show();
    	} else {
    		ManagePreferences.setInt(getBaseContext(), Tedes.DEBUG_COUNTER, ++debugcounter);
    	}
    	//END DEBUG PURPOSES
    	
    	
    	//Check how many times the application has been launched by correct password.
		int startcounter = ManagePreferences.getInt(getBaseContext(), Tedes.START_COUNTER);
		Log.d("Martin", "startcounter: "+startcounter);
		
		if(startcounter == 0) {
			//First Launch. Provide welcome info and offer to create new password.
			Toast.makeText(getBaseContext(), "first launch: welcome and make password", Toast.LENGTH_LONG).show();
			Intent i = new Intent(this, ActivityStartFirst.class);
			startActivity(i);
			
		} else {
			//Check how many consecutive failed logins
			int failedlogins = ManagePreferences.getInt(getBaseContext(), Tedes.FAILED_LOGINS);
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
