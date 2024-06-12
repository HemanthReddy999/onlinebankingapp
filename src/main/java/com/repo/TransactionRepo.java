package com.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entities.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer>{
	
	
	@Query("select t from Transaction t where t.sentBy=:name or t.receivedBy=:name")
	List<Transaction> getAllTransactions(String name);
	
	@Query("select t from Transaction t where t.sentBy=:name AND t.receivedBy='bank'")
	List<Transaction> getDeposits(String name);
	
	@Query("select t from Transaction t where t.sentBy='bank' AND t.receivedBy=:name")
	List<Transaction> getWithdrawls(String name);
	
	 @Query("SELECT t FROM Transaction t WHERE (t.sentBy = :user OR t.receivedBy = :user) AND (t.sentBy != 'bank' AND t.receivedBy != 'bank')")
	 List<Transaction> getTransfers(String user);
}
