/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ExpenseResource.java
 *
 * Copyright 2015 Hunter Marrow (PTY) Ltd. All rights reserved.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.hm.alchemy.rest.service;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.hm.alchemy.rest.service.model.Expense;
import com.hm.alchemy.rest.service.model.Expenses;
import com.hm.alchemy.rest.service.repo.ExpenseRepository;
import com.hm.alchemy.rest.service.repo.IExpenseRepository;

@Path(ExpenseResource.RESOURCE_NAME)
public class ExpenseResource extends AbstractResource<Expense> {
	
	private static final String EMPTY_STRING = "";
	private static final Logger LOGGER       = Logger.getLogger(ExpenseResource.class.getName());
	private static final String WHITESPACE   = "\\p{Z}";
	
	public static final String RESOURCE_NAME = "/expenses";
	
	@Override
	protected IExpenseRepository getRepository() throws Exception {
		return new ExpenseRepository();
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
	protected String getResourceName() {
		return RESOURCE_NAME;
	}

	@Override
	protected void validate(Expense model) throws Exception {
		
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Validating the expense data.");
		}
		
		if (model.getAmount() == null) {
			throw new NullPointerException("The amount may not be null.");
		}
		
		if (model.getAmount().doubleValue() == 0) {
			throw new SQLException("The amount may not be zero.");
		}
		
		if (model.getDateCaptured() == null) {
			throw new NullPointerException("The date captured may not be null.");
		}
		
		if (model.getReason() == null) {
			throw new NullPointerException("The reason may not be null.");
		}	
		
		if (model.getReason().replaceAll(WHITESPACE,EMPTY_STRING).isEmpty()) {
			throw new SQLException("The reason may not be empty.");
		}
		
		if (model.getVat() == null) {
			throw new NullPointerException("The vat may not be null.");
		}
		
		if (model.getVat().doubleValue() == 0) {
			throw new SQLException("The vat may not be zero.");
		}					
	}
	
	
	@Override
	public Response findAll() {
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Inside rest resource for " + getModelClassName() + ". About to get all records") ;
		}
		
		Expenses persons = new Expenses(); 		 						
		
		try {						
			IExpenseRepository repository = getRepository(); 
			
			if (repository == null) {
				throw new WebApplicationException("FIND - Could not instantiate the " + getModelClassName() + " repository!", Response.Status.METHOD_NOT_ALLOWED);
			}
																		
			persons.getExpenses().addAll(repository.find());
			
			if (persons.getExpenses().isEmpty()) {
				return Response.status(Status.NO_CONTENT).build();
			}
			
			return Response.ok(persons, MediaType.APPLICATION_JSON_TYPE).build();
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Could not find all " + getModelClassName() + " records.", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();			
		}
	}

}
