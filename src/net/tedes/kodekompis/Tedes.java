package net.tedes.kodekompis;

public final class Tedes {

	//References to drawables which is used programmatically (to avoid sifting through code)
	public static final int ICON_LOGINLIGHT_OFF = android.R.drawable.presence_invisible;
	public static final int ICON_LOGINLIGHT_ON  = android.R.drawable.presence_online;
	
	public static final int ICON_STED = R.drawable.tedes_sted;
	public static final int ICON_USER = R.drawable.tedes_user;
	public static final int ICON_PASS = R.drawable.tedes_pass;
	public static final int ICON_TRASH = R.drawable.tedes_button_trash;
	
	public static final int ICON_SORT_ALPHA = R.drawable.tedes_sort_alpha;
	public static final int ICON_SORT_ALPHA_REVERSE = R.drawable.tedes_sort_alpha_rev;
	public static final int ICON_SORT_ADDED = R.drawable.tedes_sort_added;
	public static final int ICON_SORT_ADDED_REVERSE = R.drawable.tedes_sort_added_rev;
	
	//SharedPreferences id string for keeping track of number of starts before reset
	public static final String DEBUG_COUNTER = "debugcounter";
	
	//How many reloads of ActivityMain will trigger reset of all important variables
	public static final int DEBUG_RESET = 10;
	
	//SharedPreferences id string for keeping track of number of application starts (successful logins)
	public static final String START_COUNTER = "startcounter";
	
	//Amount of failed attempts required before punishment by timer
	public static final int FAILED_LOGINS_MAX = 3;
	
	//SharedPreferences id string to keep track of failed logins (out of MAX)
	public static final String FAILED_LOGINS = "failedlogins";
	
	//SharedPreferences id string to keep track of how many consecutive times failedlogins reach MAX.
	public static final String FAILED_LOGINS_ITERATOR = "failedloginsN"; 
	
	//Seconds to wait when failing too many times and being punished by timer
	public static final int FAILED_LOGINS_WAIT = 30; 
	
	//Names of extra arguments to pass into StartFirst page Fragments
	public static final String EXTRA_START_FIRST_TEXTSOURCE = "startfirst_textsource";
	public static final String EXTRA_START_FIRST_IMGSOURCE = "startfirst_imgsource";
	
	//SharedPreferences id string to keep track of how many DataBolks added. Used only for sorting purposes, as elements may be deleted.
	public static final String DATABOLK_COUNTER = "databolkcounter";
	
	//SharedPreferences id string to keep track of active sort method on list view.
	public static final String DATABOLK_SORTING_METHOD = "dbl_sortingmethod";
	
	//Name of extra arguments to pass into LeggTil dialog fragment, for editing existing DataBolk
	public static final String EXTRA_DIALOG_EDIT_DATABOLK = "dialog_edit_databolk";
	

	
}
