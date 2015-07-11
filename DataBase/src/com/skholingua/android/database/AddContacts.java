package com.skholingua.android.database;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddContacts extends Activity implements OnClickListener {

	private Button addTodoBtn;
	private EditText _nameEditText;
	private EditText _phoneEditText;

	private SqlLiteManger _sqlLiteManger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle("Add Contact Details");

		setContentView(R.layout.contact_add);

		_nameEditText = (EditText) findViewById(R.id.name_edittext);
		_phoneEditText = (EditText) findViewById(R.id.phone_edittext);

		addTodoBtn = (Button) findViewById(R.id.add_record);

		_sqlLiteManger = new SqlLiteManger(this);
		
		addTodoBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_record:

			final String name = _nameEditText.getText().toString();
			final String desc = _phoneEditText.getText().toString();

			_sqlLiteManger.insert(name, desc);

			Intent main = new Intent(AddContacts.this,
					MainActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			startActivity(main);
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		_sqlLiteManger.open();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		_sqlLiteManger.close();
	}
}
