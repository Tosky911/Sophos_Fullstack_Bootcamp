package com.sc.backend.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class UserEntity {
	
	@Id
	private String userName;
	private String password;
	private String jwt;
	private String lastName;
	private String firstName;
	
	public UserEntity() {
	}
	
	//Constructor
	public UserEntity(String userName, String password, String jwt, String lastName, String firstName) {
		super();
		this.userName = userName;
		this.password = password;
		this.jwt = jwt;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	//Getters y Setters
	
	
}
