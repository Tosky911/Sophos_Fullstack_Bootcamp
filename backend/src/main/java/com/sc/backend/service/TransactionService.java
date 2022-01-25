package com.sc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sc.backend.entity.TransactionEntity;
import com.sc.backend.repository.TransactionRepository;
import com.sc.backend.service.impl.InterfaceTransactionService;

@Service
public class TransactionService implements InterfaceTransactionService{

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Override
	public TransactionEntity createTransaction(TransactionEntity transactionEntity, Long principalProductId) {
		return transactionRepository.save(transactionEntity);
	}
	
	@Override
	public List<TransactionEntity> listTransactionId(Long principalProductId) {
		return transactionRepository.findByPrincipalProductIdAndTransactionResult(principalProductId, "Efectiva");
	}
	
	
}
