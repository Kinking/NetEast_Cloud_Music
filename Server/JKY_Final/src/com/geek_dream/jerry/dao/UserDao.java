package com.geek_dream.jerry.dao;

import com.geek_dream.jerry.bean.User;

public interface UserDao {
	
	public boolean login(String username,String password);
	
	public boolean register(User user);

}
