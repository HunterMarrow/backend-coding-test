/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Strings.java
 *
 * Copyright 2015 Hunter Marrow (PTY) Ltd. All rights reserved.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.hm.alchemy.rest.service.utils;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 *
 */
public class Strings {
	private static final String BUNDLE_NAME = "com.hm.alchemy.rest.service.utils.strings";

	public static String getString(String propertyName) throws NullPointerException {
		ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME);
		Enumeration <String> keys = rb.getKeys();
		
		String value = null; 
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			
			if (key.equalsIgnoreCase(propertyName)) {
				value = rb.getString(key);
				break;				
			}					
		}	
		
		if (value == null) {
			throw new NullPointerException("Could not find property with key: " + propertyName);			
		}
		
		return value; 	
	}
}
