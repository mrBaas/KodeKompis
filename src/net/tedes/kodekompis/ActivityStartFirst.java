package net.tedes.kodekompis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

public class ActivityStartFirst extends FragmentActivity 
			implements InterfacePageNavigator{

	private static final int NUM_PAGES = 4;
	//The pager widget, handles animation and allows swiping
	private ViewPager mPager;

	//The pager adapter, which provides the pages to the view pager widget.
	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_start_first);
		FragmentManager fm = getSupportFragmentManager();

		// Instantiate a ViewPager and a PagerAdapter for FragmentStartFirst fragments.
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setPageTransformer(true, new DepthPageTransformer());
		mPagerAdapter = new ScreenSlidePagerAdapter(fm);
		mPager.setAdapter(mPagerAdapter);
	}

	@Override
	public void onBackPressed() {
		if (mPager == null || mPager.getCurrentItem() == 0) {
			//If looking at first slide, go back to previous activity
			super.onBackPressed();
		} else if(mPager.getCurrentItem() == NUM_PAGES-1){
			//If looking at last page (with code panel)
			//treat as slettNummer() if any digits have been entered,
			//otherwise goto previous page.
			FragmentStartFirstCodePage f = (FragmentStartFirstCodePage)((ScreenSlidePagerAdapter) mPagerAdapter).getRegisteredFragment(mPager.getCurrentItem());
			if(f.getKodeLength() == 0){
				pagePrevious();
			} else {
				f.slettNummer();
			}
		} else {
			// Otherwise, select the previous page in the welcome slide thing.
			pagePrevious();
		}
	}

	@Override
	public void pagePrevious() {
		mPager.setCurrentItem(mPager.getCurrentItem()-1, true);
	}
	
	@Override
	public void pageNext() {
		mPager.setCurrentItem(mPager.getCurrentItem()+1, true);
	}
	
	/**
	 * A simple pager adapter that represents ScreenSlidePageFragment objects, in
	 * sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
		
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment f = getRegisteredFragment(position);
			if (f != null) {
				return f;
			} else {
				switch (position){
					case 0:
						return FragmentStartFirstTextPage.newInstance(R.string.welcomestring1, R.drawable.code1);
					case 1:
						return FragmentStartFirstTextPage.newInstance(R.string.welcomestring2, 0);
					case 2:
						return FragmentStartFirstTextPage.newInstance(R.string.welcomestring3, 0);
					case 3:
						return new FragmentStartFirstCodePage();
					default:
						Log.e("Martin", "MainActivity PagerAdapter out of bounds. Pos: "+position);
						throw new RuntimeException();
				}
			}

			//Her står det en jævla kommentar.
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}

	    @Override
	    public Object instantiateItem(ViewGroup container, int position) {
	    	Fragment f = getRegisteredFragment(position);
	    	if(f != null) {
	    		return f;
	    	} else {
		        Fragment fragment = (Fragment) super.instantiateItem(container, position);
		        registeredFragments.put(position, fragment);
		        
		        //Disable "previous" button on first, and "next" button on last
		        switch (position){
					case 0:
						//First page
						((FragmentStartFirstTextPage) fragment).setButtonPrevEnabled(false);
						break;
					case NUM_PAGES-1:
						//Last page
						((FragmentStartFirstCodePage) fragment).setButtonNextEnabled(false);
						break;
					default:
						//Do nothing
					}
						
		        return fragment;
	    	}
	    }

	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	    	if(position != NUM_PAGES-1) {
		        registeredFragments.remove(position);
		        super.destroyItem(container, position, object);
	    	} else {
	    		//Do not destroy CodePage fragment just because the stupid adapter wants to.
	    		//Alternatively save state and use it when creating another CodePage fragment
	    	}
	    }

	    public Fragment getRegisteredFragment(int position) {
	        return registeredFragments.get(position);
	    }
	    
	}
}