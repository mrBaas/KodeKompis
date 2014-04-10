package net.tedes.kodekompis;

import java.io.Serializable;
import java.util.Comparator;
import java.util.UUID;

import android.content.Context;

public class DataBolk implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID mId;
	private int mNumber;
	private String mSted;
	private String mBrukernavn;
	private String mPassord;

	//Used for creating new DataBolk
	public DataBolk(Context context, String sted, String bruker, String pass) {
		this.mId = UUID.randomUUID();
		
		//Get and increase the DataBolk counter which keeps track of how many DataBolks have been saved in total.
		int dbc = PreferencesManager.getInt(context, Tedes.DATABOLK_COUNTER)+1;
		PreferencesManager.setInt(context, Tedes.DATABOLK_COUNTER, dbc);
		
		this.mNumber = dbc;
		this.mSted = sted;
		this.mBrukernavn = bruker;
		this.mPassord = pass;
	}
	
	//Used for creating representation of existing databolk from internal storage
	public DataBolk(DataBolkEncrypted bolk, String password){
		this.mId 			= bolk.getmId();
		this.mNumber 		= bolk.getNumber();
		this.mSted 			= bolk.getmSted();
		this.mBrukernavn 	= Security.dekrypter(bolk.getmBrukernavn(), password);
		this.mPassord 		= Security.dekrypter(bolk.getmPassord(), password);
	}
	
	//Needed to be able to use methods for comparing DataBolk objects.
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		//Object must be DataBolk at this point
		DataBolk test = (DataBolk)obj;
		
		//Object is considered the identical if UUID value is the same.
		return (mId.compareTo(test.mId) == 0);
	}

	//Needed to be able to use methods for comparing DataBolk objects.
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + mId.hashCode();
		return hash;
	}
	
	public String getSted() {
		return mSted;
	}

	public void setSted(String mSted) {
		this.mSted = mSted;
	}

	public String getBrukernavn() {
		return mBrukernavn;
	}

	public void setBrukernavn(String mBrukernavn) {
		this.mBrukernavn = mBrukernavn;
	}

	public String getPassord() {
		return mPassord;
	}

	public void setPassord(String mPassord) {
		this.mPassord = mPassord;
	}

	public UUID getId() {
		return this.mId;
	}
	
	public int getNumber() {
		return this.mNumber;
	}
	
	public enum SortMethod {
		 ALPHA, ALPHA_REVERSE, ADDED, ADDED_REVERSE;
		 
		 public static SortMethod getNext(SortMethod sm) {
			 switch (sm) {
			 	case ALPHA:
			 		return ALPHA_REVERSE;
			 	case ALPHA_REVERSE:
			 		return ADDED;
			 	case ADDED:
			 		return ADDED_REVERSE;
			 	case ADDED_REVERSE:
			 		return ALPHA;
			 	default:
			 		return ADDED;
			 }
		 }
		 
		 public static int getIcon(SortMethod sm) {
			 switch (sm) {
			 	case ALPHA:
			 		return Tedes.ICON_SORT_ALPHA;
			 	case ALPHA_REVERSE:
			 		return Tedes.ICON_SORT_ALPHA_REVERSE;
			 	case ADDED:
			 		return Tedes.ICON_SORT_ADDED;
			 	case ADDED_REVERSE:
			 		return Tedes.ICON_SORT_ADDED_REVERSE;
			 	default:
			 		return Tedes.ICON_SORT_ADDED;
			 }
		 }
		 
		 public static SortMethod toMyEnum (String myEnumString) {
			 try {
				 return valueOf(myEnumString);
			 } catch (Exception ex) {
				 // For error cases
				 return ADDED;
			 }
		 }
	}
	
	public static Comparator<DataBolk> COMPARE_BY_NUMBER_REVERSE = new Comparator<DataBolk>() {
        public int compare(DataBolk one, DataBolk other) {
            return one.mNumber - other.mNumber;
        }
    };
    
    public static Comparator<DataBolk> COMPARE_BY_NUMBER = new Comparator<DataBolk>() {
        public int compare(DataBolk one, DataBolk other) {
            return other.mNumber - one.mNumber;
        }
    };

    public static Comparator<DataBolk> COMPARE_BY_STED = new Comparator<DataBolk>() {
        public int compare(DataBolk one, DataBolk other) {
            return one.mSted.compareTo(other.mSted);
        }
    };
    
    public static Comparator<DataBolk> COMPARE_BY_STED_REVERSE = new Comparator<DataBolk>() {
        public int compare(DataBolk one, DataBolk other) {
            return other.mSted.compareTo(one.mSted);
        }
    };
	
}
