package net.tedes.kodekompis;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public class Fader {
	/**
     * handles all subclasses of View : TextView, Button, ImageView etc..
     * given the component's id in their layout file
     * */
    public static void FadeIn(Activity act, int viewId) {
 
        // load animation XML resource under res/anim
        Animation animation  = AnimationUtils.loadAnimation(act, R.anim.fadein);
        if(animation == null){
        	//Do not care
        	return;
        }
        // reset initialization state
        animation.reset();
        // find View by its id attribute in the XML
        final View v = act.findViewById(viewId);
        
        animation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation arg0) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        // cancel any pending animation and start this one
        if (v != null){
          v.clearAnimation();
          v.startAnimation(animation);
          v.setVisibility(View.VISIBLE);
          //Random comment
        }
    }
    
    public static void FadOut(Activity act, int viewId) {
    	 
        // load animation XML resource under res/anim
        Animation animation  = AnimationUtils.loadAnimation(act, R.anim.fadeout);
        if(animation == null){
        return; // here, we don't care
        }
        // reset initialization state
        animation.reset();
        // find View by its id attribute in the XML
        final View v = act.findViewById(viewId);
        // cancel any pending animation and start this one
        animation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation arg0) {
				v.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				v.setVisibility(View.VISIBLE);
			}
        	
        });
        
        if (v != null){
          v.clearAnimation();
          v.startAnimation(animation);
        }
    }
    
    public static void slideUp(Activity act, int viewId) {
   	 
        // load animation XML resource under res/anim
        Animation animation  = AnimationUtils.loadAnimation(act, R.anim.slideup);
        if(animation == null){
        return; // here, we don't care
        }
        // reset initialization state
        animation.reset();
        // find View by its id attribute in the XML
        final View v = act.findViewById(viewId);
        // cancel any pending animation and start this one
        animation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation arg0) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				v.setVisibility(View.GONE);
			}
        	
        });
        
        if (v != null){
          v.clearAnimation();
          v.startAnimation(animation);
        }
    }
    
    public static void slideDown(Activity act, int viewId) {
      	 
        // load animation XML resource under res/anim
        Animation animation  = AnimationUtils.loadAnimation(act, R.anim.slidedown);
        if(animation == null){
        return; // here, we don't care
        }
        // reset initialization state
        animation.reset();
        // find View by its id attribute in the XML
        final View v = act.findViewById(viewId);
        // cancel any pending animation and start this one
        animation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation arg0) {
				
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				v.setVisibility(View.VISIBLE);
			}
        	
        });
        
        if (v != null){
          v.clearAnimation();
          v.startAnimation(animation);
        }
    }
    
    
}
