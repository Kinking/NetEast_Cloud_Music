package com.geek_dream.jerry.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geek_dream.jerry.bean.User;
import com.geek_dream.jerry.dao.UserDaoImpl;
import com.geek_dream.jerry.json.JsonUtil;

/**
 * Servlet implementation class Register
 */
@SuppressWarnings("serial")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
		String jsondata=request.getParameter("jsonstring");
		JsonUtil jsonUtil=new JsonUtil();
		System.out.println(jsondata);
		List<User> list=jsonUtil.StringFromJson(jsondata);
		User user=list.get(0);
	    System.out.println(user.getSex());
		UserDaoImpl userDaoImpl=new UserDaoImpl();
		boolean b=userDaoImpl.register(user);
		if (b) 
		{
			out.write("t");
		}
		else {
			out.write("f");
		}
		out.flush();
		out.close();
	}

}
