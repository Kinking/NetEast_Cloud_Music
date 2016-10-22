package com.geek_dream.jerry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.geek_dream.jerry.bean.Contact;
import com.geek_dream.jerry.bean.User;
import com.geek_dream.jerry.db.GetConn;

public class UserDaoImpl implements UserDao{
	
	public boolean login(String username,String password) 
	{
		boolean b = false;
		GetConn getConn=new GetConn();
		ResultSet rs = null;
		Connection conn=getConn.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("select * from users where username=? and password=?");
			ps.setString(1,username);
			ps.setString(2,password);
			rs=ps.executeQuery();
			if (rs.next())
			{
				b=true;
			}
			else
			{
				b=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public boolean register(User user)
	{
		boolean b=false;
		GetConn getConn=new GetConn();
		int i = 0;
		Connection conn=getConn.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("insert into users values (?,?,?,?,?)");
			ps.setString(1,user.getUsername());
			ps.setString(2,user.getPassword());
			ps.setString(3,user.getEmail());
			ps.setString(4,user.getSex());
			ps.setString(5,user.getAge());
		
			i=ps.executeUpdate();
			if (i>0)
			{
				b=true;
			}
			else
			{
				b=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
		
	}
	
	public List<User> selectAlluser ()
	{
		List<User> list=new ArrayList<User>();
		GetConn getConn=new GetConn();	
		Connection conn=getConn.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) 
            {
				User user=new User();
				user.setUsername(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setSex(rs.getString(4));
				user.setAge(rs.getString(5));
				list.add(user);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Contact> selectAllcontact (String username)
	{
		List<Contact> list=new ArrayList<Contact>();
		GetConn getConn=new GetConn();	
		Connection conn=getConn.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("select * from users where userid=?");
            ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
            while (rs.next()) 
            {
				Contact contact=new Contact();
				contact.setName(rs.getString(1));
				contact.setSex(rs.getString(2));
				contact.setAge(rs.getString(3));
				contact.setEmail(rs.getString(4));
				contact.setUserid(rs.getString(5));
				
				list.add(contact);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//用户登录检查用
	public boolean check(String username) 
	{
		boolean b = false;
		GetConn getConn=new GetConn();
		ResultSet rs = null;
		Connection conn=getConn.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("select * from users where username=?");
			ps.setString(1,username);
			
			rs=ps.executeQuery();
			if (rs.next())
			{
				b=true;
			}
			else
			{
				b=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
	

}
