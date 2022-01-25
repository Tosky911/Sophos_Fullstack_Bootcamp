package com.sc.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sc.backend.entity.ProductEntity;
import com.sc.backend.entity.TransactionEntity;
import com.sc.backend.service.impl.ProductServiceImpl;
import com.sc.backend.service.impl.InterfaceTransactionService;

@CrossOrigin(origins = "http:localhost:4200")
@RestController
@RequestMapping("/users/{userId}/products/{productId}/transaction")
public class TransactionController {
	
	@Autowired
	InterfaceTransactionService serviceTransaction;
	
	@Autowired
	ProductServiceImpl serviceProduct;
	
	//Crear una transaccion
	@PostMapping("")
	@ResponseBody
	public TransactionEntity saveTransaction(@RequestBody TransactionEntity transactionEntity, @PathVariable("productId") Long productId) {
		ProductEntity productEntity = serviceProduct.listOneProductId(productId);
		transactionEntity.setPrincipalProductId(productId);
				
		//Si el producto esta cancelado
		if (productEntity.getState().equals("Producto cancelado")) {
			transactionEntity.setTransactionResult("Producto cancelado");
		}
		
		//Si el producto no esta cancelado
		else if (transactionEntity.getTransactionType().equals("Consignación")) {
			transactionEntity.setFinalBalance(productEntity.getBalance()+transactionEntity.getTransactionValue());
			transactionEntity.setTransactionResult("Efectiva");
			transactionEntity.setFinanceMovement("Credito");
		}
		
		//Si la transaccion es un retiro y el producto esta activo
		else if (transactionEntity.getTransactionType().equals("Retiro") && productEntity.getState().equals("activa")) {
			//Si la cuenta es de ahorros y el saldo menos el costo de la transacion (incluyendo GMF 0.4% ~ 4x1000) es mayor a 0
			if (productEntity.getTypeAccount().equals("ahorros") && productEntity.getBalance() - (1.004 * transactionEntity.getTransactionValue()) >=0) {
				
				//Creacion de la transaccion con GMF incluido
				
				TransactionEntity transactionGMF = new TransactionEntity();
				transactionGMF.setPrincipalProductId(productId);
				transactionGMF.setTransactionValue(-0.004 * transactionEntity.getTransactionValue());
				transactionGMF.setFinalBalance(productEntity.getBalance() - 0.004*transactionEntity.getTransactionValue());
				transactionGMF.setTransactionDetails("GMF 4 x 1000");
				transactionGMF.setTransactionResult("Efectiva");
				transactionGMF.setTransactionDate(transactionEntity.getTransactionDate());
				transactionGMF.setTransactionType("GMF");
				transactionGMF.setFinanceMovement("Debito");
				serviceTransaction.createTransaction(transactionGMF, productId);
				//Actualizar la transaccion
				transactionEntity.setFinalBalance(productEntity.getBalance()- (1.004 * transactionEntity.getTransactionValue()));
				transactionEntity.setTransactionValue(-transactionEntity.getTransactionValue());
				transactionEntity.setSecondaryProductId((long) 0);
				transactionEntity.setTransactionResult("Efectiva");
				transactionEntity.setFinanceMovement("Debito");
				transactionEntity.setGMF(-0.004 * transactionEntity.getTransactionValue());
				
			}
			//Si la cuenta es corriente y el saldo menos el costo de la transacion (incluyendo GMF 0.4% ~ 4x1000) es mayor a 2 millones
			else if (productEntity.getTypeAccount().equals("corriente") && productEntity.getBalance() - (1.004 * transactionEntity.getTransactionValue()) >= -2000000) {
				
				//Creacion de la transaccion con GMF incluido
				
				TransactionEntity transactionGMF = new TransactionEntity();
				transactionGMF.setPrincipalProductId(productId);
				transactionGMF.setTransactionValue(-0.004 * transactionEntity.getTransactionValue());
				transactionGMF.setFinalBalance(productEntity.getBalance() - 0.004*transactionEntity.getTransactionValue());
				transactionGMF.setTransactionDetails("GMF 4 x 1000");
				transactionGMF.setTransactionResult("Efectiva");
				transactionGMF.setTransactionDate(transactionEntity.getTransactionDate());
				transactionGMF.setTransactionType("GMF");
				transactionGMF.setFinanceMovement("Debito");
				serviceTransaction.createTransaction(transactionGMF, productId);
				
				//Actualizar la transaccion
				transactionEntity.setFinalBalance(productEntity.getBalance()- (1.004 * transactionEntity.getTransactionValue()));
				transactionEntity.setTransactionValue(-transactionEntity.getTransactionValue());
				transactionEntity.setSecondaryProductId(0L);
				transactionEntity.setTransactionResult("Efectiva");
				transactionEntity.setFinanceMovement("Debito");
				transactionEntity.setGMF(-0.004 * transactionEntity.getTransactionValue());
			}
			else {
				transactionEntity.setTransactionResult("Saldo insuficiente. El saldo disponible para hacer un retiro es: " + 0.996 * productEntity.getBalance());
				transactionEntity.setFinalBalance(productEntity.getBalance());
			}
			
		}
		
		//Si la transaccion es una transferencia y el producto esta activo
		else if(transactionEntity.getTransactionType().equals("Transferencia") && productEntity.getState().equals("activa")) {
			//Receptor de la transferencia
			ProductEntity productReceiver = serviceProduct.listOneProductId(transactionEntity.getSecondaryProductId());
			//Si la cuenta es de ahorros y el saldo menos el costo de la transacion (incluyendo GMF 0.4% ~ 4x1000) es mayor a 0
			if (productEntity.getTypeAccount().equals("ahorros") && productEntity.getBalance() - (1.004 * transactionEntity.getTransactionValue()) >=0 ) {
				transactionEntity.setSecondaryProductId(transactionEntity.getSecondaryProductId());
				
				//Creacion de la transaccion con GMF
				TransactionEntity transactionGMF = new TransactionEntity();
				transactionGMF.setPrincipalProductId(productId);
				transactionGMF.setTransactionValue(-0.004 * transactionEntity.getTransactionValue());
				transactionGMF.setFinalBalance(productEntity.getBalance() - 0.004*transactionEntity.getTransactionValue());
				transactionGMF.setTransactionDetails("GMF 4 x 1000");
				transactionGMF.setTransactionResult("Efectiva");
				transactionGMF.setTransactionDate(transactionEntity.getTransactionDate());
				transactionGMF.setTransactionType("GMF");
				transactionGMF.setFinanceMovement("Debito");
				serviceTransaction.createTransaction(transactionGMF, productId);
				
				//Actualizacion de la transaccion
				transactionEntity.setFinalBalance(productEntity.getBalance()- (1.004 * transactionEntity.getTransactionValue()));
				transactionEntity.setTransactionValue(-transactionEntity.getTransactionValue());
				transactionEntity.setSecondaryProductId((long) 0);
				transactionEntity.setTransactionResult("Efectiva");
				transactionEntity.setFinanceMovement("Debito");
				transactionEntity.setGMF(-0.004 * transactionEntity.getTransactionValue());
				
				//Creacion de la transaccion receptora
				TransactionEntity transactionReceiver = new TransactionEntity();
				transactionReceiver.setPrincipalProductId(transactionEntity.getSecondaryProductId());
				transactionReceiver.setTransactionValue(-transactionEntity.getTransactionValue());
				transactionReceiver.setFinalBalance(productReceiver.getBalance() - transactionEntity.getTransactionValue());
				transactionReceiver.setTransactionDetails("Recepcion de la transferencia " + transactionEntity.getTransactionDetails() + " desde producto: " + transactionEntity.getPrincipalProductId());
				transactionReceiver.setTransactionResult("Efectiva");
				transactionReceiver.setTransactionDate(transactionEntity.getTransactionDate());
				transactionReceiver.setTransactionType("Recibido por transferencia");
				transactionReceiver.setSecondaryProductId(transactionEntity.getPrincipalProductId());
				transactionReceiver.setFinanceMovement("Credito");
				serviceTransaction.createTransaction(transactionReceiver, transactionEntity.getSecondaryProductId());
				
				//Actualizar el saldo en el producto Emisor/Enviador
				productReceiver.setBalance(transactionReceiver.getFinalBalance());
				serviceProduct.updateBalance(productReceiver);
			}
			//Si la cuenta es corriente y el saldo menos el costo de la transacion (incluyendo GMF 0.4% ~ 4x1000) es mayor a 2 millones
			else if (productEntity.getTypeAccount().equals("corriente") && productEntity.getBalance() - (1.004 * transactionEntity.getTransactionValue()) >= -2000000) {
				transactionEntity.setSecondaryProductId(transactionEntity.getSecondaryProductId());
				
				//Creacion de la transaccion con GMF
				TransactionEntity transactionGMF = new TransactionEntity();
				transactionGMF.setPrincipalProductId(productId);
				transactionGMF.setTransactionValue(-0.004 * transactionEntity.getTransactionValue());
				transactionGMF.setFinalBalance(productEntity.getBalance() - 0.004*transactionEntity.getTransactionValue());
				transactionGMF.setTransactionDetails("GMF 4 x 1000");
				transactionGMF.setTransactionResult("Efectiva");
				transactionGMF.setTransactionDate(transactionEntity.getTransactionDate());
				transactionGMF.setTransactionType("GMF");
				transactionGMF.setFinanceMovement("Debito");
				serviceTransaction.createTransaction(transactionGMF, productId);
				
				//Actualizacion de la transaccion
				transactionEntity.setFinalBalance(productEntity.getBalance()- (1.004 * transactionEntity.getTransactionValue()));
				transactionEntity.setTransactionValue(-transactionEntity.getTransactionValue());
				transactionEntity.setSecondaryProductId((long) 0);
				transactionEntity.setTransactionResult("Efectiva");
				transactionEntity.setFinanceMovement("Debito");
				transactionEntity.setGMF(-0.004 * transactionEntity.getTransactionValue());
				
				//Creacion de la transaccion receptora
				TransactionEntity transactionReceiver = new TransactionEntity();
				transactionReceiver.setPrincipalProductId(transactionEntity.getSecondaryProductId());
				transactionReceiver.setTransactionValue(-transactionEntity.getTransactionValue());
				transactionReceiver.setFinalBalance(productReceiver.getBalance() - transactionEntity.getTransactionValue());
				transactionReceiver.setTransactionDetails("Recepcion de la transferencia " + transactionEntity.getTransactionDetails() + " desde producto: " + transactionEntity.getPrincipalProductId());
				transactionReceiver.setTransactionResult("Efectiva");
				transactionReceiver.setTransactionDate(transactionEntity.getTransactionDate());
				transactionReceiver.setTransactionType("Recibido por transferencia");
				transactionReceiver.setSecondaryProductId(transactionEntity.getPrincipalProductId());
				transactionReceiver.setFinanceMovement("Credito");
				serviceTransaction.createTransaction(transactionReceiver, transactionEntity.getSecondaryProductId());
				
				//Actualizar el saldo en el producto Emisor/Enviador
				productReceiver.setBalance(transactionReceiver.getFinalBalance());
				serviceProduct.updateBalance(productReceiver);
				
			}
			else {
				transactionEntity.setTransactionResult("Saldo insuficiente. El saldo disponible para la transferencia es: " + 0.996*productEntity.getBalance());
				transactionEntity.setFinalBalance(productEntity.getBalance());
			}
		}
		
		else {
			transactionEntity.setTransactionResult("Cuenta inactiva");
			transactionEntity.setFinalBalance(productEntity.getBalance());
		}
		
		productEntity.setBalance(transactionEntity.getFinalBalance());
		serviceProduct.updateBalance(productEntity);
		
		return serviceTransaction.createTransaction(transactionEntity, productId);
	}
	
	//Obtener la transaccion por cuenta, por usuario
	@GetMapping("")
	public List<TransactionEntity> listTransactionId(@PathVariable("productId") Long principalProductId){
		return serviceTransaction.listTransactionId(principalProductId);
	}
	
}
