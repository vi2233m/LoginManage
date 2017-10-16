package com.service;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.domain.User;
import com.mysql.jdbc.PreparedStatement;
import com.util.SqlHelper;

public class UserServer {
		
	//ɾ���û�
	public boolean DelUser(int id){
		boolean flag = false;
		String sql = "delete from user where user_id = "+ id;
		
		SqlHelper sh = SqlHelper.getInstance();
		int res = sh.execUpdate(sql, null);
		
		if(res != 0){
			flag = true;
			//System.out.println("�û�"+id+"�ѳɹ�ɾ��");
		}
//		else{
//			System.out.println("δ�ɹ�ɾ���û�");
//		}
		
		return flag;
	}
	
	//��ȡ��ǰ��¼�û���
	public String getLoginUsername(String uid){
		
		SqlHelper sh =SqlHelper.getInstance();
		String sql ="select user_name from user where user_id = ?";
		Object []params = {uid};
		//��ȡlist map ��ֵ
		Object it  =    sh.execQuery(sql, params).get(0).get("user_name");
		//����ת��Ϊ���� ��������ʱ��Ҫ��toString()��ת���ַ�������ת��Ϊ��Ҫ����������
		String userName = it.toString();

		return userName;		
	}	
	
	
	
	
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
	
	//��֤��¼�û��Ƿ����
	public boolean checkUser(User user){
		boolean relly = false;
		
		SqlHelper sh = new SqlHelper();
		//sh.getConnection();
		String sql = "select * from user where user_id = ? and user_password = ?";
		Object []params = {user.getUser_id(),user.getUser_password()};
		List list = sh.execQuery(sql, params);
		
		if (list != null && !list.isEmpty()){
			//System.out.println("------->"+sh.execQuery(sql, params));
			relly = true;
		}
		return relly;
	}

	//��ҳ�����÷�����ƽ����ݿ��в�ѯ�û�����д��User����
	public List<User> getUserInfo (int pageNow,int pageSize){
		
		String sql = "select user_id,user_name,user_email,user_company,user_school from user limit ?,?";
		int limit1 = (pageNow-1)*pageSize;
		//String limit2 = String.valueOf(pageSize);
		Object []params = {(pageNow-1)*pageSize , pageSize};
		//System.out.print("++++++++>>>"+((pageNow-1)*pageSize) +pageSize);
		
		//SqlHelper sh = new SqlHelper();
		SqlHelper sh = SqlHelper.getInstance();
		sh.getConnection();
		List<User> reslist = new ArrayList<User>();
        List<Object> list = new ArrayList<Object>();
        for(int i=0;i<params.length;i++) {
        	list.add(params[i]);
        }
        try {
			reslist = sh.executeQueryByRef(sql,list,User.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			sh.close(sh.getRs(), sh.getPs(), sh.getCt());
		}
		return reslist;		
	}
	
}
