package net.tedes.kodekompis;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FragmentStartFirst2 extends Fragment implements OnClickListener{
	
	//Callback for ActivityStartFirst,
	//with Buttons for navigating Previous/Next in the StartFirst Slides.
	private PageNavigator mCallback;
	Button bPrev, bNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_layout_start_first2, container, false);
        
        bPrev = (Button)rootView.findViewById(R.id.buttonPrevious);
        bNext = (Button)rootView.findViewById(R.id.buttonNext);
        
        bPrev.setOnClickListener(this);
        bNext.setOnClickListener(this);

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