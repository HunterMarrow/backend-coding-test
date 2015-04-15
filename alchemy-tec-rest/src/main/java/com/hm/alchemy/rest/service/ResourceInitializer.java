/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ResourceInitializer.java
 *
 * Copyright 2015 Hunter Marrow (PTY) Ltd. All rights reserved.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.hm.alchemy.rest.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/resources")
public class ResourceInitializer extends Application {
	private Set<Class<?>> clazzes = new HashSet<>();
	
	@Override
	public Set<Class<?>> getClasses() {	
		return clazzes;
	}
	
	public ResourceInitializer() {
		clazzes.add(ExpenseResource.class);		
	}
}
