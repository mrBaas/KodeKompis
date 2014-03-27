package net.tedes.kodekompis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentWelcome3 extends Fragment implements OnClickListener {

	//Kun for testing
	private TextView mVisKode;
	
	//Two strings for holding the desired input password, twice.
	private String kode1, kode2;
	
	//KodePanel med ImageButtons
	ImageButton knapp1, knapp2, knapp3, knapp4, knapp5, knapp6, knapp7, knapp8, knapp9, knapp0, slett;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_welcome_page3, container, false);

        mVisKode = (TextView)v.findViewById(R.id.welcome_code_view);
		
		//Kobler hele kode-panelet
		knapp1 = (ImageButton)v.findViewById(R.id.welcome_inputNumber1);
		knapp2 = (ImageButton)v.findViewById(R.id.welcome_inputNumber2);
		knapp3 = (ImageButton)v.findViewById(R.id.welcome_inputNumber3);
		knapp4 = (ImageButton)v.findViewById(R.id.welcome_inputNumber4);
		knapp5 = (ImageButton)v.findViewById(R.id.welcome_inputNumber5);
		knapp6 = (ImageButton)v.findViewById(R.id.welcome_inputNumber6);
		knapp7 = (ImageButton)v.findViewById(R.id.welcome_inputNumber7);
		knapp8 = (ImageButton)v.findViewById(R.id.welcome_inputNumber8);
		knapp9 = (ImageButton)v.findViewById(R.id.welcome_inputNumber9);
		knapp0 = (ImageButton)v.findViewById(R.id.welcome_inputNumber0);
		slett = (ImageButton)v.findViewById(R.id.welcome_slett);
		
		//Setter lyttere på hver knapp
		knapp1.setOnClickListener(this);
		knapp2.setOnClickListener(this);
		knapp3.setOnClickListener(this);
		knapp4.setOnClickListener(this);
		knapp5.setOnClickListener(this);
		knapp6.setOnClickListener(this);
		knapp7.setOnClickListener(this);
		knapp8.setOnClickListener(this);
		knapp9.setOnClickListener(this);
		knapp0.setOnClickListener(this);
		slett.setOnClickListener(this);
		
		slett.setVisibility(View.INVISIBLE);
        
        return v;
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.welcome_inputNumber1:
         	   addNumberToTextView(1);
         	   break;
            case R.id.welcome_inputNumber2:
         	   addNumberToTextView(2);
         	   break;
            case R.id.welcome_inputNumber3:
         	   addNumberToTextView(3);
         	   break;
            case R.id.welcome_inputNumber4:
         	   addNumberToTextView(4);
         	   break;
            case R.id.welcome_inputNumber5:
         	   addNumberToTextView(5);
 	           break;
            case R.id.welcome_inputNumber6:
         	   addNumberToTextView(6);
                break;
            case R.id.welcome_inputNumber7:
         	   addNumberToTextView(7);
                break;
            case R.id.welcome_inputNumber8:
         	   addNumberToTextView(8);
                break;
            case R.id.welcome_inputNumber9:
         	   addNumberToTextView(9);
                break;
            case R.id.welcome_inputNumber0:
         	   addNumberToTextView(0);
                break;
            case R.id.welcome_slett:
         	   slettNummer();
         	   break;
 	   } 
 	}
    
    public void addNumberToTextView(int n){
		if(mVisKode.length() < 3){
			if(mVisKode.length() == 0){
				Fader.FadeIn(getActivity(), slett.getId());
			}
			mVisKode.append(String.valueOf(n));
			
		} else if (mVisKode.length() == 3){
			mVisKode.append(String.valueOf(n));
			
			if (kode1 == null) {
				kode1 = String.valueOf(mVisKode.getText());
				mVisKode.setText("");
			} else {
				kode2 = String.valueOf(mVisKode.getText());
				
				if (kode1.equals(kode2)) {
					Toast.makeText(getActivity().getBaseContext(), "success: equal", Toast.LENGTH_LONG).show();
					Security.savePassword(getActivity(), kode1);
					Intent i = new Intent(getActivity(), ActivityCodeList.class);
					//Ship kode to ActivityCodeList
					i.putExtra("kode", kode1);
					clearKode();
					Toast.makeText(getActivity().getBaseContext(),"Kryptert kode lagret. Plain kode cleared", Toast.LENGTH_LONG).show();
					startActivity(i);
				} else {
					Toast.makeText(getActivity().getBaseContext(), "failed: not equal", Toast.LENGTH_LONG).show();
					clearKode();
				}
			}
		} else {
			//Do nothing
		}	
	}
	
	private void slettNummer(){
		if(mVisKode.length() > 0 && mVisKode.length() != 1){
			String tempText = String.valueOf(mVisKode.getText());
			mVisKode.setText(tempText.substring(0, tempText.length() - 1));
		} else if (mVisKode.length() == 1) {
			String tempText = String.valueOf(mVisKode.getText());
			mVisKode.setText(tempText.substring(0, tempText.length() - 1));
			Fader.FadOut(getActivity(), slett.getId());
		}else {
			//Do nothing
		}
	}

	private void clearKode(){
		this.kode1 = "1234567890";
		this.kode1 = null;
		this.kode2 = "1234567890";
		this.kode2 = null;
		this.mVisKode.setText("1234567890");
		this.mVisKode.setText("");
	}
	
    
}