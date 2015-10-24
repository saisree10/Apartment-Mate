package com.appartment;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class UserProfile extends Activity {
	EditText fullname,occupation,contact,address,people,ssn,pets,vehicle,flat;
	Button save,reset,date;
	DB_Tables tables;
	SQLiteDatabase db;
	String new_date=" ";
	private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		db=openOrCreateDatabase("appartment_db", MODE_PRIVATE, null);

		save = (Button)findViewById(R.id.button1);
		date = (Button)findViewById(R.id.button3);
		SharedPreferences sh=getSharedPreferences("login", MODE_PRIVATE);
		String useer = sh.getString("user", " ");
		
		fullname =(EditText)findViewById(R.id.editText1);
		ssn =(EditText)findViewById(R.id.editText2);
		occupation =(EditText)findViewById(R.id.editText3);
		contact =(EditText)findViewById(R.id.editText4);
		address =(EditText)findViewById(R.id.editText5);
		people =(EditText)findViewById(R.id.editText6);
		pets =(EditText)findViewById(R.id.editText7);
		vehicle =(EditText)findViewById(R.id.editText8);
		flat =(EditText)findViewById(R.id.editText9);

		final Calendar c = Calendar.getInstance();
	        year  = c.get(Calendar.YEAR);
	        month = c.get(Calendar.MONTH);
	        day   = c.get(Calendar.DAY_OF_MONTH);
		
	        date.setOnClickListener(new OnClickListener() {
	            @SuppressWarnings("deprecation")
				@Override
	            public void onClick(View v) {
	                showDialog(DATE_PICKER_ID);
	            }
	        });
	        try {
	    	  	Cursor cc=db.rawQuery("select * from UserProfiles where flat='"+useer+"'", null);
	    	  	cc.moveToFirst();
	    	        if(cc.getCount()>0){
	    	    		startActivity(new Intent(getApplicationContext(),UserScreen.class));
	    	    		finish();        	
	    	        }else{
	    	        	flat.setText(useer);
	    	        }
			} catch (Exception e) {
				// TODO: handle exception
	        	flat.setText(useer);

			}
			
	        
	        save.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					db.execSQL("create table if not exists UserProfiles(" +
							"FullName varchar,"+
				    		"Flat varchar primary key," +
				    		"SSN varchar," +
				    		"Occupation varchar," +
				    		"Contact varchar," +
				    		"Address varchar," +
				    		"Pets varchar," +
				    		"Vehicle varchar," +
							"People varchar)");
					String fName, flat, ssn, occ,contact,addre,pet,veh,peoples;
					fName = fullname.getText().toString();
					flat=UserProfile.this.flat.getText().toString();
					ssn=UserProfile.this.ssn.getText().toString();
					occ=occupation.getText().toString();
					contact=UserProfile.this.contact.getText().toString();
					addre=address.getText().toString();
					pet=pets.getText().toString();
					veh=vehicle.getText().toString();
					peoples=people.getText().toString();
					
					if(fName.length()>0){
						if(flat.length()>0){
							if(ssn.length()>0){
								if(occ.length()>0){
									if(addre.length()>0){
										if(pet.length()>0){
											if(veh.length()>0){
												if(peoples.length()>0){
													db.execSQL("insert or replace into UserProfiles values('"+fName+"','"+flat+"','"+ssn+"','"+occ+"','"+addre+"','"+contact+"','"+pet+"','"+veh+"','"+peoples+"')");
													startActivity(new Intent(getApplicationContext(),UserScreen.class));
													finish();
												}else{
													Toast.makeText(getApplicationContext(), "No. Of Peoples", Toast.LENGTH_SHORT).show();
												}
											}else{
												Toast.makeText(getApplicationContext(), "Vehicle", Toast.LENGTH_SHORT).show();
											}
										}else{
											Toast.makeText(getApplicationContext(), "Pets", Toast.LENGTH_SHORT).show();
										}
									}else{
										Toast.makeText(getApplicationContext(), "Address", Toast.LENGTH_SHORT).show();
									}
								}else{
									Toast.makeText(getApplicationContext(), "Occupation", Toast.LENGTH_SHORT).show();
								}
							}else{
								Toast.makeText(getApplicationContext(), "SSN", Toast.LENGTH_SHORT).show();
							}
						}else{
							Toast.makeText(getApplicationContext(), "Flat No", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(getApplicationContext(), "FullName", Toast.LENGTH_SHORT).show();
					}
					
				
				}
			});
	      

	}
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_PICKER_ID:
            return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }
	  private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
	        @Override
	        public void onDateSet(DatePicker view, int selectedYear,
	                int selectedMonth, int selectedDay) {
	            year  = selectedYear;
	            month = selectedMonth;
	            day   = selectedDay;
	            new_date =""+(new StringBuilder().append(day).append("-").append(month + 1)
	                    .append("-").append(year)
	                    .append(" "));
	     
	           }
	        };
}
