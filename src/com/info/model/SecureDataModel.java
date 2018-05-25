package com.info.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SecureDataModel {

	private  SimpleIntegerProperty secureData_id;
	private  SimpleStringProperty secureData_name;
	private  SimpleStringProperty secureData_categories;
	private  SimpleStringProperty secureData_DateOfCreation;
	private  SimpleStringProperty secureData_updateDate;
	private  SimpleStringProperty secureData_Note;
	public int getSecureData_id() {
		return secureData_id.get();
	}
	public void setSecureData_id(int secureData_id) {
		this.secureData_id = new SimpleIntegerProperty(secureData_id);
	}
	public String getSecureData_name() {
		return secureData_name.get();
	}
	public void setSecureData_name(String secureData_name) {
		this.secureData_name = new SimpleStringProperty(secureData_name);
	}
	public String getSecureData_categories() {
		return secureData_categories.get();
	}
	public void setSecureData_categories(String secureData_categories) {
		this.secureData_categories = new SimpleStringProperty(secureData_categories);
	}
	public String getSecureData_DateOfCreation() {
		return secureData_DateOfCreation.get();
	}
	public void setSecureData_DateOfCreation(String secureData_DateOfCreation) {
		this.secureData_DateOfCreation = new SimpleStringProperty(secureData_DateOfCreation);
	}
	public String getSecureData_Note() {
		return secureData_Note.get();
	}
	public void setSecureData_Note(String secureData_Note) {
		this.secureData_Note = new SimpleStringProperty(secureData_Note);
	}
	public  String getSecureData_updateDate() {
		return secureData_updateDate.get();
	}
	public  void setSecureData_updateDate(String secureData_updateDate) {
		this.secureData_updateDate = new SimpleStringProperty(secureData_updateDate);
	}
}
