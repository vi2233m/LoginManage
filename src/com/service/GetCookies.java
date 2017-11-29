package com.service;

import java.text.SimpleDateFormat;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCookies {
	
	public String getLastTime(HttpServletRequest request,
            HttpServletResponse response, String uid){
		
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
					//System.out.println("cuid="+cuid +"; uid="+uid);
					//���µ�½ʱ��
					if(!uid.equals(this.getUid(request, response, uid))){
						Cookie cookie = new Cookie("LastTime",nowTime);
						cookie.setMaxAge(3600);
						response.addCookie(cookie);
						System.out.println("���µ�½ʱ��");
					}
					b =true;
					break;
				}

			}
		}
		//�״ε�¼
		if( !b){
			Cookie cookie = new Cookie("LastTime",nowTime);
			//Cookie cookie2 = new Cookie("cuid",uid);
			cookie.setMaxAge(3600);
			//cookie2.setMaxAge(3600);
			response.addCookie(cookie);
			//response.addCookie(cookie2);
			value = null;
		}
		
		return value;
	}

	
	public String getUid(HttpServletRequest request,
            HttpServletResponse response, 
            String uid){
		
        String cuid = null;
        boolean b = false;
        	
		Cookie cookies[] = request.getCookies();
		if (cookies != null){			
			for(int j=0; j<cookies.length; j++){
				if ( cookies[j].getName().equals("cuid")){
					cuid = cookies[j].getValue();
					System.out.println("cuid===="+cuid);
					//b = true;
					break;
				}				
			}
			if (cuid != null){
				if (cuid.equals(uid)){
					b = true;
					System.out.println("cuid====uid");
				}
			}else{
				Cookie cookie2 = new Cookie("cuid",uid);
				cookie2.setMaxAge(3600);
				response.addCookie(cookie2);
				System.out.println("����cuid");
			}
		}
		//�״ε�¼
		if( !b){
			Cookie cookie2 = new Cookie("cuid",uid);
			cookie2.setMaxAge(3600);
			response.addCookie(cookie2);
			System.out.println("����cuid");
		}
		
		return cuid;
	}
	
}
