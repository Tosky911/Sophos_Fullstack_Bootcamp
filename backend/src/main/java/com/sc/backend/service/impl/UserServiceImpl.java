package com.sc.backend.service.impl;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sc.backend.entity.UserEntity;
import com.sc.backend.repository.UserRepository;
import com.sc.backend.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
	@Autowired
	UserRepository userRepository;

	@Override
	public List<UserEntity> get() throws Exception {
		return userRepository.findAll();
	}

	@Override
	public List<UserEntity> save(List<UserEntity> users) throws Exception {
		for (UserEntity userVO : users) {
			userRepository.save(userVO);
		}
		return users;
	}

	@Override
	public boolean delete(String userName) throws Exception {
		userRepository.deleteById(userName);
		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserEntity userVO = userRepository.findByUserName(userName);
		UserDetails userDetails = new User(userVO.getUserName(), userVO.getPassword(), new ArrayList<>());
		
		return userDetails;
	}
}
