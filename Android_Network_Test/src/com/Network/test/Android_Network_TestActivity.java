package com.Network.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class Android_Network_TestActivity extends Activity
{
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);       
       
        //* WIFI 상태 확인//
        boolean isWifiAvail = ni.isAvailable();  // 사용여부 확인
        boolean isWifiConn = ni.isConnected(); //접속여부 확인
        
        //* 3G 상태 확인//
        ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileAvail = ni.isAvailable(); // 사용여부 확인
        boolean isMobileConn = ni.isConnected(); //접속여부 확인

        String status = "WiFi\nAvail = " + isWifiAvail + "\nConn = "
          + isWifiConn + "\nMobile\nAvail = " + isMobileAvail
          + "\nConn = " + isMobileConn + "\n";

        TextView state = (TextView)findViewById(R.id.result);
        
        state.setText(status);
                
        //** 연결 상태를 즉시 확인 할수 있도록 메시지를 띄워 줍니다. **//
        
        if (!isWifiConn && !isMobileConn) 
        {
            AlertShow("Wifi 혹은 3G망이 연결되지 않았거나 원활하지 않습니다.네트워크 확인후 다시 접속해 주세요!");            
        }
        else if(isWifiConn)
        {
        	 AlertShow("Wifi 연결 상태입니다.");   
        }
        else
        {
        	AlertShow("3G 연결 상태입니다.");  
        }
                
	}

    private void AlertShow(String msg) {

        AlertDialog.Builder alert_internet_status = new AlertDialog.Builder(
                this);
        alert_internet_status.setTitle("Warning");
        alert_internet_status.setMessage(msg);
        alert_internet_status.setPositiveButton("close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); 
                        
                    }
                });
        alert_internet_status.show();
    }
	}