package com.service;

import java.text.SimpleDateFormat;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCookies {
	
	public String getLastTime(HttpServletRequest request,
            HttpServletResponse response){
		
        //��ȡʱ�䣬���ڸ���cookie
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy��MM��dd��-HH:mm:ss");
        String nowTime=simpleDateFormat.format(new java.util.Date());
        System.out.println("==nowTime==>"+nowTime);
        String value = "";
        boolean b = false;
        
		Cookie cookies[] = request.getCookies();
		if (cookies != null){
			for(int i=0; i<cookies.length; i++){
				if ( cookies[i].getName().equals("LastTime")){
					value = cookies[i].getValue();
					//���µ�½ʱ��
					Cookie cookie = new Cookie("LastTime",nowTime);
					cookie.setMaxAge(3600);
					response.addCookie(cookie);
					b =true;
					break;
				}

			}
		}
		//�״ε�¼
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
