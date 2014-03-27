package net.tedes.kodekompis;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentStartFailed extends Fragment {
	
	//Holds number of seconds left before successfully waited.
	private TextView mTextTimer;
	private CountDownTimer timer;
	
	//holds how many consecutive sets of multiple failures due to password attempts.
	private int n;

	@Override
	public void onStop() {
		super.onStop();
		Log.d("Martin","timer.cancel()");
		timer.cancel();
		getActivity().finish();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_layout_start_failed, parent, false);
		
		mTextTimer = (TextView)v.findViewById(R.id.start_failed_texttimer);

		n = PreferencesManager.getInt(getActivity().getBaseContext(), "failedloginsN");
		
		//Add (Tedes.FAILED_LOGINS_WAIT) more seconds to wait for every round of failures.
		timer = new CountDownTimer(Tedes.FAILED_LOGINS_WAIT*1000*(n+1), 1000) {

			 public void onTick(long millisUntilFinished) {
			     mTextTimer.setText("Vent: " + (millisUntilFinished / 1000) + " sekunder.");
			 }

			 public void onFinish() {
			     mTextTimer.setText(getString(R.string.startfailed_waitfinished));
			     
			     //Increment cumulative failure count, to increase penalty next time
			     //(Do this upon successful wait, not on creation, to avoid issues with cancel.)
			     Log.d("Martin","timer finished and failedlogins reset.");
			     PreferencesManager.setInt(getActivity().getBaseContext(), "failedloginsN", ++n);
			     PreferencesManager.setInt(getActivity().getBaseContext(), "failedlogins", 0);
			     getActivity().finish();
			 }
			}.start();
		
		return v;
	}
	
}
