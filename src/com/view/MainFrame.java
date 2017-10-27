package com.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.service.GetCookies;
import com.service.UserServer;
import com.util.SqlHelper;
import com.domain.*;

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
		
		//String uid = request.getParameter("username");
		HttpSession session = request.getSession();
		GetCookies getcookies = new GetCookies();
		
		out.print("<h1>登录成功</h1>");
		String uid = (String) session.getAttribute("uid");
		if (uid == null){
			//response.sendRedirect("/LoginManage3/LoginServlet");
			request.setAttribute("error", "对不起,请重新登录！");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
			//System.out.print("uid===null跳转");
		}
		if (uid != null){
			String userName = new UserServer().getLoginUsername(uid);
			out.println("当前登录用户："+ userName); //根据用户ID，查找出用户名并显示，待实现。。。
		}
		//设置cookie 记录上次登陆时间
		//GetCookies getcookies = new GetCookies();
		if (getcookies.getLastTime(request, response) == null){
			out.println("欢迎首次登陆！</br>");
		}else{
			out.println("您上次登陆的时间为:"+getcookies.getLastTime(request, response)+"</br>");
		}
		//out.println("欢迎登陆！< /br>");
		//out.print("<a href='/LoginManage3/LoginServlet'>返回登录页面</a>");
		out.print("<hr></hr>");
		//out.print("<p>【timg.jpg】 <a href='/LoginManage/DownLoadServlet?filename=timg.jpg'>点击下载</a></p>");
		//out.print("<p>【timg1.jpg】 <a href='/LoginManage/DownLoadServlet?filename=timg1.jpg'>点击下载</a></p>");
		//表格显示数据库中的所有用户
		int pageNow = 1; //第几页
		int pageSize = 5;//每页显示几条记录
		int pageCount = 0;//总共有几页
		int rowCount = 0;//数据库总共有多少行

		//得到当前页
		String spageNow = request.getParameter("pageNow");
		if (spageNow != null){
			pageNow =Integer.parseInt(spageNow);
		}
		
		//获取数据库中的总条数
		UserServer us = new UserServer();
		pageCount= us.getPageCount(pageSize);
		

		//获取数据库中的表格的数据
		//System.out.print("========="+us.getUserInfo(pageNow, pageSize));			
		out.print("<h2>用户列表</h2>");
		out.print("<table width='500' border='2' color='green'>");
		out.print("<tr><th>ID</th><th>用户名</th><th>邮箱</th><th>公司</th><th>毕业院校</th><th>修改用户</th><th>删除用户</th></tr>");
		List<User> al = us.getUserList(pageNow, pageSize);
		//System.out.println("====用户列表==="+al.toString());
		for (User user : al){
			//User user = new User();
			int id = user.getUser_id();
			String name = user.getUser_name();
			String email = user.getUser_email();
			String company = user.getUser_company();
			String school = user.getUser_school();
			
			String del="1";
			String upa="2";
			String del_href= "javascript:if(confirm('确实要删除该用户吗?'))location='/LoginManage3/UserClServlet?id="
					+ id
					+ "&type="
					+ del +"'";
			
			//System.out.println("===aaa===="+del_href);
			out.print("<tr><td>"+id+"</td><td>"+name+"</td><td>"+email+"</td><td>"+company+"</td><td>"+school+"</td>"
					+ "<td>"+"<a href='/LoginManage3/UserClServlet?id="+id+"&type="+upa+"'>修改</a>"+"</td>"
					+ "<td>"+"<a href="+del_href+">删除</a>"+"</td></tr>");
			//javascript:if(confirm('确实要删除该用户吗?'))location=/LoginManage3/UserClServlet?id="+id+"&lb="+del+"
		}
		out.print("</table>");	

		
		//控制点击上一页
		if (pageNow > 1){
			out.println("<a href=/LoginManage3/MainFrame?pageNow="+(pageNow-1) +"&username="+uid
					+ ">上一页</a>");
		}else{
			out.println("<a href=/LoginManage3/MainFrame?username=" +uid
					+ ">上一页</a>");
		}
		//控制点击具体页数
		for(int i=pageNow;i<pageNow+3;i++){
			if(i <= pageCount){
				out.println("<a href=/LoginManage3/MainFrame?pageNow="+ i + "&username="+uid + ">"
					+ "<"+i+"></a>");
			}else{
				out.println("<a href=/LoginManage3/MainFrame?pageNow="+pageCount +"&username="+uid 
						+ "<"+i+"></a>");
			}
			//System.out.println("j===="+i);
		}
		//控制点击下一页
		if(pageNow <pageCount){
			out.print("<a href=/LoginManage3/MainFrame?pageNow="+(pageNow+1) +"&username="+uid
					+ ">下一页</a>");
		}else{
			out.println("<a href=/LoginManage3/MainFrame?pageNow="+pageCount +"&username="+uid
					+ ">下一页</a>");
		}
		
		out.println("当前页:"+pageNow+"/总页数:"+pageCount);
		//跳转到第几页
		out.print("<br/>");
		
		out.println("<p>跳转到第 <input id='PageNo' size='4'> 页 <input type='button' onclick='location.href=/LoginManage3/MainFrame?pageNow=2' value='GO'></p>");
		
		out.print("<a href='/LoginManage3/LoginServlet'>返回登录页面</a>");
//		out.println("跳转到：<input type='text' width='10px' id='go' value="+pageNow+"></input>");
//		out.print("<input name='pclog' type='button' value='GO' onClick='location.href=/LoginManage3/MainFrame?pageNow=2'/>"); 
		
//		out.println("跳转到：<input type='text' width='10px' name='go' value="+pageNow+"></input>");
//		out.println("<button type=button >GO</button>");
//		String pageGo = request.getParameter("go");
//		System.out.println("pageGo= "+pageGo);

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
