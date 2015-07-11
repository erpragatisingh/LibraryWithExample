package com.example.contact;

import java.util.ArrayList;

import com.example.contact.business.AsyncContactReanTIme;
import com.example.contact.business.Contact;
import com.example.contact.business.UserAdapter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	ArrayList<Contact> list;
	int count = 1;
	UserAdapter adapter;
	ProgressBar progressbar;
	ListView lvUser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);

		progressbar = (ProgressBar) findViewById(R.id.progressbar);
		lvUser = (ListView) findViewById(R.id.lvUser);
		list = new ArrayList<Contact>();
		// list = UserBusiness.getAllUsers(MainActivity.this);
		adapter = new UserAdapter(this,list);
		lvUser.setAdapter(adapter);
		new LoadContacAsyn().execute();
		this.getApplicationContext()
				.getContentResolver()
				.registerContentObserver(ContactsContract.Contacts.CONTENT_URI,
						true, contentObserver);
	}

	MyContentObserver contentObserver = new MyContentObserver();

	private class MyContentObserver extends ContentObserver {

		public MyContentObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);

			new Thread(new AsyncContactReanTIme(MainActivity.this, list,adapter))
					.start();
		}

	}

	

	public class LoadContacAsyn extends AsyncTask<Void, Void, Void> {
		boolean isSynchronous = false;

		@Override
		protected Void doInBackground(Void... params) {

			// if (list.size() == 0) {
			StringBuffer buffer = new StringBuffer();
			int version_sdk;
			try {
				version_sdk = Integer.valueOf(android.os.Build.VERSION.SDK);
			} catch (NumberFormatException e) {
				version_sdk = 0;
			}
			ContentResolver cr = getContentResolver();
			Cursor pCur = null;
			if (version_sdk > 11) {
				pCur = cr.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null, null, null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " ASC");
			} else {

				pCur = cr
						.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
								new String[] {
										ContactsContract.CommonDataKinds.Phone._ID,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
										ContactsContract.CommonDataKinds.Phone.NUMBER,
										ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME },
								null,
								null,
								ContactsContract.CommonDataKinds.Phone.CONTACT_ID
										+ " ASC");
			}
			while (pCur.moveToNext()) {
				String contact_id = pCur
						.getString(pCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
				String contact_name = pCur
						.getString(pCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				String contact_name_order = "";

				String phone = pCur
						.getString(pCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				int phoneId = pCur
						.getInt(pCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
				String avatar = "";
				if (version_sdk > 11) {
					avatar = pCur
							.getString(pCur
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
				}
				if (avatar == null)
					avatar = "";

				Contact contact = new Contact(contact_id, contact_name, phone,
						phoneId, avatar);
				list.add(contact);

			}

			pCur.close();

			// }

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			progressbar.setVisibility(View.GONE);
			lvUser.setVisibility(View.VISIBLE);
			adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

	}
}