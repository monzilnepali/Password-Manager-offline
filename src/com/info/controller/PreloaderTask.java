package com.info.controller;

import java.util.List;

import com.info.dao.UserDao;
import com.info.model.PasswordData;

import javafx.concurrent.Task;

public class PreloaderTask extends Task<List<PasswordData>> {
    private CurrentUserSingleton singleton=CurrentUserSingleton.getInstance();

    @Override
    protected List<PasswordData> call() throws Exception {
       
        //getting list of password
        List<PasswordData> passwordlist=UserDao.RetrievePasswordDb(singleton.getVuser());
        Thread.sleep(2000);
        return passwordlist;
        
        
        
        
        
        
    }

}
