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
	//四大金刚
//	String driver = "com.mysql.jdbc.Driver";
//	String url = "jdbc:mysql://localhost:3306/test";
//	String username = "root";
//	String password = "123456";
	public static String driver;
	public static String url;
	public static String username;
	public static String password;
	
	private static SqlHelper per = null;
	
	//读取配置文件
	static {
		Properties prop = new Properties();
		
		InputStream is = SqlHelper.class.getClassLoader().getResourceAsStream("com/util/db.properties");
		try {
			prop.load(is);
			//is.close();
			
            // 获取驱动
            driver = prop.getProperty("jdbc.driver");
            // 获取地址
            url = prop.getProperty("jdbc.url");
            // 获取用户名
            username = prop.getProperty("jdbc.user");
            // 获取密码
            password = prop.getProperty("jdbc.password");
            
            // 注册驱动
            try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            //System.out.println("获取配置文件的数据为："+ url + username + password);
			
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
	
	
	//三剑客
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
	
	//连接数据库
	public Connection getConnection(){
//		try {
//			Class.forName(driver);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			ct = (Connection) DriverManager.getConnection(url,username,password);
			//System.out.println("获取配置文件的数据为："+ url + username + password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ct;
	}
	
	//关闭数据库
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
    * 执行查询
    *
    * @param sql
    *            传入的预设的 sql语句
    * @param params
    *            问号参数列表
    * @return 查询后的结果
    */
	public List<Map <String, Object>> execQuery(String sql,Object []params){
		
		//获取连接
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
			// 获得结果集元数据（元数据就是描述数据的数据，比如把表的列类型列名等作为数据）
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			// 获得列的总数
			int columnCount = rsmd.getColumnCount();			
			//遍历结果集
			while(rs.next()){
				Map<String, Object> hm = new HashMap<String, Object>();
				for(int i=0;i<columnCount;i++){
					// 根据列索引取得每一列的列名,索引从1开始
					String columnName =rsmd.getColumnName(i+1);
					// 根据列名获得列值
					Object columnValue = rs.getObject(columnName);
					// 将列名作为key，列值作为值，放入 hm中，每个 hm相当于一条记录
					hm.put(columnName, columnValue);
				}
				// 将每个 hm添加到al中, al相当于是整个表，每个 hm是里面的一条记录
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
     * jdbc的封装可以用反射机制来封装,把从数据库中获取的数据封装到一个类的对象里
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
            T resultObject = cls.newInstance();  // 通过反射机制创建实例
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = rs.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); // 打开javabean的访问private权限
                field.set(resultObject, cols_value);
            }
            list.add(resultObject);
        }
        return list;

    }
	
	
	 /**
     * 执行更新
     *
     * @param sql
     *            传入的预设的 sql语句
     * @param params
     *            问号参数列表
     * @return 影响行数
     */
     public int execUpdate(String sql, Object[] params) {
           try {
                 getConnection();// 获得连接对象
                 ps = (PreparedStatement) this. ct .prepareStatement(sql);// 获得预设语句对象

                 if (params != null) {
                       // 设置参数列表
                       for (int i = 0; i < params. length; i++) {
                             // 因为问号参数的索引是从1开始，所以是i+1，将所有值都转为字符串形式，好让setObject成功运行
                             this .ps .setObject(i + 1, params[i] + "" );
                      }
                }

                 return this .ps .executeUpdate(); // 执行更新，并返回影响行数

          } catch (Exception e) {
                 // TODO Auto-generated catch block
                e.printStackTrace();
          } finally {
                 this .close( this. rs, this. ps , this .ct );
          }
           return 0;
    }
}
