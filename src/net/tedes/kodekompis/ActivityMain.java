package net.tedes.kodekompis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

public class ActivityMain extends FragmentActivity {

	private static final int NUM_PAGES = 3;
	//The pager widget, handles animation and allows swiping
	private ViewPager mPager;
	
	//The pager adapter, which provides the pages to the view pager widget.
	private PagerAdapter mPagerAdapter;
	
	@Override
	protected void onStop() {
	    super.onStop();  // Always call the superclass method first
	    selectFragment();
	}
	
	@Override
	protected void onRestart() {
	    super.onStop();  // Always call the superclass method first
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_main);
		
		//Henter ut getSupportFragmentManager() i stede for getFragmentManager()
		//St�tter tidligere android versjoner
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null) {
						
			selectFragment();
		}
	}
	
    @Override
    public void onBackPressed() {
        if (mPager == null || mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
	
    private void selectFragment(){
    	FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
    	
    	//Keep track of how many times the application (ActivityMain) has been launched.
		int startcounter = PreferencesManager.getInt(getBaseContext(), "startcounter");
		Log.d("Martin", "startcounter: "+startcounter);
		
		//Do something special on first time start
		//TODO: Limit counter to first start only. After it works.
		if(startcounter < 100) {
			// Instantiate a ViewPager and a PagerAdapter.
	        mPager = (ViewPager) findViewById(R.id.pager);
	        mPager.setPageTransformer(true, new DepthPageTransformer());
	        mPagerAdapter = new ScreenSlidePagerAdapter(fm);
	        mPager.setAdapter(mPagerAdapter);
			//fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		} else {
			//Normal start
			//Check how many consecutive failed logins, before adding FragmentCodePanel
			int failedlogins = PreferencesManager.getInt(getBaseContext(), "failedlogins");
			Log.d("Martin", "check failed logins on start: "+failedlogins);
			
			if(failedlogins > 4) {
				Toast.makeText(getBaseContext(), "Too many failed logins", 3000);
				//Do not proceed
			} else {
				fragment = new FragmentCodePanel();
				fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
			}
		}
    }
    
	/**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	switch (position){
        		case 0:
        			//Toast.makeText(getBaseContext(), "case 0", Toast.LENGTH_LONG).show();
        			return new FragmentWelcome1();
        		case 1:
        			//Toast.makeText(getBaseContext(), "case 1", Toast.LENGTH_LONG).show();
        			return new FragmentWelcome2();
        		case 2:
        			//Toast.makeText(getBaseContext(), "case 2", Toast.LENGTH_LONG).show();
        			return new FragmentWelcome3();
        			default:
        				Log.e("Martin", "MainActivity PagerAdapter out of bounds. Pos: "+position);
        				throw new RuntimeException();
        	}
        	
            //return new FragmentWelcome();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
