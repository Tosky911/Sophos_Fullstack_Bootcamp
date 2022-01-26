package com.sc.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.backend.entity.CustomerEntity;
import com.sc.backend.repository.CustomerRepository;
import com.sc.backend.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<CustomerEntity> list() throws Exception{
		return (List<CustomerEntity>) customerRepository.findAll();
	}	
	
	@Override
	public Optional<CustomerEntity> listOneCustomerId(Long id) throws Exception {
		return customerRepository.findById(id);
	}
	
	@Override
	public CustomerEntity add(CustomerEntity customerEntity) throws Exception {
		return customerRepository.save(customerEntity);
	}
	
	@Override
	public CustomerEntity edit(CustomerEntity customerEntity) throws Exception {
		return customerRepository.save(customerEntity);
	}
	
	@Override
	public void delete(Long id) throws Exception {
		customerRepository.deleteById(id);
	}
}
