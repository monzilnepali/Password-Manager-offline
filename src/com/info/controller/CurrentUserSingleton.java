package com.info.controller;

import com.info.model.User;

import javafx.stage.Stage;

public class CurrentUserSingleton {
    
    private User vuser;
    private Stage primaryStage;
    
    private static CurrentUserSingleton singleton=new CurrentUserSingleton();
    private CurrentUserSingleton() {}//by making the contructor private we cannot object of this class outside this class
    public static CurrentUserSingleton getInstance() {
        return singleton;
    }
    public User getVuser() {
        return vuser;
    }
    public void setVuser(User vuser) {
        this.vuser = vuser;
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    

}
