package com.util;

import java.util.ArrayList;
//import java.awt.List;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;

public class SqlHelper {
	//�Ĵ���
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/test";
	String username = "root";
	String password = "123456";
	//������
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	public Connection getCt() {
		return ct;
	}

	public void setCt(Connection ct) {
		this.ct = ct;
	}

	public PreparedStatement getPs() {
		return ps;
	}

	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	
	//�������ݿ�
	public Connection getConnection(){
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ct = (Connection) DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ct;
	}
	
	//�ر����ݿ�
	public void close(ResultSet rs,PreparedStatement ps,Connection ct){
		if (rs != null){
			try {
				ct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rs = null;
		if(ps != null){
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ps = null;
		
		if (ct != null){
			try {
				ct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ct = null;
	}
	
    /**
    * ִ�в�ѯ
    *
    * @param sql
    *            �����Ԥ��� sql���
    * @param params
    *            �ʺŲ����б�
    * @return ��ѯ��Ľ��
    */
	public List<Map <String, Object>> execQuery(String sql,Object []params){
		
		//��ȡ����
		try{
			this.getConnection();
			ps = (PreparedStatement) ct.prepareStatement(sql);
			
			if (params != null){
				for(int i = 0; i< params.length; i++){
					ps.setObject(i+1, params[i]+"");
				}
			}
			  					
			rs = ps.executeQuery();
			
			List<Map<String, Object>> al = new ArrayList<Map<String, Object>>();
			// ��ý����Ԫ���ݣ�Ԫ���ݾ����������ݵ����ݣ�����ѱ�����������������Ϊ���ݣ�
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			// ����е�����
			int columnCount = rsmd.getColumnCount();			
			//���������
			while(rs.next()){
				Map<String, Object> hm = new HashMap<String, Object>();
				for(int i=0;i<columnCount;i++){
					// ����������ȡ��ÿһ�е�����,������1��ʼ
					String columnName =rsmd.getColumnName(i+1);
					// �������������ֵ
					Object columnValue = rs.getObject(columnName);
					// ��������Ϊkey����ֵ��Ϊֵ������ hm�У�ÿ�� hm�൱��һ����¼
					hm.put(columnName, columnValue);
				}
				// ��ÿ�� hm���ӵ�al��, al�൱������������ÿ�� hm�������һ����¼
				al.add(hm);
			}
			return al;
			
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				this.close(rs,ps,ct);
			}
		
		return null;		
	}
	
	 /**
     * ִ�и���
     *
     * @param sql
     *            �����Ԥ��� sql���
     * @param params
     *            �ʺŲ����б�
     * @return Ӱ������
     */
     public int execUpdate(String sql, Object[] params) {
           try {
                 getConnection();// ������Ӷ���
                 ps = (PreparedStatement) this. ct .prepareStatement(sql);// ���Ԥ��������

                 if (params != null) {
                       // ���ò����б�
                       for (int i = 0; i < params. length; i++) {
                             // ��Ϊ�ʺŲ����������Ǵ�1��ʼ��������i+1��������ֵ��תΪ�ַ�����ʽ������setObject�ɹ�����
                             this .ps .setObject(i + 1, params[i] + "" );
                      }
                }

                 return this .ps .executeUpdate(); // ִ�и��£�������Ӱ������

          } catch (Exception e) {
                 // TODO Auto-generated catch block
                e.printStackTrace();
          } finally {
                 this .close( this. rs, this. ps , this .ct );
          }
           return 0;
    }
}