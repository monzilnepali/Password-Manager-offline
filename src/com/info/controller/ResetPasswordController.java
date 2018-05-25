package com.info.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.info.dao.SendMail;
import com.info.dao.UserDao;
import com.mifmif.common.regex.Generex;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ResetPasswordController implements Initializable {

	@FXML private StackPane ResetPasswordStackPane;
	@FXML private Pane emailVerification;//asking user to enter email address
    @FXML private Pane confirmation;//asking user to copy code send to email and enter it for verification
	@FXML private Pane NewPassword;//setup new password 
	@FXML private Button ContinueBtn;
	@FXML private TextField emailField;
	@FXML private Label msgLabel;
	@FXML private TextField codeVerificationTextField;
	@FXML private Button confirmationBtn;
	@FXML private Label msgLabel02;
	@FXML private PasswordField resetPasswordField;
	@FXML private PasswordField reresetPasswordField;
	@FXML private Button changePasswordBtn;
	private static String code;
	private static String currentUserEmail;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("password reset controller called");
		emailVerification.setVisible(true);
		confirmation.setVisible(false);
		NewPassword.setVisible(false);
		
		emailField.setOnMousePressed(e->{
			msgLabel.setText("");
		});
		
		ContinueBtn.setOnAction(e->{
			//checking email field
			if(emailField.getText().equals("")){
				msgLabel.setText("please check input");
			}
			//else if(FxmlController.emailVerification(emailField.getText())){
				//msgLabel.setText("invalid email Address");
			//}
			else{
				//checking useremail indatabase if it exist or not
				
				ExecutorService executor=Executors.newFixedThreadPool(2);
				Callable<Boolean> c=()->{
					System.out.println("check email");
					System.out.println("thread name"+Thread.currentThread().getName());
					return UserDao.checkEmailAddress(emailField.getText());
					
				};
				Callable<Boolean> d=()->{
					System.out.println("send mail");
					Generex generex=new Generex("[a-z0-9A-z]{11}");
					code=generex.random();
					System.out.println("thread name001"+Thread.currentThread().getName());
					return SendMail.SendMailMethod(code, emailField.getText());
					
				};
				Future<Boolean> future=executor.submit(c);
			
				try{
					Boolean result=future.get();
					if(result){
						ResetPasswordController.currentUserEmail=emailField.getText();
						//useremail exist proceed to send mail to useremail address
						 emailVerification.setVisible(false);
							confirmation.setVisible(true);
						Future<Boolean> future1=executor.submit(d);
						try{
							Boolean result1=future1.get();
							System.out.println("mail sended "+result1);
							  
							
						}catch(Exception ex1){
							ex1.printStackTrace();
						}
						
						
					}else{
						msgLabel.setText("email Address doest exist");
					}
					
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
			    executor.shutdown();

				
				
				
				
				
				
				
			}
			
			
		});
		
		confirmationBtn.setOnAction(e->{
			 System.out.println("confirmation btn");
			 if(codeVerificationTextField.getText().equals("")){
				//empty field
				 msgLabel02.setText("please check your input");
				 
			 }else{
				 
				 
				 if(codeVerificationTextField.getText().equals(code)){
					 System.out.println("we are good to for password reset");
					 confirmation.setVisible(false);
						NewPassword.setVisible(true);
						
						
						
				 }else{
					 msgLabel02.setText("invalid code");
				 }
			 }
			 
			
		});
		changePasswordBtn.setOnAction(e->{
			System.out.println("change password button clicked");
			
			if(resetPasswordField.getText().equals("") && reresetPasswordField.getText().equals("")){
				//checki  input error
				
			}else{
				
				if(resetPasswordField.getText().equals(reresetPasswordField.getText())){
					//password match 
					if(UserDao.updatePassword(ResetPasswordController.currentUserEmail, resetPasswordField.getText())){
					 System.out.println("password change");
					 Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
					 stage.close();
					}else{
						System.out.println("error occur while updating password");
					}
					
				}else{
					//error password doesnot match
				}
			}
			
		});
		
		
		
	}
	

	
}
