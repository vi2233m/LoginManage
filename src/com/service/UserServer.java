package com.service;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.PreparedStatement;
import com.util.SqlHelper;

public class UserServer {
//	int pageNow = 1; //第几页
//	int pageSize = 3;//每页显示几条记录
//	int pageCount = 0;//总共有几页
//	int rowCount = 0;//数据库总共有多少行
	//int pageCount =0;
		
	public int getPageCount(int pageSize){
		Connection ct = null;
		PreparedStatement ps =null;
		ResultSet rs =null;
		int pageCount =0;
		
		SqlHelper sh =new SqlHelper();
		ct =sh.getConnection();
		String sql ="select count(1) from user";
		try{
			ps = (PreparedStatement) ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				int rowCount = rs.getInt(1);
				if (rowCount%pageSize != 0){
					pageCount = rowCount/pageSize +1;
				}else{
					pageCount = rowCount/pageSize;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sh.close(rs, ps, sh.getCt());
		}
		return pageCount;
		
	}
}
