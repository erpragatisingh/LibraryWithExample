package com.skholingua.android.database;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditContacts extends Activity implements OnClickListener {
	
	private EditText _nameText, _phoneText;
	private Button updateBtn, deleteBtn;
	
	private long _id;
	
	private SqlLiteManger _sqlLiteManger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle("Edit Contact Details");
		  
		setContentView(R.layout.contact_edit);

		_sqlLiteManger = new SqlLiteManger(this);
		_sqlLiteManger.open();

		_nameText = (EditText) findViewById(R.id.name_edittext);
		_phoneText = (EditText) findViewById(R.id.phone_edittext);
		
		updateBtn = (Button) findViewById(R.id.btn_update);
		deleteBtn = (Button) findViewById(R.id.btn_delete);

		Intent intent = getIntent();
		String id = intent.getStringExtra("id");
		String name = intent.getStringExtra("name");
		String phone = intent.getStringExtra("phone");

		_id = Long.parseLong(id);

		_nameText.setText(name);
		_phoneText.setText(phone);
		
		updateBtn.setOnClickListener(this);
		deleteBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_update:
			String name = _nameText.getText().toString();
			String phone = _phoneText.getText().toString();
			
			_sqlLiteManger.update(_id, name, phone);
			this.returnHome();
			break;

		case R.id.btn_delete:
			_sqlLiteManger.delete(_id);
			this.returnHome();
			break;
		}
	}

	public void returnHome() {
		Intent home_intent = new Intent(getApplicationContext(), MainActivity.class)
				.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(home_intent);
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
