/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Expenses.java
 *
 * Copyright 2015 Hunter Marrow (PTY) Ltd. All rights reserved.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.hm.alchemy.rest.service.model;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "expenses")
public class Expenses {
	private Set<Expense> expenses = new HashSet<>();

	public Set<Expense> getExpenses() {
		return expenses;
	} 		
}
