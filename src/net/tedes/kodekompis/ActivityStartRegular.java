package net.tedes.kodekompis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class ActivityStartRegular extends FragmentActivity {
	
	private FragmentStartRegular f;
	
	@Override
	public void onBackPressed() {
		if (f.getKodeLength() == 0) {
			//If length of password is zero, go back to previous activity.
			super.onBackPressed();
		} else {
			// Otherwise, delete most recent digit.
			f.slettNummer();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_start_regular);
		
		FragmentManager fm = getSupportFragmentManager();
		this.f = (FragmentStartRegular)fm.findFragmentById(R.id.fragmentContainerStartRegular);
		
		if (f == null) {
			f = new FragmentStartRegular();
			fm.beginTransaction().add(R.id.fragmentContainerStartRegular, f).commit();
		}
	}

}
