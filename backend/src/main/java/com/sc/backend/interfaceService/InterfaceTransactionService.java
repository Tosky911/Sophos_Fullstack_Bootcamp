package com.sc.backend.interfaceService;

import java.util.List;

import com.sc.backend.entity.Transaction;

public interface InterfaceTransactionService {
	public Transaction createTransaction(Transaction transaction, Long principalProductId);
	public List<Transaction> listTransactionId(Long principalProductId);
}
