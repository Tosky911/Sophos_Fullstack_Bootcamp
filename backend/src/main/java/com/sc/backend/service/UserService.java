package com.sc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.backend.entity.CustomerEntity;
import com.sc.backend.interfaceService.InterfaceUserService;
import com.sc.backend.repository.CustomerRepository;

@Service
public class UserService implements InterfaceUserService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<CustomerEntity> list(){
		return (List<CustomerEntity>) customerRepository.findAll();
	}	
	
	@Override
	public Optional<CustomerEntity> listOneUserId(Long id) {
		return customerRepository.findById(id);
	}
	
	@Override
	public CustomerEntity add(CustomerEntity customerEntity) {
		return customerRepository.save(customerEntity);
	}
	
	@Override
	public CustomerEntity edit(CustomerEntity customerEntity) {
		return customerRepository.save(customerEntity);
	}
	
	@Override
	public void delete(Long id) {
		customerRepository.deleteById(id);
	}
	
}
