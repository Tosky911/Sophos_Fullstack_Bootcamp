package com.sc.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMERS")
public class CustomerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Tipo documento
	private String typeId;
	//Numero documento
	@Column(unique = true)
	private String numId;
	//Nombres
	private String firstName;
	//Apellidos
	private String lastName;
	//Correo
	private String email;
	//Fecha nacimiento
	private String birthdayDate;
	//Fecha creacion cuenta
	private String creationDate;
	
	public CustomerEntity () {}

	//Constructor
	public CustomerEntity(Long id, String typeId, String numId, String firstName, String lastName, String email,
			String birthdayDate, String creationDate) {
		super();
		this.id = id;
		this.typeId = typeId;
		this.numId = numId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.birthdayDate = birthdayDate;
		this.creationDate = creationDate;
	}

	//Getters and Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getNumId() {
		return numId;
	}

	public void setNumId(String numId) {
		this.numId = numId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthdayDate() {
		return birthdayDate;
	}

	public void setBirthdayDate(String birthdayDate) {
		this.birthdayDate = birthdayDate;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
