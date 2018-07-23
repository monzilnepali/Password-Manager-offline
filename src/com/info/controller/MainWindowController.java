package com.info.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.info.dao.UserDao;
import com.info.model.PasswordData;
import com.info.model.User;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MainWindowController implements Initializable {

private static boolean dialogFlag=false;
   protected User vuser;
   private PasswordData currentTableRow=null;
   private static  ContextMenu contextMenu = new ContextMenu();
   private static Event tableRowSelectEvent=null;
   @FXML private Label currentUser;
	@FXML private Button add_btn;
	
	@FXML protected TableView<PasswordData> table;
	@FXML private TableColumn<PasswordData,String> label;
	@FXML private TableColumn<PasswordData,String>email;
	@FXML private TableColumn<PasswordData,String>categories;
	//@FXML private TableColumn<PasswordData,Integer> dateOfCreation;
	@FXML private TableColumn<PasswordData,Integer> lastDateOfModify;
	
	//sidebar button
	@FXML private   Button home;
	@FXML private  Button secure_note;
	@FXML private  Button strength_analysis;
	@FXML private  Button password_generator;
	@FXML private VBox sidebar;
	@FXML private BorderPane RightSideView;
    @FXML private   Button previousBtn;	
    //settingicon
    @FXML private FontAwesomeIconView settingBtn;
    @FXML private MenuItem aboutMenu;
   @FXML private MenuBar menubar;
   //singleton 
   private CurrentUserSingleton singleton=CurrentUserSingleton.getInstance();
   
   
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vuser=singleton.getVuser();
		System.out.println("main window controller initlialize called");
		settingBtn.setOnMousePressed(e->{
			System.out.println("setting btn clicked");
		
			//loading setting fxml file
			SettingPaneController controller=new SettingPaneController();
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/SettingPane.fxml"));
			loader.setController(controller);
			try {
				RightSideView.setCenter(loader.load());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		});
		
		aboutMenu.setOnAction(e->{
			System.out.println("menu item clicked");
			//opening about stage
			try {
				Parent p=FXMLLoader.load(getClass().getResource("/application/About.fxml"));
				Scene newScene =new Scene(p);
				Stage newStage=new Stage();
				Stage primaryStage=(Stage)menubar.getScene().getWindow();
				newStage.setScene(newScene);
				newStage.setTitle("Password Manager : About");
				newStage.initModality(Modality.WINDOW_MODAL);
				newStage.initOwner(primaryStage);
				newStage.show();
				
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
	}
		
	
	public void btn1Action(ActionEvent event){
		//sidebar btn action
		//System.out.println("home btn clicked"+currentActiveBtn.getId());
		if(previousBtn!=null){
			previousBtn.setStyle("-fx-background-color:transparent");
		}else{
			//first home is active
			previousBtn=home;
			home.setStyle("-fx-background-color:transparent");
		}
	
		Button currentActiveBtn=((Button)event.getSource());
		
	//	System.out.println(currentActiveBtn.getId());
		currentActiveBtn.setStyle("-fx-background-color:#20232D;");
	//	System.out.println(previousBtn.getId());
		
		if(currentActiveBtn.getId()!=previousBtn.getId()){
			//if current Active Btn is same as previous btn then no need to perform any active,left as is it
		if(currentActiveBtn.getId().equals("home")){
			//home btn clicked
			
			//System.out.println("home called agained");
			//loading sideview of tableview
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/Home.fxml"));
			loader.setController(this);
			RightSideView.getStylesheets().add(getClass().getResource("/application/Home.css").toExternalForm());
			try {
				RightSideView.setCenter(loader.load());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			getDataInTable();
			//event listeniser for showing content menu
			table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>(){
				@Override
				public void handle(ContextMenuEvent event) {
					// TODO Auto-generated method stub
					System.out.println("context menu option called");
					tableRowSelectEvent=event;
					currentTableRow=table.getSelectionModel().getSelectedItem();
					contextMenu.show(table, event.getScreenX(), event.getScreenY());
					
				}
	        	
	        });
				}
		
		 if(currentActiveBtn.getId().equals("secure_note")){
			 //if secure note bnt clicked open securenote.fxml file with its seperate controller
		//	 System.out.println("securenote panel called");
			 SecureNoteController sController=new SecureNoteController(vuser);
			 FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/SecureNote.fxml"));
			 RightSideView.getStylesheets().add(getClass().getResource("/application/Home.css").toExternalForm());
				loader.setController(sController);
				try {
					RightSideView.setCenter(loader.load());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		 }
		 if(currentActiveBtn.getId().equals("password_generator")){
			 //if secure note bnt clicked open securenote.fxml file with its seperate controller
		//	 System.out.println("securenote panel called");
			 PasswordGeneratorController sController=new PasswordGeneratorController(vuser);
			 FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/PasswordGenerator.fxml"));
			 RightSideView.getStylesheets().add(getClass().getResource("/application/PasswordGenerator.css").toExternalForm());
				loader.setController(sController);
				try {
					RightSideView.setCenter(loader.load());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		 }
		}	
		
		
		previousBtn=currentActiveBtn;
	}
	public  void mainWindowInitialize(List<PasswordData> pdlist){
	//	System.out.println("home called");
		//loading sideview of tableview
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/Home.fxml"));
		RightSideView.getStylesheets().add(getClass().getResource("/application/Home.css").toExternalForm());
		loader.setController(this);
		try {
			RightSideView.setCenter(loader.load());
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		
		System.out.println("populating table");
		ObservableList<PasswordData> list=FXCollections.observableArrayList(pdlist);
        label.setCellValueFactory(new PropertyValueFactory<PasswordData,String>("label"));
        email.setCellValueFactory(new PropertyValueFactory<PasswordData,String>("email"));
        categories.setCellValueFactory(new PropertyValueFactory<PasswordData,String>("categories"));
    //  dateOfCreation.setCellValueFactory(new PropertyValueFactory<PasswordData,Integer>("dateOfCreation"));
        lastDateOfModify.setCellValueFactory(new PropertyValueFactory<PasswordData,Integer>("lastDateOfModify"));
        
        table.setItems(list);
		
		
		
		
		
		
		
		// Create ContextMenu
       
 
        MenuItem item1 = new MenuItem(" View Data ");
       item1.setStyle("-fx-padding:2 8 2 8;");
        item1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	table.getSelectionModel().clearSelection();
            	System.out.println("current selected table row is"+currentTableRow.getLabel());
            	try {
					editDataFunction(tableRowSelectEvent,currentTableRow,false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        MenuItem item2 = new MenuItem(" Edit Data ");
        item2.setStyle("-fx-padding:2 8 2 8;");
        item2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	table.getSelectionModel().clearSelection();
            	System.out.println("edit data context menu");
            	try {
					editDataFunction(tableRowSelectEvent,currentTableRow,true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        
        MenuItem item3 = new MenuItem(" Delete Data ");
        item3.setStyle("-fx-padding:2 8 2 8;");
        item3.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	table.getSelectionModel().clearSelection();
                //delete data action
            	deleteDataAction(tableRowSelectEvent,currentTableRow);
            }
        });
        MenuItem item4 = new MenuItem(" Copy Password ");
        item4.setStyle("-fx-padding:2 8 2 8;");
        item4.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	table.getSelectionModel().clearSelection();
                
            }
        });
        MenuItem item5 = new MenuItem(" View Log Data ");
        item5.setStyle("-fx-padding:2 8 2 8;");
//        item5.setOnAction(new EventHandler<ActionEvent>() {
// 
//            @Override
//            public void handle(ActionEvent event) {
//                
//            }
//        });
        MenuItem item6 = new MenuItem(" Close ");
        item6.setStyle("-fx-padding:2 8 2 8;");
        item6.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	table.getSelectionModel().clearSelection();
                contextMenu.hide();
            }
        });
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
       
 
        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(item1,item2,item3,item4,item5,separatorMenuItem,item6);	
       
       
        table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>(){
        	
			@Override
			public void handle(ContextMenuEvent event) {
				// TODO Auto-generated method stub
				System.out.println("context menu option called");
				tableRowSelectEvent=event;
				currentTableRow=table.getSelectionModel().getSelectedItem();
			
				contextMenu.show(table, event.getScreenX(), event.getScreenY());
				
				
				
				
			}
        	
        });
		
        
		
		


	}
	public void getDataInTableFromRefreshbtn(ActionEvent event){
		getDataInTable();
	}
	
	public  void getDataInTable(){
		
	     //   ArrayList<PasswordData> passwordlist;
	        
	        Service<Void> databaseService=new Service<Void>() {

				@Override
				protected Task<Void> createTask() {
					return new Task<Void>() {

						@Override
						protected Void call() throws Exception {
					   System.out.println("database thread :"+Thread.currentThread().getName());
					  
						    ObservableList<PasswordData> list=FXCollections.observableArrayList(UserDao.RetrievePasswordDb(vuser));
						    label.setCellValueFactory(new PropertyValueFactory<PasswordData,String>("label"));
							email.setCellValueFactory(new PropertyValueFactory<PasswordData,String>("email"));
							categories.setCellValueFactory(new PropertyValueFactory<PasswordData,String>("categories"));
						//	dateOfCreation.setCellValueFactory(new PropertyValueFactory<PasswordData,Integer>("dateOfCreation"));
							lastDateOfModify.setCellValueFactory(new PropertyValueFactory<PasswordData,Integer>("lastDateOfModify"));
							
							table.setItems(list);
							
							return null;
						}};
				}};
		
				databaseService.start();
				

			
			
			
			
			//for viewing data
			//after double click in row ,view window will popup
			table.setOnMouseClicked(e->{
				System.out.println("mouse clicked");
				 MouseButton button = e.getButton();
				 if(button==MouseButton.PRIMARY){
					 System.out.println("primary button clicked");
				tableRowSelectEvent=e;
				currentTableRow=table.getSelectionModel().getSelectedItem();
              table.setId("blue_cell");
				contextMenu.hide();
				 if(e.getClickCount()==2){
			     System.out.println("table clicked");	
			     table.getSelectionModel().clearSelection();
			     try {
					editDataFunction(tableRowSelectEvent,currentTableRow,false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    
					 }
				 }System.out.println("secondary button clicked");
					
			
			});
			
		
       
	}
	//get table data from database
	
	//showing edit window
	public void editDataFunction(Event e,PasswordData pd,Boolean flag) throws Exception{
		   Stage primaryStage=(Stage)((Node)e.getSource()).getScene().getWindow();
		   Stage PasswordDialog = null;
		try {
			System.out.println("user id in editdata function"+this.vuser.getUser_id());
			PasswordDialog = new PasswordDialog(primaryStage,this.vuser);
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
		

		//opening edit data panel for viewing data also
	 	FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/application/EditData.fxml"));
		try{
			loader.load();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
        EditDataController editController=loader.getController();
        editController.EditDataScene(pd,flag,this,com.info.controller.PasswordDialog.getPassword());
        Parent p=loader.getRoot();
        Scene newScene =new Scene(p);
      
        newScene.getStylesheets().add(getClass().getResource("/application/EditData.css").toExternalForm());
        Stage EditWindow=new Stage();
        EditWindow.setScene(newScene);
        if(flag){
        	//edit window
        	  EditWindow.setTitle("Password Manager - Edit Password Data");
        }else{
        EditWindow.setTitle("Password Manager - View Password Data");
        }
        EditWindow.initModality(Modality.WINDOW_MODAL);
        EditWindow.initOwner(primaryStage);
        EditWindow.setResizable(false);
        EditWindow.show();
		
	}
	
	public void addBtnAction(ActionEvent event) throws IOException{
		//assigning data in choicebox
		System.out.println("add btn clicked");
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(getClass().getResource("/application/AddData.fxml"));
		loader.load();
		AddDataController  addController=loader.getController();
		addController.addDatacontrollerInitialize(vuser,"",this);
		Parent p=loader.getRoot();
		Scene newScene=new Scene(p);
		
		newScene.getStylesheets().add(getClass().getResource("/application/AddData.css").toExternalForm());
		//creating new stage
		Stage AddWindow=new Stage();
		//accessing main stage
		Stage primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
		//applying blur effect
		//setting scene in new stage
		AddWindow.setScene(newScene);
		//setting title for stage
		AddWindow.setTitle("Add new data");
		 // Specifies the modality for new window.
        AddWindow.initModality(Modality.WINDOW_MODAL);
     // Specifies the owner Window (parent) for new window
        AddWindow.initOwner(primaryStage);
        //setting window resizable false
        AddWindow.setResizable(false);
        //second window shown;
		AddWindow.show();
		
		
		
		
		
	}
	
	//delete selected row data from database
	public void deleteDataAction(Event event,PasswordData pd){
	
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are your sure to delete this data");
			Label label001=new Label("Label :"+pd.getPassword());
			Label label002=new Label("Email :"+pd.getEmail());
			VBox vb=new VBox();
			vb.setSpacing(10);
			ObservableList<Node> list=vb.getChildren();
			list.addAll(label001,label002);
          alert.getDialogPane().setExpandableContent(vb);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			   //deleting data from database based upon passwordid
				UserDao.deleteDataFromPasswordDb(pd.getPassword_id());
				getDataInTable();
			} else {
			    // ... user chose CANCEL or closed the dialog
			}
		
	}



	



}
