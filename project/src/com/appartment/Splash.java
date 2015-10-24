package com.appartment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		 Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() { 
		         public void run() { 
		 			startActivity(new Intent(getApplicationContext(),MainActivity.class));
		 			
		         } 
		    }, 3000); 
	}

}
