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
import com.sc.backend.service.CustomerService;
import com.sc.backend.service.ProductService;


@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	ProductService productService;
	
	//Listar a todos los clientes
	@GetMapping("")
	public ResponseEntity<GeneralResponse<List<CustomerEntity>>> list(){
		
		GeneralResponse<List<CustomerEntity>> response = new GeneralResponse<>();
		HttpStatus status = null;
		List<CustomerEntity> data = null;
		
		try {
			
			data = customerService.list();
			
			if(data == null || data.size() == 0) {
				response.setMessageResult( "0 customers were found.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult(data.size()+ " customers were found.");
				response.setCodeError(0);
			}
			
			response.setMessage("Successful Query");
			response.setSuccess(true);
			response.setData(data);
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage("Something has failed. Error: "+ e.getLocalizedMessage()  +". Please contact support.");
			response.setSuccess(false);
		}
		
		return new ResponseEntity<>(response, status);
	}
	
	//Listar un cliente por su Id
	@GetMapping("/{id}")
	public ResponseEntity<GeneralResponse<CustomerEntity>> listOneCustomerId(@PathVariable("id") Long id) {
		
		GeneralResponse<CustomerEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		Optional<CustomerEntity> customer; 
		
		try {
			customer = customerService.listOneCustomerId(id);
			if (customer == null || customer.get() == null) {
				response.setMessageResult( "Customer wasn't found.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult("Customer " + customer.get().getId() +" was found.");
				response.setCodeError(0);
			}
			response.setMessage("Successful Query");
			response.setSuccess(true);
			response.setData(customer.get());
			status = HttpStatus.OK;
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage("Something has failed. Error: "+ e.getLocalizedMessage()  +". Please contact support.");
			response.setSuccess(false);
		}
		return new ResponseEntity<>(response, status);
	}
	
	//Crear un nuevo cliente
	@PostMapping
	@ResponseBody
	public  ResponseEntity<GeneralResponse<CustomerEntity>> save(@RequestBody CustomerEntity customerEntity) {
		
		GeneralResponse<CustomerEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		CustomerEntity data = null; 
		
		try {
			
			data = customerService.add(customerEntity);
			
			if(data == null) {
				response.setMessageResult( "Customer wasn't saved.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult("Customer with Id: " + customerEntity.getNumId()+ " was saved.");
				response.setCodeError(0);
			}

				response.setMessage("Successful Save");
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
	
	
	//Editar/Actualizar cliente por su Id
	@PutMapping("/{id}")
	public ResponseEntity<GeneralResponse<CustomerEntity>> edit(@RequestBody CustomerEntity customerEntity) {
		
		GeneralResponse<CustomerEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		CustomerEntity data= null;
		
		try {
			
			data = customerService.add(customerEntity);
			if (data == null) {
				response.setMessageResult( "0 customers were updated.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult("Customer with Id: " + data.getId() + " was updated.");
				response.setCodeError(0);
			}
			
			response.setMessage("Successful Update");
			response.setSuccess(true);
			response.setData(data);
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage("Something has failed. Error: "+ e.getLocalizedMessage()  +" . Please contact support.");
			response.setSuccess(false);
		}
		
		return new ResponseEntity<>(response, status);
	}
	
	//Eliminar la info de un cliente por su Id
	@DeleteMapping("/{id}")
	public ResponseEntity<GeneralResponse<Long>> delete(@PathVariable("id") Long id) {
 
		GeneralResponse<Long> response = new GeneralResponse<>();
		HttpStatus status = null;
		
		//Se verifica la cantidad de productos activos del cliente
		List<ProductEntity> productEntity = productService.listProductId(id);
		int state_count = 0;
		for (ProductEntity model: productEntity) {
			if (model.getState().equals("activa") || model.getState().equals("inactiva")) {
				state_count ++;
			}
		}
		try {
			customerService.delete(id);
			
			if(state_count > 0 || id ==null) {
				response.setCodeError(1);
				response.setMessageResult( "Customer still has products in his account or Id doesn't exist, it wasn't deleted.");
				response.setData(null);
			} else {
				response.setCodeError(0);
				response.setMessageResult("Customer with Id: " + id +" was deleted.");
			}
			
			response.setMessage("Successful Removal");
			response.setSuccess(true);
			response.setData(id.longValue());
			status = HttpStatus.OK;
		}catch(Exception e) {
			
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage("Something has failed. Error: "+ e.getLocalizedMessage()  +" . Please contact support.");
			response.setSuccess(false);
		}
		return new ResponseEntity<>(response, status);
	}
	
	
}
