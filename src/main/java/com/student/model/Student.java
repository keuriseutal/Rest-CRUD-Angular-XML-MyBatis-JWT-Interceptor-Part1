package com.student.model;

public class Student {
	
	private int id;
	private String fname;
	private String lname;
	private String section;
	
	public Student() {
		id = 0;
	}
	
	public Student(int id, String fname, String lname, String section) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.section = section;
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
