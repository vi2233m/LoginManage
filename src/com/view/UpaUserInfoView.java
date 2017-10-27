package com.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.User;
import com.service.UserServer;

/**
 * Servlet implementation class UpaUserInfoView
 */
@WebServlet("/UpaUserInfoView")
public class UpaUserInfoView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpaUserInfoView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("����ת��UpaUserInfoView ҳ��");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");		
		PrintWriter out = response.getWriter();
		
		//String type = request.getParameter("type");
		String id = request.getParameter("id");
		UserServer us = new UserServer();
		List<User> al = us.getUserInfo(Integer.parseInt(id));		
		
		out.println("<h1>�޸��û���Ϣ</h1>");
		out.print("<form action='' method='post'>");
		out.println("��  ��  ID��<input type='text' name='uid' readonly unselectable='on' value="+id+"><br/>");
		out.println("��  ��  ����<input type='text' name='username' value="+al.get(0).getUser_name()+"><br/>");
		out.println("�ܡ����룺<input type='password' name='password' value="+al.get(0).getUser_password()+"><br/>");
		out.println("�ʡ����䣺<input type='text' name='email' value="+al.get(0).user_email+"><br/>");
		out.println("������˾��<input type='text' name='company' value="+al.get(0).getUser_company()+"><br/>");
		out.println("��ҵԺУ��<input type='text' name='school' value="+al.get(0).getUser_school()+"><br/>");
		out.println("<input type='submit' value='�ύ�޸�'><br/>");
		out.print("</form>");
		//System.out.print("========>"+"<a href='/LoginManage3/MainFrame?username="+id+"'>����</a>");
		out.print("<a href='/LoginManage3/MainFrame?username="+id+"'>����</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
