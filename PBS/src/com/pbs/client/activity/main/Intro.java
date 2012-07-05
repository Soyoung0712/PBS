package com.pbs.client.activity.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.pbs.client.R;
import com.pbs.client.activity.mygroup.GroupList;

public class Intro extends Activity {


	boolean state;
	
	boolean isWifiAvail;
	boolean isWifiConn;
	boolean isMobileAvail;
	boolean isMobileConn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_main);// 로딩 이미지 있는곳
		
		initialize();
		
		

	
	}

	private void initialize() {

		Handler handler = new Handler() {
			
			public void handleMessage(Message msg) {
				state = Network_State();
				
				
				if (state)
				{
				
					startActivity(new Intent(Intro.this, GroupList.class));// 이동될 클래스
					
				}
				else
				{
					AlertShow("단말기의 네트워크 상태를 확인해주세요.");
				
				}
							
			}
		};

		handler.sendEmptyMessageDelayed(0, 1000); // ms, 1.5초후 종료시킴		
	}
	
	@Override
	public void onRestart()
	{
		super.onRestart();
		
		finish();
		
	}
	
	public boolean Network_State()
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		// * WIFI 상태 확인//
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		isWifiAvail = ni.isAvailable(); // 사용여부 확인
		isWifiConn = ni.isConnected(); // 접속여부 확인

		// * 3G 상태 확인//
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		isMobileAvail = ni.isAvailable(); // 사용여부 확인
		isMobileConn = ni.isConnected(); // 접속여부 확인
		
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
		isMobileAvail = ni.isAvailable();
		isMobileConn = ni.isConnected();

		// ** 연결 상태를 즉시 확인 할수 있도록 메시지를 띄워 줍니다. **//

		if (!isWifiConn && !isMobileConn) //
		{
			return false;
		}
		else
			return true;
	}

	private void AlertShow(String msg)
	{

		AlertDialog.Builder alert_internet_status = new AlertDialog.Builder(this);
		alert_internet_status.setTitle("네트워크 오류");
		alert_internet_status.setMessage(msg);
	//	alert_internet_status.setIcon(R.drawable.warning);  * 이미지 *
		alert_internet_status.setPositiveButton("close", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				finish();
			}
		});
		alert_internet_status.show();

	}
}