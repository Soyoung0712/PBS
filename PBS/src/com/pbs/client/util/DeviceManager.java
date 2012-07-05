package com.pbs.client.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceManager {
	
	static private String myPhoneNumber = null; 
	/**
	 *  내 폰 전화번호 가져오기
	 * @param activity
	 * @return
	 */
	static public String getMyPhoneNumber(Activity activity) {
		if( myPhoneNumber != null ) {
			return myPhoneNumber;
		}else {
			TelephonyManager tmg = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
			myPhoneNumber = tmg.getLine1Number();
			
			if (myPhoneNumber != null) {
				if (myPhoneNumber.startsWith("+82"))
					myPhoneNumber =  "0" + myPhoneNumber.substring(3);				
			}
			
			return myPhoneNumber;
		}			
	}
}
