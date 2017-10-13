package com.service;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.domain.User;
import com.mysql.jdbc.PreparedStatement;
import com.util.SqlHelper;

public class UserServer {
		

	//�������ݿ⹤��������棬��ȡ��ҳ��
	public int getPageCount(int pageSize){

		int pageCount =0;
		int rowCount =0;
		
		SqlHelper sh =new SqlHelper();
		String sql ="select count(1) from user";
		//System.out.println("���ص�rowcount ��"+sh.execQuery(sql, null).get(0).get("count(1)"));
		
		//��ȡlist map ��ֵ
		Object it  =    sh.execQuery(sql, null).get(0).get("count(1)");
		//����ת��Ϊ���� ��������ʱ��Ҫ��toString()��ת���ַ�������ת��Ϊ��Ҫ����������
		rowCount = Integer.parseInt(it.toString());
		
		if (rowCount%pageSize != 0){
			pageCount = rowCount/pageSize +1;
		}else{
			pageCount = rowCount/pageSize;
		}

		return pageCount;		
	}
	
	
	public boolean checkUser(User user){
		boolean relly = false;
		
		SqlHelper sh = new SqlHelper();
		//sh.getConnection();
		String sql = "select * from user where user_id = ? and user_password = ?";
		Object []params = {user.getId(),user.getUser_password()};
		List list = sh.execQuery(sql, params);
		
		if (list != null && !list.isEmpty()){
			//System.out.println("------->"+sh.execQuery(sql, params));
			relly = true;
		}
		return relly;
	}
	
	public List<User> getUserInfo (int pageNow,int pageSize){
		
		String sql = "select user_id,user_name,user_email,user_company,user_school from user limit ?,?";
		String limit1 = String.valueOf(((pageNow-1)*pageSize));
		String limit2 = String.valueOf(pageSize);
		Object []params = {(pageNow-1)*pageSize , pageSize};
		//System.out.print("++++++++>>>"+((pageNow-1)*pageSize) +pageSize);
		
		SqlHelper sh = new SqlHelper();
		List<Map<String,Object>> list= sh.execQuery(sql, params);
		ArrayList<User> userlist = new ArrayList<User>();
		
		for(Map<String,Object> us:list){
			User user = new User();
			user.setId(Integer.parseInt(us.get("user_id").toString()));
			user.setUser_name(us.get("user_name").toString());
			user.setUser_email(us.get("user_email").toString());
			user.setUser_company(us.get("user_company").toString());
			user.setUser_school(us.get("user_school").toString());
			userlist.add(user);
		}
		System.out.print("=========="+userlist);	
		return userlist;
	}
	
}
