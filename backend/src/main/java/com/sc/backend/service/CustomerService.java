package com.sc.backend.service;

import java.util.List;
import java.util.Optional;

import com.sc.backend.entity.CustomerEntity;

public interface CustomerService{
	
	public List<CustomerEntity> list() throws Exception;
	public Optional<CustomerEntity> listOneCustomerId(Long id) throws Exception;
	public CustomerEntity add(CustomerEntity customerEntity) throws Exception;
	public CustomerEntity edit(CustomerEntity customerEntity) throws Exception;
	public void delete(Long id) throws Exception;
		
}
