package com.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bean.LoginBean;
import com.service.CustomUserDetails;
import com.service.CustomUserDetailsService;
import com.util.JWTUtil;

@Controller
public class MainController {

	
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	JWTUtil jwtUtil;
	
	@GetMapping("/login")
	public String login() {
		return "/login";
	}
	@GetMapping("/authenticate")
	public String home(HttpServletResponse response,@RequestBody LoginBean login) {
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(login.getEmail());
		
		
		System.out.println(userDetails.getUsername());
		System.out.println(userDetails.getPassword());
		System.out.println(userDetails.getAuthorities());
		System.out.println(userDetails.getEmail());
		String token=jwtUtil.generateToken(userDetails);
//		Cookie ck =new Cookie("Authentication",token);
//		response.addCookie(ck);	
		return null;
	}
	
}
