/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * AbstractResource.java
 *
 * Copyright 2015 Hunter Marrow (PTY) Ltd. All rights reserved.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.hm.alchemy.rest.service;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.hm.alchemy.rest.service.model.IModel;
import com.hm.alchemy.rest.service.repo.IRepository;


public abstract class AbstractResource<T extends IModel> implements IResource<T> {
	private static final Logger LOGGER 		  = Logger.getLogger(AbstractResource.class.getName());
	private static final String FORWARD_SLASH = "/";
	
	/**
	 * @return			 - the {@link IRepository} object for the model currently being processed
	 * @throws Exception - thrown when a connection to the database in unable to be made	
	 */
	protected abstract IRepository<T> getRepository() throws Exception; 
	
	/**
	 * @return - The Class representing the current model that is being processed 
	 */
	protected abstract Class<T> getModelClass();
	
	
	/**
	 * @return - The current processed Class's simple name
	 */
	protected abstract String getModelClassName(); 
	
	/**
	 * @return - The current resource location/directory/folder
	 */
	protected abstract String getResourceName(); 
	
	
	/**
	 * @param model		 - represents the current {@link IModel} Object that is being processed 
	 * @throws Exception - thrown when the {@link IModel} specific validations fails
	 */
	protected abstract void validate(T model) throws Exception;
	
	@Override
	public Response create(T modal) {
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Inside rest resource for " + getModelClassName() + ". Creating data: " + modal) ;
		}
		
		T record = null;
		
		try {			
			IRepository<T> repository = getRepository();			
			
			if (repository == null) {
				throw new IOException("Could not access the repository for model: " + getModelClassName());
			}
			
			validate(modal);
			record = repository.create(modal);
						
			String readResource = getResourceName() + FORWARD_SLASH + record.getId(); 						 		
			
			return Response.created(URI.create(readResource)).build();
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Could not create " + getModelClassName() + " record with data " + modal, e);			
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@Override
	public abstract Response findAll();

	@Override
	public Response read(int id) {
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Inside rest resource for " + getModelClassName() + ". Reading data for ID: " + id) ;
		}
		
		T model = null; 		
		
		try {			
			IRepository<T> repository = getRepository();			
					
			if (repository == null) {
				throw new IOException("Could not access the repository for model: " + getModelClassName());
			}
			
			model = repository.read(Long.valueOf(id));
			
			if (model == null) {								
				return Response.status(Status.NO_CONTENT).build();
			}			
			
			return Response.ok(model).build();
			
		}
		catch(Exception e) {								
			LOGGER.log(Level.SEVERE, "Could not read " + getModelClassName() + " record with ID " + id, e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();						
		}
	}

}
