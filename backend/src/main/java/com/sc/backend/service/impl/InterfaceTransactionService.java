package com.sc.backend.service.impl;

import java.util.List;

import com.sc.backend.entity.TransactionEntity;

public interface InterfaceTransactionService {
	public TransactionEntity createTransaction(TransactionEntity transactionEntity, Long principalProductId);
	public List<TransactionEntity> listTransactionId(Long principalProductId);
}
