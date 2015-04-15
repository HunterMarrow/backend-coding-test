/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * IResource.java
 *
 * Copyright 2015 Hunter Marrow (PTY) Ltd. All rights reserved.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.hm.alchemy.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.hm.alchemy.rest.service.model.IModel;

public interface IResource<T extends IModel> {
	
	/**
	 * @param modal - The model resource that will be created.
	 * @return		- HTTP response, with a valid Status code and either an 
	 * 			      error message if or location header to newly created model 
	 */
	@POST
	@Consumes("application/json")
	Response create(T modal);
	
	/**
	 * @return	- a List of {@link IModel} objects (person or address) in JSON format
	 */
	@GET	
	@Produces("application/json") 
	Response findAll();
	
	/**
	 * @param id - represents the unique ID linked to the {@link IModel} object
	 * @return   - the record that is found with the ID supplied in JSON format
	 */
	@GET
	@Path("/{id}")
	@Produces("application/json") 
	Response read(@PathParam("id") int id);
	
}

