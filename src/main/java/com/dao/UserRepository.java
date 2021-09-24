package com.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bean.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {

	
	public UserEntity findByEmail(String email);
	public UserEntity  findByUserName(String userName);
	
}
