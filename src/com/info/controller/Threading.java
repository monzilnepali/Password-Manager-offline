package com.info.controller;

import java.util.concurrent.Callable;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Threading extends Application {
	Button btn1=new Button("open");
	Button btn2 =new Button("close");
	Label status=new Label("no operation performed");
	TextArea text=new TextArea();
	Stage stage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		//creating new Window
		VBox hb=new VBox();
		stage=primaryStage;
		hb.getChildren().addAll(btn1,btn2,status,text);
		Scene scene=new Scene(hb,500,350);
		primaryStage.setScene(scene);
		primaryStage.show();
		btn1.setOnAction(e->{
			System.out.println("start button clicked");
			startTask();
		});
		btn2.setOnAction(e->{
			System.out.println("close button clicked");
		});
		
		
	}
	public void startTask(){
		
		Runnable task=new Runnable(){

			@Override
			public void run() {
				runtask();
				
			}
			
		};
	//	Callable<String>task=new Callable<String>();
	//	Callable<String> new=new Callable(){
		//creating new Thread to run task in background
		Thread backgroundThread=new Thread(task);
		//terminate the thread if application is closed
		backgroundThread.setDaemon(true);
		//start the thread
		backgroundThread.start();
		
	}
	public void runtask(){
		//creating new Scene is another thread;
		HBox hb=new HBox();
		Scene scene=new Scene(hb,400,500);
		
	
				
				Stage window=new Stage();
				window.setScene(scene);
				window.show();
		
			
		
		
	}
	
	public static void main(String[] args){
		launch(args);
	}

}
