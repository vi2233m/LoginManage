package com.service;

import java.text.SimpleDateFormat;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCookies {
	
	public String getLastTime(HttpServletRequest request,
            HttpServletResponse response){
		
        //获取时间，用于更新cookie
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss");
        String nowTime=simpleDateFormat.format(new java.util.Date());
        System.out.println("==nowTime==>"+nowTime);
        String value = "";
        boolean b = false;
        
		Cookie cookies[] = request.getCookies();
		if (cookies != null){
			for(int i=0; i<cookies.length; i++){
				if ( cookies[i].getName().equals("LastTime")){
					value = cookies[i].getValue();
					//更新登陆时间
					Cookie cookie = new Cookie("LastTime",nowTime);
					cookie.setMaxAge(3600);
					response.addCookie(cookie);
					b =true;
					break;
				}

			}
		}
		//首次登录
		if( !b){
			Cookie cookie = new Cookie("LastTime",nowTime);
			//Cookie cookie2 = new Cookie("username",)
			cookie.setMaxAge(3600);
			response.addCookie(cookie);
			value = null;
		}
		
		return value;
	}

}
