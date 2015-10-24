package com.appartment;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomBlog extends ArrayAdapter<String>{
	private final Activity context;
	private final ArrayList<String> web;
	
	public CustomBlog(Activity context, ArrayList<String> blog_data) {
	super(context, R.layout.list_blog,blog_data);
	this.context = context;
	this.web = blog_data;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.list_blog, null, true);
	
	TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
	ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
	imageView.setBackgroundResource(R.drawable.circle);
	txtTitle.setText(web.get((web.size()-1)-position));
	
	return rowView;
	}


}	
