package com.appartment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AdminScreen extends Activity {
	Spinner cities;
	EditText appt_name,rooms,manager,user_name,password,ssn;
	Button create,reset,show;
	DB_Tables tables;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_screen);
		
		db=openOrCreateDatabase("appartment_db", MODE_PRIVATE, null);
		
		cities = (Spinner)findViewById(R.id.spinner1);
		create = (Button)findViewById(R.id.button1);
		reset = (Button)findViewById(R.id.button2);
		show = (Button)findViewById(R.id.button3);
		
		appt_name =(EditText)findViewById(R.id.editText1);
		rooms =(EditText)findViewById(R.id.editText2);
		manager =(EditText)findViewById(R.id.editText3);
		user_name =(EditText)findViewById(R.id.editText4);
		password =(EditText)findViewById(R.id.editText5);
		ssn =(EditText)findViewById(R.id.editText6);
		
		create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			String result=	validateAllValues();
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
			}
		});
		reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(AdminScreen.this)
			    .setTitle("Reset")
			    .setMessage("Are you sure you want to delete this entry?")
			    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	resetAllValues();
			        	
			        }
			     })
			    .setNegativeButton("No", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            dialog.dismiss();
			        }
			     })
			     .show();
			}
		});
		
		show.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				try {
					Cursor c= db.rawQuery("select * from Managers", null);
					c.moveToFirst();
					
					
					AlertDialog.Builder builderSingle = new AlertDialog.Builder(
			                AdminScreen.this);
			        builderSingle.setTitle("Managers (Appts)");
			        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
			        		AdminScreen.this,
			                android.R.layout.select_dialog_item);
			        
			        arrayAdapter.add(c.getString(c.getColumnIndex("Manager"))+" ("+c.getString(c.getColumnIndex("AppartmentName"))+")");
			 
			        while (c.moveToNext()) {
			            arrayAdapter.add(c.getString(c.getColumnIndex("Manager"))+" ("+c.getString(c.getColumnIndex("AppartmentName"))+")");
					}
			        builderSingle.setNegativeButton("cancel",
			                new DialogInterface.OnClickListener() {

			                    @Override
			                    public void onClick(DialogInterface dialog, int which) {
			                        dialog.dismiss();
			                    }
			                });

			        builderSingle.setAdapter(arrayAdapter,
			                new DialogInterface.OnClickListener() {

			                    @Override
			                    public void onClick(DialogInterface dialog, int which) {
			                        final String strName = arrayAdapter.getItem(which);
			                  
			                    }
			                });
			        builderSingle.show();		

					
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "No managers to show", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}
	
	void resetAllValues(){
		appt_name.setText("");
		rooms.setText("");
		manager.setText("");
		user_name.setText("");
		password.setText("");
		ssn.setText("");

	}
	
	String validateAllValues(){
		if(appt_name.getText().toString().length()>0){
			if(rooms.getText().toString().length()>0){
				if(manager.getText().toString().length()>0){
					if(user_name.getText().toString().length()>0){
						if(password.getText().toString().length()>0){
							if(ssn.getText().toString().length()>0){
							return	enterValuesInDB();
								
							}else{
								return "SSN is required!";
							}
						}else{
							return "Password is required!";
						}
					}else{
						return "Unique UserName is required!";
					}
				}else{
					return "Manager name is required!";
				}
			}else{
				return "Rooms count is required!";
			}
		}else{
			return "Appartment name is required!";
		}
	
	}

	private String enterValuesInDB() {
		// TODO Auto-generated method stub
		try {
			
		String city = cities.getSelectedItem().toString();
		String appName =appt_name.getText().toString();
		String rooms1 =rooms.getText().toString();
		String managers =manager.getText().toString();
		String uName =user_name.getText().toString();
		String pwd =password.getText().toString();
		String ssnNo =ssn.getText().toString();
		tables =new DB_Tables();
		tables.CreateManagersTable(db);
		Cursor c= db.rawQuery("select * from Managers where UserName='"+uName+"'", null);
		c.moveToFirst();
		if(c.getCount()<1){
		
		db.execSQL("insert or replace into Managers values('"+city+"','"+
				appName+"','"+
				rooms1+"','"+
				managers+"','"+
				uName+"','"+
				pwd+"','"+
				ssnNo+"')");	
		appt_name.setText("");
		rooms.setText("");
		manager.setText("");
		user_name.setText("");
		password.setText("");
		ssn.setText("");

		return "Values added successfully";
		}else{
			return "UserName already exists";
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "DB not created";
		}
		
	}

	 @Override  
	    public boolean onCreateOptionsMenu(Menu menu) {  
	        // Inflate the menu; this adds items to the action bar if it is present.  
	        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu  
	        return true;  
	    }  
	      
	    @Override  
	    public boolean onOptionsItemSelected(MenuItem item) {  
	        switch (item.getItemId()) {  
	            case R.id.logout:  
					startActivity(new Intent(getApplicationContext(),MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
						finish();
	            return true;     
	              default:  
	                return super.onOptionsItemSelected(item);  
	        }  
	    }  
}
