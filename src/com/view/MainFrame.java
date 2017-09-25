package com.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainFrame
 */
@WebServlet("/MainFrame")
public class MainFrame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainFrame() {
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
		//response.setHeader("Content-Disposition", "attachement;filename='timg.jpg'");
		PrintWriter out = response.getWriter();
		out.print("<h1>下载页面</h1><br/>");
		out.print("<p>【timg.jpg】 <a href='/LoginManage/DownLoadServlet?filename=timg.jpg'>点击下载</a></p>");
		out.print("<p>【timg1.jpg】 <a href='/LoginManage/DownLoadServlet?filename=timg1.jpg'>点击下载</a></p>");
		out.print("<a href='/LoginManage/LoginServlet'>返回登录页面</a>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
