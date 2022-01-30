package com.sc.backend.controller;

import java.util.List;
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

import com.sc.backend.entity.ProductEntity;
import com.sc.backend.entity.TransactionEntity;
import com.sc.backend.model.GeneralResponse;
import com.sc.backend.service.ProductService;
import com.sc.backend.service.TransactionService;

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
			
			data = productService.findAll(customerId);
			
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
	public ResponseEntity<GeneralResponse<ProductEntity>> addProduct(@RequestBody ProductEntity productEntity, @PathVariable("customerId") Long customerId, TransactionEntity transactionEntity ) {
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
			data = productService.save(productEntity);
			
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
			status = HttpStatus.CREATED;
			
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
	public ResponseEntity<GeneralResponse<ProductEntity>> deactivateProduct(@RequestBody ProductEntity productEntity, @PathVariable("productId") Long productId) {
		
		GeneralResponse<ProductEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		ProductEntity product = null;

		try {
			product = productService.findById(productId);
			
			if(product == null) {
				response.setMessageResult("Product wasn't found.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				if (product.getState().equals("activa")) {
					
					product.setState("inactiva");
					productService.save(product);
					response.setMessageResult("Product with Id: "+ product.getproductId() + " was deactivated.");
					response.setCodeError(3);
					} else {
						response.setMessageResult("Product can't be deactivated");
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
	public ResponseEntity<GeneralResponse<ProductEntity>> activateProduct(@RequestBody ProductEntity productEntity, @PathVariable("productId") Long productId) {
		
		GeneralResponse<ProductEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		ProductEntity product = null;

		try {
			product = productService.findById(productId);
			
			if(product == null) {
				response.setMessageResult("Product wasn't found.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				if (product.getState().equals("inactiva")) {
					
					product.setState("activa");
					productService.save(product);
					response.setMessageResult("Product with Id: "+ product.getproductId() + " was activated.");
					response.setCodeError(3);
					} else {
						response.setMessageResult("Product can't be activated");
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
	public ResponseEntity<GeneralResponse<ProductEntity>> cancelProduct(@RequestBody ProductEntity productEntity, @PathVariable("productId") Long productId){
		
		GeneralResponse<ProductEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		ProductEntity product = null;
				
		try {
			
			product = productService.findById(productId);
			
			//Si el producto viene vacio
			if(product == null){
				response.setMessageResult("Product can't be cancelled.");
				response.setCodeError(1);
				response.setData(null);
			//Si el producto aun tiene saldo
			}else if(product.getBalance() != 0){
				response.setMessageResult("Product cannot be canceled because it still has a balance.");
				response.setCodeError(1);
				response.setData(null);
			//Si hay datos y el saldo es 
			}else if(product.getState().equals("Cancelado")){
				response.setMessageResult("Product cannot be canceled because it's already cancelled.");
				response.setCodeError(1);
				response.setData(null);
			} else {
				product.setState("Cancelado");
				productService.save(product);
				response.setMessageResult("Product with Id " + productId +" was cancelled");
				response.setCodeError(3);
			}
			
			response.setMessage("Successful Account Cancellation");
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
	
	//Descancelar la cuenta
	@PutMapping("/{productId}/uncancel")
	public ResponseEntity<GeneralResponse<ProductEntity>> uncancelProduct(@RequestBody ProductEntity productEntity, @PathVariable("productId") Long productId){
		
		GeneralResponse<ProductEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		ProductEntity product = null;
				
		try {
			
			product = productService.findById(productId);
			
			//Si el producto viene vacio
			if(product == null){
				response.setMessageResult("Product can't be uncanceled.");
				response.setCodeError(1);
				response.setData(null);
				
			}else if(product.getState().equals("activa") || product.getState().equals("inactiva")){
				response.setMessageResult("Product cannot be uncanceled because it's not cancelled.");
				response.setCodeError(1);
				response.setData(null);
			} else {
				product.setState("activa");
				productService.save(product);
				response.setMessageResult("Product with Id " + productId +" was uncanceled");
				response.setCodeError(3);
			}
			
			response.setMessage("Successful Account Reactivation");
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
	
	//Actualizar una cuenta
	@PutMapping("/{productId}")
	public ResponseEntity<GeneralResponse<ProductEntity>> updateProduct(@RequestBody ProductEntity productEntity, @PathVariable("productId") Long productId) {
		
		GeneralResponse<ProductEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		ProductEntity product= null;
		
		try {
			product = productService.save(productEntity);
			
			if (product == null) {
				response.setMessageResult("Unable to update product.");
				response.setCodeError(1);
				response.setData(null);
			}else if(product.getState().equalsIgnoreCase("Cancelado")) {
				response.setMessageResult("Unable to update product because it is cancelled.");
				response.setCodeError(1);
				response.setData(null);
			}else {
				response.setMessageResult("Product with Id: " + productId + " was updated.");
				response.setCodeError(0);
			}
			
			response.setMessage("Successful Update");
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
	
	//Depositar dinero a una cuenta
	@PutMapping("/{productId}/{money}/deposit")
	public ResponseEntity<GeneralResponse<ProductEntity>> addMovement(@RequestBody ProductEntity productEntity, @PathVariable("productId") Long productId, @PathVariable("money") int money) {
		
		GeneralResponse<ProductEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		ProductEntity product = null;
	
		try {
			product =  productService.findById(productId);
			
			if(product == null) {
				response.setMessageResult("Unable to deposit money to the product");
				response.setCodeError(1);
				response.setData(null);
			}else {
				if (productEntity.getBalance()>=0) {
					response.setMessageResult("The deposit of: " + money +" to product with Id: "+ productId +" was made.");
					productEntity.setBalance(productEntity.getBalance() + money);
					productService.save(productEntity);
				}

			}
			response.setMessage("Successful Account Deposit");
			response.setSuccess(true);
			response.setData(product);
			status = HttpStatus.OK;

		} catch (Exception e) {

		}
		
		return new ResponseEntity<>(response, status);
	}
	
	//Retirar dinero de una cuenta
	@PutMapping("/{productId}/{money}/withdraw")
	public ResponseEntity<GeneralResponse<ProductEntity>> withdrawMovement(@RequestBody ProductEntity productEntity, @PathVariable("productId") Long productId, @PathVariable("money") double money) {
		
		GeneralResponse<ProductEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		ProductEntity product = null;
		
		try {
			product = productService.findById(productId);
			
			if(product == null) {
				response.setMessageResult("Unable to withdraw money from the product");
				response.setCodeError(1);
				response.setData(null);
			} else {
				if (money > productEntity.getBalance()) {
					productEntity.setBalance(0);
					response.setMessageResult("There is not enough balance in the product to make the entire withdrawal");
					response.setCodeError(2);
				}else {
					productEntity.setBalance(productEntity.getBalance() - money);
					response.setMessageResult("The withdraw of: " + money + " from product with Id: "+ productId +" was made.");
					response.setCodeError(0);
					productService.save(productEntity);
				}

			}
			
			response.setMessage("Successful Account Withdrawal");
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
	
}
