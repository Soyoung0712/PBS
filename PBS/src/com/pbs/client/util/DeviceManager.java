package com.pbs.client.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceManager {
	
	/**
	 *  내 폰 전화번호 가져오기
	 * @param activity
	 * @return
	 */
	static public String getMyPhoneNumber(Activity activity) {
		TelephonyManager tmg = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
		return tmg.getLine1Number();
	}
}
