package com.sc.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.sc.backend.entity.CustomerEntity;
import com.sc.backend.repository.CustomerRepository;
import com.sc.backend.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
	
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
