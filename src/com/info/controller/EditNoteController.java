package com.info.controller;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.info.dao.NoteDao;
import com.info.dao.Security;
import com.info.model.SecureDataModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditNoteController implements Initializable {

	private SecureNoteController sc;
	@FXML private TextField note_Name;
	@FXML private ComboBox<String> Note_categories;
	@FXML private TextArea note_textArea;
	@FXML private Label note_createdDate;
	@FXML private Label note_Date;
	@FXML private Label note_updateCreated;
	@FXML private Button edit_noteBtn;
	@FXML private Button edit_noteBtn1;
	ObservableList<String> list=FXCollections.observableArrayList("Bank ","Email","ATM code","security No.");
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		System.out.println("edit note controller");
		Note_categories.setItems(list);
		
		
		
	}
	public void editNote(SecureDataModel sd,SecureNoteController s,Boolean flag,String Master) throws Exception{
		Security se=new Security(Master);
		
		if(flag){
			//means edit window
			edit_noteBtn.setVisible(true);
			edit_noteBtn1.setVisible(false);
		}else{
			//view data window and have option to switch to edit mode
			
			edit_noteBtn.setVisible(false);
			edit_noteBtn1.setVisible(true);
			note_Name.setEditable(false);
			Note_categories.setDisable(true);
			note_textArea.setEditable(false);
			
			edit_noteBtn1.setOnAction(e->{
				edit_noteBtn.setVisible(true);
				edit_noteBtn1.setVisible(false);
				Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
				stage.setTitle("Password Manager- Edit secureNote");
				note_Name.setEditable(true);
				Note_categories.setDisable(false);
				note_textArea.setEditable(true);
			});
			
		}
		
		
		
		this.sc=s;
	
			String decryptNote=se.decrypt(sd.getSecureData_Note());
		
		note_Name.setText(sd.getSecureData_name());
		note_textArea.setText(decryptNote);
		Note_categories.setValue(sd.getSecureData_categories());
		note_createdDate.setText(sd.getSecureData_DateOfCreation());
		if(sd.getSecureData_updateDate()!=null){
			note_updateCreated.setText(sd.getSecureData_updateDate());
		}else{
			note_updateCreated.setText("no update made");
		}
		
		edit_noteBtn.setOnAction(e->{
			System.out.println("bntn edit");
			//checking if there is any change made
			if(note_Name.getText().equals(sd.getSecureData_name())
					&& note_textArea.getText().equals(decryptNote) 
					&& Note_categories.getValue().equals(sd.getSecureData_categories())){
				System.out.println("nothing to change ");
				
				
			}else{
				//System.out.println("update data");
				sd.setSecureData_name(note_Name.getText());
				sd.setSecureData_categories(Note_categories.getValue());
				sd.setSecureData_Note(note_textArea.getText());
				
				if(NoteDao.UpdateNoteToDatabase(sd)){
					System.out.println("update maded");
					Stage primaryStage=(Stage)((Node)e.getSource()).getScene().getWindow();
					primaryStage.close();
					sc.getNoteDataFromTable();
				}else{
					System.out.println("error while updating the note");
				}
				
			}
			
		});
	}

}
