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
		out.print("<h1>��¼�ɹ�</h1>");
		out.print("��ǰ��¼�û���"+ "");
		out.print("<a href='/LoginManage3/LoginServlet'>���ص�¼ҳ��</a>");
		out.print("<hr></hr>");
		//out.print("<p>��timg.jpg�� <a href='/LoginManage/DownLoadServlet?filename=timg.jpg'>�������</a></p>");
		//out.print("<p>��timg1.jpg�� <a href='/LoginManage/DownLoadServlet?filename=timg1.jpg'>�������</a></p>");
		//�����ʾ���ݿ��е������û�
		int pageNow = 1; //�ڼ�ҳ
		int pageSize = 3;//ÿҳ��ʾ������¼
		int pageCount = 0;//�ܹ��м�ҳ
		int rowCount = 0;//���ݿ��ܹ��ж�����
		
		String spageNow = request.getParameter("pageNow");
		if (spageNow != null){
			pageNow =Integer.parseInt(spageNow);
			//pageNow = (int) spageNow;
		}
		
		
		//�������ݿ��ѯ����
		Connection ct = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			ct = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","123456");
			
			//��ȡ���ݿ��е�������
			UserServer us = new UserServer();
			pageCount= us.getPageCount(pageSize);
			
			//��ȡ���ݿ��еı�������
			ps = (PreparedStatement) ct.prepareStatement("select user_id,user_name,user_email,user_company,user_school from user limit ? , ?");
			ps.setObject(1, (pageNow-1)*pageSize);
			ps.setObject(2, pageSize);
			rs = ps.executeQuery();
						
			out.print("<h2>�û��б�</h2>");
			out.print("<table width='500' border='2' color='green'>");
			out.print("<tr><th>ID</th><th>�û���</th><th>����</th><th>��˾</th><th>��ҵԺУ</th></tr>");
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
		//���Ƶ����һҳ
		if (pageNow > 1){
			out.println("<a href=/LoginManage3/MainFrame?pageNow="+(pageNow-1)
					+ ">��һҳ</a>");
		}else{
			out.println("<a href=/LoginManage3/MainFrame>��һҳ</a>");
		}
		//���Ƶ������ҳ��
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
		//���Ƶ����һҳ
		if(pageNow <pageCount){
			out.print("<a href=/LoginManage3/MainFrame?pageNow="+(pageNow+1)
					+ ">��һҳ</a>");
		}else{
			out.println("<a href=/LoginManage3/MainFrame?pageNow="+pageCount
					+ ">��һҳ</a>");
		}
		
		out.println("��ǰҳ:"+pageNow+"/��ҳ��:"+pageCount);
		//��ת���ڼ�ҳ
		out.print("<br/>");
		out.println("��ת����<input type='text' width='10px' name='go' value="+pageNow+"></input>");
		out.println("<button type=button >GO</button>");
		String pageGo = request.getParameter("go");
		System.out.println("pageGo= "+pageGo);

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
