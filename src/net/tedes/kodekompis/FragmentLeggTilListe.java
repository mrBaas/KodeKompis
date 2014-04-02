package net.tedes.kodekompis;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class FragmentLeggTilListe extends DialogFragment implements OnClickListener {
	
	private OnNewBolkFinished mCallback;
	private DataBolk bolken;
	
	private Button mAvbryt;
	private Button mLeggTil;
	
	private EditText mSted;
	private EditText mBruker;
	private EditText mPass;
	
	//TextWatcher
    final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3){}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkInput();
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    public FragmentLeggTilListe() {
        //Empty constructor required for DialogFragment
    }

    //Container Activity must implement this interface
    public interface OnNewBolkFinished {
        public void sendBolkenVidere(DataBolk bolken);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnNewBolkFinished) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnNewBolkFinished");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_create, container);
        
        Window window = getDialog().getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE); 
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        
        mAvbryt  = (Button)view.findViewById(R.id.dialog_button_avbryt);
        mLeggTil = (Button)view.findViewById(R.id.dialog_button_legg_til);
        
        mSted   = (EditText)view.findViewById(R.id.dialog_input_sted);
        mBruker = (EditText)view.findViewById(R.id.dialog_input_bruker);
        mPass   = (EditText)view.findViewById(R.id.dialog_input_passord);
        
        mAvbryt.setOnClickListener(this);
        mLeggTil.setOnClickListener(this);
        
        //Add listeners to EditText fields, to enable "Legg Til" button when all fields populated.
        mSted.addTextChangedListener(textWatcher);
        mBruker.addTextChangedListener(textWatcher);
        mPass.addTextChangedListener(textWatcher);

        //Run once to disable "Legg Til" button if any EditText field is empty.
        checkInput();

        return view;
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
        	case R.id.dialog_button_avbryt:
        		//Do Something
        		getDialog().dismiss();
        		break;
        	case R.id.dialog_button_legg_til:
        		bolken = new DataBolk(mSted.getText().toString(), mBruker.getText().toString(), mPass.getText().toString());
        		mCallback.sendBolkenVidere(bolken);
        		getDialog().dismiss();
        		break;
		}
	}
	
	@SuppressLint("NewApi")
	private void checkInput(){

        String s1 = mSted.getText().toString();
        String s2 = mBruker.getText().toString();
        String s3 = mPass.getText().toString();

        //Method isEmpty() (more efficient) is not available prior to Android version 9.
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD){
        	mLeggTil.setEnabled(!s1.isEmpty() && !s2.isEmpty() && !s3.isEmpty());
        } else {
        	if (s1.length() > 0 && s2.length() > 0 && s3.length() > 0) {
                mLeggTil.setEnabled(true);
            } else {
            	mLeggTil.setEnabled(false);
            }
        } 
        
    }
}
