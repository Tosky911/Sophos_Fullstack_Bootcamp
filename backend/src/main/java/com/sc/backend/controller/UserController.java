package com.sc.backend.controller;


import java.util.List;

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

import com.sc.backend.entity.Product;
import com.sc.backend.entity.User;

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
	public List<User> list(){
		return serviceUser.list();
	}
	
	//Crear un nuevo usuario
	@PostMapping("")
	@ResponseBody
	public User save(@RequestBody User user) {
		return serviceUser.add(user);
	}
	
	//Alistar un usuario por su Id
	@GetMapping("/{id}")
	public User listIdOneUser(@PathVariable("id") Long id) {
		return serviceUser.listIdOneUser(id);
	}
	
	//Editar la info de un usuario por su Id
	@PutMapping("/{id}")
	public User edit(@RequestBody User user, @PathVariable("id") Long id) {
		user.setId(id);
		return serviceUser.edit(user);
	}
	
	//Eliminar la info de un usuario por su Id
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		List<Product> product = serviceProduct.listIdProduct(id);
		int state_count = 0;
		for (Product model: product) {
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
