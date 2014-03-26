package net.tedes.kodekompis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

public class ActivityMain extends FragmentActivity {

	@Override
	protected void onRestart(){
		super.onRestart();
		selectFragment();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_main);
		
		//Henter ut getSupportFragmentManager() i stede for getFragmentManager()
		//Støtter tidligere android versjoner
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null) {
			//Chooses between First Launch, or Regular Launch mode
			selectFragment();
		}
	}
	
    private void selectFragment(){
    	
    	//FOR DEBUG PURPOSES: EVERY 5 LOADS, RESET TO FIRST TIME USE
    	int debugcounter = PreferencesManager.getInt(getBaseContext(), "debugcounter");
    	Toast.makeText(getBaseContext(), "debugcounter: "+debugcounter+" (reset on 5)", Toast.LENGTH_LONG).show();
    	if(debugcounter > 4){
    		InternalStorage.deleteList(getBaseContext());
    		InternalStorage.deletePassword(getBaseContext());
    		PreferencesManager.setInt(getBaseContext(), "debugcounter", 1);
    		PreferencesManager.setInt(getBaseContext(), "startcounter", 0);
    		PreferencesManager.setInt(getBaseContext(), "failedlogins", 0);
    		Toast.makeText(getBaseContext(), "Debug: Reset system", Toast.LENGTH_LONG).show();
    	} else {
    		PreferencesManager.setInt(getBaseContext(), "debugcounter", ++debugcounter);
    	}
    	//END DEBUG PURPOSES
    	
    	
    	//Check how many times the application has been launched by correct password.
		int startcounter = PreferencesManager.getInt(getBaseContext(), "startcounter");
		Log.d("Martin", "startcounter: "+startcounter);
		
		if(startcounter == 0) {
			//First Launch, give info and create new password
			Toast.makeText(getBaseContext(), "first launch", Toast.LENGTH_LONG).show();
			Intent i = new Intent(this, ActivityStartFirst.class);
			startActivity(i);
			
		} else {
			//Normal start, ask for password
			Toast.makeText(getBaseContext(), "normal launch", Toast.LENGTH_LONG).show();
			
			//Check how many consecutive failed logins, before adding FragmentCodePanel
			//TODO: Add some way to keep from password hammering.
			int failedlogins = PreferencesManager.getInt(getBaseContext(), "failedlogins");
			Log.d("Martin", "check failed logins on start: "+failedlogins);
			
			if(failedlogins > 4) {
				//Do not proceed
				Toast.makeText(getBaseContext(), "Error: Too many failed logins", Toast.LENGTH_LONG).show();
			} else {
				//Regular Start
				Intent i = new Intent(this, ActivityStartRegular.class);
				startActivity(i);
			}
		}
    }
}
