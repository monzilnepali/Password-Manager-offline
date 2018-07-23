package com.info.controller;

import java.io.File;

import com.info.dao.TableCreationHandler;

import javafx.concurrent.Task;

public class DatabaseCreationHandler extends Task<Integer> {

    @Override
    protected Integer call() throws Exception {
        System.out.println("DatabaseCreationHandler called");

        // first creation of file in directory
        File dbFile = new File("../password.db");
        System.out.println(dbFile.getAbsolutePath());
        if (dbFile.createNewFile()) {
            updateMessage("Db creation");
            updateProgress(1, 4);
            Thread.sleep(200);
            //creation of table
            if(TableCreationHandler.createTableUser()) {
                updateMessage("userdb table created");
                updateProgress(2,4);
                Thread.sleep(200);
                if(TableCreationHandler.createTablePassword()) {
                    updateMessage("password db created");
                    updateProgress(3,4);
                    Thread.sleep(200);
                    if(TableCreationHandler.createTableNotedb()) {
                        updateMessage("Done");
                        updateProgress(4,4);
                     
                       
                    }
                }
            }
            
           
            
            
        }

        return null;
    }

}
