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
import com.sc.backend.entity.UserEntity;
import com.sc.backend.interfaceService.InterfaceProductService;
import com.sc.backend.interfaceService.InterfaceUserService;

@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	InterfaceUserService serviceUser;
	
	@Autowired
	InterfaceProductService serviceProduct;
	
	//Alistar a todos los usuarios
	@GetMapping("")
	public List<UserEntity> list(){
		return serviceUser.list();
	}
	
	//Crear un nuevo usuario
	@PostMapping("")
	@ResponseBody
	public UserEntity save(@RequestBody UserEntity userEntity) {
		return serviceUser.add(userEntity);
	}
	
	//Alistar un usuario por su Id
	@GetMapping("/{id}")
	public Optional<UserEntity> listOneUserId(@PathVariable("id") Long id) {
		return serviceUser.listOneUserId(id);
	}
	
	//Editar la info de un usuario por su Id
	@PutMapping("/{id}")
	public UserEntity edit(@RequestBody UserEntity userEntity, @PathVariable("id") Long id) {
		userEntity.setId(id);
		return serviceUser.edit(userEntity);
	}
	
	//Eliminar la info de un usuario por su Id
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		List<ProductEntity> productEntity = serviceProduct.listProductId(id);
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
			serviceUser.delete(id);
		}
	}
	
}
