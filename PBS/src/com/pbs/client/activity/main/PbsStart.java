///////////////////////////////
//Ÿ��Ʋ�� ��������
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
        
        state = Network_State(); //��Ʈ��ũ ���� ����.
        

        if(state)
        {
	         Intent intent = new Intent(PbsStart.this, GroupList.class);
	         startActivity(intent);
        }
        else
        	AlertShow("Wifi Ȥ�� 3G���� ������� �ʾҰų� ��Ȱ���� �ʽ��ϴ�.��Ʈ��ũ Ȯ���� �ٽ� ������ �ּ���!");
   
        
//        tabHost.addTab(tabHost.newTabSpec("Tab2")
//        		.setIndicator("�� �׷� �����")
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

		// * WIFI ���� Ȯ��//
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		isWifiAvail = ni.isAvailable(); // ��뿩�� Ȯ��
		isWifiConn = ni.isConnected(); // ���ӿ��� Ȯ��

		// * 3G ���� Ȯ��//
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		isMobileAvail = ni.isAvailable(); // ��뿩�� Ȯ��
		isMobileConn = ni.isConnected(); // ���ӿ��� Ȯ��

		// ** ���� ���¸� ��� Ȯ�� �Ҽ� �ֵ��� �޽����� ��� �ݴϴ�. **//

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