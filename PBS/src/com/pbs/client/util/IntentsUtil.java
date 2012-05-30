package com.pbs.client.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class IntentsUtil {

	/**
	 * 전화걸기
	 * @param activity
	 * @param phoneNumber
	 */
	static public void phoneCall(Activity activity,  String phoneNumber) {
		Intent call = new Intent();
		call.setAction(android.content.Intent.ACTION_CALL);
		call.setData(Uri.parse("tel:" + phoneNumber));
		activity.startActivity(call);
	}
	
}
