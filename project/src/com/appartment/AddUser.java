package com.appartment;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUser extends Activity{
	EditText uname,pass;
	Button save;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_user);
		
		uname=(EditText)findViewById(R.id.editText1);
		pass=(EditText)findViewById(R.id.editText2);
		save=(Button)findViewById(R.id.button1);
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			String user= uname.getText().toString();
			String password=pass.getText().toString();
			if(user.length()>0 && password.length()>0){
			SQLiteDatabase db = openOrCreateDatabase("appartment_db", MODE_PRIVATE, null);
			DB_Tables db_Tables=new DB_Tables();
			db_Tables.CreateAddUsersTable(db, user, password);
			Toast.makeText(getApplicationContext(), "User added successfully!", Toast.LENGTH_LONG).show();
			uname.setText("");
			}
			}
		});
		
		
		
	}

}
