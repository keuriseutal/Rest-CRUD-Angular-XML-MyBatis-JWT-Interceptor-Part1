package com.student.model;

public class Student {
	
	private int id;
	private String fname;
	private String lname;
	private String section;
	private String uname;
	private String pass;
	private boolean isLoggedIn;
	
	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Student() {
		id = 0;
	}
	
	public Student(int id, String fname, String lname, String section, boolean isLoggedIn) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.section = section;
		this.isLoggedIn = false;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	
}
