package com.info.controller;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.info.dao.NoteDao;
import com.info.model.SecureDataModel;
import com.info.model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



public class AddNoteController implements Initializable {
	private static User Vuser;
   private static Boolean dialogFlag=false;
	private SecureNoteController sc;
	 @FXML private ComboBox<String> Note_categories;
	 @FXML private TextField note_Name;
	 @FXML private TextArea note_textArea;
	ObservableList<String> list=FXCollections.observableArrayList("Bank ","Email","ATM code","security No.");	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		System.out.println("add note controller");
		Note_categories.setItems(list);
		
		
		
	}
	public void setUser(User vuser,SecureNoteController s){
		System.out.println("name manjil");
		this.sc=s;
		Vuser=vuser;
	}
	public void addNoteToDatabaseBtn(ActionEvent event) throws NoSuchAlgorithmException{
		
		
		//assking user for master passsword
		
		 Stage primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
		   Stage PasswordDialog = null;
		try {
			System.out.println("user id in editdata function"+AddNoteController.Vuser.getUser_id());
			primaryStage.close();
			PasswordDialog = new PasswordDialog(primaryStage,AddNoteController.Vuser);
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
		
		
		
		
		System.out.println("btn clicked");
		SecureDataModel sd=new SecureDataModel();
		sd.setSecureData_name(note_Name.getText());
		sd.setSecureData_categories(Note_categories.getValue());
		sd.setSecureData_Note(note_textArea.getText());
		if(NoteDao.addNoteToDatabase(sd,com.info.controller.PasswordDialog.getPassword())){
			//successfully inserted new note in database
			//accessing current stage
		//	Stage currentStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			primaryStage.close();
			sc.getNoteDataFromTable();
		
		}else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!!!");
			alert.setHeaderText(null);
			alert.setContentText("Error while inserting data in database.Please try Again");
			alert.showAndWait();
		}

	}

}
