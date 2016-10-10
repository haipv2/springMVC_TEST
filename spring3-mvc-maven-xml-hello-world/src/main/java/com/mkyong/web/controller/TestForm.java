package com.mkyong.web.controller;

import java.util.Date;

public class TestForm {
	Date toDate;
	Date fromDate;
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

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

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
}
