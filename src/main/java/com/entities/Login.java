package com.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="logins")
public class Login {

    private int id;

	@Id
	private String gmail;
	
	private String password;
	
	private int failedCount;
	
	private String accStatus;
	
	private String lastLoginTime;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	public String getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(String accStatus) {
		this.accStatus = accStatus;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Override
	public String toString() {
		return "Logins [id=" + id + ", email=" + gmail + ", password=" + password + ", failedCount=" + failedCount
				+ ", accStatus=" + accStatus + ", lastLoginTime=" + lastLoginTime + "]";
	}
	
	
}
