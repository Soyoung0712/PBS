///////////////////////////////
//타이틀바 만들어야함
///////////////////////////////

package com.pbs.client.activity.main;

import com.pbs.client.R;
import com.pbs.client.activity.mygroup.GroupList;
import com.pbs.client.activity.newgroup.CreateGroup;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class PbsStart extends TabActivity {
	
	boolean isWifiAvail;
	boolean isWifiConn;
	boolean isMobileAvail;
	boolean isMobileConn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        boolean state;
        
        state = Network_State(); //네트워크 상태 감지.
        

        if(state)
        {
	         Intent intent = new Intent(PbsStart.this, GroupList.class);
	         startActivity(intent);
        }
        else
        	AlertShow("Wifi 혹은 3G망이 연결되지 않았거나 원활하지 않습니다.네트워크 확인후 다시 접속해 주세요!");
   
        
//        tabHost.addTab(tabHost.newTabSpec("Tab2")
//        		.setIndicator("새 그룹 만들기")
//        		.setContent(new Intent(this, CreateGroup.class)));
        
	}

    public void onPause()
    {
    	super.onPause();
    	
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

		// ** 연결 상태를 즉시 확인 할수 있도록 메시지를 띄워 줍니다. **//

		if (!isWifiConn && !isMobileConn) //
		{		
			return false;
		}
		else return true;
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
                        finish();
                    }
                });
        alert_internet_status.show();
  
    }
}