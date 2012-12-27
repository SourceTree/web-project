/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * GenericDAOHibernate.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.dao;

import static org.sourcetree.AppConstants.SUPPRESS_WARNINGS_UNCHECKED;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.sourcetree.entity.AbstractEntity;
import org.sourcetree.enums.QueryCriteriaTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of <code>GenericDAO</code> using Hibernate. The SessionFactory
 * property is annotated for automatic injection.
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
public abstract class GenericDAOHibernate<T extends AbstractEntity, ID extends Serializable>
		implements GenericDAO<T, ID>
{
	@Autowired
	private SessionFactory sessionFactory;

	private Dialect dialect;

	/**
	 * executed by spring after properties set to set the dialect.
	 */
	@PostConstruct
	public void afterConstruct()
	{
		dialect = ((SessionFactoryImplementor) sessionFactory).getDialect();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings(SUPPRESS_WARNINGS_UNCHECKED)
	@Override
	public ID save(T newInstance)
	{
		return (ID) sessionFactory.getCurrentSession().save(newInstance);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(T transientObject)
	{
		sessionFactory.getCurrentSession().update(transientObject);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(T persistentObject)
	{
		sessionFactory.getCurrentSession().delete(persistentObject);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings(SUPPRESS_WARNINGS_UNCHECKED)
	@Override
	public T find(ID id)
	{
		if (id != null)
		{
			return (T) sessionFactory.getCurrentSession().get(getEntityClass(),
					id);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T findByParameter(String key, Object value)
	{
		return findByParameter(key, value, false, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T findByParameter(String key, Object value,
			QueryCriteriaTypeEnum queryCriteriaTypeEnum)
	{
		if (queryCriteriaTypeEnum == QueryCriteriaTypeEnum.DELETED)
		{
			return findByParameter(key, value, true, false);
		}

		return findByParameter(key, value, false, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings(SUPPRESS_WARNINGS_UNCHECKED)
	@Override
	public T findByParameter(String key, Object value, boolean retreiveDeleted,
			boolean ignoreCase)
	{
		if (!StringUtils.isBlank(key) && value != null)
		{
			StringBuilder queryStr = new StringBuilder("from ");
			queryStr.append(getEntityClass().getName()).append(" as _etc_");
			queryStr.append(" where ");

			if (!ignoreCase)
			{
				queryStr.append("_etc_.").append(key).append("= :VAL");
			}
			else
			{
				queryStr.append(dialect.getLowercaseFunction()).append("(")
						.append("_etc_.").append(key).append(")");
				queryStr.append("= :VAL");
			}

			if (!retreiveDeleted)
			{
				queryStr.append(" and _etc_.deleted= :DELETED");
			}

			Query query = sessionFactory.getCurrentSession().createQuery(
					queryStr.toString());

			if (!retreiveDeleted)
			{
				query.setParameter("DELETED", Boolean.FALSE);
			}

			if (!ignoreCase)
			{
				query.setParameter("VAL", value);
			}
			else
			{
				query.setParameter("VAL", value.toString().toLowerCase());
			}

			return (T) query.uniqueResult();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean deleteById(ID id)
	{
		if (id != null)
		{
			T entity = find(id);
			if (entity != null)
			{
				sessionFactory.getCurrentSession().delete(entity);
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings(SUPPRESS_WARNINGS_UNCHECKED)
	@Override
	public List<T> findAll()
	{
		return sessionFactory.getCurrentSession()
				.createCriteria(getEntityClass())
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(ID id)
	{
		if (id != null)
		{
			T entity = find(id);
			if (entity != null)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existsByParameter(String key, String value)
	{
		return existsByParameter(key, value, false, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existsByParameter(String key, String value,
			QueryCriteriaTypeEnum queryCriteriaTypeEnum)
	{
		if (queryCriteriaTypeEnum == QueryCriteriaTypeEnum.DELETED)
		{
			return existsByParameter(key, value, true, false);
		}

		return existsByParameter(key, value, false, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existsByParameter(String key, String value,
			boolean excludeDeleted, boolean ignoreCase)
	{
		if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value))
		{
			T entity = findByParameter(key, value, !excludeDeleted, ignoreCase);

			if (entity != null)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the sessionFactory
	 */
	protected SessionFactory getSessionFactory()
	{
		return this.sessionFactory;
	}

	/**
	 * @return the dialect
	 */
	protected Dialect getDialect()
	{
		return this.dialect;
	}

	/**
	 * Get the specified type from the DAO
	 * 
	 * @return type of the entity class
	 */
	protected abstract Class<T> getEntityClass();
}
