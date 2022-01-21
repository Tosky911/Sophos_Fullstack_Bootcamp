package com.sc.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//Tipo documento
	private String typeId;
	//Numero documento
	private String numId;
	//Nombre de usuario
	private String username;
	//Nombres
	private String firsName;
	//Apellidos
	private String lastName;
	//Correo
	private String email;
	//Fecha nacimiento
	private String birthdayDate;
	//Fecha creacion cuenta
	private String creationDate;
	
	public User () {}

	//Constructor
	public User(Long id, String typeId, String numId, String username, String firsName, String lastName, String email,
			String birthdayDate, String creationDate) {
		super();
		this.id = id;
		this.typeId = typeId;
		this.numId = numId;
		this.username = username;
		this.firsName = firsName;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirsName() {
		return firsName;
	}

	public void setFirsName(String firsName) {
		this.firsName = firsName;
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
