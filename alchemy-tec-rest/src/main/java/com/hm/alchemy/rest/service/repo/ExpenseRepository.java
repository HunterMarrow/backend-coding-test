/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ExpenseRepository.java
 *
 * Copyright 2015 Hunter Marrow (PTY) Ltd. All rights reserved.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.hm.alchemy.rest.service.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hm.alchemy.rest.service.model.Expense;

public class ExpenseRepository extends AbstractRepository<Expense> implements IExpenseRepository{
	private static final Logger LOGGER = Logger.getLogger(ExpenseRepository.class.getName());
	
	public ExpenseRepository() throws Exception {
		super();
	}

	@Override
	protected Class<Expense> getModelClass() {
		return Expense.class;
	}

	@Override
	protected String getModelClassName() {
		return Expense.class.getSimpleName();
	}

	@Override
	protected void setValues(PreparedStatement ps, Expense model) throws SQLException {	
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Setting values on prepared statement. Data: " + model);
		}
		
		ps.setObject(1, model.getDateCaptured());
		ps.setBigDecimal(2, model.getAmount());
		ps.setString(3, model.getReason());		
		ps.setBigDecimal(4, model.getVat());		
	}

}
