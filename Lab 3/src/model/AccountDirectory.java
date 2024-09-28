/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.*;

/**
 *
 * @author Lakshman
 */
public class AccountDirectory {
	
	private ArrayList<Account> accounts;
	
	public AccountDirectory() {
		this.accounts = new ArrayList<>();
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
	
	public Account addAccount() {
		Account acc = new Account();
		accounts.add(acc);
		return  acc;
	}
	
	public void deleteAccount(Account account) {
		accounts.remove(account);
	}
	
}
