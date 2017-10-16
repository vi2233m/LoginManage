package com.manage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.UserServer;

/**
 * Servlet implementation class UserClServlet
 */
@WebServlet(description = "修改，删除用户", urlPatterns = { "/UserClServlet" })
public class UserClServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserClServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String uid = request.getParameter("id");		
		//Integer.parseInt(uid);
		String lb = request.getParameter("lb");
		
		//PrintWriter out = response.getWriter();
		
		if ("1".equals(lb)){
			
			UserServer us = new UserServer();
			if(us.DelUser(Integer.parseInt(uid))){
				System.out.println("用户 "+uid+" 已成功删除");
				response.sendRedirect("/LoginManage3/MainFrame?username="+uid);//bug,无法获取登录id就无法跳转到页面。后续session模块是否能解决这个问题
			}else{
				System.out.println("未成功删除用户");
				request.getRequestDispatcher("/MainFrame").forward(request, response);
			}
		}else if("2".equals(lb)){
			System.out.println("选择了修改用户 "+uid);
			request.getRequestDispatcher("/UpaUserInfoView").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
