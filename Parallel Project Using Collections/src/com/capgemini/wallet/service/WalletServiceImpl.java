package com.capgemini.wallet.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.beans.Wallet;
import com.capgemini.wallet.exception.InsufficientBalanceException;
import com.capgemini.wallet.exception.InvalidInputException;
import com.capgemini.wallet.repo.WalletRepo;
import com.capgemini.wallet.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService {

	WalletRepo repo = new WalletRepoImpl();
	Customer customer;
	Wallet wallet;
	
	Scanner scanner = new Scanner(System.in);

	 public WalletServiceImpl(Map<String, Customer> data){
	 repo= new WalletRepoImpl(data);
	 }
	 public WalletServiceImpl(WalletRepo repo) {
	 this.repo = repo;
	 }
	
	 public WalletServiceImpl() {
	 }

	@Override
	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws InvalidInputException {
		// validate here
		if(isValid(mobileNo)) {
			Wallet wallet = new Wallet();
			Customer customer = new Customer();
		
			wallet.setBalance(amount);
			customer.setName(name);
			customer.setMobileNo(mobileNo);
			customer.setWallet(wallet);
		
			repo.save(customer);

			return customer;
		}
		else throw new InvalidInputException();
	
			
		}
		

	@Override
	public Customer showBalance(String mobileno) throws InvalidInputException {
		 Customer customer=repo.findOne(mobileno);
		 if(customer!=null) {
			 return customer;
		 }
		 else
			 throw new InvalidInputException("Invalid mobile no ");
		
	}

	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InvalidInputException {
		
		Customer customerSource = repo.findOne(sourceMobileNo);
		Customer customerTarget = repo.findOne(targetMobileNo);
		
		int i=customerSource.getWallet().getBalance().compareTo(amount);
		
		if(i!=-1) {
			Wallet wallet0=customerSource.getWallet();
			wallet0.setBalance(wallet0.getBalance().subtract(amount));
			customerSource.setWallet(wallet0);
			boolean b = repo.save(customerSource);
			
			Wallet wallet1 = customerTarget.getWallet();
			wallet1.setBalance(wallet1.getBalance().add(amount));
			
			customerTarget.setWallet(wallet1);
			boolean b1 = repo.save(customerTarget);
			return customerSource;
			
		}
		else {
			throw new InvalidInputException("Invalid amount ");
		}

	}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException {
		if(amount.compareTo(new BigDecimal(0)) <= 0) 
			throw new InvalidInputException("Enter valid amount");
		
		if(isValid(mobileNo)) 
		{
			Customer customer = repo.findOne(mobileNo);
			Wallet wallet = customer.getWallet();
			wallet.setBalance(wallet.getBalance().add(amount));
		
			repo.remove(mobileNo);
		
			if(repo.save(customer)) {
				return customer;
			}
		}
		return null;
	}

	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException{
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null) {
			
			wallet = customer.getWallet();
			// amount validation here
			int i = wallet.getBalance().compareTo(amount);
			if(i!=-1) {
				wallet.setBalance(wallet.getBalance().subtract(amount));
				
				customer.setWallet(wallet);
				boolean b = repo.save(customer);
				 
			}
			else {
				throw new InsufficientBalanceException("INSUFFICIENT BALANCE");
			}
			
			return customer;
		 }
		 else
			 throw new InvalidInputException("Invalid mobile no ");
	}
	@Override
	public boolean isValid(String mobileNo) throws InvalidInputException {
		
			if(String.valueOf(customer.getMobileNo()).matches("[1-9][0-9]{9}") && customer.getMobileNo()!=null) {
				if(customer.getName().matches("[A-Z][a-z]*")&& customer.getName()!=null) {
					return true;
				}		
				else {
					System.err.println("\nName can contain only alphabets");
					System.out.println("\nEnter correct name: ");
					customer.setName(scanner.next());
					return false;
				}
					
			}
			else {
				
				System.err.println("\nPhone no is invalid");
				System.out.println("\nEnter correct number: ");
				customer.setMobileNo(scanner.next());
				return false;
			}

	}
	
	
	
}
