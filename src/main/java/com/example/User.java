package com.example;


import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;

@Enitiy
public class User {

	@Id
	@GeneratedValue
	Long id ; 
	String firstName ;
	String lastName;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public User()
	{
		
	}

	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	
	
}
