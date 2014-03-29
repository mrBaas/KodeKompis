package net.tedes.kodekompis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentStartFirst3 extends Fragment implements OnClickListener {

	private TextView mTextHeader;
	
	//Strings for holding the desired input password, twice.
	private String kode, kodeConfirm;
	
	//Manage indicator lamps for illustrating entered digits
	private ImageView[] indicators;
	private int indicatorOff = android.R.drawable.presence_invisible;
	private int indicatorOn = android.R.drawable.presence_online;
	
	//KodePanel med ImageButtons
	private ImageButton[] digits;
	private ImageButton slett;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_layout_start_first3, parent, false);
		
		kode = "";
		
		mTextHeader = (TextView)v.findViewById(R.id.welcome_header);
		
		indicators = new ImageView[]{
				(ImageView)v.findViewById(R.id.indicator1),
				(ImageView)v.findViewById(R.id.indicator2),
				(ImageView)v.findViewById(R.id.indicator3),
				(ImageView)v.findViewById(R.id.indicator4)
		};
	
		//Kobler hele kode-panelet
		digits = new ImageButton[]{
				(ImageButton)v.findViewById(R.id.inputNumber0),
				(ImageButton)v.findViewById(R.id.inputNumber1),
				(ImageButton)v.findViewById(R.id.inputNumber2),
				(ImageButton)v.findViewById(R.id.inputNumber3),
				(ImageButton)v.findViewById(R.id.inputNumber4),
				(ImageButton)v.findViewById(R.id.inputNumber5),
				(ImageButton)v.findViewById(R.id.inputNumber6),
				(ImageButton)v.findViewById(R.id.inputNumber7),
				(ImageButton)v.findViewById(R.id.inputNumber8),
				(ImageButton)v.findViewById(R.id.inputNumber9)
		};
		
		slett = (ImageButton)v.findViewById(R.id.slett);
		
		//Setter lyttere på hver knapp.
		for (ImageButton i : digits) {
			i.setOnClickListener(this);
		}
		slett.setOnClickListener(this);
		slett.setVisibility(View.INVISIBLE);
		
		return v;
	}
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.inputNumber1:
         	   addNumberToTextView(1);
         	   break;
            case R.id.inputNumber2:
         	   addNumberToTextView(2);
         	   break;
            case R.id.inputNumber3:
         	   addNumberToTextView(3);
         	   break;
            case R.id.inputNumber4:
         	   addNumberToTextView(4);
         	   break;
            case R.id.inputNumber5:
         	   addNumberToTextView(5);
 	           break;
            case R.id.inputNumber6:
         	   addNumberToTextView(6);
                break;
            case R.id.inputNumber7:
         	   addNumberToTextView(7);
                break;
            case R.id.inputNumber8:
         	   addNumberToTextView(8);
                break;
            case R.id.inputNumber9:
         	   addNumberToTextView(9);
                break;
            case R.id.inputNumber0:
         	   addNumberToTextView(0);
                break;
            case R.id.slett:
         	   slettNummer();
         	   break;
 	   } 
 	}
    
	public void addNumberToTextView(int n){
		int len = getKodeLength();
		if(len < 4) {
			indicators[len].setImageResource(indicatorOn);
			kode = kode + String.valueOf(n);
		} else {
			//Do nothing; input longer than required code
		}
		
		switch (len){
			case 0:
				//This is the first digit of the code
				Fader.FadeIn(getActivity(), slett.getId());
				break;
			case 3:
				//This is the last digit of the code
				//Loader skal etter hvert hit

				if (kodeConfirm == null || kodeConfirm.equals("")) {
					//Finished password first time.
					kodeConfirm = kode;
					kode = "";
					for (ImageView i : indicators) {
						i.setImageResource(indicatorOff);
					}
					mTextHeader.setText(getString(R.string.welcome_entersecond));
					break;
				} else {
					if (!kodeConfirm.equals(kode)) {
						//Finished password second time, but not equal.
						Toast.makeText(getActivity().getBaseContext(), "failed: not equal", Toast.LENGTH_LONG).show();
						clearKode();
						for (ImageView i : indicators) {
							i.setImageResource(indicatorOff);
						}
						mTextHeader.setText(getString(R.string.welcome_enterfirst));
						break;
					}
				}
				
				//If this point of the switch is reached, the two finished codes are equal.
				Toast.makeText(getActivity().getBaseContext(), "success: equal", Toast.LENGTH_LONG).show();
				Security.savePassword(getActivity(), kodeConfirm);
				Intent i = new Intent(getActivity(), ActivityDataList.class);
				i.putExtra("kode", kodeConfirm);
				clearKode();
				Toast.makeText(getActivity().getBaseContext(),"Kryptert kode lagret. Plain kode cleared", Toast.LENGTH_LONG).show();
				startActivity(i);
				break;
			default:
				//This is any other digits of the code, no special behavior.
		}
	}
	
	public void slettNummer(){
		int len = getKodeLength();
		if(len > 0){
			kode = kode.substring(0, kode.length() - 1);
			indicators[kode.length()].setImageResource(indicatorOff);
			if (len == 1) {
				Fader.FadOut(getActivity(), slett.getId());
			}
		} 
	}

	public int getKodeLength() {
		return this.kode.length();
	}
	
	private void clearKode(){
		this.kode  = "1234567890";
		this.kode  = "";
		this.kodeConfirm = "1234567890";
		this.kodeConfirm = "";
	}
	
    
}