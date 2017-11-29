package com.manage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.User;
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
		//PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");		
		
		String uid = request.getParameter("id");		
		//Integer.parseInt(uid);
		String type = request.getParameter("type");		
		//PrintWriter out = response.getWriter();
		
		UserServer us = new UserServer();
		
		if ("1".equals(type)){			
			//UserServer us = new UserServer();
			if(us.DelUser(Integer.parseInt(uid))){
				System.out.println("用户 "+uid+" 已成功删除");
				response.sendRedirect("/LoginManage3/MainFrame?username="+uid);//bug,无法获取登录id就无法跳转到页面。后续session模块是否能解决这个问题
			}else{
				System.out.println("未成功删除用户");
				request.getRequestDispatcher("/MainFrame").forward(request, response);
			}
		}else if("2".equals(type)){
			
			System.out.println("选择了修改用户 "+uid);
			request.getRequestDispatcher("/UpaUserInfoView").forward(request, response);
			
		}else if("22".equals(type)){
			
			User user = new User();
			user.setUser_id(Integer.parseInt(request.getParameter("uid")));
			user.setUser_name(request.getParameter("username"));
			user.setUser_email(request.getParameter("email"));
			user.setUser_company(request.getParameter("company"));
			user.setUser_school(request.getParameter("school"));
			
			if(us.UpaUser(user)){
				System.out.print("用户信息修改成功");
				request.getRequestDispatcher("/MainFrame").forward(request, response);
			}
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
