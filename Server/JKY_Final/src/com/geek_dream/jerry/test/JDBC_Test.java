package com.geek_dream.jerry.test;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.geek_dream.jerry.db.GetConn;

public class JDBC_Test {

	public static void main(String[] args) throws Exception {
		
		Properties p=new Properties();
		Connection con=null;
		
		try {
			p.load(new FileInputStream("src/db.properties"));
			Class.forName(p.getProperty("dirStr"));
			con=DriverManager.getConnection
					(p.getProperty("conStr"),p.getProperty("username"),p.getProperty("pwd"));
			
						
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	//	viewData(con);	
	}
	
	public static void viewData(Connection con) throws Exception{
		Statement stm=con.createStatement();
		String sql="select * from users";
		ResultSet rs=stm.executeQuery(sql);
		while(rs.next()){
			System.out.println(rs.getString("name")+"\t"+rs.getString("sex")+"\t"
					+rs.getString("hometown")+rs.getString("emotion")+"\t"+rs.getString("birth")+"\t");
		}
		
		
	}

}
