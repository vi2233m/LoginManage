package com.manage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownLoadServlet
 */
@WebServlet("/DownLoadServlet")
public class DownLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownLoadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String filename = request.getParameter("filename");
		System.out.println("获取文件名为："+filename);
		response.setHeader("Content-Disposition", "attachment;filename="+filename);
		
		String path=null;
		//String path = "D:/apache-tomcat-8.5.20/webapps/LoginManage/Download/timg1.jpg" ;
		
		if ("timg.jpg".equals(filename)){
			path = this.getServletContext().getRealPath("/images/"+filename);
			System.out.print("timg的path："+path);
		}else{
			path = "E:/apache-tomcat-8.5.20/webapps/LoginManage/Download/timg1.jpg";
		}
		
		FileInputStream fis = new FileInputStream(path);
		byte buff[] = new byte[1024];
		int len = 0;
		OutputStream os = response.getOutputStream();
		
		while ((len = fis.read(buff)) > 0) {
			os.write(buff);			
		}
		//os.write(fis.read());
		os.close();
		fis.close();
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
