package com.geek_dream.jerry.biz;

import com.geek_dream.jerry.bean.User;

public interface UserBiz {
	
	public boolean login(String username,String password);
	
	public boolean register(User user);

}
