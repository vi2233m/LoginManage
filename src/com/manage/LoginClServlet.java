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
		System.out.println("��¼��id = "+uid+" ��¼�û����� = " +passwd );

//�汾3.0�� ͨ��MVCģʽ�����ݿ⹤������죬��֤�û�������
		User user = new User();
		user.setUser_id(Integer.parseInt(uid));
		user.setUser_password(passwd);
		
		UserServer userserver = new UserServer();
		if (userserver.checkUser(user)){
		request.getRequestDispatcher("/MainFrame").forward(request, response);

		}else{
			System.out.println("�û������������");
			request.setAttribute("error", "erroruser");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
		}
		
		
		
//�汾2.0 ��ֱ���������ݿ���֤�˺�����		
//		//�����ݿ���ȡ�û�������
//		Connection ct = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		
//		try{
//			//1���������ݿ�����
//			Class.forName("com.mysql.jdbc.Driver");
//			//2����ȡ����
//			ct = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123456");
//			//3������PrepareStatement ����sql��ѯ���
//			ps = (PreparedStatement) ct.prepareStatement("select * from user where user_id = ? and user_password = ?");
//			//�� �� ��ֵ
//			ps.setObject(1, uname);
//			ps.setObject(2, passwd);
//			//4��ִ�в���
//			rs = ps.executeQuery();
//			//���ݽ��������
//			if (rs.next()){
//				request.getRequestDispatcher("/MainFrame").forward(request, response);
//	
//			}else{
//				request.setAttribute("error", "�û������������");
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
