package com.info.controller;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.info.dao.UserDao;
import com.info.model.User;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PasswordDialog extends Stage{
	
	@FXML private PasswordField password_field;
	private static String vpassword;
	public PasswordDialog(Stage owner,User vuser) throws IOException {
		super();
        initOwner(owner);
        setTitle("Password Prompt");
        initModality(Modality.APPLICATION_MODAL);
        GridPane root = new GridPane();
        
        root.setPadding(new Insets(20));
        root.setHgap(5);
        root.setVgap(15);
       System.out.println("parent width"+owner.getWidth());
     
        root.setStyle("-fx-background-color:#20232D;");
        Label labelPassword = new Label("Enter Master Password:");
        labelPassword.setStyle("	-fx-text-fill:#EEEEEE;"
        		+ "-fx-font-size:13px;");
  
        PasswordField fieldPassword = new PasswordField();
        fieldPassword.setPrefWidth(250);
        fieldPassword.setPrefHeight(37);
        fieldPassword.setStyle("-fx-background-radius:2;"
        		+ "-fx-background-color:#2E313B;"
        		+ "-fx-text-fill:white;"
        		+ "-fx-font-size:14px;"
        		+ "-fx-border-width:1px"
        		);
  
        Button loginButton = new Button("Continue");
        loginButton.setCursor(Cursor.HAND);
        
      loginButton.setStyle("-fx-background-color:#2979ff;"
      		+ "-fx-padding:2 10 2 10;"
      		+ "-fx-text-fill:white;"
      		+"-fx-font-size:14px");
   
         
        GridPane.setHalignment(labelPassword, HPos.LEFT);
        root.add(labelPassword, 0, 0);
  
        
        // Horizontal alignment for Password field.
        GridPane.setHalignment(fieldPassword, HPos.LEFT);
        root.add(fieldPassword, 0, 1);
  
        // Horizontal alignment for Login button.
        GridPane.setHalignment(loginButton, HPos.CENTER);
        root.add(loginButton, 0, 2);
  
        Scene scene = new Scene(root, 300,150);
       this.setResizable(false);
   
        setScene(scene);
        
        loginButton.setOnAction(e->{
        	System.out.println("button clicked");
        	//checking master relate to username
        	if(fieldPassword.getText()!=null){
        		//checking passsword in another thread
        		ExecutorService executor=Executors.newSingleThreadExecutor();
        		Callable<Boolean> c=()->{
					return UserDao.checkPassword(vuser, fieldPassword.getText());
        			
        		};
        		Future<Boolean> future=executor.submit(c);
        		try{
        			Boolean result=future.get();
        			if(result){
        				System.out.println("password matched");
        				PasswordDialog.vpassword=fieldPassword.getText();
            			close();
        			}
        			
        			
        		}catch(Exception ex){
        			ex.printStackTrace();
        		}
        		
        		
        	}
        	fieldPassword.setStyle("-fx-border-color:red;"
        			+ "-fx-background-color:#2E313B;"
        			+"-fx-text-fill:white;"
        			+"-fx-font-size:14px;");
        });
        
        fieldPassword.setOnMouseClicked(e->{
        	 fieldPassword.setStyle("-fx-background-radius:2;"
              		+ "-fx-background-color:#2E313B;"
              		+ "-fx-text-fill:white;"
              		+ "-fx-font-size:14px;"
              		+ "-fx-border-width:1px"
              		);
        });
        	
        	
       
      

    }
	public static String getPassword(){
		return PasswordDialog.vpassword;
	}
	
}