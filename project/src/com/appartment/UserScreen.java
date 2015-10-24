package com.appartment;

import java.util.ArrayList;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class UserScreen extends Activity {
	    ListView list,list1;
	    private DrawerLayout mDrawerLayout;
	    Button slide;
	    EditText post_data;
	    Button post;
	    String[] web = {
	    		"BLOG",
	    		"COMPLAINT",
	    		"REMINDERS",
	               "EVENTS",
	               "REQUEST FOR PARKING",
	               "PAY RENT",
	               "INBOX",
	               "MY PROFILE",
	               "LOGOUT"
	        } ;
	    Integer[] imageId1 = {  R.drawable.circle   };
	    SQLiteDatabase db;
	    ArrayList<String> blog_data;
	    CustomBlog adap;
	    TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.user_screen);
		 db = openOrCreateDatabase("appartment_db", MODE_PRIVATE, null);
		 blog_data=new ArrayList<String>();
    	mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
		list1=(ListView)findViewById(R.id.listview);
		list=(ListView)findViewById(R.id.listView1);
		slide=(Button)findViewById(R.id.slider);
		post=(Button)findViewById(R.id.button1);
		post_data = (EditText)findViewById(R.id.editText1);
		tv=(TextView)findViewById(R.id.textView1);
		
		
