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
       
        //* WIFI ���� Ȯ��//
        boolean isWifiAvail = ni.isAvailable();  // ��뿩�� Ȯ��
        boolean isWifiConn = ni.isConnected(); //���ӿ��� Ȯ��
        
        //* 3G ���� Ȯ��//
        ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileAvail = ni.isAvailable(); // ��뿩�� Ȯ��
        boolean isMobileConn = ni.isConnected(); //���ӿ��� Ȯ��

        String status = "WiFi\nAvail = " + isWifiAvail + "\nConn = "
          + isWifiConn + "\nMobile\nAvail = " + isMobileAvail
          + "\nConn = " + isMobileConn + "\n";

        TextView state = (TextView)findViewById(R.id.result);
        
        state.setText(status);
                
        //** ���� ���¸� ��� Ȯ�� �Ҽ� �ֵ��� �޽����� ��� �ݴϴ�. **//
        
        if (!isWifiConn && !isMobileConn) 
        {
            AlertShow("Wifi Ȥ�� 3G���� ������� �ʾҰų� ��Ȱ���� �ʽ��ϴ�.��Ʈ��ũ Ȯ���� �ٽ� ������ �ּ���!");            
        }
        else if(isWifiConn)
        {
        	 AlertShow("Wifi ���� �����Դϴ�.");   
        }
        else
        {
        	AlertShow("3G ���� �����Դϴ�.");  
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