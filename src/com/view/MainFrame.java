package com.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.service.UserServer;
import com.util.SqlHelper;


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
		out.print("<h1>登录成功</h1>");
		out.print("当前登录用户："+ "");
		out.print("<a href='/LoginManage3/LoginServlet'>返回登录页面</a>");
		out.print("<hr></hr>");
		//out.print("<p>【timg.jpg】 <a href='/LoginManage/DownLoadServlet?filename=timg.jpg'>点击下载</a></p>");
		//out.print("<p>【timg1.jpg】 <a href='/LoginManage/DownLoadServlet?filename=timg1.jpg'>点击下载</a></p>");
		//表格显示数据库中的所有用户
		int pageNow = 1; //第几页
		int pageSize = 3;//每页显示几条记录
		int pageCount = 0;//总共有几页
		int rowCount = 0;//数据库总共有多少行
		
		String spageNow = request.getParameter("pageNow");
		if (spageNow != null){
			pageNow =Integer.parseInt(spageNow);
			//pageNow = (int) spageNow;
		}
		
		
		//连接数据库查询数据
		Connection ct = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			ct = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123456");
			
			//获取数据库中的总条数
			UserServer us = new UserServer();
			pageCount= us.getPageCount(pageSize);
			
			//获取数据库中的表格的数据
			ps = (PreparedStatement) ct.prepareStatement("select user_id,user_name,user_email,user_company,user_school from user limit ? , ?");
			ps.setObject(1, (pageNow-1)*pageSize);
			ps.setObject(2, pageSize);
			rs = ps.executeQuery();
						
			out.print("<h2>用户列表</h2>");
			out.print("<table width='500' border='2' color='green'>");
			out.print("<tr><th>ID</th><th>用户名</th><th>邮箱</th><th>公司</th><th>毕业院校</th></tr>");
			while (rs.next()){
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String company = rs.getString(4);
				String school = rs.getString(5);
				
				//System.out.print("======"+name);
				out.print("<tr><td>"+id+"</td><td>"+name+"</td><td>"+email+"</td><td>"+company+"</td><td>"+school+"</td></tr>");
			}
			out.print("</table>");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rs = null;
			}
			if (ps != null){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ps = null;
			}
			if(ct != null){
				try {
					ct.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ct = null;
			}
		}
		//控制点击上一页
		if (pageNow > 1){
			out.println("<a href=/LoginManage3/MainFrame?pageNow="+(pageNow-1)
					+ ">上一页</a>");
		}else{
			out.println("<a href=/LoginManage3/MainFrame>上一页</a>");
		}
		//控制点击具体页数
		for(int i=pageNow;i<pageNow+3;i++){
			if(i <= pageCount){
				out.println("<a href=/LoginManage3/MainFrame?pageNow="+ i + ">"
					+ "<"+i+"></a>");
			}else{
				out.println("<a href=/LoginManage3/MainFrame?pageNow="+pageCount
						+ "<"+i+"></a>");
			}
			//System.out.println("j===="+i);
		}
		//控制点击下一页
		if(pageNow <pageCount){
			out.print("<a href=/LoginManage3/MainFrame?pageNow="+(pageNow+1)
					+ ">下一页</a>");
		}else{
			out.println("<a href=/LoginManage3/MainFrame?pageNow="+pageCount
					+ ">下一页</a>");
		}
		
		out.println("当前页:"+pageNow+"/总页数:"+pageCount);
		//跳转到第几页
		out.print("<br/>");
		out.println("跳转到：<input type='text' width='10px' name='go' value="+pageNow+"></input>");
		out.println("<button type=button >GO</button>");
		String pageGo = request.getParameter("go");
		System.out.println("pageGo= "+pageGo);

		//out.print("<a href=/LoginManage2/MainFrame?pageNow="+ Integer.parseInt(pageGo) +">跳转</a>");
		
		//out.println("<p>当前页:"+pageNow+ "</p>");
		//out.print("<a href='/LoginManage2/LoginServlet'>返回登录页面</a>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
