package com.info.controller;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.info.dao.UserDao;
import com.info.model.PasswordData;
import com.info.model.User;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AddDataController  implements Initializable {
	private static boolean dialogFlag=false;
     	private static User vuser;
     	private static MainWindowController mainController;
	//add data scene ui component
		@FXML private TextField label_field;
		@FXML private TextField email_field;
		@FXML private PasswordField password_field;
		@FXML private TextField password_text_field;
		@FXML private FontAwesomeIconView eye_icon;
		@FXML private Button add_btn;
		@FXML private ComboBox<String> 	categories_field;
		ObservableList<String> list=FXCollections.observableArrayList("Email","Social Network","youtube","Website");
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("add data controller called");
		categories_field.setItems(list);
	}
	public void addDatacontrollerInitialize(User vuser,String Pass,MainWindowController mainController){
		//mainwindow controller is passed as arugment because we want to call is method to refresh table data with new one
		AddDataController.vuser=vuser;
		AddDataController.mainController=mainController;
		password_text_field.setVisible(false);
		password_field.setText(Pass);//when it called from password generator panel which pass password as parameter
		
		eye_icon.setOnMouseClicked(e->{
			//togglling the eye icon to view password and mask it
			//System.out.println("eye clicked");
			//System.out.println("icon name"+eye_icon.getGlyphName());
			if(eye_icon.getGlyphName().equals("EYE_SLASH")){
			   eye_icon.setGlyphName("EYE");
			   //showing password
			    password_text_field.setText(password_field.getText());
			    password_text_field.setVisible(true);
			    password_field.setVisible(false);
			   
			}else{
				//hidding password
				eye_icon.setGlyphName("EYE_SLASH");
				  password_field.setText(password_text_field.getText());
				    password_text_field.setVisible(false);
				    password_field.setVisible(true);
			}
		});
	
		
	}
		public void addDataAction(ActionEvent event) throws NoSuchAlgorithmException{
		
		if(label_field.getText()!=null && email_field.getText()!=null && password_field.getText()!=null && categories_field.getValue()!=null ){
			//opening model toask user for master password so that data can encrypted 
			 Stage primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			   Stage PasswordDialog = null;
			try {
				System.out.println("user id in editdata function"+AddDataController.vuser.getUser_id());
				primaryStage.close();
				PasswordDialog = new PasswordDialog(primaryStage,AddDataController.vuser);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			   
			   PasswordDialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
			          public void handle(WindowEvent we) {
			              System.out.println("Stage is closing");
			              
			              dialogFlag=true;
			          }
			      });  
			   PasswordDialog.sizeToScene();
			   PasswordDialog.showAndWait();
			   System.out.println("dialogflag"+dialogFlag);
			   if(dialogFlag){
				   //user close the dialog prompt box 
				   //if exit we have to return from this function
				   System.out.println("return from this from edit function");
				  return;
			   }
			
			
			
			//System.out.println(vuser.getEmail());
			PasswordData pd=new PasswordData();
			pd.setLabel(label_field.getText());
			pd.setEmail(email_field.getText());
			pd.setPassword(password_field.getText());
			pd.setCategories(categories_field.getValue());
			
			ExecutorService executor=Executors.newSingleThreadExecutor();
			Callable<Boolean> c=()->{
				
				return UserDao.addDataIntoPasswordDb(vuser,pd,com.info.controller.PasswordDialog.getPassword());
				
			};
			Future<Boolean> future=executor.submit(c);
			try{
				Boolean result=future.get();
				if(result){
					primaryStage.close();
				    if(mainController!=null){
				   //null is passes as argument when we called addData function from password generate window
				   mainController.getDataInTable();
				    }
				}else{
					System.out.println("error while add new data");
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			
			
			
		}else{
			//error
			//nothing to change all input is same as in databae so no
		}
	}

}
