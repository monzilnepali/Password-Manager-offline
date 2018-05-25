package com.info.controller;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FirstPreloader extends Preloader {
    ProgressBar bar;
    Stage stage;
 
    private Scene createPreloaderScene() {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        return new Scene(p, 300, 150);        
    }
    
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        System.out.println("preloader called");
        stage.setScene(createPreloaderScene());        
        stage.show();
    }
    
    @Override
    public void handleProgressNotification(ProgressNotification pn) {
    	System.out.println("pending");
        bar.setProgress(pn.getProgress());
    }
 
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
    	System.out.println(" before preloader hide");
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
        	
            stage.hide();
        }
    }    
}