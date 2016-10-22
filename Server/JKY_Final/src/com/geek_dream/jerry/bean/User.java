package com.geek_dream.jerry.bean;

import java.io.Serializable;

public class User implements Serializable{
	
	private String username;
	private String password;
	private String email;
	private String sex;
	private String age;


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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

	public User(String username, String password,String email, String sex,
			String age) {
		super();

		this.username = username;
		this.password = password;
		this.email = email;
		this.sex = sex;
		this.age = age;

	}
	public User() {
		super();
	}

}
