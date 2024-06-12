package com.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.entities.Bankaccount;
import com.repo.BankRepo;



@Component
public class CommonUtilities {
	
	@Autowired
	BankRepo bankRepo;
	
	@Autowired
	private JavaMailSender ms;

	public String getTime()
	{
		LocalDateTime time=LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String newTime=time.format(formatter);
		return newTime;
	}
	
	public void sendMail(String to,String sub,String body)
	{
		SimpleMailMessage m =new SimpleMailMessage();
		m.setTo(to);
		m.setSubject(sub);
		m.setText(body);
		ms.send(m);
	}
	
	public int generateOtp(String targetGmail)
	{
		Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        String sub="OTP for bank login";
        String sotp=String.valueOf(otp);
        sendMail(targetGmail,sub,sotp);
        return otp;
	}
	
	public Long getNewBankAccNo()
	{
		Random r=new Random();
		boolean b=true;
		Long accNo=0L;
		while(b)
		{		
			accNo=3249900000L+r.nextInt(99999);			
			List<Bankaccount> blist = bankRepo.getAccountByNo(accNo);
			if(blist.isEmpty())
			{
				b=false;
			}		
		}
		return accNo;
	}
	
	public boolean passwordValidator(String password)
	{
		if(password.length()<8)
		{
			return false;
		}
		if(!password.matches(".*[A-Z].*"))            
		{
			return false;
		}
		if(!password.matches(".*[a-z].*"))
		{
			return false;
		}
		if(!password.matches(".*\\d.*"))
		{
			return false;
		}
		if(!password.matches(".*[!@#$%^&*()_+\\[\\]{};':\"\\\\|,.<>/?].*"))
		{
		    return false;
		}
		return true;
	}
	
}
