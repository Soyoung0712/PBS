package com.pbs.client.util;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceManager {
	
	/**
	 *  �� �� ��ȭ��ȣ ��������
	 * @param activity
	 * @return
	 */
	static public String getMyPhoneNumber(Activity activity) {
		TelephonyManager tmg = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
		return tmg.getLine1Number();
	}
}
