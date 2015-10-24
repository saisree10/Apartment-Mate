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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ManagerScreen extends Activity{
	ImageView iv ;
	int img_pad=10;
	Button add,events, reminders, notifications, parking, Blog;
	DB_Tables tables;
	SQLiteDatabase db;
	Animation rotation ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manager_screen);
		 db = openOrCreateDatabase("appartment_db", MODE_PRIVATE, null);

		add=(Button)findViewById(R.id.button1);
		Blog=(Button)findViewById(R.id.button2);
		reminders=(Button)findViewById(R.id.button3);
		parking=(Button)findViewById(R.id.button4);
		notifications=(Button)findViewById(R.id.button5);
		events=(Button)findViewById(R.id.button6);
		 rotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_rotate);
		rotation.setRepeatCount(Animation.INFINITE);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				iv =new ImageView(ManagerScreen.this);
				iv.setBackgroundResource(R.drawable.reminders);
				iv.setPadding(img_pad, img_pad, img_pad, img_pad);
				new AlertDialog.Builder(ManagerScreen.this)
			    .setPositiveButton("Add User", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            dialog.dismiss();
						startActivity(new Intent(getApplicationContext(),AddUser.class));

			        }
			     })
			    .setNegativeButton("Show Users", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            dialog.dismiss();
			            startActivity(new Intent(getApplicationContext(),AllUsers.class));

			        }
			     })
			     
			    .setView(iv)
			     .show();

			}
		});
		Blog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	            startActivity(new Intent(getApplicationContext(),BlogActivity.class));

			}
		});
		reminders.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Complints.class));
			}
		});
		parking.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				AlertDialog.Builder builderSingle = new AlertDialog.Builder(
		                ManagerScreen.this);
		        builderSingle.setTitle("Parking Requests");
		        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
		        		ManagerScreen.this,
		                android.R.layout.select_dialog_item);
		        try {
		        	Cursor c= db.rawQuery("select * from Parking", null);
					c.moveToFirst();
			        arrayAdapter.add(c.getString(c.getColumnIndex("Flat"))+":- "+c.getString(c.getColumnIndex("message")));
					
					while(c.moveToNext()){
						 arrayAdapter.add(c.getString(c.getColumnIndex("Flat"))+":- "+c.getString(c.getColumnIndex("message")));
									}
				} catch (Exception e) {
					// TODO: handle exception
			        arrayAdapter.add("No Requests are available");

					e.printStackTrace();
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
		                     //  dialog.cancel();
		                    }
		                });
		        builderSingle.show();	
		        }
		});
		events.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),EventsView.class));
				
			}
		});
		notifications.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder builderSingle = new AlertDialog.Builder(ManagerScreen.this);
		        builderSingle.setTitle("Enter Flat No:");
		        final EditText ed1 =new EditText(ManagerScreen.this);
		        builderSingle.setView(ed1);
		        builderSingle.setNegativeButton("cancel",
		                new DialogInterface.OnClickListener() {

		                    @Override
		                    public void onClick(DialogInterface dialog, int which) {
		                        dialog.dismiss();
		                    }
		                });
		        builderSingle.setPositiveButton("Ok",
		                new DialogInterface.OnClickListener() {

		                    @Override
		                    public void onClick(DialogInterface dialog, int which) {
		                        dialog.dismiss();
		                        final String strName = ed1.getText().toString();
		                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
		                        		ManagerScreen.this);
		                        builderInner.setMessage(strName);
		                        final EditText ed =new EditText(ManagerScreen.this);
		                        builderInner.setView(ed);
		                        builderInner.setPositiveButton("Ok",
		                                new DialogInterface.OnClickListener() {

		                                    @Override
		                                    public void onClick(
		                                            DialogInterface dialog,
		                                            int which) {
		                                        dialog.dismiss();
		                                      String val =  ed.getText().toString();
		                            			
		                    		db.execSQL("create table if not exists Packages(Flat varchar,message varchar)");
		                       		db.execSQL("insert or replace into Packages values('"+strName+"','"+val+"')");
		                            		             }
		                                });
		                        builderInner.show();
		                    }
		                });

		      
		        builderSingle.show();	
     		}
		});
		
		
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
