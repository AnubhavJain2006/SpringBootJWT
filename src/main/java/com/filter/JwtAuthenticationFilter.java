package com.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.service.CustomUserDetailsService;
import com.sun.net.httpserver.Filter.Chain;
import com.util.JWTUtil;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	JWTUtil jwtUtil;

	@Autowired
	CustomUserDetailsService customUserDetailsService;
	//Authorization Bearer "token"
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken=request.getHeader("Authorization");
		String username=null;
		String jwtAuthToken=null;
		UserDetails userDetails=null;
		if(jwtToken!=null && jwtToken.startsWith("Bearer "))
		{
			
			jwtAuthToken=jwtToken.substring(7);
			
			try {
				username=this.jwtUtil.extractUsername(jwtAuthToken);
				userDetails=this.customUserDetailsService.loadUserByUsername(username);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
	
}
