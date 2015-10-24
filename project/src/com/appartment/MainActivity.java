package com.appartment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText user_name,password;
	Button login;
	DB_Tables tables;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db=openOrCreateDatabase("appartment_db", MODE_PRIVATE, null);
		SharedPreferences sh=getSharedPreferences("login", MODE_PRIVATE);
		final Editor ed= sh.edit();
		login =(Button)findViewById(R.id.button1);
		user_name =(EditText)findViewById(R.id.editText1);
		password =(EditText)findViewById(R.id.editText2);
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			String user=user_name.getText().toString();
			String pwd = password.getText().toString();
			if(user.length()>0 && password.length()>0)
			{
				
			if(user.equals("admin") && pwd.equals("admin")){
				Intent i = new Intent(getApplicationContext(),AdminScreen.class);
				i.putExtra("who_login", "admin");
				startActivity(i);
				finish();
			
				}
			else {
				try {
					checkForUName(user,pwd);
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "User Not Found", Toast.LENGTH_SHORT).show();
				}
			}
			}
			else
			{
			Toast.makeText(getApplicationContext(), "Enter UserName/Password", Toast.LENGTH_SHORT).show();	
			}
			
			}

			private void checkForUName(String user, String pwd) {
				// TODO Auto-generated method stub
				
				Cursor c1 = db.rawQuery("select * from Managers where UserName='"+user+"'", null);
				c1.moveToFirst();
				if(c1.getCount()>0){
					if(pwd.equalsIgnoreCase(c1.getString(c1.getColumnIndex("Password")))){
						Intent i = new Intent(getApplicationContext(),ManagerScreen.class);
						i.putExtra("UserName", user);
						i.putExtra("Password", pwd);
						ed.putString("user", "manager");
						ed.commit();
						startActivity(i);
						finish();
					}else{
						Toast.makeText(getApplicationContext(), "Password not matched", Toast.LENGTH_SHORT).show();
					}
				}else{
					Cursor c = db.rawQuery("select * from Users where UserName='"+user+"'", null);
					c.moveToFirst();
					if(c.getCount()>0)
					{
						if(pwd.equalsIgnoreCase(c.getString(c.getColumnIndex("Password"))))
						{
							try {
								
							
							SharedPreferences sh=getSharedPreferences("login", MODE_PRIVATE);
						String useer = sh.getString("user", " ");
						Log.v("flat", "user -"+useer);
						 Cursor c2= db.rawQuery("select * from UserProfiles where Flat='"+useer+"'", null);
						 c2.moveToFirst();
							if(c2.getCount()>0){
								Intent i = new Intent(getApplicationContext(),UserScreen.class);
								i.putExtra("UserName", user);
								i.putExtra("Password", pwd);
								ed.putString("user", user);
								ed.commit();
								startActivity(i);
									
							}else{
								Intent i = new Intent(getApplicationContext(),UserProfile.class);
								i.putExtra("UserName", user);
								i.putExtra("Password", pwd);
								ed.putString("user", user);
								ed.commit();
								startActivity(i);
															
							}
							} catch (Exception e) {
								// TODO: handle exception
								Intent i = new Intent(getApplicationContext(),UserProfile.class);
								i.putExtra("UserName", user);
								i.putExtra("Password", pwd);
								ed.putString("user", user);
								ed.commit();
								startActivity(i);
							}
							finish();
								
						}
						else{
							Toast.makeText(getApplicationContext(), "Password not matched", Toast.LENGTH_SHORT).show();
						}
					}
				}
				
			}
		});
	}
	
	
}
