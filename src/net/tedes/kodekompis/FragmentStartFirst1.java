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

public class FragmentStartFirst1 extends Fragment implements OnClickListener {

	public static final FragmentStartFirst1 newInstance(int sourceText, int sourceImg) {
		FragmentStartFirst1 fragment = new FragmentStartFirst1();

        final Bundle args = new Bundle(2);
        args.putInt(Tedes.EXTRA_START_FIRST_TEXTSOURCE, sourceText);
        args.putInt(Tedes.EXTRA_START_FIRST_IMGSOURCE, sourceImg);
        fragment.setArguments(args);

        return fragment;
    }
	
	//Callback for ActivityStartFirst,
	//with Buttons for navigating Previous/Next in the StartFirst Slides.
	private PageNavigator mCallback;
	Button bPrev, bNext;
	
	private int		 mTextSource;
	private TextView mText;
	
	private int		  mImageTopSource;
	private ImageView mImageTop;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextSource = getArguments().getInt(Tedes.EXTRA_START_FIRST_TEXTSOURCE);
        mImageTopSource = getArguments().getInt(Tedes.EXTRA_START_FIRST_IMGSOURCE);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_layout_start_first_textpage, container, false);

        mImageTop = (ImageView)rootView.findViewById(R.id.pageImageTop);
        mImageTop.setImageResource(mImageTopSource);
        
        //R.string.welcomestring1
        
        mText = (TextView)rootView.findViewById(R.id.pageText);
        mText.setText(getString(mTextSource));
        
        bPrev = (Button)rootView.findViewById(R.id.buttonPrevious);
        bNext = (Button)rootView.findViewById(R.id.buttonNext);
        
        bPrev.setOnClickListener(this);
        bNext.setOnClickListener(this);
        
        //Disable "previous" button on first slide
        bPrev.setEnabled(false);
        
        return rootView;
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (PageNavigator) activity;
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
}