package net.tedes.kodekompis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class CodeListActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.code_list_main_layout);
		
		//Henter ut getSupportFragmentManager() i stede for getFragmentManager()
		//Støtter tidligere android versjoner
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null) {
			fragment = new CodePanelFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
		

	}
}
