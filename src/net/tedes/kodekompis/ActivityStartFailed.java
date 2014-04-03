package net.tedes.kodekompis;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class ActivityStartFailed extends FragmentActivity {
	
	private FragmentStartFailed f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_start_failed);
		
		FragmentManager fm = getSupportFragmentManager();
		f = (FragmentStartFailed)fm.findFragmentById(R.id.fragmentContainerStartFailed);
		
		if (f == null) {
			f = new FragmentStartFailed();
			fm.beginTransaction().add(R.id.fragmentContainerStartFailed, f).commit();
		}
	}

	@Override
	public void onBackPressed() {
		if (!f.isFinished()) {
			super.onBackPressed();
			Log.d("Martin","f.getTimer().cancel()");
			f.getTimer().cancel();
			this.finish();
		} else {
			super.onBackPressed();
		}
	}
}
