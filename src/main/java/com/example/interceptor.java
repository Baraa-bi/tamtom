package com.example.Social;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class interceptor extends HandlerInterceptorAdapter{


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Calendar cal = Calendar.getInstance();
		int day = cal.get(cal.DAY_OF_WEEK);
		if(day==17)
		{
			response.getWriter().write("sorry .... our website is closed at saturday :D");
			return false;
		}
		else
			return true;

		
	}
	
	

}
