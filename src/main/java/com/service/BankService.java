package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.entities.Bankaccount;
import com.entities.Transaction;
import com.repo.BankRepo;
import com.repo.TransactionRepo;

import jakarta.transaction.Transactional;

@Service
public class BankService {
	
	@Autowired
	BankRepo bankRepo;
	
	@Autowired
	TransactionRepo transactionRepo;
	
	@Autowired
	CommonUtilities cu;
	
	
	public int getBal(String gmail)
	{
	    int bal=bankRepo.getBalance(gmail);	
		return bal;
	}
	
	@Transactional
	public void depositValidation(String amount,String user)
	{
		int depBal=Integer.parseInt(amount);
		bankRepo.depositBal(depBal,user);	
		String name=bankRepo.getName(user);
		saveTransaction(name,"bank",depBal,"deposit");
	}

	
	@Transactional
	public String withdrawValidation(String amount,String user)
	{
		String response="";
		int withdrawBal=Integer.parseInt(amount);
		int currentBal=bankRepo.getBalance(user);
		if(withdrawBal>currentBal)
		{
			response="insufficientFunds";
		}
		else
		{
			bankRepo.withdrawBal(withdrawBal, user);
			String name=bankRepo.getName(user);
			saveTransaction("bank",name,withdrawBal,"withdraw");
			response="withdrawSucess";
		}
		return response;
	}


	@Transactional
	public String transferValidation(String recipient,String user,String amount,Model m)
	{
		String response="";
		List<Bankaccount> recipientCheck=bankRepo.getRecipient(recipient);
		if(recipientCheck.isEmpty())
		{
			response="noUserFound";
		}
		else if(recipientCheck.get(0).getGmail().equals(user))
		{
			response="sameUser";
		}
		else
		{
			int transferAmount=Integer.parseInt(amount);
			int userBal=bankRepo.getBalance(user);	
			if(transferAmount>userBal)
			{
				response="insufficientFunds";
			}
			else
			{
				bankRepo.depositBal(transferAmount,recipient);
				bankRepo.withdrawBal(transferAmount, user);
				String sender=bankRepo.getName(user);
				String receiver=bankRepo.getName(recipient);
				m.addAttribute("receiver", receiver);
				saveTransaction(sender,receiver,transferAmount,"transfer");
				response="transferSucessfull";
			}
			
		}		
		return response;
	}


	public List<Transaction> getAllTransactions(String currentUser)
	{
		String name=bankRepo.getName(currentUser);
		List<Transaction> l=transactionRepo.getAllTransactions(name);
		return l;
	}
	
	public List<Transaction> getDeposits(String currentUser)
	{
		String name=bankRepo.getName(currentUser);
		List<Transaction> l=transactionRepo.getDeposits(name);
		return l;
	}
	
	public List<Transaction> getWithdrawls(String currentUser)
	{
		String name=bankRepo.getName(currentUser);
		List<Transaction> l=transactionRepo.getWithdrawls(name);
		return l;
	}
	
	public List<Transaction> getTransfers(String currentUser)
	{
		String name=bankRepo.getName(currentUser);
		List<Transaction> l=transactionRepo.getTransfers(name);
		return l;
	}
	
	public void saveTransaction(String sender,String Receiver,int amount,String type)
	{
		Transaction t=new Transaction();
		String time=cu.getTime();
		t.setAmount(amount);
		t.setOnDate(time);
		t.setReceivedBy(Receiver);
		t.setSentBy(sender);
		t.setTransactionType(type);
		transactionRepo.save(t);
	}
	
	
	

}
