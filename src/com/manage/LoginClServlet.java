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
		System.out.println("��¼��id = "+uid+" ��¼�û����� = " +passwd );

//�汾3.0�� ͨ��MVCģʽ�����ݿ⹤������죬��֤�û�������
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
				System.out.println("�û������������");
				request.setAttribute("err", "erroruser");
				request.getRequestDispatcher("/LoginServlet").forward(request, response);
			}
		
		}else{
			request.setAttribute("error", "�Բ���,�����µ�¼��");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
			//System.out.print("����ת");
		}
		
		
		//�汾1.0 ��û��ͨ�����ݿ���֤�û�����
//		if ("zjp".equals(uname) && "123".equals(passwd)){
//			response.sendRedirect("/LoginManage/MainFrame");
//		}else{
//			response.getWriter().print("<h1>�û������������</h1><br/>");
//			response.getWriter().print("<a href='/LoginManage/LoginServlet'>���ص�¼ҳ��</a>");
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
