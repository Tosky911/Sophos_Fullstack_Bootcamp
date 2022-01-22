package com.sc.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sc.backend.entity.Product;
import com.sc.backend.entity.Transaction;

@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping("/users/{userId}/products")
public class ProductController {
	
	@Autowired
	InterfaceProductService serviceProduct;
	
	@Autowired
	InterfaceTransactionService serviceTransaction;
	
	//Alistar los productos del usuario
	@GetMapping("")
	public List<Product> listIdProduct(@PathVariable("userId") Long userId){
		return serviceProduct.listIdProduct(userId);
	}
	
	//Lista de los productos diferentes al seleccionado
	@GetMapping("/{productId}/different")
	public List<Product> listIdOtherAvailableProducts(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId){
		return serviceProduct.listIdOtherAvaibleProducts(userId,productId);
	}
	
	//Crear un nuevo producto para un cliente
	@PostMapping("")
	@ResponseBody
	public Product save(@RequestBody Product product, @PathVariable("userId") Long userId, Transaction transaction ) {
		product.setUserId(userId);
		
		transaction.setPrincipalProductId(product.getproductId());
		transaction.setTransactionDetails("Creacion producto");
		transaction.setTransactionResult("Efectiva");
		transaction.setFinalBalance(0);
		transaction.setTransactionValue(0);
		transaction.setTransactionType("Creacion cuenta");
		transaction.setTransactionDate(transaction.getTransactionDate());
		serviceTransaction.createTransaction(transaction, product.getproductId());
		return serviceProduct.addProduct(product, userId);
	}
	
	//Obtener un producto de un usuario
	@GetMapping("/{productId}")
	public Product ListIdOneProduct(@PathVariable("productId") Long productId){
		return serviceProduct.listIdOneProduct(productId);
	}
	
	//Cambiar el estado a activo o inactivo
	@PutMapping("/{productId}/changeState")
	public Product changeState(Product product, @PathVariable("productId") Long productId){
		product = ListIdOneProduct(productId);
		product.setproductId(productId);
		product.setUserId(product.getUserId());
		product.setTypeAccount(product.getTypeAccount());
		product.setNumAccount(product.getNumAccount());
		product.setCreationDate(product.getCreationDate());
		product.setBalance(product.getBalance());
		if (product.getState().equals("activa")) {
			product.setState("inactiva");
		} else if (product.getState().equals("inactiva")){
			product.setState("activa");
		} else if (product.getState().equals("Cancelado")){
			product.setState("Cancelado");
		}
		
		return serviceProduct.changeState(product);
	}
	
	//Cambiar el estado para cancelar el producto
	@PutMapping("/{productId}/cancel")
	public Product cancelProduct(Product product, @PathVariable("productId") Long productId){
		product = ListIdOneProduct(productId);
		product.setproductId(productId);
		product.setUserId(product.getUserId());
		product.setTypeAccount(product.getTypeAccount());
		product.setNumAccount(product.getNumAccount());
		product.setCreationDate(product.getCreationDate());
		product.setBalance(product.getBalance());
		if (product.getBalance() != 0) {
			product.setState(product.getState());
		} else{
			product.setState("Cancelado");
		}
		
		return serviceProduct.changeState(product);
	}
	
	//Actualizar el saldo de un producto
	@PutMapping("/{productId}")
	public Product updateBalance(Product product, @PathVariable("productId") Long productId, int finalBalance) {
		product = ListIdOneProduct(productId);
		product.setBalance(finalBalance);
		return serviceProduct.updateBalance(product);
	}
	
	//Depositar dinero a una cuenta
	@PutMapping("/{productId}/{money}")
	public Product addMovement(Product product, @PathVariable("productId") Long productId, @PathVariable("money") int money) {
		product = ListIdOneProduct(productId);
		product.setproductId(productId);
		product.setUserId(product.getUserId());
		product.setTypeAccount(product.getTypeAccount());
		product.setNumAccount(product.getNumAccount());
		product.setCreationDate(product.getCreationDate());
		product.setState(product.getState());
		
		if (product.getBalance()>=0) {
			product.setBalance(product.getBalance() + money);
		}
		return serviceProduct.changeState(product);
	}
	
	//Retirar dinero de una cuenta
	@PutMapping("/{productId}/{money}/retirar")
	public Product withdrawMovement(Product product, @PathVariable("productId") Long productId, @PathVariable("money") int money) {
		product = ListIdOneProduct(productId);
		product.setproductId(productId);
		product.setUserId(product.getUserId());
		product.setTypeAccount(product.getTypeAccount());
		product.setNumAccount(product.getNumAccount());
		product.setCreationDate(product.getCreationDate());
		product.setState(product.getState());
		
		if (money > product.getBalance()) {
			product.setBalance(0);
		}else {
			product.setBalance(product.getBalance() - money);
		}
		
		return serviceProduct.changeState(product);
	}
	
}
