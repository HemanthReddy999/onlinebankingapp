package com.entities;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {

	private int id;
	
	private String firstName;
	
	private String lastName;
	
	@Id
	private String gmail;
	
	private Date dateOfBirth;
	
	private String gender;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String fName) {
		this.firstName = fName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lName) {
		this.lastName = lName;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String email) {
		this.gmail = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fName=" + firstName + ", lName=" + lastName + ", gmail=" + gmail + ", dateOfBirth="
				+ dateOfBirth + ", gender=" + gender + "]";
	}

	
	
	
	
	

}
