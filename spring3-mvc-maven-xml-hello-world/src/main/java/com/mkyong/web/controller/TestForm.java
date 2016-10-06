package com.mkyong.web.controller;

public class TestForm {
	String name;
	
	int confirmNumber;
	
	public int getConfirmNumber() {
		return confirmNumber;
	}

	public void setConfirmNumber(int confirmNumber) {
		this.confirmNumber = confirmNumber;
	}

	public TestForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestForm(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
