package com.capgemini.wallet.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import com.capgemini.wallet.Util.DBUtil;
import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.beans.Wallet;
import com.capgemini.wallet.exception.InvalidInputException;



public class WalletRepoImpl implements WalletRepo{
	Customer customer;
	
	private Map<String, Customer> data=new HashMap<String, Customer>(); 
	public WalletRepoImpl(Map<String, Customer> data) {
		this.data = data;
	}

	
	public WalletRepoImpl() {
		
	}


	@Override
	public boolean save(Customer customer) throws InvalidInputException {
		try(Connection con = DBUtil.getConnection())
		{
			PreparedStatement pstm = con.prepareStatement("insert into customer values(?,?,?)");
			pstm.setString(1, customer.getName());
			pstm.setString(2, customer.getMobileNo());
			pstm.setBigDecimal(3, customer.getWallet().getBalance());
			
			pstm.execute();
			return true;
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Customer findOne(String mobileNo) throws InvalidInputException
	{
		try(Connection con = DBUtil.getConnection())
		{
			PreparedStatement pstm = con.prepareStatement("select * from customer where phone = ?");
			pstm.setString(1, mobileNo);
			ResultSet res = pstm.executeQuery();
			
			if(res.next() == false) throw new InvalidInputException("No customer with this mobile Number");
			
			Customer customer = new Customer();
			Wallet wallet = new Wallet();
			
			customer.setName(res.getString(1));
			customer.setMobileNo(res.getString(2));
			
			wallet.setBalance(res.getBigDecimal(3));
			customer.setWallet(wallet);
			
			return customer;
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}


	@Override
	public void remove(String mobileNo) 
	{try(Connection con = DBUtil.getConnection())
		{
		PreparedStatement pstm = con.prepareStatement("delete from customer where phone = ?");
		
		pstm.setString(1, mobileNo);
		pstm.execute();
		return;
		
	} 
	catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}

}
