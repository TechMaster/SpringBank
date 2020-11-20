package com.xbank;

import java.util.ArrayList;
import java.util.List;

import com.xbank.beans.Account;

public class Helper {

	public static List<Account> getEmps() {
		Account e1 = new Account(1, "Account 1");
		Account e2 = new Account(2, "Account 2");
		Account e3 = new Account(3, "Account 3");
		Account e4 = new Account(4, "Account 4");
		Account e5 = new Account(5, "Account 5");
		List<Account> emps = new ArrayList<>();
		emps.add(e1);
		emps.add(e2);
		emps.add(e3);
		emps.add(e4);
		emps.add(e5);
		return emps;
	}
}
