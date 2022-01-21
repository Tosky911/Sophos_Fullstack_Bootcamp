package com.sc.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Valor con el que se asocia a la entidad User
	@JoinColumn(name = "id")
	private Long userId;
	
	//Saldo de la cuenta
	private double balance;
	
	//Tipo de cuenta
	private int typeAccount;
	
	//Numero de cuenta
	private String numAccount;
	
	//Creacion de cuenta
	private String creationDate;
	
	//Estado de la cuenta
	private String state;

	public Product() {
		
	}

	//Constructor
	public Product(Long id, Long userId, double balance, int typeAccount, String numAccount, String creationDate,
			String state) {
		super();
		this.id = id;
		this.userId = userId;
		this.balance = balance;
		this.typeAccount = typeAccount;
		this.numAccount = numAccount;
		this.creationDate = creationDate;
		this.state = state;
	}

	//Getter and Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getTypeAccount() {
		return typeAccount;
	}

	public void setTypeAccount(int typeAccount) {
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
