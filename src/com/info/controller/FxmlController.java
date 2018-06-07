package com.info.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.info.dao.UserDao;
import com.info.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FxmlController implements Initializable   {
	@FXML private TextField emailField;
	@FXML private PasswordField passwordField;
	@FXML private Hyperlink signup_link;
	@FXML private Label title_label;
	//signup panel UI element
	@FXML private TextField signup_emailAddress_textField;
	@FXML private PasswordField signup_password_textField;
	@FXML private PasswordField signup_repassword_textField;
	@FXML private CheckBox termCondition_label;
	@FXML private CheckBox defaultCheckBox;
	@FXML private Button login_btn;
	@FXML private Hyperlink resetPasswordLink;
	@FXML private static Pane loginPane;
	private Stage MainWindow=null;
//	private static User vuser;

	
	
	
//	public void signupPanel(ActionEvent event){
//		//switching scene to signup when link in login screen clicked
//		System.out.println("signup opened ");
//		try{
//			//switching scene
//			//there is only stage in program whereas there can multiple scene in progarm
//			//to switching we have to access to previous stage to change of scene
//			
//			//loading the signup Fxml file
//			Parent signupParent=FXMLLoader.load(getClass().getResource("/application/signup_design.fxml"));
//			//creating the scene of signup
//			Scene signupScene=new Scene(signupParent);
//			//loading its css file in the scene
//			signupScene.getStylesheets().add(getClass().getResource("/application/signup_design.css").toExternalForm());	
//			//acces previous stage to change scene of it
//			Stage window=(Stage)((Node) event.getSource()).getScene().getWindow();
//			//setting new scene in stage
//			window.setScene(signupScene);
//			window.show();
//			
//		}catch(Exception e){
//			System.out.println("error occur"+e);
//		}
//	}
//	
//	public void loginScreen(ActionEvent event){
//		//switching scene to login panel
//		System.out.println("switching back to login screen");
//		try{
//			//loading loginscreen fxml file
//			Parent loginParent=FXMLLoader.load(getClass().getResource("/application/MainScene.fxml"));
//			//creating scene for login screen
//			Scene loginScene=new Scene(loginParent);
//			//loading the css file in scene
//			loginScene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
//			//setting scene in stage,first we have to access the original stage from controller class
//			Stage Window=(Stage)((Node)event.getSource()).getScene().getWindow();
//			//setting scene into stage
//			Window.setScene(loginScene);
//			Window.show();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
	//signup 
	
	public void signupUser(ActionEvent event){
		System.out.println("signup btn clicked");
		if(termCondition_label.isSelected()){//checking checkbox condition
	    if(signup_emailAddress_textField.getText().equals("")&& signup_password_textField.getText().equals("") && signup_repassword_textField.getText().equals("")){
	    	alertBox("Password Manager - Signup","Please check your Inputs");
	    
	    }else{
	    	//email verification
	    	if(emailVerification(signup_emailAddress_textField.getText())){
	    		//email pattern matches
	    		//checking passowor field and retype passoword field
	    		 if(signup_password_textField.getText().equals(signup_repassword_textField.getText())){
	    			 //same
	    			 alertBox("Password Manager - Signup","everything is ok");
	    			User user=new User();
	    			 user.setEmail(signup_emailAddress_textField.getText());
	    			 user.setPassword(signup_password_textField.getText());
	    			 
	    			 
	    			 ExecutorService executor = Executors.newSingleThreadExecutor();
	    			    Callable<Boolean> c = () -> {
	    			    	 UserDao userdao=new UserDao();
	    			    	 System.out.println(Thread.currentThread().getName());
	    	    			 return userdao.registerUser(user);
	    			      };
	    			    Future<Boolean> future = executor.submit(c);
	    			    try {
	    			        Boolean result = future.get(); //waits for the thread to complete
	    			        if(result){
	    			        	System.out.println("open logged window");
	    			        }else{
	    			        	System.out.println("show error about unable to signup");
	    			        }
	    			    } catch (ExecutionException | InterruptedException e) {
	    			        e.printStackTrace();
	    			    }
	    			    executor.shutdown();
	    			 
	    		 }else{
	    			 alertBox("Password Manager - Signup","Password doesnot match");
	    		 }
	    	}else{
	    		//not matches
	    		alertBox("Password Manager - Signup","Please enter valid Email address");
	    	}
	    	
	    }
		}else{
			alertBox("Password Manager - Signup","please accept term and condition");
		}
	}
	public void checkLogin(ActionEvent event) throws InterruptedException{
		login_btn.setText("loading");
		
		
		if(emailField.getText().equals("") && passwordField.equals("") && defaultCheckBox.isSelected()==false){
			alertBox("Password Manager","please check your input");
			
		}else{
			if(emailVerification(emailField.getText())){
				User user=new User();
				user.setEmail(emailField.getText());
				user.setPassword(passwordField.getText());
				//user.setDefaultUser(defaultCheckBox.isSelected());
			   
				//Parent root = null;
				//verified user 
				//User vuser=userdao.loginAuth(user);
				
				ExecutorService executor=Executors.newSingleThreadExecutor();
				Callable<User> c=()->{
					UserDao userdao=new UserDao();
					System.out.println(Thread.currentThread().getName());
					return userdao.loginAuth(user);
				};
				
				Future<User> future=executor.submit(c);
				User user1=new User();
			
				  try {
					 user1=future.get();
				}  catch (ExecutionException | InterruptedException e1) {
					e1.printStackTrace();
				}
				
				
				
				if(user1!=null){
					//writing file
					
					
					MainWindow=(Stage)((Node)event.getSource()).getScene().getWindow();
					// vuser=user1;
					//code start for communicating between two controller for exchanaging the information
						FXMLLoader loader=new FXMLLoader();
						loader.setLocation(getClass().getResource("/application/MainWindow.fxml"));
						try{
							loader.load();
						}catch(Exception e){
							e.printStackTrace();
						}
						MainWindowController mainController=loader.getController();
						mainController.mainWindowInitialize(user1);
						//end of code for communication between two controller
						Parent p=loader.getRoot();
						//creating scene for mainWindow 
						Scene mainWindowScene =new Scene(p);
						//loading css file for scene
						mainWindowScene.getStylesheets().add(getClass().getResource("/application/MainWindow.css").toExternalForm());
						//access stage 
					//	Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
						MainWindow.setScene(mainWindowScene);
						MainWindow.setTitle("Password Manager - "+user1.getEmail());
						MainWindow.show();
					
					
					
					
					
					

				}else{
					alertBox("Password Manager","Email Address and Password doesnot match");
					

				}
				
			}else{
				
				alertBox("Password Manager","invalid Email Address");
				
			}
			
		}
		
	}//end login auth
	
	
	
	//alert box 
	private void alertBox(String title,String message){
		Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle(title);
    	alert.setHeaderText(null);
    	alert.setContentText(message);
    	alert.showAndWait();
	}
	//checking the email pattern
	protected static boolean emailVerification(String email){
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(email);
         
	    return matcher.matches();//match =true return

		
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("fxml controller called");
		
	
		
		
	}
	
	
	
	
}
