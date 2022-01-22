package com.sc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sc.backend.entity.Transaction;

@Service
public class TransactionService implements InterfaceTransactionService{

	@Autowired
	private InterfaceTransaction interfaceTransaction;
	
	@Override
	public Transaction createTransaction(Transaction transaction, Long principalProductId) {
		return interfaceTransaction.save(transaction);
	}
	
	@Override
	public List<Transaction> ListIdTransaction(Long principalProductId) {
		return interfaceTransaction.findByProductPrincipalIdAndTransactionResult(principalProductId, "Efectiva");
	}
	
	
}
