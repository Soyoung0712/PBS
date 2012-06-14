package com.pbs.client.activity.main;

 
 
import android.app.ProgressDialog;
import android.content.Context;
import android.os.HandlerThread;

 

//* HendlerThread�κ��� ���
public class WaitDlg extends HandlerThread {
	Context mContext;
	String mTitle;
	String mMsg;
	ProgressDialog mProgress;
	
	public WaitDlg(Context context, String title, String msg) {
		super("waitdlg");
		mContext = context;
		mTitle = title;
		mMsg = msg;
		
		setDaemon(true);
	}
	
	protected void onLooperPrepared() {
		mProgress = new ProgressDialog(mContext);
		mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgress.setTitle(mTitle);
		mProgress.setMessage(mMsg);
		mProgress.setCancelable(false);
		mProgress.show();
	}
	
	// ������ �ܺο��� ���Ḧ ���� ȣ��ȴ�.
	public static void stop(WaitDlg dlg) {
		dlg.mProgress.dismiss();
		// ��ȭ���ڰ� ������� ������ ����� ��� ��
		try { Thread.sleep(100); } catch (InterruptedException e) {;}
		// �޽��� ���� �����ؾ� ��
		dlg.getLooper().quit();
	}
}
//*/
