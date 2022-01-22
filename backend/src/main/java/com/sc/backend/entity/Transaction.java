package com.sc.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Valor con el que se asocia a la entidad Product
	@JoinColumn(name = "productId")
	private int principalProductId;
	private int secundaryProductId;
	
	//Tipo transaccion
	private String transactionType;
	
	//Valor de la transaccion
	private double transactionValue;
	
	//Fecha de transaccion
	private String transactionDate;
	
	//Detalles de transaccion
	private String transactionDetails;
	
	//Resultado de la transaccion
	private String transactionResult;
	
	//Saldo final de la transaccion
	private double finalBalance;
	
	//GMF impuesto
	private double GMF;
	
	//Movimiento financiero
	private String financeMovement;
	
	public Transaction() {
		
	}

	//Constructor
	
	public Transaction(Long id, int principalProductId, int secundaryProductId, String transactionType,
			double transactionValue, String transactionDate, String transactionDetails, String transactionResult,
			double finalBalance, double GMF, String financeMovement) {
		super();
		this.id = id;
		this.principalProductId = principalProductId;
		this.secundaryProductId = secundaryProductId;
		this.transactionType = transactionType;
		this.transactionValue = transactionValue;
		this.transactionDate = transactionDate;
		this.transactionDetails = transactionDetails;
		this.transactionResult = transactionResult;
		this.finalBalance = finalBalance;
		this.GMF = GMF;
		this.financeMovement = financeMovement;
	}
	
	//Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPrincipalProductId() {
		return principalProductId;
	}

	public void setPrincipalProductId(int principalProductId) {
		this.principalProductId = principalProductId;
	}

	public int getSecundaryProductId() {
		return secundaryProductId;
	}

	public void setSecundaryProductId(int secundaryProductId) {
		this.secundaryProductId = secundaryProductId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public double getTransactionValue() {
		return transactionValue;
	}

	public void setTransactionValue(double transactionValue) {
		this.transactionValue = transactionValue;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(String transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public String getTransactionResult() {
		return transactionResult;
	}

	public void setTransactionResult(String transactionResult) {
		this.transactionResult = transactionResult;
	}

	public double getFinalBalance() {
		return finalBalance;
	}

	public void setFinalBalance(double finalBalance) {
		this.finalBalance = finalBalance;
	}

	public double getGMF() {
		return GMF;
	}

	public void setGMF(double gMF) {
		GMF = gMF;
	}

	public String getFinanceMovement() {
		return financeMovement;
	}

	public void setFinanceMovement(String financeMovement) {
		this.financeMovement = financeMovement;
	}
		
	
}
