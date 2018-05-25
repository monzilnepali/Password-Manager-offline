package com.info.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PasswordData {

	private  SimpleIntegerProperty password_id;
	private  SimpleStringProperty label;
	private  SimpleStringProperty email;
	private  SimpleStringProperty categories;
	private  SimpleStringProperty password;
	private  SimpleStringProperty dateOfCreation;
	private  SimpleStringProperty LastDateOfModify;
	

	public Integer getPassword_id() {
		return password_id.get();
	}
	public void setPassword_id(Integer password_id) {
		this.password_id = new SimpleIntegerProperty(password_id);
	}
	public String getEmail() {
		return email.get();
	}
	public void setEmail(String email) {
		this.email = new SimpleStringProperty(email);
	}
	public String getPassword() {
		return password.get();
	}
	public void setPassword(String password) {
		this.password =new SimpleStringProperty(password);
	}
	public String getDateOfCreation() {
		return dateOfCreation.get();
	}
	public void setDateOfCreation(String date) {
		this.dateOfCreation = new SimpleStringProperty(date);
	}
	public String getLastDateOfModify() {
		return LastDateOfModify.get();
	}
	public void setLastDateOfModify(String lastDateOfModify) {
		this.LastDateOfModify = new SimpleStringProperty(lastDateOfModify);
	}
	public String getLabel() {
		return label.get();
	}
	public void setLabel(String label) {
		this.label = new SimpleStringProperty(label);
	}
	public String getCategories() {
		return categories.get();
	}
	public void setCategories(String categories) {
		this.categories = new SimpleStringProperty( categories);
	}
	
}
