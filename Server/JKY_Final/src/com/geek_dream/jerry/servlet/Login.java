package com.geek_dream.jerry.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geek_dream.jerry.dao.UserDaoImpl;

/**
 * Servlet implementation class Login
 */

@SuppressWarnings("serial")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
	    PrintWriter out=response.getWriter();
	    System.out.println("jinlail");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		UserDaoImpl userDaoImpl=new UserDaoImpl();
		boolean b=userDaoImpl.login(username,password);
		if (b) 
		{
			out.write("登录成功");
		}
		else 
		{
			out.write("登录失败");
		}
		out.flush();
		out.close();
	}
}


