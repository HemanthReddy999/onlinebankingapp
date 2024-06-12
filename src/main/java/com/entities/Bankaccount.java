package com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="bankaccounts")
public class Bankaccount {

	private int id;
	
	private String gmail;
	
	private String name;
	
	@Id
	private long bankAccNo;
	
	private int availableBal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String email) {
		this.gmail = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(long bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public int getAvailableBal() {
		return availableBal;
	}

	public void setAvailableBal(int availableBal) {
		this.availableBal = availableBal;
	}

	@Override
	public String toString() {
		return "Bankaccount [id=" + id + ", email=" + gmail + ", name=" + name + ", bankAccNo=" + bankAccNo
				+ ", availableBal=" + availableBal + "]";
	}
	
	
	
}
