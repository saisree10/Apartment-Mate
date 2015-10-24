package com.appartment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Payment extends Activity{

	EditText cardnumber,expdate,expmnth,expyear,cvv,name;
	Button payment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment);
		cardnumber =(EditText)findViewById(R.id.cardNumberTextEdit);
		expdate =(EditText)findViewById(R.id.cardDateEditText);
		expmnth =(EditText)findViewById(R.id.cardMonthEditText);
		
		expyear =(EditText)findViewById(R.id.cardYearEditText);
		cvv =(EditText)findViewById(R.id.verificationEditText);
		name =(EditText)findViewById(R.id.cardHolderEditText);
		
		payment=(Button)findViewById(R.id.registerButton);
		
		payment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String num=cardnumber.getText().toString();
				String cvv1=cvv.getText().toString();
				String mnth=expdate.getText().toString();
				String yr=expyear.getText().toString();
				String name1=name.getText().toString();
				if(num.length()==16){
					if(mnth.length()==2){
						if(yr.length()==2){
							if(cvv1.length()==3){
								if(name1.length()>3){
									Toast.makeText(getApplicationContext(), "Your payment was successfull!", Toast.LENGTH_SHORT).show();
									finish();	
								}else{
									Toast.makeText(getApplicationContext(), "Enter valid name", Toast.LENGTH_SHORT).show();
								}

							}else{
								Toast.makeText(getApplicationContext(), "Enter cvv", Toast.LENGTH_SHORT).show();
							}

						}else{
							Toast.makeText(getApplicationContext(), "Enter valid year", Toast.LENGTH_SHORT).show();
						}

					}else{
						Toast.makeText(getApplicationContext(), "Enter valid month", Toast.LENGTH_SHORT).show();
					}

				}else{
					Toast.makeText(getApplicationContext(), "Enter valid card number", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		
	}

}
