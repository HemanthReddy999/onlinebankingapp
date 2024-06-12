package com.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.entities.Bankaccount;
import com.entities.Login;
import com.entities.User;
import com.repo.BankRepo;
import com.repo.LoginRepo;
import com.repo.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	LoginRepo loginRepo;

	@Autowired
	BankRepo bankRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	CommonUtilities cu;

	@Transactional
	public String login(String userName,String password,Model m)
	{
		String response="";
		List<Login> user=loginRepo.findByGmail(userName);
		if(user.isEmpty())
		{
			response="noUserFound";
		}
		else
		{
			Login checkUser=new Login();
			checkUser.setAccStatus(user.get(0).getAccStatus());
			checkUser.setFailedCount(user.get(0).getFailedCount());
			checkUser.setPassword(user.get(0).getPassword());
			if(checkUser.getAccStatus().equals("unlocked") && checkUser.getFailedCount()<=2)
			{
				if(checkUser.getPassword().equals(password))
				{
					String oldTime=loginRepo.getOldTime(userName);
					m.addAttribute("oldTime", oldTime);
					String newTime=cu.getTime();
					loginRepo.setNewTime(userName, newTime);
					String userFname=userRepo.getFname(userName);
					m.addAttribute("userFname", userFname);
					loginRepo.setFailedCountZero(userName);
					response="dashboard";
				}
				else
				{
					if(checkUser.getFailedCount()==2)
					{
						loginRepo.lockAccount(userName);					
						response="accountLocked";
					}
					loginRepo.incrementFailedCount(userName);
					response="invalidPassword";
				}
			}
			else
			{
				response="accountLocked";
			}			

		}
		return response;
	}

	public String newUserReg(String fname,String lname,String email,String password,Date dob,String gender)
	{
		int id=userRepo.getMaxId();

		User u=new User();		
		u.setId(id+1);
		u.setDateOfBirth(dob);
		u.setFirstName(fname);
		u.setGender(gender);
		u.setLastName(lname);
		u.setGmail(email);
		userRepo.save(u);

		Login l=new Login();
		l.setId(id+1);
		l.setGmail(email);
		l.setPassword(password);
		l.setAccStatus("unlocked");
		l.setFailedCount(0);
		l.setLastLoginTime(cu.getTime());
		loginRepo.save(l);

		Bankaccount b=new Bankaccount();
		b.setId(id+1);
		b.setGmail(email);
		b.setAvailableBal(0);
		b.setName(fname+" "+lname);
		b.setBankAccNo(cu.getNewBankAccNo());
		bankRepo.save(b);

		return "accountCreated";
	}
}
