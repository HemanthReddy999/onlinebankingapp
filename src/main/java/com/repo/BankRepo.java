package com.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.entities.Bankaccount;

public interface BankRepo extends JpaRepository<Bankaccount, Long> {


	@Query("select b.name from Bankaccount b where b.gmail=:currentUser")
	String getName(String currentUser);


	@Query("SELECT b FROM Bankaccount b WHERE b.gmail = :gmail OR CAST(b.bankAccNo AS string) = :gmail")
	List<Bankaccount> getRecipient(String gmail);
	
	@Query("SELECT b FROM Bankaccount b WHERE b.bankAccNo = :accNo")
	List<Bankaccount> getAccountByNo(Long accNo);


	@Modifying
	@Query("update Bankaccount b set b.availableBal=b.availableBal+:balance where b.gmail=:gmail")
	void depositBal(int balance,String gmail);

	@Query("select b.availableBal from Bankaccount b where b.gmail=:gmail")
	int getBalance(String gmail);


	@Modifying
	@Query("update Bankaccount b set b.availableBal=b.availableBal-:balance where b.gmail=:gmail")
	void withdrawBal(int balance,String gmail);


}
