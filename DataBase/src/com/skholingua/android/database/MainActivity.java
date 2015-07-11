package com.skholingua.android.database;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	private SqlLiteManger _sqlLiteManger;

	private ListView listView;

	private SimpleCursorAdapter adapter;

	final String[] from = new String[] { SqlLiteDbHelper._ID,
			SqlLiteDbHelper.CONTACTS_NAME, SqlLiteDbHelper.CONTACTS_PHONE_NO };

	final int[] to = new int[] { R.id.id, R.id.name, R.id.phone };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.list_view);
		listView.setEmptyView(findViewById(R.id.empty));

		_sqlLiteManger = new SqlLiteManger(this);
		_sqlLiteManger.open();
		Cursor cursor = _sqlLiteManger.fetch();

		adapter = new SimpleCursorAdapter(this, R.layout.contact_view_child,
				cursor, from, to, 0);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long viewId) {
				TextView idTextView = (TextView) view.findViewById(R.id.id);
				TextView nameTextView = (TextView) view.findViewById(R.id.name);
				TextView phoneTextView = (TextView) view
						.findViewById(R.id.phone);

				String id = idTextView.getText().toString();
				String name = nameTextView.getText().toString();
				String phone = phoneTextView.getText().toString();

				Intent modify_intent = new Intent(getApplicationContext(),
						EditContacts.class);
				modify_intent.putExtra("name", name);
				modify_intent.putExtra("phone", phone);
				modify_intent.putExtra("id", id);

				startActivity(modify_intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.add_contacts) {
			Intent add_mem = new Intent(this, AddContacts.class);
			startActivity(add_mem);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		_sqlLiteManger.open();
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		_sqlLiteManger.close();
	}
}
