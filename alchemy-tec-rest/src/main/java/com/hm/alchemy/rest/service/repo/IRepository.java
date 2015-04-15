/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * IRepository.java
 *
 * Copyright 2015 Hunter Marrow (PTY) Ltd. All rights reserved.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.hm.alchemy.rest.service.repo;

import java.util.List;

import com.hm.alchemy.rest.service.model.IModel;


public interface IRepository<T extends IModel> {
	/**
	 * @param model			- represents the {@link IModel} object that is currently being processed
	 * @return				- the newly created {@link IModel} object with the unique ID set
	 * @throws Exception	- thrown when an error occurs during the creation of the newly created {@link IModel} object
	 */
	T create(T model) throws Exception; 	
	
	
	/**
	 * @param id			- represents the unique ID for the {@link IModel} object
	 * @return				- the {@link IModel} record for the unique ID supplied
	 * @throws Exception	- thrown when an error occurs while trying to find the record for the ID supplied
	 */
	T read(Long id) throws Exception; 
	
	
	/**
	 * @return				- a {@link List} of {@link IModel} objects. If no records exists, then an empty list is returned 
	 * @throws Exception	- thrown when an error occurs while trying to find any {@link IModel} objects
	 */
	List<T> find() throws Exception;
}
