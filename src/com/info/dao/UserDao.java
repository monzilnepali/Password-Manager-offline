package com.info.dao;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.info.model.PasswordData;
import com.info.model.User;
import com.info.util.DbConnection;

public class UserDao {

//	private static final String salt="EKcSy1M5S6";
	public Boolean registerUser(User user){
		//new user entry in database
		Connection conn=null;
		PreparedStatement pst = null;
		try{
			conn=DbConnection.getConnection();
			String query="INSERT INTO userdb (user_email,user_password) VALUES (?,?) ";
			 pst=conn.prepareStatement(query);
			pst.setString(1, user.getEmail());
			pst.setString(2, hashing(user.getPassword()));
			pst.execute();
			System.out.println("data inserted");
			return true;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			    try { pst.close(); } catch (Exception e) { /* ignored */ }
			    try { conn.close(); } catch (Exception e) { /* ignored */ }
		}
		return false;
	
	}
	public User loginAuth(User user){
		//checking the email and password in database 
		Connection conn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		try{
			conn=DbConnection.getConnection();
			String query="SELECT  user_id,user_email,user_password FROM userdb where user_email=? AND user_password=?";
			pst=conn.prepareStatement(query);
			pst.setString(1, user.getEmail());
			pst.setString(2,hashing(user.getPassword()));
			
			rs=pst.executeQuery();
			while(rs.next()){
				User validUser=new User();
				System.out.println("user id from database"+rs.getInt(1));
		    	validUser.setUser_id(rs.getInt(1));
				validUser.setEmail(user.getEmail());
				
				return validUser;
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{rs.close();}catch(Exception e){}
			try{pst.close();}catch(Exception e){}
			try{conn.close();}catch(Exception e){}
			
		}
		return null;
		
	}
	
	
	public static  boolean checkPassword(User user,String pass){
		//checking the email and password in database 
		Connection conn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		try{
			conn=DbConnection.getConnection();
			String query="SELECT user_id,user_password FROM  userdb where user_id=? AND user_password=?";
			pst=conn.prepareStatement(query);
			pst.setInt(1, user.getUser_id());
			System.out.println("user id"+user.getUser_id());
			System.out.println("user passs"+pass);
			pst.setString(2,hashing(pass));
			rs=pst.executeQuery();
			while(rs.next()){
				System.out.println("password matched");
				return true;
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{rs.close();}catch(Exception e){}
			try{pst.close();}catch(Exception e){}
			try{conn.close();}catch(Exception e){}
			
		}
		return false;
		
	}
	public static boolean checkEmailAddress(String email){
		Connection conn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		try{
			conn=DbConnection.getConnection();
			String query="SELECT user_email from userdb WHERE user_email=?";
			pst=conn.prepareStatement(query);
			pst.setString(1,email);
			 rs=pst.executeQuery();
			while(rs.next()){
				return true;
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{rs.close();}catch(Exception e){}
			try{pst.close();}catch(Exception e){}
			try{conn.close();}catch(Exception e){}
			
		}
		
		return false;
	}
	public static Boolean updatePassword(String email,String password){
		System.out.println("update password dao");
		System.out.println("change email:"+email +"password"+password);
		Connection conn=null;
		PreparedStatement pst=null;
		try{
			
			conn=DbConnection.getConnection();
			String query="UPDATE userdb SET user_password=? WHERE user_email=?";
			pst=conn.prepareStatement(query);
			pst.setString(1,hashing(password));
			pst.setString(2,email);
			pst.execute();
			return true;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			try{pst.close();}catch(Exception e){}
			try{conn.close();}catch(Exception e){}
			
		}
		return false;
	}
	public static boolean checkUserCount(){
		//checking the number of user in database
		Connection conn=null;
		Statement st = null;
		ResultSet rs = null;
		try{
			conn=DbConnection.getConnection();
			String query="SELECT COUNT(*) AS total FROM userdb";
			 st=conn.createStatement();
			 rs=st.executeQuery(query);
			System.out.println(rs.getInt("total"));
			System.out.println("key ");
			if(rs.getInt("total")>=1){
				return true;//means we restrict the signup page to open
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{rs.close();}catch(Exception e){/* igonored */}
			try{st.close();}catch(Exception e){/* igonored */}
			try{conn.close();}catch(Exception e){/* igonored */}
			
		}
		return false;
	}
	public static ArrayList<PasswordData> RetrievePasswordDb(User vuser){
		Connection conn=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		try{
			conn=DbConnection.getConnection();
			String query="SELECT * FROM passworddb where user_id=?";
			 pst=conn.prepareStatement(query);
			 pst.setInt(1, vuser.getUser_id());
			 rs=pst.executeQuery();
			 ArrayList<PasswordData> list=new ArrayList<PasswordData>();
			 while(rs.next()){
				 //System.out.println(rs.getInt(2)+rs.getString(3)+rs.getString(4)+rs.getInt(5)+rs.getInt(6));
				 PasswordData pd=new PasswordData();
				 pd.setPassword_id(rs.getInt(2));
				 pd.setLabel(rs.getString(3));
				 pd.setEmail(rs.getString(4));
				 pd.setPassword(rs.getString(5));
				 pd.setDateOfCreation(rs.getString(6));
				 pd.setLastDateOfModify(rs.getString(7));
				 pd.setCategories(rs.getString(8));
				 list.add(pd);
			 }
			return list;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//to make sure connection is closed event error occur
			try{rs.close();}catch(Exception e){/* igonored */}
			try{pst.close();}catch(Exception e){/* igonored */}
			try{conn.close();}catch(Exception e){/* igonored */}
		}
		return null;
		
	}
	//adding new data in password database
	public static Boolean addDataIntoPasswordDb(User vuser,PasswordData pd,String Master) throws NoSuchAlgorithmException{
		Security se=new Security(Master);
		
		
		Connection conn=null;
		PreparedStatement pst=null;
		try{
			conn=DbConnection.getConnection();
			String query="INSERT INTO passworddb (user_id,label,email,password,DateOfCreation,categories) VALUES (?,?,?,?,?,?) ";
			pst=conn.prepareStatement(query);
		//	System.out.println(vuser.getUser_id());
			pst.setInt(1, vuser.getUser_id());
			pst.setString(2, pd.getLabel());
			pst.setString(3, pd.getEmail());
			pst.setString(4,  se.encrypt(pd.getPassword()));
			pst.setString(5,getCurrentTime());
			pst.setString(6, pd.getCategories());
			pst.execute();
			
			System.out.println("data inserted");
			return true;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{pst.close();}catch(Exception e){/* igonored */}
			try{conn.close();}catch(Exception e){/* igonored */}
		}
		return false;
		
	}
	public static void deleteDataFromPasswordDb(int id){
		Connection conn=null;
		PreparedStatement pst=null;
		try{
			conn=DbConnection.getConnection();
			String query="DELETE FROM passworddb WHERE pass_id=?";
			pst=conn.prepareStatement(query);
			pst.setInt(1, id);
			pst.execute();
			System.out.println("data deleted");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{pst.close();}catch(Exception e){/* igonored */}
			try{conn.close();}catch(Exception e){/* igonored */}
		}
	}
	public static void updateDataToPasswordDb(int pid,PasswordData pnewdata){
		System.out.println("update called");
		String date=getCurrentTime();
		System.out.println("date"+date);
		Connection conn=null;
		PreparedStatement pst=null;
		try{
			conn=DbConnection.getConnection();
			String query="UPDATE passworddb SET label=?,email=?,password=?,categories=?,LastDateOfModify=? WHERE pass_id=?";
			pst=conn.prepareStatement(query);
			pst.setString(1,pnewdata.getLabel());
			pst.setString(2, pnewdata.getEmail());
			pst.setString(3, pnewdata.getPassword());
			pst.setString(4, pnewdata.getCategories());
			pst.setString(5, getCurrentTime());
			pst.setInt(6, pid);
			pst.execute();
			System.out.println("data updated");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{pst.close();}catch(Exception e){/* igonored */}
			try{conn.close();}catch(Exception e){/* igonored */}
		}
		
	}
	
	public static Boolean changePassword(String oldPass,String newPass){
		Connection conn=null;
		PreparedStatement pst=null;
		try{
			conn=DbConnection.getConnection();
			String query="UPDATE  userdb SET user_password=? WHERE user_password=?";
			pst=conn.prepareStatement(query);
			pst.setString(1,hashing(newPass) );
			pst.setString(2, hashing(oldPass));
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
	
	public static String getCurrentTime(){
		Date CurrentDate =new Date();
	//	System.out.println(CurrentDate.toString());
		
		  SimpleDateFormat ft =new SimpleDateFormat ("E yyyy-MM-dd 'at' hh:mm");
		return ft.format(CurrentDate);
		
	}
	public static final String hashing(String password) throws NoSuchAlgorithmException, NoSuchProviderException {
		//for hashing user password before storing in database using SHA1 algorithm
		String hpassword=password+generateKeyPair().getPrivate().getFormat();
		MessageDigest mDigest=MessageDigest.getInstance("SHA1");
		byte[] result=mDigest.digest(hpassword.getBytes());
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
		return sb.toString();
	}
	public static final KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException{
	     	long seed=999;
		//  Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		    KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DSA");
		    SecureRandom rng = SecureRandom.getInstance("SHA1PRNG", "SUN");
		    rng.setSeed(seed);
		    keyGenerator.initialize(1024, rng);
		    return (keyGenerator.generateKeyPair());
		
	}


}
