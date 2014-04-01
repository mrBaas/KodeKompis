package net.tedes.kodekompis;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

    // Container Activity must implement this interface
    public interface OnNewBolkFinished {
        public void sendBolkenVidere(DataBolk bolken);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnNewBolkFinished) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnNewBolkFinished");
        }
    }

	
	private DataBolk bolken;
	
	private Button mAvbryt;
	private Button mLeggTil;
	
	private EditText mSted;
	private EditText mBruker;
	private EditText mPass;

    public FragmentLeggTilListe() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_create, container);
        
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE); 
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        
      
        
        
        mAvbryt = (Button)view.findViewById(R.id.dialog_button_avbryt);
        mLeggTil = (Button)view.findViewById(R.id.dialog_button_legg_til);
        
        mSted = (EditText)view.findViewById(R.id.dialog_input_sted);
        mBruker = (EditText)view.findViewById(R.id.dialog_input_bruker);
        mPass = (EditText)view.findViewById(R.id.dialog_input_passord);
        
        mAvbryt.setOnClickListener(this);
        mLeggTil.setOnClickListener(this);
        
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
}
