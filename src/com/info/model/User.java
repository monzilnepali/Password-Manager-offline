package com.info.model;

public class User {
   private int user_id;
	private String email;
	private String Password;
	private Boolean defaultUser;
	
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public Boolean getDefaultUser() {
		return defaultUser;
	}
	public void setDefaultUser(Boolean defaultUser) {
		this.defaultUser = defaultUser;
	}
}
