package com.info.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.info.model.SecureDataModel;
import com.info.util.DbConnection;

public class NoteDao {

	
	public static Boolean addNoteToDatabase(SecureDataModel sd,String Master) throws NoSuchAlgorithmException{
	   Security se=new Security(Master);
		
		Connection conn=null;
		PreparedStatement pst=null;
		try{
			conn=DbConnection.getConnection();
			String query="INSERT INTO Notedb (note_name,note_categories,note_text,note_creationDate) VALUES (?,?,?,?)";
			pst=conn.prepareStatement(query);
			pst.setString(1, sd.getSecureData_name());
			pst.setString(2, sd.getSecureData_categories());
			pst.setString(3, se.encrypt(sd.getSecureData_Note()));
			pst.setString(4, UserDao.getCurrentTime());
			pst.execute();
             return true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{pst.close();}catch(Exception e){/* igonored */}
			try{conn.close();}catch(Exception e){/* igonored */}
		}
		return false;
	}
	public static ArrayList<SecureDataModel> RetrieveSecureNote(){
		Connection conn=null;
		Statement pst=null;
		ResultSet rs=null;
		try{
			conn=DbConnection.getConnection();
			String query="SELECT * FROM Notedb ";
			pst=conn.createStatement();
			rs=pst.executeQuery(query);
			ArrayList<SecureDataModel> list=new ArrayList<>();
			while(rs.next()){
			
				SecureDataModel sd=new SecureDataModel();
				sd.setSecureData_id(rs.getInt(1));
				sd.setSecureData_name(rs.getString(2));
				sd.setSecureData_categories(rs.getString(3));
				sd.setSecureData_Note(rs.getString(4));
				sd.setSecureData_DateOfCreation(rs.getString(5));
				sd.setSecureData_updateDate(rs.getString(6));
				list.add(sd);
				
			}
			
			return list;
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{pst.close();}catch(Exception e){/* igonored */}
			try{conn.close();}catch(Exception e){/* igonored */}
		}
		
		return null;
		
	}
	public static Boolean UpdateNoteToDatabase(SecureDataModel sd){
		
		Connection conn=null;
		PreparedStatement pst=null;
		try{
			conn=DbConnection.getConnection();
			String query="UPDATE Notedb SET note_name=?,note_categories=?,note_text=?,note_updateDate=? WHERE note_id=? ";
			pst=conn.prepareStatement(query);
			pst.setString(1,sd.getSecureData_name() );
			pst.setString(2, sd.getSecureData_categories());
			pst.setString(3, sd.getSecureData_Note());
			pst.setString(4, UserDao.getCurrentTime());
			pst.setInt(5, sd.getSecureData_id());
			pst.execute();
			return true;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{pst.close();}catch(Exception e){/* igonored */}
			try{conn.close();}catch(Exception e){/* igonored */}
		}
		
		
		return false;
		
	}
	public static Boolean deleteNote(SecureDataModel sd){
		Connection conn=null;
		PreparedStatement pst=null;
		try{
			conn=DbConnection.getConnection();
			String query="DELETE FROM Notedb WHERE note_id=?";
			pst=conn.prepareStatement(query);
			pst.setInt(1,sd.getSecureData_id() );
			pst.execute();
			return true;
			
			
		}catch(Exception e){
			
		}finally{
			try{pst.close();}catch(Exception e){/* igonored */}
			try{conn.close();}catch(Exception e){/* igonored */}
		}
		return false;
		
	}
}
