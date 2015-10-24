package com.appartment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class CustomListImage extends ArrayAdapter<String>{
	private final Activity context;
	ArrayList<String> data,dates;
	public CustomListImage(Activity context,ArrayList<String> data,ArrayList<String> dates) {
	super(context, R.layout.customimage, data);
	this.context = context;
	this.data=data;
	this.dates=dates;
	}
	@SuppressLint({  "InflateParams",})
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		
		ViewHolder holder ;
		if(view==null){
			LayoutInflater inflater = context.getLayoutInflater();
			view= inflater.inflate(R.layout.customimage, null);
			holder=new ViewHolder();
			holder. txtTitle = (TextView) view.findViewById(R.id.textView1);
			holder. txtTime = (TextView) view.findViewById(R.id.txt);

			view.setTag(holder);
		}else{
			holder=(ViewHolder) view.getTag();
		}
	
	   
	holder.	txtTitle.setText(data.get(position));
	holder.	txtTime.setText(dates.get(position));
	
	
	return view;
	}
	public static class ViewHolder{
		TextView txtTime;
		TextView txtTitle;
	}
	
}	
