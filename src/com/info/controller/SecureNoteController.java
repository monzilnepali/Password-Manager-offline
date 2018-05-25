package com.info.controller;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



public class SecureNoteController implements Initializable {

 @FXML private AnchorPane pane;
 @FXML private Button add_btn;
 @FXML private TableView < SecureDataModel > secureNote_table;
 @FXML private TableColumn < SecureDataModel, String > secureData_name;
 @FXML private TableColumn < SecureDataModel, String > secureData_categories;
 @FXML private TableColumn < SecureDataModel, String > secureData_updateDate;
 private static ContextMenu contextMenu = new ContextMenu();
 private static SeparatorMenuItem separatorMenuItem;
 private static SecureDataModel currentTableRow;
private static Event tableRowSelectEvent;
private Boolean dialogFlag=false;
private static User vuser;



 public SecureNoteController(User user) {
	// TODO Auto-generated constructor stub
	 vuser=user;
}
@Override
 public void initialize(URL location, ResourceBundle resources) {
  //System.out.println("secure note controlle called");
  //loading data in table
  getNoteDataFromTable();
  contextMenu.getItems().clear();



  //context menu
  MenuItem item1 = new MenuItem(" View Data ");
  item1.setStyle("-fx-padding:2 8 2 8;");
  item1.setOnAction(new EventHandler < ActionEvent > () {

   @Override
   public void handle(ActionEvent event) {
    //System.out.println("current selected table row is"+currentTableRow.getLabel());
	   editNoteFunction(tableRowSelectEvent,currentTableRow,false);
   }
  });
  MenuItem item2 = new MenuItem(" Edit Data ");
  item2.setStyle("-fx-padding:2 8 2 8;");
  item2.setOnAction(new EventHandler < ActionEvent > () {

   @Override
   public void handle(ActionEvent event) {
    System.out.println("edit data context menu");
   
    editNoteFunction(tableRowSelectEvent,currentTableRow,true);
   }
  });

  MenuItem item3 = new MenuItem(" Delete Data ");
  item3.setStyle("-fx-padding:2 8 2 8;");
  item3.setOnAction(new EventHandler < ActionEvent > () {

   @Override
   public void handle(ActionEvent event) {
    //delete data action
	   deleteNoteFunction(tableRowSelectEvent,currentTableRow);
   }
  });
  MenuItem item4 = new MenuItem(" Copy Password ");
  item4.setStyle("-fx-padding:2 8 2 8;");
  item4.setOnAction(new EventHandler < ActionEvent > () {

   @Override
   public void handle(ActionEvent event) {

   }
  });
  MenuItem item5 = new MenuItem(" View Log Data ");
  item5.setStyle("-fx-padding:2 8 2 8;");
  item5.setOnAction(new EventHandler < ActionEvent > () {

   @Override
   public void handle(ActionEvent event) {

   }
  });
  MenuItem item6 = new MenuItem(" Close ");
  item6.setStyle("-fx-padding:2 8 2 8;");
  item6.setOnAction(new EventHandler < ActionEvent > () {

   @Override
   public void handle(ActionEvent event) {
     contextMenu.hide();
   }
  });
  separatorMenuItem = new SeparatorMenuItem();


  // Add MenuItem to ContextMenu
  contextMenu.getItems().addAll(item1, item2, item3, item4, item5, separatorMenuItem, item6);


  secureNote_table.setOnContextMenuRequested(new EventHandler < ContextMenuEvent > () {

   @Override
   public void handle(ContextMenuEvent event) {
    // TODO Auto-generated method stub
    System.out.println("context menu option called");
    //tableRowSelectEvent=event;
    //currentTableRow=table.getSelectionModel().getSelectedItem();
    contextMenu.show(secureNote_table, event.getScreenX(), event.getScreenY());

   }

  });






  //end of context menu

  secureNote_table.setOnMouseClicked(e -> {
	  System.out.println("mouse clicked");
	  MouseButton button=e.getButton();
	  if(button==MouseButton.PRIMARY){
		  System.out.println("primary button clicked");
		  tableRowSelectEvent=e;
		  currentTableRow=secureNote_table.getSelectionModel().getSelectedItem();
		  secureNote_table.setId("blue_cell");
		  contextMenu.hide();
	       if (e.getClickCount() == 2) {
	    	   System.out.println("twice button clicked");
	          currentTableRow = secureNote_table.getSelectionModel().getSelectedItem();
	          editNoteFunction(tableRowSelectEvent,currentTableRow,false);
       }
	  }
  });



 }//end of initialize
 //edit window panel
 public void editNoteFunction(Event event,SecureDataModel selected,Boolean flag) {
	 
	 Stage primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
	   Stage PasswordDialog = null;
	try {
		System.out.println("user id in editdata function"+vuser.getUser_id());
		PasswordDialog = new PasswordDialog(primaryStage,vuser);
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
	
	 
	 
	 
	 
	 
	 
	 FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(getClass().getResource("/application/EditNote.fxml"));
	    try {
	     loader.load();
	    } catch (Exception e1) {
	     // TODO Auto-generated catch block
	     e1.printStackTrace();
	    }
	    EditNoteController noteController = loader.getController();
	    try {
			noteController.editNote(selected, this,flag,com.info.controller.PasswordDialog.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Parent p = loader.getRoot();
	    Scene noteScene = new Scene(p);
	    Stage noteStage = new Stage();
	    noteStage.setScene(noteScene);
	    
	    if(flag){
	    	//edit window
	    	 noteStage.setTitle("Password Manager- Edit SecureNote");
	    }else{
	    	 noteStage.setTitle("Password Manager- SecureNote View");
	    }
	   
	    noteStage.initModality(Modality.WINDOW_MODAL);
	    noteStage.initOwner(primaryStage);
	    noteStage.setResizable(false);
	    noteStage.show();


 }
 public void refreshNoteBtn(ActionEvent event) {
  getNoteDataFromTable();
 }
 public void getNoteDataFromTable() {
  ArrayList < SecureDataModel > list = getDataFromDatabase();
  ObservableList < SecureDataModel > observablelist = FXCollections.observableArrayList(list);

  secureData_name.setCellValueFactory(new PropertyValueFactory < SecureDataModel, String > ("secureData_name"));
  secureData_categories.setCellValueFactory(new PropertyValueFactory < SecureDataModel, String > ("secureData_categories"));
  secureData_updateDate.setCellValueFactory(new PropertyValueFactory < SecureDataModel, String > ("secureData_updateDate"));
  secureNote_table.setItems(observablelist);

 
 
  
 }
 public ArrayList < SecureDataModel > getDataFromDatabase() {
  //multithreading and getdata from database in different thread
  ExecutorService executor = Executors.newSingleThreadExecutor();
  Callable < ArrayList < SecureDataModel >> c = new Callable < ArrayList < SecureDataModel >> () {
   public ArrayList < SecureDataModel > call() {
    ArrayList < SecureDataModel > list = NoteDao.RetrieveSecureNote();
    return list;
   }
  };
  Future < ArrayList < SecureDataModel >> future = executor.submit(c);
  try {
   ArrayList < SecureDataModel > result = future.get(); //waits for the thread to complete

   return result;
  } catch (ExecutionException | InterruptedException e) {
   e.printStackTrace();
  }
  executor.shutdown();
  return null;

 }

 public void addNote(ActionEvent event) {
  //adding new Secure Note operation
  System.out.println("add btn clicked");
  //open pop for adding secure note
  //transfering control to its own controller
  FXMLLoader loader = new FXMLLoader();
  loader.setLocation(getClass().getResource("/application/AddNote.fxml"));
  try {
   loader.load();
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  AddNoteController newController = loader.getController();
  newController.setUser(vuser, this);
  Parent p = loader.getRoot();

  //creating new scene 
  Scene newScene = new Scene(p);
  newScene.getStylesheets().add(getClass().getResource("/application/AddNote.css").toExternalForm());
  Stage newWindow = new Stage();
  //accessing current stage;
  Stage primaryWindow = (Stage)((Node) event.getSource()).getScene().getWindow();
  newWindow.setScene(newScene);
  newWindow.setTitle("AddSecure Note");
  newWindow.initModality(Modality.WINDOW_MODAL);
  newWindow.initOwner(primaryWindow);
  newWindow.setResizable(false);
  newWindow.show();





 }
 
 
 public void deleteNoteFunction(Event event,SecureDataModel selected){
	 
	 if (selected == null) {
		   System.out.println("please select row ");
		  } else {
		   Alert alert = new Alert(AlertType.CONFIRMATION);
		   alert.setTitle("Confirmation Dialog");
		   alert.setHeaderText(null);
		   alert.setContentText("Are your sure to delete this data");
		   Label label001 = new Label("Label :" + selected.getSecureData_name());
		   Label label002 = new Label("Email :" + selected.getSecureData_Note());
		   VBox vb = new VBox();
		   vb.setSpacing(10);
		   ObservableList < Node > list = vb.getChildren();
		   list.addAll(label001, label002);
		   alert.getDialogPane().setExpandableContent(vb);
		   Optional < ButtonType > result = alert.showAndWait();
		   if (result.get() == ButtonType.OK) {
		    System.out.println("delete");
		    //deleting data from database based upon passwordid
		    if (NoteDao.deleteNote(selected)) {
		     getNoteDataFromTable();
		     System.out.println("successfully deleted");
		    } else {
		     System.out.println("error occur while deleting note!Please try again");
		    }
		   } else {
		    // ... user chose CANCEL or closed the dialog
		   }
		  }
 }
 

}