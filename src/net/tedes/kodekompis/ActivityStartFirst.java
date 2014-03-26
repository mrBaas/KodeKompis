package net.tedes.kodekompis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

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
			// If the user is currently looking at the first step, allow the system to handle the
			// Back button. This calls finish() on this activity and pops the back stack.
			super.onBackPressed();
		} else {
			// Otherwise, select the previous step.
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
	}

	/**
	 * A simple pager adapter that represents ScreenSlidePageFragment objects, in
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