package com.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bean.UserEntity;
import com.dao.UserRepository;
import com.util.JWTUtil;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserEntity user=userRepository.findByUserName(email);
		if(user==null)
		{
			throw new UsernameNotFoundException("Invalid Credentials");
		}
		else {
//			String token=jwtUtil.generateToken(new CustomUserDetails(user));
//			user.setAuthToken(token);
//			System.out.println("Token value :"+token);
//			user=userRepository.save(user);
			return new CustomUserDetails(user);	
		}
	}
}
