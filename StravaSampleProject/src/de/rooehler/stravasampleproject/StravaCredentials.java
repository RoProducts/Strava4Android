package de.rooehler.stravasampleproject;

public class StravaCredentials {
	
	private int mAppId;
	
	private String mStravaSecret;


	public StravaCredentials(int pAppId, String pStravaSecret) {

		this.mAppId = pAppId;
		
		this.mStravaSecret = pStravaSecret;
		
	}

	public int getAppId() {
		return mAppId;
	}

	public String getStravaSecret() {
		return mStravaSecret;
	}
	
	public boolean hasValidCredentials(){
		
		return mAppId > 0 && mStravaSecret != null;
		
	}
	

}
