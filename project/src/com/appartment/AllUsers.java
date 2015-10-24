package com.appartment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AllUsers extends Activity{
	ListView list;
	SQLiteDatabase db;
	ArrayList<String> names,room_no;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_users);
		list=(ListView)findViewById(R.id.listView1);
		 db = openOrCreateDatabase("appartment_db", MODE_PRIVATE, null);
		 names=new ArrayList<String>();
		 room_no=new ArrayList<String>();
		 Cursor c= db.rawQuery("select * from UserProfiles", null);
		 c.moveToFirst();
		 names.add(c.getString(c.getColumnIndex("FullName")));
		 room_no.add(c.getString(c.getColumnIndex("Flat")));
		 while (c.moveToNext()) {
			 names.add(c.getString(c.getColumnIndex("FullName")));
			 room_no.add(c.getString(c.getColumnIndex("Flat")));
		}
		 CustomListImage adap =new CustomListImage(AllUsers.this, names, room_no);
		 list.setAdapter(adap);
		 list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				 Cursor c= db.rawQuery("select * from UserProfiles where Flat='"+room_no.get(arg2)+"'", null);
				 c.moveToFirst();
				 AlertDialog.Builder builderSingle = new AlertDialog.Builder(AllUsers.this);
		            builderSingle.setTitle("Details");
		            final ArrayAdapter<String> data = new ArrayAdapter<String>(
		            		AllUsers.this,
		                    android.R.layout.select_dialog_item);
		            data.add("Name : "+c.getString(c.getColumnIndex("FullName")));
					 data.add("Flat : "+c.getString(c.getColumnIndex("Flat")));
					 data.add("SSN : "+c.getString(c.getColumnIndex("SSN")));
					 data.add("Occupation : "+c.getString(c.getColumnIndex("Occupation")));
					 data.add("Contact : "+c.getString(c.getColumnIndex("Contact")));
					 data.add("Address : "+c.getString(c.getColumnIndex("Address"))); 
					 data.add("Pets : "+c.getString(c.getColumnIndex("Pets")));
					 data.add("Vehicle : "+c.getString(c.getColumnIndex("Vehicle"))); 
					 data.add("People : "+c.getString(c.getColumnIndex("People")));
					 
		            builderSingle.setNegativeButton("cancel",
		                    new DialogInterface.OnClickListener() {

		                        @Override
		                        public void onClick(DialogInterface dialog, int which) {
		                            dialog.dismiss();
		                        }
		                    });

		            builderSingle.setAdapter(data,
		                    new DialogInterface.OnClickListener() {

		                        @Override
		                        public void onClick(DialogInterface dialog, int which) {
		                           
		                           
		                        }
		                    });
		            builderSingle.show();
			}
		});
	}

}
