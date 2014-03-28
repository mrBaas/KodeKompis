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

public class ActivityStartFirst extends FragmentActivity {

	private static final int NUM_PAGES = 3;
	//The pager widget, handles animation and allows swiping
	private ViewPager mPager;

	//The pager adapter, which provides the pages to the view pager widget.
	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_start_first);
		FragmentManager fm = getSupportFragmentManager();

		// Instantiate a ViewPager and a PagerAdapter for FragmentWelcome.
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
		} else if(mPager.getCurrentItem() == 2){
			//If looking at last page (with code panel)
			//treat as slettNummer() if any digits have been entered,
			//otherwise goto previous page.
			FragmentStartFirst3 f = (FragmentStartFirst3)((ScreenSlidePagerAdapter) mPagerAdapter).getRegisteredFragment(mPager.getCurrentItem());
			if(f.getKodeLength() == 0){
				mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			} else {
				f.slettNummer();
			}
		} else {
			// Otherwise, select the previous page in the welcome slide thing.
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
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
			switch (position){
			case 0:
				//Toast.makeText(getBaseContext(), "case 0", Toast.LENGTH_LONG).show();
				return new FragmentStartFirst1();
			case 1:
				//Toast.makeText(getBaseContext(), "case 1", Toast.LENGTH_LONG).show();
				return new FragmentStartFirst2();
			case 2:
				//Toast.makeText(getBaseContext(), "case 2", Toast.LENGTH_LONG).show();
				return new FragmentStartFirst3();
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

	    @Override
	    public Object instantiateItem(ViewGroup container, int position) {
	        Fragment fragment = (Fragment) super.instantiateItem(container, position);
	        registeredFragments.put(position, fragment);
	        return fragment;
	    }

	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) {
	        registeredFragments.remove(position);
	        super.destroyItem(container, position, object);
	    }

	    public Fragment getRegisteredFragment(int position) {
	        return registeredFragments.get(position);
	    }
		
	}
}