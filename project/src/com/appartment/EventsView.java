package com.appartment;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;

public class EventsView extends Activity{
	CalendarView cv;
	boolean isManager=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calanderview);
		cv=(CalendarView)findViewById(R.id.calendarView1);
		SharedPreferences sh=getSharedPreferences("login", MODE_PRIVATE);
		String useer = sh.getString("user", " ");
		if(useer.equalsIgnoreCase("manager")){
			isManager=true;
		}else{
			isManager=false;
		}
		cv.setOnDateChangeListener(new OnDateChangeListener() {
			
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				
			final	String date =+dayOfMonth+"-"+month+"-"+year;
			if(isManager){
				addEvent(date);
			}else{
				try {
					
						showEvent(date);
					}
				 catch (Exception e) {
					// TODO: handle exception
					 Toast.makeText(getApplicationContext(), "No event found on this day", Toast.LENGTH_SHORT).show();
				}
			}
			
			}
		});
	}
	protected void showEvent(final String date) {
		// TODO Auto-generated method stub
        SQLiteDatabase 	 db = openOrCreateDatabase("appartment_db", MODE_PRIVATE, null);
Cursor c=db.rawQuery("select * from Events where Dates='"+date+"'", null);
c.moveToFirst();
String event = c.getString(c.getColumnIndex("Event"));
		AlertDialog.Builder builderInner = new AlertDialog.Builder(
          		EventsView.this);
          builderInner.setTitle("Event on "+parseDateToddMMyyyy(date));
          builderInner.setMessage(event);
          builderInner.setPositiveButton("Ok",
                  new DialogInterface.OnClickListener() {

                      @Override
                      public void onClick(
                              DialogInterface dialog,
                              int which) {
                          dialog.dismiss();
      			             }
                  });
          builderInner.show();
      
	}
	protected void addEvent(final String date) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builderInner = new AlertDialog.Builder(
          		EventsView.this);
          builderInner.setTitle("Add Event on "+parseDateToddMMyyyy(date));
          final EditText ed =new EditText(EventsView.this);
          builderInner.setView(ed);
          builderInner.setPositiveButton("Ok",
                  new DialogInterface.OnClickListener() {

                      @Override
                      public void onClick(
                              DialogInterface dialog,
                              int which) {
                        String val =  ed.getText().toString();
                        if(val.length()>0){
                        SQLiteDatabase 	 db = openOrCreateDatabase("appartment_db", MODE_PRIVATE, null);
      		db.execSQL("create table if not exists Events(Event varchar,Dates varchar)");
         		db.execSQL("insert or replace into Events values('"+val+"','"+date+"')");
                dialog.dismiss();
                        }else{
                      	  Toast.makeText(getApplicationContext(), "Need event desc", Toast.LENGTH_SHORT).show();
                        }
                 
                      }
                      });
          builderInner.show();
      
		
	}
	public String parseDateToddMMyyyy(String time) {
	    String inputPattern = "dd-MM-yyyy";
	    String outputPattern = "dd-MMM-yyyy";
	    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
	    SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

	    Date date = null;
	    String str = null;

	    try {
	        date = inputFormat.parse(time);
	        str = outputFormat.format(date);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return str;
	}

}
