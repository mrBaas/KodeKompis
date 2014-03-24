package net.tedes.kodekompis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class CodePanelFragment extends Fragment implements OnClickListener {

	//Kun for testing
	TextView mVisKode;
	
	//KodePanel med ImageButtons
	ImageButton knapp1, knapp2, knapp3, knapp4, knapp5, knapp6, knapp7, knapp8, knapp9, knapp0, slett;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.code_fragment_layout, parent, false);
		
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
		if(mVisKode.length() < 4){
			mVisKode.append(String.valueOf(n));
		}else {
			//Ikke gjør noenting i første omgang
		}	
	}
	
	public void slettNummer(){
		if(!mVisKode.getText().equals("") || mVisKode.length() < 4){
			String tempText = String.valueOf(mVisKode.getText());
			mVisKode.setText(tempText.substring(0, tempText.length() - 1));
		} 
	}
	
}
