package com.sc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.backend.entity.CustomerEntity;
import com.sc.backend.interfaceService.InterfaceUserService;
import com.sc.backend.repository.UserRepository;

@Service
public class UserService implements InterfaceUserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<CustomerEntity> list(){
		return (List<CustomerEntity>) userRepository.findAll();
	}	
	
	@Override
	public Optional<CustomerEntity> listOneUserId(Long id) {
		return userRepository.findById(id);
	}
	
	@Override
	public CustomerEntity add(CustomerEntity customerEntity) {
		return userRepository.save(customerEntity);
	}
	
	@Override
	public CustomerEntity edit(CustomerEntity customerEntity) {
		return userRepository.save(customerEntity);
	}
	
	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	
}
