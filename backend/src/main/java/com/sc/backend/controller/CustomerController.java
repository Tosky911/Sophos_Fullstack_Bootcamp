package com.sc.backend.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.sc.backend.model.GeneralResponse;
import com.sc.backend.entity.CustomerEntity;
import com.sc.backend.service.impl.ProductServiceImpl;
import com.sc.backend.service.impl.CustomerServiceImpl;

@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	CustomerServiceImpl customerServiceImpl;
	
	@Autowired
	ProductServiceImpl productServiceImpl;
	
	//Alistar a todos los usuarios
	@GetMapping("")
	public ResponseEntity<GeneralResponse<List<CustomerEntity>>> list(){
		
		GeneralResponse<List<CustomerEntity>> response = new GeneralResponse<>();
		HttpStatus status = null;
		List<CustomerEntity> data = null;
		
		try {
			
			data = customerServiceImpl.list();
			String msg = "It found " + data.size() + " users.";
			
			response.setMessage(msg);
			response.setSuccess(true);
			response.setData(data);
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			
			String msg = "Something has failed. Please contact suuport.";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage(msg);
			response.setSuccess(false);
		}
		
		return new ResponseEntity<>(response, status);
	}
	
	//Crear un nuevo usuario
	@PostMapping("")
	@ResponseBody
	public  ResponseEntity<GeneralResponse<CustomerEntity>> save(@RequestBody CustomerEntity customerEntity) {
		
		GeneralResponse<CustomerEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		CustomerEntity data = null; 
		
		try {
			
			data = customerServiceImpl.add(customerEntity);
			String msg = "It saved customer " + data.getId() + ".";
			
			response.setMessage(msg);
			response.setSuccess(true);
			response.setData(data);
			status = HttpStatus.OK;

		} catch (Exception e) {

			String msg = "Something has failed. Please contact support.";
			response.setMessage(msg);
			response.setSuccess(false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			
		}
		
		return new ResponseEntity<>(response, status);
	}
	
	//Alistar un usuario por su Id
	@GetMapping("/{id}")
	public Optional<CustomerEntity> listOneCustomerId(@PathVariable("id") Long id) {
		return customerServiceImpl.listOneCustomerId(id);
	}
	
	//Editar/Actualizar la info de un usuario por su Id
	@PutMapping("/{id}")
	public ResponseEntity<GeneralResponse<CustomerEntity>> edit(@RequestBody CustomerEntity customerEntity, @PathVariable("id") Long id) {
		
		GeneralResponse<CustomerEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		CustomerEntity data= null;
		customerEntity.setId(id);
		
		try {
			
			data = customerServiceImpl.add(customerEntity);
			String msg = "It updated customer" + data.getId() + ".";
			
			response.setMessage(msg);
			response.setSuccess(true);
			response.setData(data);
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			
			String msg = "Something has failed. Please contact support.";
			response.setMessage(msg);
			response.setSuccess(false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<>(response, status);
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
