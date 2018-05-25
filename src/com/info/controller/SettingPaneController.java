package com.info.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.info.dao.UserDao;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

public class SettingPaneController implements Initializable {

	@FXML private Button deleteBtn;
	@FXML private Button changeBtn;
	@FXML private PasswordField deletePasswordField;
	@FXML private Label msgLabel;
	@FXML private PasswordField currentPasswordField;
	@FXML private PasswordField newPasswordField;
	@FXML private PasswordField repeatPasswordField;
	@FXML private VBox changePanel;
	@FXML private Label currentPassword_label;
	@FXML private Label newPassword_label;
	@FXML private Label repeatPassword_label;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		deletePasswordField.setVisible(false);
		
		//hide the password change input box and only reveal when user click change password button
	
		
		
		
		
		
		
		
		// TODO Auto-generated method stub
		System.out.println("settingPaneControllerinitlai called");
		deleteBtn.setOnAction(e->{
			System.out.println("delete btn clicked");
			deletePasswordField.setVisible(true);
			deleteBtn.setLayoutX(210);
		});
		//changing password
		
		changeBtn.setOnAction(e->{
			System.out.println("change btn clicked");
			
			//checking if the any input field is empty or not
		if(currentPasswordField.getText().equals("") && newPasswordField.getText().equals("") && repeatPasswordField.getText().equals("")){
				
			msgLabel.setText("Please check your inputs");
				
			}else{
				
				//checking whether new password field and repeat password field matches or not
				if(newPasswordField.getText().equals(repeatPasswordField.getText())){
					//updating password in database
					if(UserDao.changePassword(currentPasswordField.getText(), newPasswordField.getText())){
						//password change
						System.out.println("password change");
					}else{
						msgLabel.setText("Please check your current Password");
					}
					
				}else{
					msgLabel.setText("New Password and Repeat Password doesnot match");
				}
			}
		});
		
	}
	public void settingInitial(){
		System.out.println("settingpane controller called");
		
		
	}

}
