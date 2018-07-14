package com.capgemini.wallet.pl;

import java.math.BigDecimal;
import java.util.Scanner;

import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.exception.InsufficientBalanceException;
import com.capgemini.wallet.exception.InvalidInputException;
import com.capgemini.wallet.service.WalletService;
import com.capgemini.wallet.service.WalletServiceImpl;

public class Client {

	public static void main(String[] args) throws InvalidInputException,InsufficientBalanceException 
	{
		WalletService service = new WalletServiceImpl();
		Customer customer;
		int choice;
		String selection;
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("\n**********Welcome to Bank Wallet Services**************");
			System.out.println("\nChoose among the following menu: ");
			System.out.println("  1. Create Account");
			System.out.println("  2. Show Balance");
			System.out.println("  3. Fund Transfer");
			System.out.println("  4. Deposit");
			System.out.println("  5. Withdraw");
			System.out.println("  6. Exit");
			System.out.println("Enter your choice: ");

			choice = scanner.nextInt();
			switch (choice) {
			case 1:

				System.out.println("Enter name: ");
				String name = scanner.next();
				System.out.println("Enter phone number: ");
				String mobileno = scanner.next();
				System.out.println("Enter amount: ");
				BigDecimal amount=scanner.nextBigDecimal();
				//BigDecimal amount = new BigDecimal(scanner.next());
				customer = service.createAccount(name, mobileno, amount);
				System.out.println(customer);
				break;

			case 2:

				System.out.println("Enter mobile number");
				customer = service.showBalance(scanner.next());
				System.out.println(customer.getWallet());
				break;

			case 3:
				System.out.println("Enter sender mobile number");
				String sourceMobileNo = scanner.next();
				System.out.println("Enter reciever mobile number");
				String targetMobileNo = scanner.next();
				System.out.println("Enter amount:");
				amount = scanner.nextBigDecimal();
				customer = service.fundTransfer(sourceMobileNo, targetMobileNo, amount);
				System.out.println(customer);
				break;

			case 4:// deposit

				System.out.println("Enter mobile phone: ");
				String mobileNo = scanner.next();
				System.out.println("Enter the amount to be deposited: ");
				amount = new BigDecimal(scanner.next());
				customer = service.depositAmount(mobileNo, amount);
				System.out.println(customer);
				break;

			case 5:

				System.out.println("Enter mobile phone: ");
				mobileNo = scanner.next();
				System.out.println("Enter the amount to be withdrawn: ");
				amount = new BigDecimal(scanner.next());
				customer = service.withdrawAmount(mobileNo, amount);
				System.out.println(customer);
				break;

			case 6:
				System.out.println("****************Thankyou!***************");
				System.exit(0);

			default:
				System.out.println("Enter correct choice");

			}// switch case ends here
			System.out.println("Do you want to continue?(y/n)");
			selection = scanner.next();
		} while (selection.equalsIgnoreCase("y"));
		System.out.println("**********Thankyou!!************");
		scanner.close();
	}// main ends here
	

}
