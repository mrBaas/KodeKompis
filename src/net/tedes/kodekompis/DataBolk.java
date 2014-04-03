package net.tedes.kodekompis;

import java.io.Serializable;
import java.util.UUID;

public class DataBolk implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID mId;
	private String mSted;
	private String mBrukernavn;
	private String mPassord;

	public DataBolk(String sted, String bruker, String pass) {
		this.mId = UUID.randomUUID();
		this.mSted = sted;
		this.mBrukernavn = bruker;
		this.mPassord = pass;
	}

	public DataBolk(DataBolkEncrypted bolk, String password){
		this.mId 			= bolk.getmId();
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
	
	public String getmSted() {
		return mSted;
	}

	public void setmSted(String mSted) {
		this.mSted = mSted;
	}

	public String getmBrukernavn() {
		return mBrukernavn;
	}

	public void setmBrukernavn(String mBrukernavn) {
		this.mBrukernavn = mBrukernavn;
	}

	public String getmPassord() {
		return mPassord;
	}

	public void setmPassord(String mPassord) {
		this.mPassord = mPassord;
	}

	public UUID getmId() {
		return mId;
	}
	
	
	
}
