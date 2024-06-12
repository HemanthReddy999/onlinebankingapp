package com;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entities.Login;
import com.entities.Transaction;
import com.repo.LoginRepo;
import com.service.BankService;
import com.service.CommonUtilities;
import com.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class FrontController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	BankService bankService;
	
	@Autowired
	LoginRepo loginRepo;
	
	@Autowired
	CommonUtilities cu;
	
	@GetMapping("/home")
	public String home()
	{
		return "home";
	}
	
	@PostMapping("/signin")
	public String validateSignin(@RequestParam("userName") String userName,@RequestParam("password") String password,HttpSession session,Model m) {
		String response=userService.login(userName, password,m);
		session.setAttribute("user", userName);		
		return response;
	}
	
	@GetMapping("/signup")
	public String signUp()
	{
		return "enterGmail";
	}
	
	@GetMapping("/{formtype}")
	public String dashboard(@PathVariable("formtype") String formtype,HttpSession s)
	{
		if (s.getAttribute("user") == null) {
            return "home";
        }
		if(formtype.equals("deposit"))
		{
			return "depositForm";
		}
		if(formtype.equals("withdraw"))
		{
			return "withdrawForm";
		}
		if(formtype.equals("transfer"))
		{
			return "transferForm";
		}
		if(formtype.equals("transaction"))
		{
			return "transactionType";
		}
		return "home";
	}

	@GetMapping("/showBalance")
	public String depSucess(HttpSession session,Model m)
	{
		if (session.getAttribute("user") == null) {
            return "home";
        }
		String currentUser=(String)session.getAttribute("user");
		int balance=bankService.getBal(currentUser);
		m.addAttribute("balance", balance);
		return "showBalance";
		
	}	
	
	@PostMapping("/validateDeposit")
	public String validateDeposit(@RequestParam("amount") String amount,Model m,HttpSession s)
	{		
		String currentUser=(String)s.getAttribute("user");
		bankService.depositValidation(amount,currentUser);
		m.addAttribute("bal", amount);
		return "depositSucess";
	}
	
	@PostMapping("/validateWithdraw")
	public String validateWithdraw(@RequestParam("amount") String amount,HttpSession s,Model m)
	{
		String currentUser=(String)s.getAttribute("user");
		m.addAttribute("amount", amount);
		return bankService.withdrawValidation(amount, currentUser);
	}
	
	@PostMapping("/validateTransfer")
	public String validateTransfer(@RequestParam("amount") String amount,@RequestParam("recipient") String recipient,HttpSession s,Model m)
	{
		String currentUser=(String)s.getAttribute("user");
		m.addAttribute("amount", amount);
		return bankService.transferValidation(recipient, currentUser, amount,m);
	}	
	
	@GetMapping("/transactions")
	public String transactionHistory(HttpSession s,Model m,@RequestParam("type") String type)
	{
		if (s.getAttribute("user") == null) {
            return "home";
        }
		String response="";
		if(type.equals("all"))
		{
			String currentUser=(String)s.getAttribute("user");
			List<Transaction> tl=bankService.getAllTransactions(currentUser);
			m.addAttribute("transactions", tl);
			response="transactionHistory";
	    }
		else if(type.equals("deposit")) 
		{
			String currentUser=(String)s.getAttribute("user");
			List<Transaction> tl=bankService.getDeposits(currentUser);
			m.addAttribute("transactions", tl);
			response="transactionHistory";
		}
		else if(type.equals("withdraw")) 
		{
			String currentUser=(String)s.getAttribute("user");
			List<Transaction> tl=bankService.getWithdrawls(currentUser);
			m.addAttribute("transactions", tl);
			response="transactionHistory";
		}
		else if(type.equals("transfer")) 
		{
			String currentUser=(String)s.getAttribute("user");
			List<Transaction> tl=bankService.getTransfers(currentUser);
			m.addAttribute("transactions", tl);
			response="transactionHistory";
		}
		
		return response;
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    return "redirect:/home";
		
	}
	
	
	int genOtp;
	String okGmail;
	
	@PostMapping("/validateGmail")
	public String checkGmail(@RequestParam("gmail") String gmail)
	{
		String response="";
		List<Login> byGmail = loginRepo.findByGmail(gmail);
		if(!byGmail.isEmpty())
		{
			response="userExists";
		}
		else
		{
			genOtp=cu.generateOtp(gmail);
			okGmail=gmail;
			response="otpPage";
		}
		return response;
	}
	
	@PostMapping("/validateOtp")
	public String validateOtp(@RequestParam("otp") int otp,Model m)
	{
		String response = "";
		if(genOtp==otp)
		{
			m.addAttribute("gmail",okGmail);
			response="/registration";
		}
		else
		{
			response="invalidOtp";
		}
		return response;
	}
	
	@PostMapping("/register")
	public String newUserReg
	(       @RequestParam("fname") String fname,
			@RequestParam("lname") String lname,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("dob") Date dob,
			@RequestParam("gender") String gender){	
		
		
		
		if(cu.passwordValidator(password))
		{
			return userService.newUserReg(fname, lname, email, password,dob, gender);
		}
		
		return "weakPassword";
	}
	
	
	
	
	
	
	
	
	
	
	
}
