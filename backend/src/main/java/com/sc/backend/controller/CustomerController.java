package com.sc.backend.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sc.backend.entity.ProductEntity;
import com.sc.backend.entity.CustomerEntity;
import com.sc.backend.service.impl.ProductServiceImpl;
import com.sc.backend.service.impl.CustomerServiceImpl;

@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping("/CUSTOMERS")
public class CustomerController {

	@Autowired
	CustomerServiceImpl customerServiceImpl;
	
	@Autowired
	ProductServiceImpl productServiceImpl;
	
	//Alistar a todos los usuarios
	@GetMapping("")
	public List<CustomerEntity> list(){
		return customerServiceImpl.list();
	}
	
	//Crear un nuevo usuario
	@PostMapping("")
	@ResponseBody
	public CustomerEntity save(@RequestBody CustomerEntity customerEntity) {
		return customerServiceImpl.add(customerEntity);
	}
	
	//Alistar un usuario por su Id
	@GetMapping("/{id}")
	public Optional<CustomerEntity> listOneUserId(@PathVariable("id") Long id) {
		return customerServiceImpl.listOneUserId(id);
	}
	
	//Editar la info de un usuario por su Id
	@PutMapping("/{id}")
	public CustomerEntity edit(@RequestBody CustomerEntity customerEntity, @PathVariable("id") Long id) {
		customerEntity.setId(id);
		return customerServiceImpl.edit(customerEntity);
	}
	
	//Eliminar la info de un usuario por su Id
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		List<ProductEntity> productEntity = productServiceImpl.listProductId(id);
		int state_count = 0;
		for (ProductEntity model: productEntity) {
			if (model.getState().equals("activa") || model.getState().equals("inactiva")) {
				state_count ++;
			}
		}
		
		if (state_count > 0 ) {
			System.out.println("Productos activos o inactivos");
		} else {
			System.out.println("Todos los productos cancelados");
			customerServiceImpl.delete(id);
		}
	}
	
}
