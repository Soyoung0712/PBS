package com.pbs.client.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceManager {
	
	static private String myPhoneNumber = null; 
	/**
	 *  �� �� ��ȭ��ȣ ��������
	 * @param activity
	 * @return
	 */
	static public String getMyPhoneNumber(Activity activity) {
		if( myPhoneNumber != null ) {
			return myPhoneNumber;
		}else {
			TelephonyManager tmg = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
			myPhoneNumber = tmg.getLine1Number();
			return myPhoneNumber;
		}			
	}
}
