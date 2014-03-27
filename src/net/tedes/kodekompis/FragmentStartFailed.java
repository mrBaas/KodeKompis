package net.tedes.kodekompis;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentStartFailed extends Fragment {
	
	private TextView mTextTimer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_layout_start_failed, parent, false);
		
		mTextTimer = (TextView)v.findViewById(R.id.start_failed_texttimer);
		new CountDownTimer(15000, 1000) {//CountDownTimer(edittext1.getText()+edittext2.getText()) also parse it to long

			 public void onTick(long millisUntilFinished) {
			     mTextTimer.setText("Vent: " + (millisUntilFinished / 1000) + " sekunder.");
			 }

			 public void onFinish() {
			     mTextTimer.setText("ferdig!");
			 }
			}
			.start();
		
		return v;
	}
	
}
