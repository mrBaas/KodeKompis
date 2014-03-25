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
