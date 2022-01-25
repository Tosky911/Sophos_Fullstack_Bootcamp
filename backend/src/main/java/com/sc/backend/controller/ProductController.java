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

import com.sc.backend.entity.ProductEntity;
import com.sc.backend.entity.TransactionEntity;
import com.sc.backend.service.impl.ProductServiceImpl;
import com.sc.backend.service.impl.TransactionServiceImpl;

@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping("/users/{userId}/products")
public class ProductController {
	
	@Autowired
	ProductServiceImpl serviceProduct;
	
	@Autowired
	TransactionServiceImpl serviceTransaction;
	
	//Alistar los productos del usuario
	@GetMapping("")
	public List<ProductEntity> listIdProduct(@PathVariable("userId") Long userId){
		return serviceProduct.listProductId(userId);
	}
	
	//Lista de los productos diferentes al seleccionado
	@GetMapping("/{productId}/different")
	public List<ProductEntity> listIdOtherAvailableProducts(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId){
		return serviceProduct.listIdOtherAvailableProducts(userId,productId);
	}
	
	//Crear un nuevo producto para un cliente
	@PostMapping("")
	@ResponseBody
	public ProductEntity save(@RequestBody ProductEntity productEntity, @PathVariable("userId") Long userId, TransactionEntity transactionEntity ) {
		productEntity.setUserId(userId);
		
		transactionEntity.setPrincipalProductId(productEntity.getproductId());
		transactionEntity.setTransactionDetails("Creacion producto");
		transactionEntity.setTransactionResult("Efectiva");
		transactionEntity.setFinalBalance(0);
		transactionEntity.setTransactionValue(0);
		transactionEntity.setTransactionType("Creacion cuenta");
		transactionEntity.setTransactionDate(transactionEntity.getTransactionDate());
		serviceTransaction.createTransaction(transactionEntity, productEntity.getproductId());
		return serviceProduct.addProduct(productEntity, userId);
	}
	
	//Obtener un producto de un usuario
	@GetMapping("/{productId}")
	public ProductEntity ListIdOneProduct(@PathVariable("productId") Long productId){
		return serviceProduct.listOneProductId(productId);
	}
	
	//Cambiar el estado a activo o inactivo
	@PutMapping("/{productId}/changeState")
	public ProductEntity changeState(ProductEntity productEntity, @PathVariable("productId") Long productId){
		productEntity = ListIdOneProduct(productId);
		productEntity.setproductId(productId);
		productEntity.setUserId(productEntity.getUserId());
		productEntity.setTypeAccount(productEntity.getTypeAccount());
		productEntity.setNumAccount(productEntity.getNumAccount());
		productEntity.setCreationDate(productEntity.getCreationDate());
		productEntity.setBalance(productEntity.getBalance());
		if (productEntity.getState().equals("activa")) {
			productEntity.setState("inactiva");
		} else if (productEntity.getState().equals("inactiva")){
			productEntity.setState("activa");
		} else if (productEntity.getState().equals("Cancelado")){
			productEntity.setState("Cancelado");
		}
		
		return serviceProduct.changeState(productEntity);
	}
	
	//Cambiar el estado para cancelar el producto
	@PutMapping("/{productId}/cancel")
	public ProductEntity cancelProduct(ProductEntity productEntity, @PathVariable("productId") Long productId){
		productEntity = ListIdOneProduct(productId);
		productEntity.setproductId(productId);
		productEntity.setUserId(productEntity.getUserId());
		productEntity.setTypeAccount(productEntity.getTypeAccount());
		productEntity.setNumAccount(productEntity.getNumAccount());
		productEntity.setCreationDate(productEntity.getCreationDate());
		productEntity.setBalance(productEntity.getBalance());
		if (productEntity.getBalance() != 0) {
			productEntity.setState(productEntity.getState());
		} else{
			productEntity.setState("Cancelado");
		}
		
		return serviceProduct.changeState(productEntity);
	}
	
	//Actualizar el saldo de un producto
	@PutMapping("/{productId}")
	public ProductEntity updateBalance(ProductEntity productEntity, @PathVariable("productId") Long productId, int finalBalance) {
		productEntity = ListIdOneProduct(productId);
		productEntity.setBalance(finalBalance);
		return serviceProduct.updateBalance(productEntity);
	}
	
	//Depositar dinero a una cuenta
	@PutMapping("/{productId}/{money}")
	public ProductEntity addMovement(ProductEntity productEntity, @PathVariable("productId") Long productId, @PathVariable("money") int money) {
		productEntity = ListIdOneProduct(productId);
		productEntity.setproductId(productId);
		productEntity.setUserId(productEntity.getUserId());
		productEntity.setTypeAccount(productEntity.getTypeAccount());
		productEntity.setNumAccount(productEntity.getNumAccount());
		productEntity.setCreationDate(productEntity.getCreationDate());
		productEntity.setState(productEntity.getState());
		
		if (productEntity.getBalance()>=0) {
			productEntity.setBalance(productEntity.getBalance() + money);
		}
		return serviceProduct.changeState(productEntity);
	}
	
	//Retirar dinero de una cuenta
	@PutMapping("/{productId}/{money}/retirar")
	public ProductEntity withdrawMovement(ProductEntity productEntity, @PathVariable("productId") Long productId, @PathVariable("money") int money) {
		productEntity = ListIdOneProduct(productId);
		productEntity.setproductId(productId);
		productEntity.setUserId(productEntity.getUserId());
		productEntity.setTypeAccount(productEntity.getTypeAccount());
		productEntity.setNumAccount(productEntity.getNumAccount());
		productEntity.setCreationDate(productEntity.getCreationDate());
		productEntity.setState(productEntity.getState());
		
		if (money > productEntity.getBalance()) {
			productEntity.setBalance(0);
		}else {
			productEntity.setBalance(productEntity.getBalance() - money);
		}
		
		return serviceProduct.changeState(productEntity);
	}
	
}
