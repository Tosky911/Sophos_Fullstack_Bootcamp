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
import com.sc.backend.entity.Transaction;
import com.sc.backend.interfaceService.InterfaceProductService;
import com.sc.backend.interfaceService.InterfaceTransactionService;

@CrossOrigin(origins = "http:localhost:4200")
@RestController
@RequestMapping("/users/{userId}/products/{productId}/transaction")
public class TransactionController {
	
	@Autowired
	InterfaceTransactionService serviceTransaction;
	
	@Autowired
	InterfaceProductService serviceProduct;
	
	//Crear una transaccion
	@PostMapping("")
	@ResponseBody
	public Transaction saveTransaction(@RequestBody Transaction transaction, @PathVariable("productId") Long productId) {
		ProductEntity productEntity = serviceProduct.listOneProductId(productId);
		transaction.setPrincipalProductId(productId);
				
		//Si el producto esta cancelado
		if (productEntity.getState().equals("Producto cancelado")) {
			transaction.setTransactionResult("Producto cancelado");
		}
		
		//Si el producto no esta cancelado
		else if (transaction.getTransactionType().equals("Consignación")) {
			transaction.setFinalBalance(productEntity.getBalance()+transaction.getTransactionValue());
			transaction.setTransactionResult("Efectiva");
			transaction.setFinanceMovement("Credito");
		}
		
		//Si la transaccion es un retiro y el producto esta activo
		else if (transaction.getTransactionType().equals("Retiro") && productEntity.getState().equals("activa")) {
			//Si la cuenta es de ahorros y el saldo menos el costo de la transacion (incluyendo GMF 0.4% ~ 4x1000) es mayor a 0
			if (productEntity.getTypeAccount().equals("ahorros") && productEntity.getBalance() - (1.004 * transaction.getTransactionValue()) >=0) {
				
				//Creacion de la transaccion con GMF incluido
				
				Transaction transactionGMF = new Transaction();
				transactionGMF.setPrincipalProductId(productId);
				transactionGMF.setTransactionValue(-0.004 * transaction.getTransactionValue());
				transactionGMF.setFinalBalance(productEntity.getBalance() - 0.004*transaction.getTransactionValue());
				transactionGMF.setTransactionDetails("GMF 4 x 1000");
				transactionGMF.setTransactionResult("Efectiva");
				transactionGMF.setTransactionDate(transaction.getTransactionDate());
				transactionGMF.setTransactionType("GMF");
				transactionGMF.setFinanceMovement("Debito");
				serviceTransaction.createTransaction(transactionGMF, productId);
				//Actualizar la transaccion
				transaction.setFinalBalance(productEntity.getBalance()- (1.004 * transaction.getTransactionValue()));
				transaction.setTransactionValue(-transaction.getTransactionValue());
				transaction.setSecondaryProductId((long) 0);
				transaction.setTransactionResult("Efectiva");
				transaction.setFinanceMovement("Debito");
				transaction.setGMF(-0.004 * transaction.getTransactionValue());
				
			}
			//Si la cuenta es corriente y el saldo menos el costo de la transacion (incluyendo GMF 0.4% ~ 4x1000) es mayor a 2 millones
			else if (productEntity.getTypeAccount().equals("corriente") && productEntity.getBalance() - (1.004 * transaction.getTransactionValue()) >= -2000000) {
				
				//Creacion de la transaccion con GMF incluido
				
				Transaction transactionGMF = new Transaction();
				transactionGMF.setPrincipalProductId(productId);
				transactionGMF.setTransactionValue(-0.004 * transaction.getTransactionValue());
				transactionGMF.setFinalBalance(productEntity.getBalance() - 0.004*transaction.getTransactionValue());
				transactionGMF.setTransactionDetails("GMF 4 x 1000");
				transactionGMF.setTransactionResult("Efectiva");
				transactionGMF.setTransactionDate(transaction.getTransactionDate());
				transactionGMF.setTransactionType("GMF");
				transactionGMF.setFinanceMovement("Debito");
				serviceTransaction.createTransaction(transactionGMF, productId);
				
				//Actualizar la transaccion
				transaction.setFinalBalance(productEntity.getBalance()- (1.004 * transaction.getTransactionValue()));
				transaction.setTransactionValue(-transaction.getTransactionValue());
				transaction.setSecondaryProductId(0L);
				transaction.setTransactionResult("Efectiva");
				transaction.setFinanceMovement("Debito");
				transaction.setGMF(-0.004 * transaction.getTransactionValue());
			}
			else {
				transaction.setTransactionResult("Saldo insuficiente. El saldo disponible para hacer un retiro es: " + 0.996 * productEntity.getBalance());
				transaction.setFinalBalance(productEntity.getBalance());
			}
			
		}
		
		//Si la transaccion es una transferencia y el producto esta activo
		else if(transaction.getTransactionType().equals("Transferencia") && productEntity.getState().equals("activa")) {
			//Receptor de la transferencia
			ProductEntity productReceiver = serviceProduct.listOneProductId(transaction.getSecondaryProductId());
			//Si la cuenta es de ahorros y el saldo menos el costo de la transacion (incluyendo GMF 0.4% ~ 4x1000) es mayor a 0
			if (productEntity.getTypeAccount().equals("ahorros") && productEntity.getBalance() - (1.004 * transaction.getTransactionValue()) >=0 ) {
				transaction.setSecondaryProductId(transaction.getSecondaryProductId());
				
				//Creacion de la transaccion con GMF
				Transaction transactionGMF = new Transaction();
				transactionGMF.setPrincipalProductId(productId);
				transactionGMF.setTransactionValue(-0.004 * transaction.getTransactionValue());
				transactionGMF.setFinalBalance(productEntity.getBalance() - 0.004*transaction.getTransactionValue());
				transactionGMF.setTransactionDetails("GMF 4 x 1000");
				transactionGMF.setTransactionResult("Efectiva");
				transactionGMF.setTransactionDate(transaction.getTransactionDate());
				transactionGMF.setTransactionType("GMF");
				transactionGMF.setFinanceMovement("Debito");
				serviceTransaction.createTransaction(transactionGMF, productId);
				
				//Actualizacion de la transaccion
				transaction.setFinalBalance(productEntity.getBalance()- (1.004 * transaction.getTransactionValue()));
				transaction.setTransactionValue(-transaction.getTransactionValue());
				transaction.setSecondaryProductId((long) 0);
				transaction.setTransactionResult("Efectiva");
				transaction.setFinanceMovement("Debito");
				transaction.setGMF(-0.004 * transaction.getTransactionValue());
				
				//Creacion de la transaccion receptora
				Transaction transactionReceiver = new Transaction();
				transactionReceiver.setPrincipalProductId(transaction.getSecondaryProductId());
				transactionReceiver.setTransactionValue(-transaction.getTransactionValue());
				transactionReceiver.setFinalBalance(productReceiver.getBalance() - transaction.getTransactionValue());
				transactionReceiver.setTransactionDetails("Recepcion de la transferencia " + transaction.getTransactionDetails() + " desde producto: " + transaction.getPrincipalProductId());
				transactionReceiver.setTransactionResult("Efectiva");
				transactionReceiver.setTransactionDate(transaction.getTransactionDate());
				transactionReceiver.setTransactionType("Recibido por transferencia");
				transactionReceiver.setSecondaryProductId(transaction.getPrincipalProductId());
				transactionReceiver.setFinanceMovement("Credito");
				serviceTransaction.createTransaction(transactionReceiver, transaction.getSecondaryProductId());
				
				//Actualizar el saldo en el producto Emisor/Enviador
				productReceiver.setBalance(transactionReceiver.getFinalBalance());
				serviceProduct.updateBalance(productReceiver);
			}
			//Si la cuenta es corriente y el saldo menos el costo de la transacion (incluyendo GMF 0.4% ~ 4x1000) es mayor a 2 millones
			else if (productEntity.getTypeAccount().equals("corriente") && productEntity.getBalance() - (1.004 * transaction.getTransactionValue()) >= -2000000) {
				transaction.setSecondaryProductId(transaction.getSecondaryProductId());
				
				//Creacion de la transaccion con GMF
				Transaction transactionGMF = new Transaction();
				transactionGMF.setPrincipalProductId(productId);
				transactionGMF.setTransactionValue(-0.004 * transaction.getTransactionValue());
				transactionGMF.setFinalBalance(productEntity.getBalance() - 0.004*transaction.getTransactionValue());
				transactionGMF.setTransactionDetails("GMF 4 x 1000");
				transactionGMF.setTransactionResult("Efectiva");
				transactionGMF.setTransactionDate(transaction.getTransactionDate());
				transactionGMF.setTransactionType("GMF");
				transactionGMF.setFinanceMovement("Debito");
				serviceTransaction.createTransaction(transactionGMF, productId);
				
				//Actualizacion de la transaccion
				transaction.setFinalBalance(productEntity.getBalance()- (1.004 * transaction.getTransactionValue()));
				transaction.setTransactionValue(-transaction.getTransactionValue());
				transaction.setSecondaryProductId((long) 0);
				transaction.setTransactionResult("Efectiva");
				transaction.setFinanceMovement("Debito");
				transaction.setGMF(-0.004 * transaction.getTransactionValue());
				
				//Creacion de la transaccion receptora
				Transaction transactionReceiver = new Transaction();
				transactionReceiver.setPrincipalProductId(transaction.getSecondaryProductId());
				transactionReceiver.setTransactionValue(-transaction.getTransactionValue());
				transactionReceiver.setFinalBalance(productReceiver.getBalance() - transaction.getTransactionValue());
				transactionReceiver.setTransactionDetails("Recepcion de la transferencia " + transaction.getTransactionDetails() + " desde producto: " + transaction.getPrincipalProductId());
				transactionReceiver.setTransactionResult("Efectiva");
				transactionReceiver.setTransactionDate(transaction.getTransactionDate());
				transactionReceiver.setTransactionType("Recibido por transferencia");
				transactionReceiver.setSecondaryProductId(transaction.getPrincipalProductId());
				transactionReceiver.setFinanceMovement("Credito");
				serviceTransaction.createTransaction(transactionReceiver, transaction.getSecondaryProductId());
				
				//Actualizar el saldo en el producto Emisor/Enviador
				productReceiver.setBalance(transactionReceiver.getFinalBalance());
				serviceProduct.updateBalance(productReceiver);
				
			}
			else {
				transaction.setTransactionResult("Saldo insuficiente. El saldo disponible para la transferencia es: " + 0.996*productEntity.getBalance());
				transaction.setFinalBalance(productEntity.getBalance());
			}
		}
		
		else {
			transaction.setTransactionResult("Cuenta inactiva");
			transaction.setFinalBalance(productEntity.getBalance());
		}
		
		productEntity.setBalance(transaction.getFinalBalance());
		serviceProduct.updateBalance(productEntity);
		
		return serviceTransaction.createTransaction(transaction, productId);
	}
	
	//Obtener la transaccion por cuenta, por usuario
	@GetMapping("")
	public List<Transaction> listTransactionId(@PathVariable("productId") Long principalProductId){
		return serviceTransaction.listTransactionId(principalProductId);
	}
	
}
