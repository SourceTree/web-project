/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * GenericDAO.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.dao;

import java.io.Serializable;
import java.util.List;

import org.sourcetree.entity.AbstractEntity;
import org.sourcetree.enums.QueryCriteriaTypeEnum;

/**
 * Interface for a Data Access Object that can be used for a single specified
 * type domain object. A single instance implementing this interface can be used
 * only for the type of domain object specified in the type parameters.
 * 
 * @param <T>
 *            The type of the entity object for which this instance is to be
 *            used.
 * @param <ID>
 *            The type of the id of the entity object for which this instance is
 *            to be used.
 * 
 * @author Venkaiah Chowdary Koneru
 */
public interface GenericDAO<T extends AbstractEntity, ID extends Serializable>
{
	/**
	 * Entity passed to this method will be saved to the session.
	 * 
	 * @param newInstance
	 * @return id of the new entity object
	 */
	ID save(T newInstance);

	/**
	 * Entity object passed to this method will be updated into session.
	 * 
	 * @param transientObject
	 */
	void update(T transientObject);

	/**
	 * Entity object passed to this method will be deleted permanently.
	 * 
	 * @param persistentObject
	 */
	void delete(T persistentObject);

	/**
	 * Remove the entity with the specified type and id from the datastore.
	 * 
	 * @param id
	 * @return <code>true</code> if the object is found in the datastore and
	 *         deleted, <code>false</code> if the item is not found.
	 */
	boolean deleteById(ID id);

	/**
	 * <p>
	 * Get the entity with the specified type and id from the datastore.
	 * 
	 * <p>
	 * If none is found, return null.
	 * 
	 * @param id
	 *            id
	 * @return reference of the entity for the specified id
	 */
	T find(ID id);

	/**
	 * Get the entity with the specified value from the datastore.
	 * 
	 * @param key
	 *            entity property to check
	 * @param value
	 *            string value of the property to check against
	 * @return entity object for the specified key with value
	 */
	T findByParameter(String key, Object value);

	/**
	 * Get the entity with the specified value from the datastore. If
	 * <code>queryCriteriaTypeEnum</code> is IGNORE_CASE then Value must be of
	 * type String
	 * 
	 * @param key
	 *            entity property to check
	 * @param value
	 *            string value of the property to check against
	 * @param queryCriteriaTypeEnum
	 *            DELETED = to retrieve deleted, IGNORE_CASE = to retrieve by
	 *            ignoring case.
	 * @return entity object for the specified key with value
	 */
	T findByParameter(String key, Object value,
			QueryCriteriaTypeEnum queryCriteriaTypeEnum);

	/**
	 * Get the entity with the specified value from the datastore.
	 * 
	 * @param key
	 *            entity property to check
	 * @param value
	 *            string value of the property to check against
	 * @param retreiveDeleted
	 *            to include deleted data from datastore or not
	 * @param ignoreCase
	 *            to include ignore case criteria in query
	 * @return entity object for the specified key with value
	 */
	T findByParameter(String key, Object value, boolean retreiveDeleted,
			boolean ignoreCase);

	/**
	 * Get a list of all the objects of the specified type.
	 * 
	 * @return list of all objects of specified type
	 */
	List<T> findAll();

	/**
	 * Checks whether the specified type exists for the id
	 * 
	 * @param id
	 * @return <code>true</code> if the object is found in the datastore and
	 *         <code>false</code> if the item is not found.
	 */
	boolean exists(ID id);

	/**
	 * Checks whether the specified type exists for the passed-in key with value
	 * 
	 * @param key
	 *            entity property to check
	 * @param value
	 *            value of the property to check against
	 * @return <code>true</code> if the object is found in the datastore and
	 *         <code>false</code> if the item is not found.
	 */
	boolean existsByParameter(String key, String value);

	/**
	 * Checks whether the specified type exists for the passed-in key with value
	 * 
	 * @param key
	 *            entity property to check
	 * @param value
	 *            string value of the property to check against
	 * @param queryCriteriaTypeEnum
	 *            DELETED = to exclude deleted, 1 = to check by ignoring case
	 * @return <code>true</code> if the object is found in the datastore and
	 *         <code>false</code> if the item is not found.
	 */
	boolean existsByParameter(String key, String value,
			QueryCriteriaTypeEnum queryCriteriaTypeEnum);

	/**
	 * Checks whether the specified type exists for the passed-in key with value
	 * 
	 * @param key
	 *            entity property to check
	 * @param value
	 *            value of the property to check against
	 * @param excludeDeleted
	 *            to include deleted data from datastore or not
	 * @param ignoreCase
	 *            to check the existence of passed in value by ignore case
	 * @return <code>true</code> if the object is found in the datastore and
	 *         <code>false</code> if the item is not found.
	 */
	boolean existsByParameter(String key, String value, boolean excludeDeleted,
			boolean ignoreCase);
}
