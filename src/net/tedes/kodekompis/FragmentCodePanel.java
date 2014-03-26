package net.tedes.kodekompis;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentCodePanel extends Fragment implements OnClickListener {

	//Kun for testing
	private TextView mVisKode;
	
	//KodePanel med ImageButtons
	ImageButton knapp1, knapp2, knapp3, knapp4, knapp5, knapp6, knapp7, knapp8, knapp9, knapp0, slett;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_layout_code, parent, false);
		
		
		mVisKode = (TextView)v.findViewById(R.id.code_view);
		
		//Kobler hele kode-panelet
		knapp1 = (ImageButton)v.findViewById(R.id.inputNumber1);
		knapp2 = (ImageButton)v.findViewById(R.id.inputNumber2);
		knapp3 = (ImageButton)v.findViewById(R.id.inputNumber3);
		knapp4 = (ImageButton)v.findViewById(R.id.inputNumber4);
		knapp5 = (ImageButton)v.findViewById(R.id.inputNumber5);
		knapp6 = (ImageButton)v.findViewById(R.id.inputNumber6);
		knapp7 = (ImageButton)v.findViewById(R.id.inputNumber7);
		knapp8 = (ImageButton)v.findViewById(R.id.inputNumber8);
		knapp9 = (ImageButton)v.findViewById(R.id.inputNumber9);
		knapp0 = (ImageButton)v.findViewById(R.id.inputNumber0);
		slett = (ImageButton)v.findViewById(R.id.slett);
		
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
		if(mVisKode.length() < 3){
			if(mVisKode.length() == 0){
				Fader.FadeIn(getActivity(), slett.getId());
			}
			mVisKode.append(String.valueOf(n));
		
			
			//For testing purposes
//			if(mVisKode.length() == 1) {
//				String appPassword = "1234";
//				String feilPassord = "2345";
//				
//				String itemStedPlain   = "stad";
//				String itemBrukerPlain   = "bruker";
//				String itemPassPlain   = "passord";
//
//				DataBolk 		  test1 = new DataBolk("Plain: "+itemStedPlain, itemBrukerPlain, itemPassPlain);
//				DataBolkEncrypted test2 = new DataBolkEncrypted(test1, appPassword);
//				
//				List<DataBolkEncrypted> testlist = new ArrayList<DataBolkEncrypted>();
//				testlist.add(test2);
//				InternalStorage.writeListEncrypted(getActivity().getBaseContext(), (ArrayList<DataBolkEncrypted>) testlist);
//			}
			
		} else if (mVisKode.length() == 3){
			//Loader skal etter hvert hit
			mVisKode.append(String.valueOf(n));
			Intent i = new Intent(getActivity(), ActivityCodeList.class);
			//Ship kode to ActivityCodeList
			String kode = String.valueOf(mVisKode.getText());
			i.putExtra("kode", kode);
			
			//MOVE TO FIRST TIME LAUNCH ONLY
			//Security.savePassword(getActivity(), kode);
			//Toast.makeText(getActivity().getBaseContext(), "kode: "+String.valueOf(mVisKode.getText()), Toast.LENGTH_LONG).show();
			startActivity(i);
		} else {
			//Do nothing
		}	
	}
	
	public void slettNummer(){
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
}
