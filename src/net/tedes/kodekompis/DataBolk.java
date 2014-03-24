package net.tedes.kodekompis;

public class DataBolk {
	
	int mId;
	String mSted;
	String mBrukernavn;
	String mPassord;

	public DataBolk(int id, String sted, String bruker, String pass) {
		this.mId = id;
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

	public int getmId() {
		return mId;
	}
	
	
	
}
