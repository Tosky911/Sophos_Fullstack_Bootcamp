package com.sc.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTS")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	//Valor con el que se asocia a la entidad Customer
	@JoinColumn(name = "customerId")
	private Long customerId;
	
	//Saldo de la cuenta
	private double balance;
	
	//Tipo de cuenta
	private String typeAccount;
	
	//Numero de cuenta
	private String numAccount;
	
	//Creacion de cuenta
	private String creationDate;
	
	//Estado de la cuenta
	private String state;

	public ProductEntity() {
		
	}

	//Constructor
	public ProductEntity(Long id, Long customerId, double balance, String typeAccount, String numAccount, String creationDate,
			String state) {
		super();
		this.productId = id;
		this.customerId = customerId;
		this.balance = balance;
		this.typeAccount = typeAccount;
		this.numAccount = numAccount;
		this.creationDate = creationDate;
		this.state = state;
	}

	//Getter and Setters
	
	public Long getproductId() {
		return productId;
	}

	public void setproductId(Long id) {
		this.productId = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getTypeAccount() {
		return typeAccount;
	}

	public void setTypeAccount(String typeAccount) {
		this.typeAccount = typeAccount;
	}

	public String getNumAccount() {
		return numAccount;
	}

	public void setNumAccount(String numAccount) {
		this.numAccount = numAccount;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	
}
