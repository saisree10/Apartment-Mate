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
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Complints extends Activity{
	 SQLiteDatabase db;
	 ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complient);
		 db = openOrCreateDatabase("appartment_db", MODE_PRIVATE, null);
			lv=(ListView)findViewById(R.id.listView1);
		final ArrayList<String> _messages=new ArrayList<String>();
		ArrayList<String> _types=new ArrayList<String>();

		try {
				Cursor c= db.rawQuery("select * from Complaints", null);
				c.moveToFirst();
				_messages.add(c.getString(c.getColumnIndex("Flat"))+": "+c.getString(c.getColumnIndex("message")));
				_types.add(c.getString(c.getColumnIndex("type")));
				
				while(c.moveToNext()){
					_messages.add(c.getString(c.getColumnIndex("Flat"))+": "+c.getString(c.getColumnIndex("message")));
					_types.add(c.getString(c.getColumnIndex("type")));
									
				}
				CustomListImage	adap =new CustomListImage(Complints.this, _messages, _types);
				lv.setAdapter(adap);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
			final	String flat = _messages.get(pos).split(": ")[0];
			   AlertDialog.Builder builderInner = new AlertDialog.Builder(
               		Complints.this);
               builderInner.setMessage("Reply");
               final EditText ed =new EditText(Complints.this);
               builderInner.setView(ed);
               builderInner.setPositiveButton("Ok",
                       new DialogInterface.OnClickListener() {

                           @Override
                           public void onClick(
                                   DialogInterface dialog,
                                   int which) {
                               dialog.dismiss();
                             String val = "Manager: "+ ed.getText().toString();
                   			
                         	db.execSQL("create table if not exists Inbox(Flat varchar,message varchar)");
                       		db.execSQL("insert or replace into Inbox values('"+flat+"','"+val+"')");
                       		             }
                       });
               builderInner.show();
	      	
				
			}
		});
	}

}