try {
 	SharedPreferences sh=getSharedPreferences("login", MODE_PRIVATE);
	String useer = sh.getString("user", " ");
	 Cursor c= db.rawQuery("select * from UserProfiles where Flat='"+useer+"'", null);
	 c.moveToFirst();
	String name= c.getString(c.getColumnIndex("FullName"));
		tv.setText("Welcome "+name);
} catch (Exception e) {
	// TODO: handle exception
}
		try {
			Cursor c= db.rawQuery("select * from Blog", null);
			c.moveToFirst();
			blog_data.add(c.getString(c.getColumnIndex("name"))+": "+c.getString(c.getColumnIndex("data")));
			while(c.moveToNext()){
				blog_data.add(c.getString(c.getColumnIndex("name"))+": "+c.getString(c.getColumnIndex("data")));
							
			}
			
			adap =new CustomBlog(UserScreen.this, blog_data);
			list.setAdapter(adap);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		post.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String dataa =post_data.getText().toString();
				if(dataa.length()>0){
					saveData(dataa);
				}
			}
		});
		
		mDrawerLayout.setScrimColor(Color.TRANSPARENT);
		slide.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
	                mDrawerLayout.closeDrawer(Gravity.RIGHT);
	            } else {
	            	
	                mDrawerLayout.openDrawer(Gravity.RIGHT);
	            }
			}
		});
		try{
  	  	  CustomSlider adapter = new   CustomSlider(UserScreen.this, web, imageId1, "HOME");
  	              list1.setCacheColorHint(color.transparent);
  	                      list1.setAdapter(adapter);
  	  	}catch(Exception e){
  	  		Log.v("Exception", ""+e);
  	  	}
  	
  	  list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            	switch (position) {
  				case 0:
  					break;
  					
  				case 1:
  					postComplaint();
  					break;
  					
  				case 2:
  					seeParcels();
  					break;
  				case 3:
  					startActivity(new Intent(getApplicationContext(),EventsView.class));
  					break;
  				case 4:
  					requestParking();
  					break;
  				case 5:
  					payment();
  					
  					break;
  				case 6:
  					checkInbox();
  					break;
  				case 7:
  					startActivity(new Intent(getApplicationContext(),UserProfileData.class));
  	  			  		break;
  				case 8:
  					startActivity(new Intent(getApplicationContext(),MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
  						finish();
  		  					
  					break;

  				}
            	if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
	                mDrawerLayout.closeDrawer(Gravity.RIGHT);
	            }
            }
        });
	}

	

	protected void payment() {
		// TODO Auto-generated method stub
	     AlertDialog.Builder builderInner = new AlertDialog.Builder(
                 UserScreen.this);
         builderInner.setTitle("Payment");
         final EditText ed =new EditText(UserScreen.this);
         ed.setHint("Enter amount to be paid");
         builderInner.setView(ed);
         builderInner.setPositiveButton("Ok",
                 new DialogInterface.OnClickListener() {

                     @Override
                     public void onClick(
                             DialogInterface dialog,
                             int which) {
                         dialog.dismiss();
             		
            		startActivity(new Intent(getApplicationContext(),Payment.class));
            	  			
     	     		             }
                 });
         builderInner.show();
     
		  	
	}



	protected void checkInbox() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                UserScreen.this);
        builderSingle.setTitle("You have the following today");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                UserScreen.this,
                android.R.layout.select_dialog_item);
        try {
        	SharedPreferences sh=getSharedPreferences("login", MODE_PRIVATE);
    		String useer = sh.getString("user", " ");
			Cursor c= db.rawQuery("select * from Inbox where Flat='"+useer+"'", null);
			c.moveToFirst();
	        arrayAdapter.add(c.getString(c.getColumnIndex("message")));
			
			while(c.moveToNext()){
			    arrayAdapter.add(c.getString(c.getColumnIndex("message")));
					}
		} catch (Exception e) {
			// TODO: handle exception
	        arrayAdapter.add("No messages are available");

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



	protected void requestParking() {
	     AlertDialog.Builder builderInner = new AlertDialog.Builder(
                 UserScreen.this);
         builderInner.setTitle("Request for parking");
         final EditText ed =new EditText(UserScreen.this);
         builderInner.setView(ed);
         builderInner.setPositiveButton("Ok",
                 new DialogInterface.OnClickListener() {

                     @Override
                     public void onClick(
                             DialogInterface dialog,
                             int which) {
                         dialog.dismiss();
                       String val =  ed.getText().toString();
                   	SharedPreferences sh=getSharedPreferences("login", MODE_PRIVATE);
             		String useer = sh.getString("user", " ");
             		 Cursor c= db.rawQuery("select * from UserProfiles where Flat='"+useer+"'", null);
             		 c.moveToFirst();
             		String name= c.getString(c.getColumnIndex("FullName"));
             			
     		db.execSQL("create table if not exists Parking(Flat varchar,message varchar,name varchar)");
        		db.execSQL("insert or replace into Parking values('"+useer+"','"+val+"','"+name+"')");
             		             }
                 });
         builderInner.show();
     
	}

	protected void seeParcels() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                UserScreen.this);
        builderSingle.setTitle("List of parcels");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                UserScreen.this,
                android.R.layout.select_dialog_item);
        try {
        	SharedPreferences sh=getSharedPreferences("login", MODE_PRIVATE);
    		String useer = sh.getString("user", " ");
			Cursor c= db.rawQuery("select * from Packages where Flat='"+useer+"'", null);
			c.moveToFirst();
	        arrayAdapter.add(c.getString(c.getColumnIndex("message")));
			
			while(c.moveToNext()){
			    arrayAdapter.add(c.getString(c.getColumnIndex("message")));
					}
		} catch (Exception e) {
			// TODO: handle exception
	        arrayAdapter.add("No Packages are available");

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

	protected void postComplaint() {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                UserScreen.this);
        builderSingle.setTitle("Write a Complaint");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                UserScreen.this,
                android.R.layout.select_dialog_item);
        arrayAdapter.add("Kitchen");
        arrayAdapter.add("Carpenter");
        arrayAdapter.add("Pipes");
        arrayAdapter.add("Bathroom");
        arrayAdapter.add("Plumber");
        arrayAdapter.add("Other");
        
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
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                UserScreen.this);
                        builderInner.setMessage(strName);
                        final EditText ed =new EditText(UserScreen.this);
                        builderInner.setView(ed);
                        builderInner.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                      String val =  ed.getText().toString();
                                  	SharedPreferences sh=getSharedPreferences("login", MODE_PRIVATE);
                            		String useer = sh.getString("user", " ");
                            		 Cursor c= db.rawQuery("select * from UserProfiles where Flat='"+useer+"'", null);
                            		 c.moveToFirst();
                            		String name= c.getString(c.getColumnIndex("FullName"));
                            			
                    		db.execSQL("create table if not exists Complaints(Flat varchar,type varchar,message varchar,name varchar)");
                       		db.execSQL("insert or replace into Complaints values('"+useer+"','"+strName+"','"+val+"','"+name+"')");
                            		             }
                                });
                        builderInner.show();
                    }
                });
        builderSingle.show();		
	}

	protected void saveData(String dataa) {
		// TODO Auto-generated method stub
		SharedPreferences sh=getSharedPreferences("login", MODE_PRIVATE);
		String useer = sh.getString("user", " ");
		Log.v("flat", "user -"+useer);
		 Cursor c= db.rawQuery("select * from UserProfiles where Flat='"+useer+"'", null);
		 c.moveToFirst();
		String name= c.getString(c.getColumnIndex("FullName"));
			
		db.execSQL("create table if not exists Blog(Flat varchar,data varchar,name varchar)");
		db.execSQL("insert or replace into Blog values('"+useer+"','"+	dataa+"','"+name+"')");
		if(adap==null){
			blog_data.add(name+":"+dataa);
			
			adap =new CustomBlog(UserScreen.this, blog_data);
			list.setAdapter(adap);
			
		}else{
			blog_data.add(name+":"+dataa);
			
			adap.notifyDataSetChanged();
		}
		
	}

}
