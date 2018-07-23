package com.info.dao;

import java.sql.Connection;
import java.sql.Statement;

import com.info.util.DbConnection;

public class TableCreationHandler {

    public static Boolean createTableUser() {
        Connection conn=null;
        Statement st=null;
        try {
            conn=DbConnection.getConnection();
            String query="CREATE table userdb("
                    + "user_id  integer PRIMARY KEY AUTOINCREMENT,"
                    + "user_email varchar(50),"
                    + "user_password varchar(200)"
                    + ");";
            st=    conn.createStatement();  
            st.execute(query);
            return true;
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        
        return false;
    }
    public static Boolean createTablePassword() {
        Connection conn=null;
        Statement st=null;
        try {
            conn=DbConnection.getConnection();
            String query="CREATE table passworddb("
                    + "user_id integer references userdb(user_id) ON DELETE CASCADE,"
                    + "pass_id integer primary key autoincrement,"
                    + "label varchar(20),"
                    + "email varchar(50),"
                    + "password varchar(200),"
                    + "DateOfCreation String,"
                    + "lastDateOfModify String,"
                    + "categories varchar(15)"
                    + ");";
            st=    conn.createStatement();  
            st.execute(query);
            return true;
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        
        return false;
    }
    public static Boolean createTableNotedb() {
        Connection conn=null;
        Statement st=null;
        try {
            conn=DbConnection.getConnection();
            String query="CREATE table Notedb("
                    + "user_id integer references userdb(user_id) on delete cascade,"
                    + "note_id integer primary key autoincrement,"
                    + "note_name varchar(50),"
                    + "note_categories varchar(20),"
                    + "note_text varchar(100),"
                    + "note_creationDate String,"
                    + "note_updateDate String"
                    + ");";
            st=    conn.createStatement();  
            st.execute(query);
            return true;
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        
        return false;
    }
    
}
