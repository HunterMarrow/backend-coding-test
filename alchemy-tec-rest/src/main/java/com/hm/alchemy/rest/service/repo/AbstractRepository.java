/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * AbstractRepository.java
 *
 * Copyright 2015 Hunter Marrow (PTY) Ltd. All rights reserved.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.hm.alchemy.rest.service.repo;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.hm.alchemy.rest.service.model.IModel;
import com.hm.alchemy.rest.service.utils.Strings;

public abstract class AbstractRepository<T extends IModel> implements IRepository<T>{	
	
	private static final String JDBC_RESOURCE = "jdbcResource";

	private static final Logger LOGGER = Logger.getLogger(AbstractRepository.class.getName());

	private static final String FIND_ALL = ".FindAll";
	private static final String READ     = ".Read";
	private static final String CREATE   = ".Create";

	private Connection connection;	
	
	public AbstractRepository() throws Exception {
		InitialContext ctx = new InitialContext();
		String contextParam = Strings.getString(JDBC_RESOURCE); 
		
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Looking jdbc resource at JNDI address: " + contextParam);
		}

		DataSource ds = (DataSource)ctx.lookup(contextParam);

		connection = ds.getConnection();
	}

	private T createModel() throws InstantiationException, IllegalAccessException {
		return getModelClass().newInstance();
	}

	/**
	 * @return - The Class representing the current model that is being processed 
	 */
	protected abstract Class<T> getModelClass();
	
	
	/**
	 * @return - The current processed Class's simple name
	 */
	protected abstract String getModelClassName(); 
	
	/**
	 * @param ps			- the {@link PreparedStatement} object that will be used to execute the SQL
	 * @param model			- the current {@link IModel} object that is currently being processed
	 * @throws SQLException	- thrown when an error occurs while trying to add the {@link IModel} object properties to the {@link PreparedStatement} object
	 */
	protected abstract void setValues(PreparedStatement ps, T model) throws SQLException; 

	@Override
	public T create(T model) throws Exception {

		String sql = Strings.getString(getModelClassName() + CREATE);

		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("About to create " + getModelClassName() + " record with data " + model + " and sql " + sql);
		}

		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			setValues(ps, model);

			ps.executeUpdate(); 						
			connection.commit();
			
			try (ResultSet rs = ps.getGeneratedKeys()) {					
				if (rs.next()) {
					model.setId((Long) rs.getObject(1));										
					return model; 
				}
				throw new SQLException("Unable to create new " + getModelClassName() + " record with model: " + model);
			}						
		}	
		catch (Exception e) {
			connection.rollback();
			throw new SQLException("Could not create new  " + getModelClassName() + " record with model: " + model, e);
		}
	}

	private List<T> modelsFromResultSet(ResultSet rs) throws Exception {		
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("About to create list of " + getModelClassName() + " records from a result set");
		}

		List<T> models = new ArrayList<>();		
		BeanInfo bi = Introspector.getBeanInfo(getModelClass());

		PropertyDescriptor[] descriptors = bi.getPropertyDescriptors();

		while (rs.next()) {

			T model = createModel();

			for (PropertyDescriptor pd : descriptors) {

				Method m = pd.getWriteMethod(); 
				Method m1 = pd.getReadMethod(); 

				if (m!=null && m1!=null) {				
					Object value = null; 
										
					String propertyName = pd.getName();
					
					if (m1.getAnnotation(DatabaseField.class) != null) {
						String dbFieldName = m1.getAnnotation(DatabaseField.class).name();
						
						if (dbFieldName != null && !dbFieldName.isEmpty()) {
							propertyName = dbFieldName; 
						}
					}					
					
					Class<?> type = m1.getReturnType();
					
					value = type.cast(rs.getObject(propertyName));									

					if (value != null) {
						m.invoke(model, value);
					}
				}
			}

			models.add(model);
		}

		return models;
	}

	@Override
	public T read(Long id) throws Exception {
		String sql = Strings.getString(getModelClassName() + READ);

		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("About to read " + getModelClassName() + " record with ID " + id + " and sql " + sql);
		}

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setObject(1, id);

			try (ResultSet resultSet = ps.executeQuery()) {

				List<T> models = modelsFromResultSet(resultSet); 

				if (models.isEmpty()) {
					return null; 
				}

				if (models.size() > 1) {
					throw new SQLException("Too many " + getModelClassName() + " records were found for ID " + id);
				}

				return models.get(0);
			}
		}		
	}



	@Override
	public List<T> find() throws Exception {

		String sql = Strings.getString(getModelClassName() + FIND_ALL);

		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("About to find all " + getModelClassName() + " records with sql: " + sql);
		}

		try (PreparedStatement ps = connection.prepareStatement(sql);
			 ResultSet resultSet = ps.executeQuery()) {
			return modelsFromResultSet(resultSet);
		}

	}
}
