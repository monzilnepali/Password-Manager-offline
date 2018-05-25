package com.info.util;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DbConnection {

	public static Connection getConnection(){
		try{
			Class.forName("org.sqlite.JDBC");
			Connection conn=DriverManager.getConnection("jdbc:sqlite:E:\\SQLiteStudio\\PasswordManager");
//			Alert alert=new Alert(AlertType.INFORMATION);
//			alert.setTitle("Password Manager");
//			alert.setHeaderText(null);
//			alert.setContentText("database connection succesfully");
//			alert.showAndWait();
			
			return conn;
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
