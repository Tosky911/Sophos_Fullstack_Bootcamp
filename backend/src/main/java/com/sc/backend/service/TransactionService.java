package com.sc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sc.backend.entity.TransactionEntity;
import com.sc.backend.repository.TransactionRepository;
import com.sc.backend.service.impl.TransactionServiceImpl;


public interface TransactionService{

	public TransactionEntity createTransaction(TransactionEntity transactionEntity, Long principalProductId);
	public List<TransactionEntity> listTransactionId(Long principalProductId);
	

	
	
}
