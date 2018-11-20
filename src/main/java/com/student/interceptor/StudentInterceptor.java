package com.student.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.student.controller.StudentController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class StudentInterceptor implements HandlerInterceptor {

	public static final String BEARER_IDENTIFIER = "Bearer ";

	@Autowired
	private StudentController studentController;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		try {
			String authorization = request.getHeader("Authorization");

			if (authorization == null || !authorization.startsWith(BEARER_IDENTIFIER)) {
				throw new Exception("Missing or invalid Authorization header with Bearer type.");
			}

			String jwt = authorization.substring(BEARER_IDENTIFIER.length());

			String jwtSignature = null;
			Jws<Claims> jws = null;

			jws = Jwts.parser().setSigningKey("this is a secret key").parseClaimsJws(jwt);
			jwtSignature = (String) jws.getSignature();
			
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("JWT ERROR---------");
			return false;
		}

		return true;

	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}