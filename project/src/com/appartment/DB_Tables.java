package com.appartment;

import android.database.sqlite.SQLiteDatabase;

public class DB_Tables {
	
	void	CreateManagersTable(SQLiteDatabase db){
		
		db.execSQL("create table if not exists Managers(" +
				"City varchar,"+
	    		"AppartmentName varchar," +
	    		"Rooms varchar," +
	    		"Manager varchar," +
	    		"UserName varchar primary key," +
	    		"Password varchar," +
				"SSN varchar)");
		
	}

	void	CreateAddUsersTable(SQLiteDatabase db, String uname, String pass){
		
		db.execSQL("create table if not exists Users(UserName varchar primary key,Password varchar)");
		db.execSQL("insert or replace into Users values('"+uname+"','"+	pass+"')");
	}

	

}
