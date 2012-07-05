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
		setContentView(R.layout.start_main);// �ε� �̹��� �ִ°�
		
		initialize();
		
		

	
	}

	private void initialize() {

		Handler handler = new Handler() {
			
			public void handleMessage(Message msg) {
				state = Network_State();
				
				
				if (state)
				{
				
					startActivity(new Intent(Intro.this, GroupList.class));// �̵��� Ŭ����
					
				}
				else
				{
					AlertShow("�ܸ����� ��Ʈ��ũ ���¸� Ȯ�����ּ���.");
				
				}
							
			}
		};

		handler.sendEmptyMessageDelayed(0, 1000); // ms, 1.5���� �����Ŵ		
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

		// * WIFI ���� Ȯ��//
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		isWifiAvail = ni.isAvailable(); // ��뿩�� Ȯ��
		isWifiConn = ni.isConnected(); // ���ӿ��� Ȯ��

		// * 3G ���� Ȯ��//
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		isMobileAvail = ni.isAvailable(); // ��뿩�� Ȯ��
		isMobileConn = ni.isConnected(); // ���ӿ��� Ȯ��
		
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
		isMobileAvail = ni.isAvailable();
		isMobileConn = ni.isConnected();

		// ** ���� ���¸� ��� Ȯ�� �Ҽ� �ֵ��� �޽����� ��� �ݴϴ�. **//

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
		alert_internet_status.setTitle("��Ʈ��ũ ����");
		alert_internet_status.setMessage(msg);
	//	alert_internet_status.setIcon(R.drawable.warning);  * �̹��� *
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