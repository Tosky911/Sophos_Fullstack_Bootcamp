package com.sc.backend.service;

import java.util.List;

import com.sc.backend.entity.TransactionEntity;


public interface TransactionService{

	public TransactionEntity createTransaction(TransactionEntity transactionEntity, Long principalProductId);
	public List<TransactionEntity> listTransactionId(Long principalProductId);
	

	
	
}
