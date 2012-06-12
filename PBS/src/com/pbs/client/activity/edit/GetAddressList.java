package com.pbs.client.activity.edit;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.R;
import com.android.R.id;
import com.android.R.layout;
import com.pbs.client.model.AddressUser;

public class GetAddressList extends ListActivity {

	private NewArrayAdapter newArrayAdapter = null;
	// ��μ��� Flag (�ʱ� ������ ��μ����� ������ ����)
	boolean allClickStatuFlag = false;

	// ��ȭ��ȣ�� ����Ʈ
	private List<AddressUser> addressUserList;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Ÿ��Ʋ�� ���ֱ�
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.edit_get_address_list);

		// ��ȭ��ȣ �ּҷ� ��������
		ContentResolver cr = getContentResolver();
		Cursor c = this.managedQuery(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);
		startManagingCursor(c);

		// ��ȭ��ȣ �ּҷ� ����� ����
		addressUserList = new ArrayList<AddressUser>();
		while (c.moveToNext()) {

			// ID ����
			String id = c.getString(c
					.getColumnIndex(ContactsContract.Contacts._ID));
			// �̸� ����
			String name = c.getString(c
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

			// ��ȭ��ȣ ����
			if (Integer
					.parseInt(c.getString(c
							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
				Cursor cp = cr.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = ? ", new String[] { id }, null);
				while (cp.moveToNext()) {
					String dial = cp
							.getString(cp
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA1));
					// ��ȭ��ȣ�� �ִ� ��츸 ��ȭ��ȣ�ο� �����ش�.
					if (dial != null && dial.length() > 0) {
						// ����� ���� ����
						AddressUser addressUser = new AddressUser();
						addressUser.setId(id);
						addressUser.setName(name);
						addressUser.setDial(dial);
						// �ּҷ� ��ܿ� �߰�
						addressUserList.add(addressUser);
					}

				}
				cp.close();
			}

			/*
			 * ����,������ ������� �����Ƿ� �ּ�ó�� // ���� ���� int photoID =
			 * c.getInt(c.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
			 * Bitmap icon = null; if (photoID != 0) { Uri uri =
			 * ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,
			 * Long.parseLong(id)); InputStream in =
			 * ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
			 * icon = BitmapFactory.decodeStream(in); }
			 * addressUser.setIcon(icon);
			 * 
			 * // ���� ���� String mail = ""; Cursor cm =
			 * cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
			 * null, ContactsContract.CommonDataKinds.Email.CONTACT_ID +
			 * " = ? ", new String[] { id }, null); while (cm.moveToNext()) {
			 * mail =
			 * cm.getString(cm.getColumnIndex(ContactsContract.CommonDataKinds
			 * .Email.DATA)); } addressUser.setMail(mail); cm.close();
			 */
		}
		c.close();

		// ����Ʈ�信 ����Ʈ ����
		newArrayAdapter = new NewArrayAdapter(this);
		setListAdapter(newArrayAdapter);

		// "��μ���" ��ư ����
		CheckBox allchoice = (CheckBox) findViewById(R.id.checkBoxAll);
		allchoice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				// ��ü ����
				if (allClickStatuFlag) {
					for (int i = 0; i < addressUserList.size(); i++) {
						addressUserList.get(i).setChecked(false);
					}
					allClickStatuFlag = false;

					// ��ü ����
				} else {
					for (int i = 0; i < addressUserList.size(); i++) {
						addressUserList.get(i).setChecked(true);
					}
					allClickStatuFlag = true;
				}
				// List Refresh
				newArrayAdapter.notifyDataSetChanged();
			}
		});

		// "Ȯ��" ��ư
		Button mGroupRestul = (Button) findViewById(R.id.sendmessage);
		mGroupRestul.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Toast.makeText(GetAddressList.this, "�������� �Ϸ�",
						Toast.LENGTH_SHORT).show();

				// ������ ��ȭ��ȣ�� ����
				ArrayList<AddressUser> resultAddressUserList = new ArrayList<AddressUser>();
				for (int i = 0; i < addressUserList.size(); i++) {
					if (addressUserList.get(i).isChecked()) {
						resultAddressUserList.add(addressUserList.get(i));
					}
				}

				// ȣ���� ��Ƽ��Ƽ
				Intent intent = getIntent();
				intent.putExtra("addressUserList", resultAddressUserList);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		// "���" ��ư
		Button mGroupCancel = (Button) findViewById(R.id.cancle);
		mGroupCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Toast.makeText(GetAddressList.this, "���", Toast.LENGTH_SHORT)
						.show();
				finish();
			}
		});

	}

	class NewArrayAdapter extends ArrayAdapter {

		Activity context;

		@SuppressWarnings("unchecked")
		NewArrayAdapter(Activity context) {
			super(context, R.layout.edit_get_address_list_row, addressUserList);
			this.context = context;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			final int pos = position;
			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.edit_get_address_list_row,
					null);

			// ��ȭ��ȣ��
			AddressUser addressUser = addressUserList.get(pos);

			// �̸�
			TextView nameTextView = (TextView) row.findViewById(R.id.name);
			nameTextView.setText(addressUser.getName());

			// ��ȭ��ȣ
			TextView dialTextView = (TextView) row.findViewById(R.id.dial);
			dialTextView.setText(addressUser.getDial());

			// üũ�ڽ� ����
			CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkBox);
			checkBox.setChecked(addressUser.isChecked());
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				// Ŭ���Ҷ� ���� ���� ����
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					addressUserList.get(pos).setChecked(isChecked);
				}

			});

			return row;
		}
	}
}