package net.tedes.kodekompis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentStartFirstCodePage extends Fragment implements OnClickListener {

	private TextView mTextHeader;
	
	//Strings for holding the desired input password, twice.
	private String kode, kodeConfirm;
	
	//Manage indicator lamps for illustrating entered digits
	private ImageView[] indicators;
	private int indicatorOff = Tedes.ICON_LOGINLIGHT_OFF;
	private int indicatorOn  = Tedes.ICON_LOGINLIGHT_ON;
	
	//KodePanel med ImageButtons
	private ImageButton[] digits;
	private ImageButton slett;
	
	//Callback for ActivityStartFirst,
	//with Buttons for navigating Previous/Next in the StartFirst Slides.
	private InterfacePageNavigator mCallback;
	private Button bPrev, bNext, bRedo;
	private boolean bPrevEnabled = true;
	private boolean bNextEnabled = true;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (InterfacePageNavigator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement PageNavigator");
        }
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_layout_start_first_codepanel, parent, false);
		
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
		
        bPrev = (Button)v.findViewById(R.id.buttonPrevious);
        bNext = (Button)v.findViewById(R.id.buttonNext);
        bRedo = (Button)v.findViewById(R.id.buttonRedo);
        
        bPrev.setEnabled(bPrevEnabled);
        bNext.setEnabled(bNextEnabled);
        
        //Hide redo button until needed
        bRedo.setVisibility(View.GONE);
        
		//Setter lyttere på hver knapp.
		for (ImageButton i : digits) {
			i.setOnClickListener(this);
		}
		slett.setOnClickListener(this);
		slett.setVisibility(View.INVISIBLE);
		
        bPrev.setOnClickListener(this);
        bNext.setOnClickListener(this);
        bRedo.setOnClickListener(this);
		
		return v;
	}
    
    public void onClick(View v) {
        switch(v.getId()) {
	        case R.id.buttonPrevious:
	    		mCallback.pagePrevious();
	    		break;
	    	case R.id.buttonNext:
	    		ManageSecurity.savePassword(getActivity(), kodeConfirm);
				Intent i = new Intent(getActivity(), ActivityDataList.class);
				i.putExtra("kode", kodeConfirm);
				clearKode();
				Toast.makeText(getActivity().getBaseContext(),"Kryptert kode lagret. Plain kode cleared", Toast.LENGTH_LONG).show();
				startActivity(i);
	    		break;
	    	case R.id.buttonRedo:
	    		setButtonNextEnabled(false);
	    		Toast.makeText(getActivity().getBaseContext(), "Redo button clicked", Toast.LENGTH_LONG).show();
				clearKode();
				for (ImageView j : indicators) {
					j.setImageResource(indicatorOff);
				}
				bRedo.setVisibility(View.GONE);
				mTextHeader.setText(getString(R.string.welcome_enterfirst));
	    		break;
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
					bRedo.setVisibility(View.VISIBLE);
					Fader.FadOut(getActivity(), slett.getId());
					break;
				} else {
					if (!kodeConfirm.equals(kode)) {
						//Finished password second time, but not equal.
						Toast.makeText(getActivity().getBaseContext(), "Ulik kode. Prøv igjen.", Toast.LENGTH_LONG).show();
						clearKode();
						for (ImageView i : indicators) {
							i.setImageResource(indicatorOff);
						}
						bRedo.setVisibility(View.GONE);
						mTextHeader.setText(getString(R.string.welcome_enterfirst));
						break;
					}
				}
				
				//If this point of the switch is reached, the two finished codes are equal.
				Toast.makeText(getActivity().getBaseContext(), "success: equal", Toast.LENGTH_LONG).show();
				mTextHeader.setText(getString(R.string.welcome_finish));
				setButtonNextEnabled(true);
				Fader.FadOut(getActivity(), slett.getId());
				//TODO: Make happen indicate etc
				
				break;
			default:
				//This is any other digits of the code, no special behavior.
		}
	}
	
	public void slettNummer(){
		if(!bNextEnabled) {
			int len = getKodeLength();
			if(len > 0){
				kode = kode.substring(0, kode.length() - 1);
				indicators[kode.length()].setImageResource(indicatorOff);
				if (len == 1) {
					Fader.FadOut(getActivity(), slett.getId());
				}
			} 
		}
	}

	public int getKodeLength() {
		return this.kode.length();
	}
	
	private void clearKode(){
		if (slett.getVisibility() == View.VISIBLE) {
			Fader.FadOut(getActivity(), slett.getId());
		}
		this.kode  = "1234567890";
		this.kode  = "";
		this.kodeConfirm = "1234567890";
		this.kodeConfirm = "";
	}
	
	public void setButtonPrevEnabled(boolean enabled){
		this.bPrevEnabled = enabled;
		if(bPrev != null){
			this.bPrev.setEnabled(enabled);
		}
	}
	
	public void setButtonNextEnabled(boolean enabled){
		this.bNextEnabled = enabled;
		if(bNext != null){
			this.bNext.setEnabled(enabled);
		}
	}
    
}