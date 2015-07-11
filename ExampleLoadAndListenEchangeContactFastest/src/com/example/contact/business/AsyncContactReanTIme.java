package com.example.contact.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.Toast;

public class AsyncContactReanTIme implements Runnable {

	StringBuffer bufferChange;
	Activity context;
	ArrayList<Contact> list;
	UserAdapter adapter;
	public AsyncContactReanTIme(Activity context, ArrayList<Contact> list,UserAdapter adapter) {
		this.context = context;
		this.list = list;
		this.adapter=adapter;
	}

	@Override
	public void run() {
		bufferChange = new StringBuffer();
		// dong bo
		HashMap<String, Contact> hashMapContact = new HashMap<String, Contact>();
		HashMap<String, String> hashMapDB = new HashMap<String, String>();
		ContentResolver cr = context.getContentResolver();
		Cursor pCur = null;
		int version_sdk;
		try {
			version_sdk = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			version_sdk = 0;
		}
		if (version_sdk > 11) {
			pCur = cr
					.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							new String[] {
									ContactsContract.CommonDataKinds.Phone._ID,
									ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
									ContactsContract.CommonDataKinds.Phone.NUMBER,
									ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
									ContactsContract.CommonDataKinds.Phone.PHOTO_URI },
							null, null,
							ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
									+ " ASC");
		} else {
			pCur = cr
					.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							new String[] {
									ContactsContract.CommonDataKinds.Phone._ID,
									ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
									ContactsContract.CommonDataKinds.Phone.NUMBER,
									ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME },
							null, null,
							ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
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
			if (avatar == null) {
				avatar = "";
			}

			Contact contact = new Contact(contact_id, contact_name, phone,
					phoneId, avatar);
			hashMapContact.put(contact_id + phone, contact);
		}
		 getAllContactCompare(context, hashMapDB,
		 hashMapContact, bufferChange);

		pCur.close();
		for (Map.Entry<String, Contact> entry : hashMapContact.entrySet()) {
			String key = entry.getKey();
			if (!hashMapDB.containsKey(key)) {

				 bufferChange.append(entry.getValue().getPhone());
				 bufferChange.append("|");
				 bufferChange.append(entry.getValue().getContact_name());
				 bufferChange.append("|");
				 bufferChange.append("Inser");
				 bufferChange.append("-;-");
				 list.add(entry.getValue());

			}

		}
		onPostExecute();
	}

	protected void onPostExecute() {

		if (bufferChange.length() > 0) {
		final	String dataContacts = bufferChange.substring(0,
					bufferChange.length() - 1);
			context.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					Toast.makeText(context, dataContacts, Toast.LENGTH_LONG).show();
					adapter.notifyDataSetChanged();
					
				}
			});
			

		}

	}

	public void getAllContactCompare(Context context,
			HashMap<String, String> hashMapDB,
			HashMap<String, Contact> hashMapContact, StringBuffer bufferContacts) {

		for (int i = 0; i < list.size(); i++) {

			// int id = c.getInt(c.getColumnIndex(index));
			String contactId = list.get(i).getContact_id();

			String phone = list.get(i).getPhone();
			String key = contactId + phone;
			String contactName1 = list.get(i).getContact_name();

			if (!hashMapContact.containsKey(key)) {
				// deleteById(context, id);
				bufferContacts.append(phone);
				bufferContacts.append("|");
				bufferContacts.append(contactName1);
				bufferContacts.append("|");
				bufferContacts.append("Delete");
				bufferContacts.append("-;-");
			} else {
				Contact contact = hashMapContact.get(key);
				if (!compareTwoString(contact.getContact_name(), contactName1)) {
					// update

					bufferContacts.append(phone);
					bufferContacts.append("|");
					bufferContacts.append(contact.getContact_name());
					bufferContacts.append("|");
					bufferContacts.append("Update name");
					bufferContacts.append("-;-");
					list.get(i).setContact_name(contact.getContact_name());
				}
				if (!compareTwoString(contact.getAvatarClient(), list.get(i)
						.getAvatarClient())) {
					// update
					bufferContacts.append(phone);
					bufferContacts.append("|");
					bufferContacts.append(contact.getAvatarClient());
					bufferContacts.append("|");
					bufferContacts.append("Update avata");
					bufferContacts.append("-;-");
					list.get(i).setAvatarClient(contact.getAvatarClient());
				}
				hashMapDB.put(key, contact.getContact_name());
			}
		}
	}

	public static boolean compareTwoString(String s1, String s2) {
		if (s1 == null) {
			if (s2 == null) {
				return true;
			} else {
				return false;
			}
		} else {
			if (s2 == null) {
				return false;
			} else {
				return s1.equals(s2);
			}

		}
	}

	public static boolean isNullOrEmpty(String string) {
		if (string == null || string.length() == 0 || string.equals("null")) {
			return true;
		} else {
			return false;
		}
	}
}
