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
		
		out.print("<h1>��¼�ɹ�</h1>");
		String uid = (String) session.getAttribute("uid");
		if (uid == null){
			//response.sendRedirect("/LoginManage3/LoginServlet");
			request.setAttribute("error", "�Բ���,�����µ�¼��");
			request.getRequestDispatcher("/LoginServlet").forward(request, response);
			//System.out.print("uid===null��ת");
		}
		if (uid != null){
			String userName = new UserServer().getLoginUsername(uid);
			out.println("��ǰ��¼�û���"+ userName); //�����û�ID�����ҳ��û�������ʾ����ʵ�֡�����
		}
		//����cookie ��¼�ϴε�½ʱ��
		//GetCookies getcookies = new GetCookies();
		if (getcookies.getLastTime(request, response) == null){
			out.println("��ӭ�״ε�½��</br>");
		}else{
			out.println("���ϴε�½��ʱ��Ϊ:"+getcookies.getLastTime(request, response)+"</br>");
		}
		//out.println("��ӭ��½��< /br>");
		//out.print("<a href='/LoginManage3/LoginServlet'>���ص�¼ҳ��</a>");
		out.print("<hr></hr>");
		//out.print("<p>��timg.jpg�� <a href='/LoginManage/DownLoadServlet?filename=timg.jpg'>�������</a></p>");
		//out.print("<p>��timg1.jpg�� <a href='/LoginManage/DownLoadServlet?filename=timg1.jpg'>�������</a></p>");
		//�����ʾ���ݿ��е������û�
		int pageNow = 1; //�ڼ�ҳ
		int pageSize = 5;//ÿҳ��ʾ������¼
		int pageCount = 0;//�ܹ��м�ҳ
		int rowCount = 0;//���ݿ��ܹ��ж�����

		//�õ���ǰҳ
		String spageNow = request.getParameter("pageNow");
		if (spageNow != null){
			pageNow =Integer.parseInt(spageNow);
		}
		
		//��ȡ���ݿ��е�������
		UserServer us = new UserServer();
		pageCount= us.getPageCount(pageSize);
		

		//��ȡ���ݿ��еı�������
		//System.out.print("========="+us.getUserInfo(pageNow, pageSize));			
		out.print("<h2>�û��б�</h2>");
		out.print("<table width='500' border='2' color='green'>");
		out.print("<tr><th>ID</th><th>�û���</th><th>����</th><th>��˾</th><th>��ҵԺУ</th><th>�޸��û�</th><th>ɾ���û�</th></tr>");
		List<User> al = us.getUserList(pageNow, pageSize);
		//System.out.println("====�û��б�==="+al.toString());
		for (User user : al){
			//User user = new User();
			int id = user.getUser_id();
			String name = user.getUser_name();
			String email = user.getUser_email();
			String company = user.getUser_company();
			String school = user.getUser_school();
			
			String del="1";
			String upa="2";
			String del_href= "javascript:if(confirm('ȷʵҪɾ�����û���?'))location='/LoginManage3/UserClServlet?id="
					+ id
					+ "&type="
					+ del +"'";
			
			//System.out.println("===aaa===="+del_href);
			out.print("<tr><td>"+id+"</td><td>"+name+"</td><td>"+email+"</td><td>"+company+"</td><td>"+school+"</td>"
					+ "<td>"+"<a href='/LoginManage3/UserClServlet?id="+id+"&type="+upa+"'>�޸�</a>"+"</td>"
					+ "<td>"+"<a href="+del_href+">ɾ��</a>"+"</td></tr>");
			//javascript:if(confirm('ȷʵҪɾ�����û���?'))location=/LoginManage3/UserClServlet?id="+id+"&lb="+del+"
		}
		out.print("</table>");	

		
		//���Ƶ����һҳ
		if (pageNow > 1){
			out.println("<a href=/LoginManage3/MainFrame?pageNow="+(pageNow-1) +"&username="+uid
					+ ">��һҳ</a>");
		}else{
			out.println("<a href=/LoginManage3/MainFrame?username=" +uid
					+ ">��һҳ</a>");
		}
		//���Ƶ������ҳ��
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
		//���Ƶ����һҳ
		if(pageNow <pageCount){
			out.print("<a href=/LoginManage3/MainFrame?pageNow="+(pageNow+1) +"&username="+uid
					+ ">��һҳ</a>");
		}else{
			out.println("<a href=/LoginManage3/MainFrame?pageNow="+pageCount +"&username="+uid
					+ ">��һҳ</a>");
		}
		
		out.println("��ǰҳ:"+pageNow+"/��ҳ��:"+pageCount);
		//��ת���ڼ�ҳ
		out.print("<br/>");
		
		out.println("<p>��ת���� <input id='PageNo' size='4'> ҳ <input type='button' onclick='location.href=/LoginManage3/MainFrame?pageNow=2' value='GO'></p>");
		
		out.print("<a href='/LoginManage3/LoginServlet'>���ص�¼ҳ��</a>");
//		out.println("��ת����<input type='text' width='10px' id='go' value="+pageNow+"></input>");
//		out.print("<input name='pclog' type='button' value='GO' onClick='location.href=/LoginManage3/MainFrame?pageNow=2'/>"); 
		
//		out.println("��ת����<input type='text' width='10px' name='go' value="+pageNow+"></input>");
//		out.println("<button type=button >GO</button>");
//		String pageGo = request.getParameter("go");
//		System.out.println("pageGo= "+pageGo);

		//out.print("<a href=/LoginManage2/MainFrame?pageNow="+ Integer.parseInt(pageGo) +">��ת</a>");
		
		//out.println("<p>��ǰҳ:"+pageNow+ "</p>");
		//out.print("<a href='/LoginManage2/LoginServlet'>���ص�¼ҳ��</a>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
