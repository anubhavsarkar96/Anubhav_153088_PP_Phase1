package com.capgemini.wallet.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.beans.Wallet;
import com.capgemini.wallet.exception.InsufficientBalanceException;
import com.capgemini.wallet.exception.InvalidInputException;
import com.capgemini.wallet.repo.WalletRepoImpl;
import com.capgemini.wallet.service.WalletService;
import com.capgemini.wallet.service.WalletServiceImpl;

public class TestCaseClass {

	
	
	WalletService service;
	WalletRepoImpl repo;
	Customer customer1,customer2,customer3;
	
	
	@Before
	public void initialiseData(){
		Map<String, Customer> data = new HashMap<String,Customer>();
		customer1 = new Customer("Abhishek", "9876543210", new Wallet(new BigDecimal(9000)));
		customer2 = new Customer("Akshay","5489756165", new Wallet(new BigDecimal(5000)));
		customer3 = new Customer("Bhuvan","5541853333", new Wallet(new BigDecimal(7500)));
		
		data.put("9876543210", customer1);
		data.put("5489756165", customer2);
		data.put("5541853333", customer3);
		service = new WalletServiceImpl(data);
		
	}
	

	@Test
	public void testPhoneInput() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Akash", "7894561230", new BigDecimal(20000));
		assertEquals("7894561230", customer.getMobileNo());
	}
	
	
	@Test
	public void testNameInput() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Alisha", "7894561230", new BigDecimal(20000));
		assertEquals("Alisha", customer.getName());
	}
	
	@Test
	public void testAmountInput() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Alisha", "7894561230", new BigDecimal(20000));
		assertEquals( new BigDecimal(20000), customer.getWallet().getBalance());
	}
	
	@Test
	public void testShowBalance1() throws InvalidInputException
	{
		Customer customer = new Customer();
		customer = service.showBalance("9876543210");
		assertEquals(new BigDecimal(9000), customer.getWallet().getBalance());
	}
	
	
	@Test
	public void testShowBalance2() throws InvalidInputException
	{
		Customer customer = new Customer();
		customer = service.showBalance("5489756165");
		assertEquals(new BigDecimal(5000), customer.getWallet().getBalance());
	}
	
	
	@Test
	public void testShowBalance3() throws InvalidInputException
	{
		Customer customer = new Customer();
		customer = service.showBalance("5541853333");
		assertEquals(new BigDecimal(7500), customer.getWallet().getBalance());
	}
	
	
	
	@Test(expected = InsufficientBalanceException.class)
	public void testWithdraw1() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer = service.withdrawAmount("5541853333", new BigDecimal(100000));
		
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void testWithdraw2() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer = service.withdrawAmount("5489756165", new BigDecimal(66000));
		
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void testWithdraw3() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer = service.withdrawAmount("9876543210", new BigDecimal(9001));
		
	}
	
	@Test
	public void testValidation1() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Jane", "1234567890", new BigDecimal(1100));
		assertEquals(10,customer.getMobileNo().length());
	}
	
	@Test
	public void testValidation2() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Jane", "7894561230", new BigDecimal(1100));
		assertEquals(10,customer.getMobileNo().length());
	}
	
	@Test
	public void testDepositAmount1() throws InvalidInputException {
		Customer customer = new Customer();
		customer=service.depositAmount("9876543210", new BigDecimal(500));
		assertEquals(new BigDecimal(9500), customer.getWallet().getBalance());
	}
	
	@Test
	public void testDepositAmount2() throws InvalidInputException {
		Customer customer = new Customer();
		customer=service.depositAmount("5489756165", new BigDecimal(500));
		assertEquals(new BigDecimal(5500), customer.getWallet().getBalance());
	}
	
	@Test
	public void testDepositAmount3() throws InvalidInputException {
		Customer customer = new Customer();
		customer=service.depositAmount("5541853333", new BigDecimal(500));
		assertEquals(new BigDecimal(8000), customer.getWallet().getBalance());
	}
	
	
	@Test
	public void testfundTransfer1() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.fundTransfer("9876543210", "5489756165", new BigDecimal(500));
		assertEquals(new BigDecimal(8500), customer.getWallet().getBalance());
	}
	
	
	@Test
	public void testfundTransfer2() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.fundTransfer("9876543210", "5489756165", new BigDecimal(500));
		assertEquals(new BigDecimal(8500), customer.getWallet().getBalance());
	}
	
	
	@Test
	public void testfundTransfer3() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.fundTransfer("5541853333", "5489756165", new BigDecimal(500));
		assertEquals(new BigDecimal(7000), customer.getWallet().getBalance());
	}
	
	@Test(expected = InvalidInputException.class )
	public void testfundTransfer4() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.fundTransfer("5541853333", "5489756165", new BigDecimal(8000));
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testfindOneRepo() throws InvalidInputException {
		Customer customer = new Customer("","",new Wallet(null));
		customer = repo.findOne(customer.getMobileNo());
		
	}
	
	@Test
	public void testIsValid() throws InvalidInputException {
		Customer customer = new Customer("Pratiksha","7387678820",new Wallet(new BigDecimal(150000)));
		String mobileNo = "7387678820";
		assertTrue(service.isValid(mobileNo));
	}
	
	@Test
	public void testIsValid2() throws InvalidInputException {
		Customer customer = new Customer("Bhushan","5541853333",new Wallet(new BigDecimal(5000)));
		String mobileNo = "5541853333";
		assertTrue(service.isValid(mobileNo));
	}
	
}

