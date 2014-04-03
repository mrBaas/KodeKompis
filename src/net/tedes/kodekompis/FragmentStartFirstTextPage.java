package net.tedes.kodekompis;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentStartFirstTextPage extends Fragment implements OnClickListener {

	//Expected format: R.string.stringsourcevariable, R.drawable.imagesourcevariable
	//Use 0 (int) to feed empty sources into fragment.
	public static final FragmentStartFirstTextPage newInstance(int sourceText, int sourceImg) {
		FragmentStartFirstTextPage fragment = new FragmentStartFirstTextPage();

        final Bundle args = new Bundle(2);
        args.putInt(Tedes.EXTRA_START_FIRST_TEXTSOURCE, sourceText);
        args.putInt(Tedes.EXTRA_START_FIRST_IMGSOURCE, sourceImg);
        fragment.setArguments(args);

        return fragment;
    }
	
	//Callback for ActivityStartFirst,
	//with Buttons for navigating Previous/Next in the StartFirst Slides.
	private InterfacePageNavigator mCallback;
	private Button bPrev, bNext;
	private boolean bPrevEnabled = true;
	private boolean bNextEnabled = true;
	
	private int		 mTextSource;
	private TextView mText;
	
	private int		  mImageTopSource;
	private ImageView mImageTop;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Handle extra variables passed in by newInstance method.
        mTextSource     = getArguments().getInt(Tedes.EXTRA_START_FIRST_TEXTSOURCE);
        mImageTopSource = getArguments().getInt(Tedes.EXTRA_START_FIRST_IMGSOURCE);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_layout_start_first_textpage, container, false);

        mImageTop = (ImageView)rootView.findViewById(R.id.pageImageTop);
        mImageTop.setImageResource(mImageTopSource);
        
        mText = (TextView)rootView.findViewById(R.id.pageText);
        mText.setText(getString(mTextSource));
        
        bPrev = (Button)rootView.findViewById(R.id.buttonPrevious);
        bNext = (Button)rootView.findViewById(R.id.buttonNext);
        
        bPrev.setOnClickListener(this);
        bNext.setOnClickListener(this);
        
        bPrev.setEnabled(bPrevEnabled);
        bNext.setEnabled(bNextEnabled);
                
        return rootView;
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
	public void onClick(View v) {
		switch(v.getId()) {
        	case R.id.buttonPrevious:
        		mCallback.pagePrevious();
        		break;
        	case R.id.buttonNext:
        		mCallback.pageNext();
        		break;
		}
	}
	
	//May be called prior to onCreateView (usually is) 
	public void setButtonPrevEnabled(boolean enabled){
		this.bPrevEnabled = enabled;
		if(bPrev != null){
			this.bPrev.setEnabled(enabled);
		}
	}
	
	//May be called prior to onCreateView (usually is) 
	public void setButtonNextEnabled(boolean enabled){
		this.bNextEnabled = enabled;
		if(bNext != null){
			this.bNext.setEnabled(enabled);
		}
	}
}