package net.tedes.kodekompis;

import java.io.Serializable;
import java.util.UUID;

public class DataBolkEncrypted implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UUID mId;
	private int mNumber;
	private String mSted;
	private String mBrukernavn;
	private String mPassord;

//	public DataBolkEncrypted(UUID mId, String sted, String bruker, String pass) {
//		this.mId = mId;
//		this.mSted = sted;
//		this.mBrukernavn = bruker;
//		this.mPassord = pass;
//	}
	
	public DataBolkEncrypted(DataBolk bolk, String password) {
		byte[] salt1 = Security.generateSalt();
		byte[] salt2 = Security.generateSalt();
		
		this.mId 			= bolk.getId();
		this.mSted 			= bolk.getSted();
		this.mNumber		= bolk.getNumber();
		this.mBrukernavn 	= Security.krypter(bolk.getBrukernavn(), Security.generateKey(password, salt1), salt1);
		this.mPassord		= Security.krypter(bolk.getPassord(), Security.generateKey(password, salt2), salt2);
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
		return this.mId;
	}
	
	public int getNumber() {
		return this.mNumber;
	}
	
}
