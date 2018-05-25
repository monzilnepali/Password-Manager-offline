	package com.info.controller;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.info.model.User;
import com.mifmif.common.regex.Generex;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PasswordGeneratorController implements Initializable, ClipboardOwner {
    private static User vsuer;
	@FXML private  TextField password_field;
	@FXML private Label password_len;
	
	@FXML private Slider password_slider;
	@FXML private CheckBox checkbox_num;
	@FXML private CheckBox checkbox_alphabet_small;
	@FXML private CheckBox checkbox_alphabet_capital;
	@FXML private CheckBox checkbox_symbol;
	@FXML private Button userPasswordBtn;
	@FXML private Hyperlink clipboard_btn;
	@FXML private Button refresh_btn;
	private static final int INIT_VALUE=10;
	private LinkedList<String> list=new LinkedList<String>();
	private int i;
	private static int currentPasswordLen;
    private static StringBuffer sb=new StringBuffer("0-9a-z");
    private static Boolean symbolCharacterFlag=false;
	
	
    public PasswordGeneratorController(User vuser){
    	System.out.println("password generator controller called");
    	PasswordGeneratorController.vsuer=vuser;
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 
		list.add("0-9");
		list.add("a-z");
		password_slider.setMajorTickUnit(10);
		password_slider.setMinorTickCount(0);
	System.out.println("password controller called");
	password_slider.setValue(INIT_VALUE);
	
	password_len.setText(Math.round(INIT_VALUE)+"");
	checkbox_num.setSelected(true);
	checkbox_alphabet_small.setSelected(true);
	currentPasswordLen=INIT_VALUE;
	generatePassword(sb,INIT_VALUE,symbolCharacterFlag);
	
	
	
	//password_len.textProperty().bindBidirectional(password_slider.valueProperty(),NumberFormat.getNumberInstance());
		password_slider.valueProperty().addListener((observable,oldvalue,newvalue)->{
			int i=newvalue.intValue();
			
			currentPasswordLen=newvalue.intValue();
			password_len.setText(Integer.toString(i));
//			String result = RandomStringUtils.random(i, false, true);
//			Generex generex = new Generex("[a-z0-9]{" +i +"}"  );
//		    System.out.println("i:"+i);
//			password_field.setText(generex.random());
			generatePassword(sb,i,symbolCharacterFlag);
		});
		
		checkbox_num.selectedProperty().addListener((observable,oldvalue,newvalue)->{
		//	System.out.println(oldvalue.booleanValue());
			checboxStatus(oldvalue.booleanValue(),"0-9");
			generatePassword(sb,currentPasswordLen,symbolCharacterFlag);
		});
		
		checkbox_alphabet_small.selectedProperty().addListener((observable,oldvalue,newvalue)->{
			//System.out.println(oldvalue.booleanValue());
			checboxStatus(oldvalue.booleanValue(),"a-z");
			generatePassword(sb,currentPasswordLen,symbolCharacterFlag);
			
			
			
			
		});

		checkbox_alphabet_capital.selectedProperty().addListener((observable,oldvalue,newvalue)->{
			//System.out.println(oldvalue.booleanValue());
			checboxStatus(oldvalue.booleanValue(),"A-Z");
			generatePassword(sb,currentPasswordLen,false);
		});
		
		checkbox_symbol.selectedProperty().addListener((observable,oldvalue,newvalue)->{
		//System.out.println(oldvalue.booleanValue());
		if(oldvalue.booleanValue()){
			symbolCharacterFlag=false;
			System.out.println("special unchecked");
			generatePassword(sb,currentPasswordLen,symbolCharacterFlag);
		}else{
			symbolCharacterFlag=true;
			System.out.println("special checked");
			generatePassword(sb,currentPasswordLen,symbolCharacterFlag);
			
		}
	});
	
 //opening add btn of password manager and passing generate password as argument
		
		userPasswordBtn.setOnAction(e->{
			System.out.println("user password btn clicked");
			
			//opening addData FXMl file and pass generate password as argument
			//opening add.fxml file
			
			
			//assigning data in choicebox
			System.out.println("add btn clicked");
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("/application/AddData.fxml"));
			try {
				loader.load();
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			AddDataController  addController=loader.getController();
			addController.addDatacontrollerInitialize(vsuer,password_field.getText(),null);
			Parent p=loader.getRoot();
			Scene newScene=new Scene(p);
			
			newScene.getStylesheets().add(getClass().getResource("/application/AddData.css").toExternalForm());
			//creating new stage
			Stage AddWindow=new Stage();
			//accessing main stage
			Stage primaryStage=(Stage)((Node)e.getSource()).getScene().getWindow();
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
			
		});
		
		//copy to clipboard action
		clipboard_btn.setOnAction(e->{
			System.out.println("btn clicked");
			StringSelection stringSelection = new StringSelection(password_field.getText());
		    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		    clipboard.setContents(stringSelection, this);
		});
		
		//refresh button generating password of same setting
		refresh_btn.setOnAction(e->{
			System.out.println("refresh clicked");
			generatePassword(sb,currentPasswordLen,symbolCharacterFlag);
			
		});
		
		
}
	
	public void checboxStatus(Boolean oldvalue,String condition){
		
		if(oldvalue.booleanValue()){
			//remove it
			//System.out.println("num deleted");
			for( i=0;i<list.size();i++){
				if(list.get(i).equals(condition)){
					break;
				}
			}
			list.remove(i);
			
		}else{
			//adding in linked list
			//System.out.println("num add");
			list.add(condition);
			//System.out.println(list.size());
		 
		}
		sb.delete(0, sb.length());
	     for(String st:list){
		        sb.append(st);
		     }
	}
	public  void generatePassword(StringBuffer sd,int password_len,Boolean flag){
	     	//System.out.println("generatePassword Called");
	     	//System.out.println("regular expression is "+sd.toString());
	     	//System.out.println("password len"+password_len);
	    
	     	if(sd.toString().equals("")){
	     		System.out.println("null regrex");
	     	}else{
	     		final String st="["+sd.toString()+"]{"+password_len+"}";
	     		Callable<String> c = () -> {
	     	      //  String result="";
	     			System.out.println("multithreading"+Thread.currentThread().getName());
	     	        Generex generex=new Generex(st);
	     	          return generex.random();
	     	      };
	     	     ExecutorService executor=Executors.newSingleThreadExecutor();
	     	    Future<String> future = executor.submit(c);
	     	   try {
	     	        String result = future.get(); //waits for the thread to complete
	     	       // System.out.println(result);
	     	        password_field.setText(result);
	     	    } catch (ExecutionException | InterruptedException e) {
	     	        e.printStackTrace();
	     	    }
	     	    executor.shutdown();
	     		

	     	}
		
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		
		
	}
	
}
