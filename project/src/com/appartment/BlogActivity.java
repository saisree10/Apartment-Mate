package com.appartment;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class BlogActivity extends Activity{
	 SQLiteDatabase db;
	    ArrayList<String> blog_data;
	    CustomBlog adap;
	    EditText post_data;
	    Button post;
	    ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blog);
		list=(ListView)findViewById(R.id.listView1);
		post=(Button)findViewById(R.id.button1);
		post_data = (EditText)findViewById(R.id.editText1);
		 db = openOrCreateDatabase("appartment_db", MODE_PRIVATE, null);
		 blog_data=new ArrayList<String>();
		 try {
				Cursor c= db.rawQuery("select * from Blog", null);
				c.moveToFirst();
				blog_data.add(c.getString(c.getColumnIndex("name"))+": "+c.getString(c.getColumnIndex("data")));
				while(c.moveToNext()){
					blog_data.add(c.getString(c.getColumnIndex("name"))+": "+c.getString(c.getColumnIndex("data")));
								
				}
				adap =new CustomBlog(BlogActivity.this, blog_data);
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
	}
		 protected void saveData(String dataa) {
				// TODO Auto-generated method stub
					
				db.execSQL("create table if not exists Blog(Flat varchar,data varchar,name varchar)");
				db.execSQL("insert or replace into Blog values('Manager','"+	dataa+"','Manager')");
				if(adap==null){
					blog_data.add("Manager:"+dataa);
					
					adap =new CustomBlog(BlogActivity.this, blog_data);
					list.setAdapter(adap);
					
				}else{
					blog_data.add("Manager:"+dataa);
					
					adap.notifyDataSetChanged();
				}
			
	}
		 

}
