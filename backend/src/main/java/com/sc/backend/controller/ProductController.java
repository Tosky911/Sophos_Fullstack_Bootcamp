package com.sc.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sc.backend.entity.CustomerEntity;
import com.sc.backend.entity.ProductEntity;
import com.sc.backend.entity.TransactionEntity;
import com.sc.backend.model.GeneralResponse;
import com.sc.backend.service.ProductService;
import com.sc.backend.service.TransactionService;
import com.sc.backend.service.impl.ProductServiceImpl;
import com.sc.backend.service.impl.TransactionServiceImpl;

@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping("/customers/{customerId}/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	TransactionService transactionService;
	
	//Listar los productos del cliente
	@GetMapping("")
	public ResponseEntity<GeneralResponse<List<ProductEntity>>> listProductId(@PathVariable("customerId") Long customerId){
		
		GeneralResponse<List<ProductEntity>> response = new GeneralResponse<>();
		HttpStatus status = null;
		List<ProductEntity> data = null;
		
		try {
			
			data = productService.listProductId(customerId);
			
			if(data == null || data.size() == 0) {
				response.setMessageResult( "0 products were found.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult(data.size()+ " products were found.");
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
	
	//Lista de los productos/cuentas diferentes al seleccionado
	@GetMapping("/{productId}/different")
	public ResponseEntity<GeneralResponse<List<ProductEntity>>> listIdOtherAvailableProducts(@PathVariable("customerId") Long customerId, @PathVariable("productId") Long productId){
		
		GeneralResponse<List<ProductEntity>> response = new GeneralResponse<>();
		HttpStatus status = null;
		List<ProductEntity> data = null;
		
		try {
			data = productService.listIdOtherAvailableProducts(customerId,productId);
			
			if(data == null || data.size() == 0) {
				response.setMessageResult( "0 products were found.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult(data.size()+ " products were found.");
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
	
	//Crear un nuevo producto para un cliente
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<GeneralResponse<ProductEntity>> save(@RequestBody ProductEntity productEntity, @PathVariable("customerId") Long customerId, TransactionEntity transactionEntity ) {
		//Registro de la transaccion para la creacion de un producto en un cliente
		productEntity.setCustomerId(customerId);
		transactionEntity.setPrincipalProductId(productEntity.getproductId());
		transactionEntity.setTransactionDetails("Creacion producto");
		transactionEntity.setTransactionResult("Efectiva");
		transactionEntity.setFinalBalance(0);
		transactionEntity.setTransactionValue(0);
		transactionEntity.setTransactionType("Creacion cuenta");
		transactionEntity.setTransactionDate(transactionEntity.getTransactionDate());
		transactionService.createTransaction(transactionEntity, productEntity.getproductId());
		
		GeneralResponse<ProductEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		ProductEntity data = null; 
		
		try {
			data = productService.addProduct(productEntity);
			
			if(data == null || data.getCustomerId() == null){
				response.setMessageResult("New product "+ productEntity.getproductId() + " wasn't created and assigned to customer: " + customerId);
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult("New product "+ productEntity.getproductId() + " was created and assigned to customer: " + customerId);
				response.setCodeError(0);
			}
			
			response.setMessage("Successful Save");
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
	
	//Obtener un producto especifico de un cliente
	@GetMapping("/{productId}")
	public ResponseEntity<GeneralResponse<ProductEntity>> listByCustomerIdAndProductId(@PathVariable("customerId") Long customerId, @PathVariable("productId") Long productId){
		GeneralResponse<ProductEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		ProductEntity product;
		
		try {
			product = productService.findByCustomerIdAndProductId(customerId, productId);
			
			if(product == null || product.getproductId() == null) {
				response.setMessageResult("Product or customer wasn't found.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult("Product " + product.getproductId() +" was found.");
				response.setCodeError(0);
			}
			response.setMessage("Successful Query");
			response.setSuccess(true);
			response.setData(product);
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage("Something has failed. Error: "+ e.getLocalizedMessage()  +". Please contact support.");
			response.setSuccess(false);
		}
		
		return new ResponseEntity<>(response, status);
	}
	
	//Desactivar la cuenta
	@PutMapping("/{productId}/deactivate")
	public ResponseEntity<GeneralResponse<ProductEntity>> deactivateProduct(ProductEntity productEntity, @PathVariable("productId") Long productId) {
		
		GeneralResponse<ProductEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		ProductEntity product = null;

		try {
			product = productService.listOneProductId(productId);
			
			if(product == null) {
				response.setMessageResult("Product wasn't found.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				if (product.getState().equals("activa")) {
					
					product.setState("inactiva");
					productService.addProduct(productEntity);
					response.setMessageResult("Product with Id: "+ product.getproductId() + " was deactivated.");
					response.setCodeError(3);
					} else {
						response.setMessageResult("Product can't be deactivated because it's canceled");
						response.setCodeError(1);			
					}
			}
			
			response.setMessage("Successful Account Deactivation.");
			response.setSuccess(true);
			response.setData(product);
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage("Something has failed. Error: "+ e.getLocalizedMessage()  +" . Please contact support.");
			response.setSuccess(false);
		}
		return new ResponseEntity<>(response, status);
	}
	
	//Activar la cuenta
	@PutMapping("/{productId}/activate")
	public ResponseEntity<GeneralResponse<ProductEntity>> activateProduct(ProductEntity productEntity, @PathVariable("productId") Long productId) {
		
		GeneralResponse<ProductEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		ProductEntity product = null;

		try {
			product = productService.listOneProductId(productId);
			
			if(product == null) {
				response.setMessageResult("Product wasn't found.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				if (product.getState().equals("inactiva")) {
					
					product.setState("activa");
					productService.addProduct(productEntity);
					response.setMessageResult("Product with Id: "+ product.getproductId() + " was ctivated.");
					response.setCodeError(3);
					} else {
						response.setMessageResult("Product can't be activated because it's canceled");
						response.setCodeError(1);			
					}
			}
			
			response.setMessage("Successful Account Activation.");
			response.setSuccess(true);
			response.setData(product);
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage("Something has failed. Error: "+ e.getLocalizedMessage()  +" . Please contact support.");
			response.setSuccess(false);
		}
		return new ResponseEntity<>(response, status);
	}
	
	//Cancelar la cuenta
	@PutMapping("/{productId}/cancel")
	public ResponseEntity<GeneralResponse<ProductEntity>> cancelProduct(ProductEntity productEntity, @PathVariable("productId") Long productId){
		
		GeneralResponse<ProductEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		ProductEntity product = null;
				
		//Si la cuenta tiene saldo no se puede Cancelar
		if (productEntity.getBalance() != 0) {
			productEntity.setState(productEntity.getState());
		} else{
			productEntity.setState("Cancelado");
		}
		
		try {
			product = productService.changeState(productEntity);
			
			if(product == null){
				response.setMessageResult("Customer wasn't found.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult("Product with Id " + productId +" was canceled");
				response.setCodeError(0);
			}
			
			response.setMessage("Successful Query");
			response.setSuccess(true);
			response.setData(product);
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMessage("Something has failed. Error: "+ e.getLocalizedMessage()  +" . Please contact support.");
			response.setSuccess(false);
		}
		
		return new ResponseEntity<>(response, status);
	}
	
	//Actualizar el saldo de un producto
	
//	@PutMapping("/{productId}")
//	public ProductEntity updateBalance(ProductEntity productEntity, @PathVariable("productId") Long productId, int finalBalance) {
//		productEntity = listByCustomerIdAndProductId(productId);
//		productEntity.setBalance(finalBalance);
//		return productService.updateBalance(productEntity);
//	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<GeneralResponse<ProductEntity>> updateBalance(ProductEntity productEntity, @PathVariable("productId") Long productId, int finalBalance) {
		
		productEntity = listByCustomerIdAndProductId(productId);
		productEntity.setBalance(finalBalance);
		
		GeneralResponse<ProductEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		ProductEntity product= null;
		
		product =  productService.updateBalance(productEntity);

		return new ResponseEntity<>(response, status);
	}
	
	//Depositar dinero a una cuenta
//	@PutMapping("/{productId}/{money}")
//	public ProductEntity addMovement(ProductEntity productEntity, @PathVariable("productId") Long productId, @PathVariable("money") int money) {
//		productEntity = listByCustomerIdAndProductId(productId);
//		productEntity.setproductId(productId);
//		productEntity.setCustomerId(productEntity.getCustomerId());
//		productEntity.setTypeAccount(productEntity.getTypeAccount());
//		productEntity.setNumAccount(productEntity.getNumAccount());
//		productEntity.setCreationDate(productEntity.getCreationDate());
//		productEntity.setState(productEntity.getState());
//		
//		if (productEntity.getBalance()>=0) {
//			productEntity.setBalance(productEntity.getBalance() + money);
//		}
//		return productService.changeState(productEntity);
//	}
	
	@PutMapping("/{productId}/{money}")
	public ProductEntity addMovement(ProductEntity productEntity, @PathVariable("productId") Long productId, @PathVariable("money") int money) {
		productEntity = listByCustomerIdAndProductId(productId);
		productEntity.setproductId(productId);
		productEntity.setCustomerId(productEntity.getCustomerId());
		productEntity.setTypeAccount(productEntity.getTypeAccount());
		productEntity.setNumAccount(productEntity.getNumAccount());
		productEntity.setCreationDate(productEntity.getCreationDate());
		productEntity.setState(productEntity.getState());
		
		if (productEntity.getBalance()>=0) {
			productEntity.setBalance(productEntity.getBalance() + money);
		}
		return productService.changeState(productEntity);
	}
	
	//Retirar dinero de una cuenta
	@PutMapping("/{productId}/{money}/withdraw")
	public ProductEntity withdrawMovement(ProductEntity productEntity, @PathVariable("productId") Long productId, @PathVariable("money") int money) {
		productEntity = listByCustomerIdAndProductId(productId);
		productEntity.setproductId(productId);
		productEntity.setCustomerId(productEntity.getCustomerId());
		productEntity.setTypeAccount(productEntity.getTypeAccount());
		productEntity.setNumAccount(productEntity.getNumAccount());
		productEntity.setCreationDate(productEntity.getCreationDate());
		productEntity.setState(productEntity.getState());
		
		if (money > productEntity.getBalance()) {
			productEntity.setBalance(0);
		}else {
			productEntity.setBalance(productEntity.getBalance() - money);
		}
		
		return productService.changeState(productEntity);
	}
	
}
