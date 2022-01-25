package com.sc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.backend.entity.UserEntity;
import com.sc.backend.interfaceService.InterfaceUserService;
import com.sc.backend.repository.UserRepository;

@Service
public class UserService implements InterfaceUserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<UserEntity> list(){
		return (List<UserEntity>) userRepository.findAll();
	}	
	
	@Override
	public Optional<UserEntity> listOneUserId(Long id) {
		return userRepository.findById(id);
	}
	
	@Override
	public UserEntity add(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}
	
	@Override
	public UserEntity edit(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}
	
	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	
}
