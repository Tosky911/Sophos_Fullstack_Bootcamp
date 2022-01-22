package com.sc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.backend.entity.User;
import com.sc.backend.repository.UserRepository;

@Service
public class UserService implements InterfaceUserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> list(){
		return (List<User>) userRepository.findAll();
	}	
	
	@Override
	public User listOneUserId(Long id) {
		return userRepository.findById(id);
	}
	
	@Override
	public User add(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public User edit(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	
}
