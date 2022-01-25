package com.sc.backend.interfaceService;

import java.util.List;
import java.util.Optional;

import com.sc.backend.entity.CustomerEntity;

public interface InterfaceUserService {
	public List<CustomerEntity> list();
	public Optional<CustomerEntity> listOneUserId(Long id);
	public CustomerEntity add(CustomerEntity customerEntity);
	public CustomerEntity edit(CustomerEntity customerEntity);
	public void delete(Long id);
}
