package com.info.controller;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.info.dao.Security;
import com.info.dao.UserDao;
import com.info.model.PasswordData;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

public class EditDataController  implements Initializable, ClipboardOwner {
    private static String decryptPass;
	private static PasswordData pdata;
	private MainWindowController mainController;
	//editdata scene UiComponent
		@FXML private TextField e_label_field;
		@FXML private TextField e_email_field;
		@FXML private PasswordField e_password_field;
		@FXML private Button update_btn;
		@FXML private Button update_btn1;
		@FXML private Label DateOfCreation;
		@FXML private Label DateOfModify;
		@FXML private TextField password_text_field;
		@FXML private FontAwesomeIconView eye_icon;
		@FXML private Hyperlink clipboard_btn;
		@FXML private ComboBox<String> 	e_categories;
		ObservableList<String> list=FXCollections.observableArrayList("Email","Social Network","youtube","Website");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		e_categories.setItems(list);
		
	}
	public void EditDataScene(PasswordData pd,Boolean flag,MainWindowController mainController,String Master) throws Exception{
		Security se=new Security(Master);
		this.mainController=mainController;
		if(flag){
			//mean edit window
			update_btn.setVisible(true);
			update_btn1.setVisible(false);
		}else{
			update_btn.setVisible(false);
			update_btn1.setVisible(true);
			e_label_field.setEditable(false);
			e_email_field.setEditable(false);
			e_password_field.setEditable(false);
			password_text_field.setEditable(false);
			e_categories.setDisable(true);
			
			update_btn1.setOnAction(e->{
				Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
				stage.setTitle("Password Manager- Edit Password Data");
				update_btn.setVisible(true);
				update_btn1.setVisible(false);
				e_label_field.setEditable(true);
				e_email_field.setEditable(true);
				e_password_field.setEditable(true);
				password_text_field.setEditable(true);
				e_categories.setDisable(false);
				
			});
			
		}
		
		
		EditDataController.pdata=pd;
		decryptPass=se.decrypt(pd.getPassword());
		password_text_field.setVisible(false);
		e_label_field.setText(pd.getLabel());
		e_email_field.setText(pd.getEmail());
		e_password_field.setText(decryptPass );
		DateOfCreation.setText(pd.getDateOfCreation());
		DateOfModify.setText(pd.getLastDateOfModify());
		e_categories.setValue(pd.getCategories());
		//differenceInTime(pd.getLastDateOfModify());
		
		eye_icon.setOnMouseClicked(e->{
			//togglling the eye icon to view password and mask it
			//System.out.println("eye clicked");
			//System.out.println("icon name"+eye_icon.getGlyphName());
			if(eye_icon.getGlyphName().equals("EYE_SLASH")){
			   eye_icon.setGlyphName("EYE");
			   //showing password
			    password_text_field.setText(e_password_field.getText());
			    password_text_field.setVisible(true);
			    e_password_field.setVisible(false);
			   
			}else{
				//hidding password
				eye_icon.setGlyphName("EYE_SLASH");
				  e_password_field.setText(password_text_field.getText());
				    password_text_field.setVisible(false);
				    e_password_field.setVisible(true);
			}
		});
		clipboard_btn.setOnAction(e->{
			System.out.println("btn clicked");
			StringSelection stringSelection = new StringSelection(e_password_field.getText());
		    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		    clipboard.setContents(stringSelection, this);
		});
		
		
	}
	public void UpdateBtnAction(ActionEvent event){
		System.out.println("btn clicked");
		PasswordData newData=new PasswordData();
		newData.setLabel(e_label_field.getText());
		newData.setEmail(e_email_field.getText());
		newData.setPassword(e_password_field.getText());
		newData.setCategories(e_categories.getValue());
		//checking if there is any change in data 
		if(newData.getLabel().equals(pdata.getLabel()) && newData.getEmail().equals(pdata.getEmail()) && newData.getPassword().equals(decryptPass) && newData.getCategories().equals(pdata.getCategories())){
			//show message nothing to new to update in data
			System.out.println("nothing to change");
			
		}else{
			//updating data 
			UserDao.updateDataToPasswordDb(pdata.getPassword_id(),newData);
			 Stage primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
			  primaryStage.close();
			  mainController.getDataInTable();
			
			
		}
	}
	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
		// TODO Auto-generated method stub
		
	}
	
//	public void differenceInTime(String d1){
//		if(d1.equals("")){
//			//means there no any update in data to .DO nothing
//		}else{
//			SimpleDateFormat ft=new SimpleDateFormat("E yyyy-MM-dd 'at' hh:mm");
//			Date date1=new Date();//getting current time to find out difference in time
//		}
//		
//		
//	}
//		
////		 SimpleDateFormat ft =new SimpleDateFormat ("E yyyy-MM-dd 'at' hh:mm");
////		 Date date1=null;
////		 Date date2=new Date();//current time
////		//System.out.println("datesd"+date2.getTime());
////		 try{
////			 
////			if(d1.equals("")){
////				date1=new Date(0);
////			}else{
////			date1=ft.parse(1);
////			}
////			
////			 
////		 }catch(Exception e){
////			 e.printStackTrace();
////		 }
////		 //if there is not modification date then show message no update
////		 if(date2.getTime()!=0){
////		 long diff = date2.getTime() - date1.getTime();
////		 System.out.println("diff"+diff);
////		 long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
////		 long minutes = TimeUnit.MILLISECONDS.toMinutes(diff); 
////		 long hour=TimeUnit.MILLISECONDS.toHours(diff);
////		 long day=TimeUnit.MILLISECONDS.toDays(diff);
////		DateTime dt=new DateTime();
////		 if(seconds!=0 && (seconds/100)<60){
////			 System.out.println(seconds +"sec Ago");
////			 dt.setSecond(seconds +"sec Ago");
////		 }
////		 if(minutes!=0 && (minutes/100)<3600){
////			 dt.setMinute(minutes+"min Ago");
////		 }
////		// if(hour!=0 && (hour))
////		 
////		 System.out.println("second"+seconds+"minute"+minutes+"hour"+hour);
////		 }
////
////	}

}
