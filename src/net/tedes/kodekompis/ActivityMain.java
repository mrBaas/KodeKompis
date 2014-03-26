package net.tedes.kodekompis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

public class ActivityMain extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_main);
		
		//Henter ut getSupportFragmentManager() i stede for getFragmentManager()
		//Støtter tidligere android versjoner
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null) {
						
			//Keep track of how many times the application (ActivityMain) has been launched.
			int startcounter = PreferencesManager.getInt(getBaseContext(), "startcounter");
			PreferencesManager.setInt(getBaseContext(), "startcounter", ++startcounter);
			Log.d("Martin", "startcounter: "+startcounter);
			
			//Do something special on first time start
			if(startcounter == 1) {
				//Can use custom fragment for first time start, for example (not implemented).
				//Or first generate fragment, then invoke "first run" method in fragment, from activity class.
				
				fragment = new FragmentCodePanel();
				//fragment = new FragmentFirstRun();
				//fragment.onFirstRun();
				fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
			} else {
				//Normal start
				//Check how many consecutive failed logins, before adding FragmentCodePanel
				int failedlogins = PreferencesManager.getInt(getBaseContext(), "failedlogins");
				Log.d("Martin", "check failed logins on start: "+failedlogins);
				
				if(failedlogins > 4) {
					Toast.makeText(getBaseContext(), "Too many failed logins", 3000);
					//Do not proceed
				} else {
					fragment = new FragmentCodePanel();
					fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
				}
			}
			
			
			
		}
	}

}
