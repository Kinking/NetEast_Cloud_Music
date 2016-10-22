package com.geek_dream.jerry.bean;

public class Contact {
	
	private String name;
	private String sex;
	private String age;
	private String email;
	private String userid;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Contact(String name, String sex, String age, String email,
			String userid) {
		super();
		
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.email = email;
		this.userid = userid;
	}
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

}
