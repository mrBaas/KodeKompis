package net.tedes.kodekompis;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

public class FragmentLeggTilListe extends DialogFragment implements OnClickListener {
	
	private InterfaceBolkManager mCallback;
	private DataBolk bolken;
	
	private Button mAvbryt;
	private Button mLeggTil;
	
	private boolean existing = false;
	private DataBolk existingBolk;
	
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
    
    //Expected format: 
  	public static final FragmentLeggTilListe editBolk(DataBolk bolk) {
  		FragmentLeggTilListe fragment = new FragmentLeggTilListe();

		final Bundle args = new Bundle(1);
		args.putSerializable(Tedes.EXTRA_DIALOG_EDIT_DATABOLK, bolk);
		fragment.setArguments(args);

        return fragment;
      }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (InterfaceBolkManager) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement BolkManager");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Handle extra variables passed in by newInstance (editBolk) method.
        if (getArguments() != null) {
        	DataBolk bolk = (DataBolk)getArguments().getSerializable(Tedes.EXTRA_DIALOG_EDIT_DATABOLK);
        	if(bolk != null) {
        		this.existing 		= true;
        		this.existingBolk	= bolk;
            }
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
        
        if(existing){
        	((TextView)view.findViewById(R.id.dialog_topheader)).setText(R.string.dialog_header_edit);
        	mSted.setText(existingBolk.getmSted());
        	mBruker.setText(existingBolk.getmBrukernavn());
        	mPass.setText(existingBolk.getmPassord());
        	mLeggTil.setText(R.string.dialog_text_submit_edit);
        }
        
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
        		if(existing) {
        			existingBolk.setmSted(mSted.getText().toString());
        			existingBolk.setmBrukernavn(mBruker.getText().toString());
        			existingBolk.setmPassord(mPass.getText().toString());
        			mCallback.updateDataBolk(existingBolk);
        		} else {
	        		bolken = new DataBolk(mSted.getText().toString(), mBruker.getText().toString(), mPass.getText().toString());
	        		mCallback.addDataBolk(bolken);
        		}
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
