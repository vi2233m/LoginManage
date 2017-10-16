package com.util;

import java.util.ArrayList;
//import java.awt.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;

public class SqlHelper {
	//�Ĵ���
//	String driver = "com.mysql.jdbc.Driver";
//	String url = "jdbc:mysql://localhost:3306/test";
//	String username = "root";
//	String password = "123456";
	public static String driver;
	public static String url;
	public static String username;
	public static String password;
	
	private static SqlHelper per = null;
	
	//��ȡ�����ļ�
	static {
		Properties prop = new Properties();
		
		InputStream is = SqlHelper.class.getClassLoader().getResourceAsStream("com/util/db.properties");
		try {
			prop.load(is);
			//is.close();
			
            // ��ȡ����
            driver = prop.getProperty("jdbc.driver");
            // ��ȡ��ַ
            url = prop.getProperty("jdbc.url");
            // ��ȡ�û���
            username = prop.getProperty("jdbc.user");
            // ��ȡ����
            password = prop.getProperty("jdbc.password");
            
            // ע������
            try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            //System.out.println("��ȡ�����ļ�������Ϊ��"+ url + username + password);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
    public static SqlHelper getInstance() {
        if (per == null) {
            per = new SqlHelper();
            //per.registeredDriver();
        }
        return per;
    }
	
	
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
//		try {
//			Class.forName(driver);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			ct = (Connection) DriverManager.getConnection(url,username,password);
			//System.out.println("��ȡ�����ļ�������Ϊ��"+ url + username + password);
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
			//System.out.println("==========>>>"+ sql);
			if (params != null){
				for(int i = 0; i< params.length; i++){
					ps.setObject(i+1, params[i]);
					//System.out.println(">>>>>>>>>>"+params[i]);
				}
			}
			  					
			rs = ps.executeQuery();
			
			List<Map<String, Object>> al = new ArrayList<Map<String, Object>>();
			// ��ý����Ԫ���ݣ�Ԫ���ݾ����������ݵ����ݣ�����ѱ����������������Ϊ���ݣ�
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
				// ��ÿ�� hm��ӵ�al��, al�൱����������ÿ�� hm�������һ����¼
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
     * jdbc�ķ�װ�����÷����������װ,�Ѵ����ݿ��л�ȡ�����ݷ�װ��һ����Ķ�����
     * 
     * @param sql
     * @param params
     * @param cls
     * @return
     * @throws Exception
     */
    public <T> List<T> executeQueryByRef(String sql, List<Object> params,
            Class<T> cls) throws Exception {
        List<T> list = new ArrayList<T>();
        int index = 1;
        ps = (PreparedStatement) ct.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(index++, params.get(i));
            }
        }
        rs = ps.executeQuery();
        ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (rs.next()) {
            T resultObject = cls.newInstance();  // ͨ��������ƴ���ʵ��
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = rs.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); // ��javabean�ķ���privateȨ��
                field.set(resultObject, cols_value);
            }
            list.add(resultObject);
        }
        return list;

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
