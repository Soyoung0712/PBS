package com.pbs.client.util;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;

public class AddressUtil {

	// ��� �߰�
	public void addContact(Activity activity, String groupTitle, String name, String phoneNumber) {
		
		String kGroupId = getGroupId(activity, groupTitle);
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		int rawContactInsertIndex = ops.size();
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null)
				.build());
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
				.withValue(ContactsContract.Data.MIMETYPE,	ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,name)
				.build());
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,rawContactInsertIndex)
				.withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, kGroupId)
				.build());
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
				.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,phoneNumber)
				.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,	ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
				.build());
		
		try {
			activity.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}
	}

	// �׷� ���̵� ��������
	private String getGroupId(Activity activity, String groupTitle) {
		String kGroupId = checkHasGroup(activity, groupTitle);
		if (kGroupId == null) {
			// �׷��߰�
			ArrayList<ContentProviderOperation> opsGroup = new ArrayList<ContentProviderOperation>();
			opsGroup.add(ContentProviderOperation
					.newInsert(ContactsContract.Groups.CONTENT_URI)
					.withValue(ContactsContract.Groups.TITLE, groupTitle)
					.withValue(ContactsContract.Groups.GROUP_VISIBLE, true)
					.withValue(ContactsContract.Groups.ACCOUNT_NAME,groupTitle)
					.withValue(ContactsContract.Groups.ACCOUNT_TYPE,groupTitle).build());
			try {
				Log.w("###", "�׷��߰� : " + kGroupId + "|" + groupTitle);
				activity.getContentResolver().applyBatch(ContactsContract.AUTHORITY,	opsGroup);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return checkHasGroup(activity, groupTitle);
	}

	// �׷��� �ִ��� �˻�
	private String checkHasGroup(Activity activity, String groupTitle) {
		String selection = ContactsContract.Groups.DELETED + "=? and "+ ContactsContract.Groups.GROUP_VISIBLE + "=?";
		String[] selectionArgs = { "0", "1" };
		Cursor cursor = activity.getContentResolver().query(ContactsContract.Groups.CONTENT_URI, null, selection, selectionArgs, null);
		cursor.moveToFirst();
		int len = cursor.getCount();
		String kGroupId = null;
		for (int i = 0; i < len; i++) {
			String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups._ID));
			String title = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE));
			Log.w("###", id + " | " + title);
			if (title.equals(groupTitle)) {
				kGroupId = id;
				break;
			}
			cursor.moveToNext();
		}
		cursor.close();
		return kGroupId;
	}
}
