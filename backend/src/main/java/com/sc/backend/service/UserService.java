package com.sc.backend.service;

import java.util.List;

import com.sc.backend.entity.UserEntity;

public interface UserService {

	public List<UserEntity> get() throws Exception;
	
	public List<UserEntity> save(List<UserEntity> users) throws Exception;
	
	public boolean delete(String userName) throws Exception;
}
