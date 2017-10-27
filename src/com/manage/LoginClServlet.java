package com.manage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.domain.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.service.UserServer;

/**
 * Servlet implementation class LoginClServlet
 */
@WebServlet("/LoginClServlet")
public class LoginClServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginClServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String uid = request.getParameter("username");
		String passwd = request.getParameter("password");	
		System.out.println("登录用id = "+uid+" 登录用户密码 = " +passwd );

//版本3.0， 通过MVC模式及数据库工具类改造，验证用户及密码
		if (uid != null){
			User user = new User();
			user.setUser_id(Integer.parseInt(uid));
			user.setUser_password(passwd);
			
			UserServer userserver = new UserServer();
			if (userserver.checkUser(user)){
				HttpSession session = request.getSession();
				session.setAttribute("uid", uid);
				request.getRequestDispatcher("/MainFrame").forward(request, response);
	
			}else{
				System.out.println("用户名或密码错误");
				request.setAttribute("err", "erroruser");
				request.getRequestDispatcher("/LoginServlet").forward(request, response);
			}
		
		}else{
			request.setAttribute("error", "对不起,请重新登录！");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
			//System.out.print("已跳转");
		}
		
		
		//版本1.0 ，没有通过数据库验证用户密码
//		if ("zjp".equals(uname) && "123".equals(passwd)){
//			response.sendRedirect("/LoginManage/MainFrame");
//		}else{
//			response.getWriter().print("<h1>用户名或密码错误</h1><br/>");
//			response.getWriter().print("<a href='/LoginManage/LoginServlet'>返回登录页面</a>");
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
