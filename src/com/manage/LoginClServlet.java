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
		User user = new User();
		user.setUser_id(Integer.parseInt(uid));
		user.setUser_password(passwd);
		
		UserServer userserver = new UserServer();
		if (userserver.checkUser(user)){
		request.getRequestDispatcher("/MainFrame").forward(request, response);

		}else{
			System.out.println("用户名或密码错误");
			request.setAttribute("error", "erroruser");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
		}
		
		
		
//版本2.0 ，直接连接数据库验证账号密码		
//		//从数据库中取用户名密码
//		Connection ct = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		
//		try{
//			//1、加载数据库驱动
//			Class.forName("com.mysql.jdbc.Driver");
//			//2、获取连接
//			ct = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123456");
//			//3、创建PrepareStatement 传送sql查询语句
//			ps = (PreparedStatement) ct.prepareStatement("select * from user where user_id = ? and user_password = ?");
//			//给 ？ 赋值
//			ps.setObject(1, uname);
//			ps.setObject(2, passwd);
//			//4、执行操作
//			rs = ps.executeQuery();
//			//根据结果做处理
//			if (rs.next()){
//				request.getRequestDispatcher("/MainFrame").forward(request, response);
//	
//			}else{
//				request.setAttribute("error", "用户名或密码错误");
//				request.getRequestDispatcher("/LoginServlet").forward(request, response);
//			}
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			if (rs != null){
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				rs = null;
//			}
//			if (ps != null){
//				try {
//					ps.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				ps = null;
//			}
//			if(ct != null){
//				try {
//					ct.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				ct = null;
//			}
//		}
		
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
