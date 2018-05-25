package com.info.model;

import javafx.beans.property.SimpleStringProperty;

public class Categories {

	private final SimpleStringProperty cat;

	public Categories(String cat) {
		super();
		this.cat = new SimpleStringProperty(cat);
	}

	public String getCat() {
		return cat.get();
	}
}
