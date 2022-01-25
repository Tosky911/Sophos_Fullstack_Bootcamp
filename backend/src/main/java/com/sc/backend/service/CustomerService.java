package com.sc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.backend.entity.CustomerEntity;
import com.sc.backend.repository.CustomerRepository;
import com.sc.backend.service.impl.CustomerServiceImpl;

public interface CustomerService{
	
	public List<CustomerEntity> list();
	public Optional<CustomerEntity> listOneUserId(Long id);
	public CustomerEntity add(CustomerEntity customerEntity);
	public CustomerEntity edit(CustomerEntity customerEntity);
	public void delete(Long id);
		
}
