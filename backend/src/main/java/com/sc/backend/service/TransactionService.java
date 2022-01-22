package com.sc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sc.backend.entity.Transaction;
import com.sc.backend.interfaceService.InterfaceTransactionService;
import com.sc.backend.repository.TransactionRepository;

@Service
public class TransactionService implements InterfaceTransactionService{

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Override
	public Transaction createTransaction(Transaction transaction, Long principalProductId) {
		return transactionRepository.save(transaction);
	}
	
	@Override
	public List<Transaction> listTransactionId(Long principalProductId) {
		return transactionRepository.findByPrincipalProductIdAndTransactionResult(principalProductId, "Efectiva");
	}
	
	
}
