package com.info.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.info.model.PasswordData;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PreLoaderController implements Initializable {
    private CurrentUserSingleton singleton=CurrentUserSingleton.getInstance();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("preloader called");

        // getting list of password db

        PreloaderTask loader = new PreloaderTask();
        loader.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                new EventHandler<WorkerStateEvent>() {

                    @Override
                    public void handle(WorkerStateEvent arg0) {
                        System.out.println("preloader completed");
                        // gettting data from task
                        List<PasswordData> pdlist = loader.getValue();

                        // calling mainwindow

                        try {
                           FXMLLoader loader =new FXMLLoader();
                           loader.setLocation(getClass().getResource("/application/MainWindow.fxml"));
                           loader.load();
                           MainWindowController mainController=loader.getController();
                           mainController.mainWindowInitialize(pdlist);
                           Parent p=loader.getRoot();
                            Scene newScene=new Scene(p);
                           newScene.getStylesheets().add(getClass().getResource("/application/MainWindow.css").toExternalForm());
                            Stage newStage=singleton.getPrimaryStage();
                            newStage.setScene(newScene);
                            newStage.setTitle("Password Manager"+singleton.getVuser().getEmail());
                            newStage.show();
                            
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }

                });
        
        new Thread(loader).start();

    }

}
