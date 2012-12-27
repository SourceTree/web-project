/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * HibernateUtil.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.support;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.sourcetree.dto.ListProp;

/**
 * @author Venkaiah Chowdary Koneru
 */
public class HibernateUtil
{
	private static final long START_INDEX = -2;

	/**
	 * Constructor. private to prevent unnecessary instantiation.
	 */
	private HibernateUtil()
	{
	}

	/**
	 * Helper method to ease the query process.
	 * 
	 * @param sessionFactory
	 *            must not be null.
	 * @param countQueryString
	 *            will be used only when queryProp.pagination object is not
	 *            null. "select count(*)" will be used if this string is blank.
	 * @param selectAliasAndPropertyMap
	 *            alias and the associated property name map for query Select
	 *            section and also Sorting purposes.
	 * @param queryStringWithoutSelect
	 *            must not be blank. Must not contain query Select section also.
	 * @param parameters
	 *            can be null if no parameter is required.
	 * @param listProp
	 *            can be null.
	 * @param resultClass
	 *            can be null.
	 * 
	 * @return List of the result object. Total records count will be returned
	 *         in the queryProp.pagination.totalRecords property.
	 */
	public static List<?> list(SessionFactory sessionFactory,
			final String countQueryString,
			final Map<String, String> selectAliasAndPropertyMap,
			final String queryStringWithoutSelect,
			final Map<String, Object> parameters, final ListProp listProp,
			final Class<?> resultClass)
	{
		return list(sessionFactory, countQueryString,
				generateSelect(selectAliasAndPropertyMap),
				queryStringWithoutSelect, selectAliasAndPropertyMap,
				parameters, listProp, resultClass, false);
	}

	/**
	 * Helper method to ease the query process.
	 * 
	 * @param sessionFactory
	 *            must not be null.
	 * @param countQueryString
	 *            will be used only when queryProp.pagination object is not
	 *            null. "select count(*)" will be used if this string is blank.
	 * @param selectAliasAndPropertyMap
	 *            alias and the associated property name map for query Select
	 *            section and also Sorting purposes.
	 * @param queryStringWithoutSelect
	 *            must not be blank. Must not contain query Select section also.
	 * @param parameters
	 *            can be null if no parameter is required.
	 * @param listProp
	 *            can be null.
	 * @param resultClass
	 *            can be null.
	 * @param distinct
	 *            to include the distinct keyword in select query
	 * @return List of the result object. Total records count will be returned
	 *         in the queryProp.pagination.totalRecords property.
	 */
	public static List<?> list(SessionFactory sessionFactory,
			final String countQueryString,
			final Map<String, String> selectAliasAndPropertyMap,
			final String queryStringWithoutSelect,
			final Map<String, Object> parameters, final ListProp listProp,
			final Class<?> resultClass, boolean distinct)
	{

		return list(sessionFactory, countQueryString,
				generateSelect(selectAliasAndPropertyMap),
				queryStringWithoutSelect, selectAliasAndPropertyMap,
				parameters, listProp, resultClass, distinct);
	}

	/**
	 * Helper method to ease the query process.
	 * 
	 * @param sessionFactory
	 *            must not be null.
	 * @param countQueryString
	 *            will be used only when queryProp.pagination object is not
	 *            null. "select count(*)" will be used if this string is blank.
	 * @param selectQueryString
	 *            can be blank. Just the select section, for example:
	 *            "select a as a, b as b, ...".
	 * @param queryStringWithoutSelect
	 *            must not be blank. Must not contain query Select section also.
	 * @param aliasAndPropertyMap
	 *            alias and the associated property name map for Sorting
	 *            purposes. Can be null if no sorting required.
	 * @param parameters
	 *            can be null if no parameter is required.
	 * @param resultClass
	 *            can be null.
	 * @param listProp
	 *            can be null.
	 * @param distinct
	 *            to include the distinct keyword in select query
	 * @return List of the result object. Total records count will be returned
	 *         in the queryProp.pagination.totalRecords property.
	 */
	public static List<?> list(SessionFactory sessionFactory,
			final String countQueryString, final String selectQueryString,
			final String queryStringWithoutSelect,
			final Map<String, String> aliasAndPropertyMap,
			final Map<String, Object> parameters, final ListProp listProp,
			final Class<?> resultClass, boolean distinct)
	{
		if (sessionFactory == null
				|| StringUtils.isBlank(queryStringWithoutSelect))
		{
			return null;
		}

		Session session = sessionFactory.getCurrentSession();

		String select = "select ";
		if (distinct)
		{
			select = "select distinct ";
		}

		String groupByQueryString = "";

		String finalCountQueryString = countQueryString;

		String sortingString = "";
		if (listProp != null)
		{
			if (listProp.getStartIndex() > START_INDEX)
			{

				if (StringUtils.isBlank(countQueryString))
				{
					finalCountQueryString = "select count(*) ";
				}

				Query query = session.createQuery(finalCountQueryString
						+ queryStringWithoutSelect + groupByQueryString);

				setParameters(parameters, query);

				List<?> countList = query.list();
				if (CommonsUtil.isEmpty(countList))
				{
					listProp.setTotalRecords(0);
				}
				else
				{
					listProp.setTotalRecords(((Long) countList.iterator()
							.next()).longValue());
				}
			}

			sortingString = generateSorting(listProp, aliasAndPropertyMap);
		}

		Query query = session
				.createQuery(select
						+ (selectQueryString == null ? "" : selectQueryString)
						+ queryStringWithoutSelect + groupByQueryString
						+ sortingString);
		if (resultClass != null)
		{
			query.setResultTransformer(Transformers.aliasToBean(resultClass));
		}

		setParameters(parameters, query);

		setPagination(listProp, query);
		return query.list();
	}

	/**
	 * Helper method to ease the query process for named queries
	 * 
	 * @param sessionFactory
	 *            must not be null.
	 * @param query
	 *            must not be blank. Must be the Named query string.
	 * @param countQuery
	 *            must not be blank. Must be the Named count query string.
	 * @param listProp
	 *            can be null.
	 * @param resultClass
	 *            can be null.
	 * @param parameters
	 *            can be null if no parameter is required.
	 * @return List of the result object.
	 */
	public static List<?> namedQueryList(SessionFactory sessionFactory,
			final String query, final String countQuery,
			final ListProp listProp, final Class<?> resultClass,
			final List<Object> parameters)
	{
		if (sessionFactory == null || query == null || countQuery == null)
		{
			return null;
		}

		Session session = sessionFactory.getCurrentSession();

		if (listProp != null && listProp.getStartIndex() > START_INDEX)
		{
			Query namedCountQury = session.getNamedQuery(countQuery);
			setParameters(parameters, namedCountQury);

			List<?> countList = namedCountQury.list();
			if (CommonsUtil.isEmpty(countList))
			{
				listProp.setTotalRecords(0);
			}
			else
			{
				listProp.setTotalRecords(((BigInteger) countList.iterator()
						.next()).longValue());
			}
		}

		Query namedQuery = session.getNamedQuery(query);

		namedQuery = session.createSQLQuery(namedQuery.getQueryString()
				+ generateSorting(listProp));

		namedQuery.setResultTransformer(Transformers.aliasToBean(resultClass));
		setParameters(parameters, namedQuery);

		setPagination(listProp, namedQuery);

		return namedQuery.list();
	}

	/**
	 * Append the select part of the hql based on the property and alias map.
	 * 
	 * @param aliasAndPropertyMap
	 *            alias and the associated property name map.
	 * 
	 * @return String after append the property and alias delimited by comma.
	 */
	public static String generateSelect(Map<String, String> aliasAndPropertyMap)
	{
		if (CommonsUtil.isEmpty(aliasAndPropertyMap))
		{
			return "";
		}

		StringBuffer sb = new StringBuffer();
		sb.append(" ");
		for (Entry<String, String> propertyAndAlias : aliasAndPropertyMap
				.entrySet())
		{
			sb.append(propertyAndAlias.getValue());
			sb.append(" as ");
			sb.append(propertyAndAlias.getKey());
			sb.append(",");
		}

		sb.deleteCharAt(sb.length() - 1);
		sb.append(" ");

		return sb.toString();
	}

	/**
	 * to generate the group by clause
	 * 
	 * @param aliasAndPropertyMap
	 * @return string representation of groupby query
	 */
	private static String generateSelectForGroupBy(
			Map<String, String> aliasAndPropertyMap)
	{
		if (CommonsUtil.isEmpty(aliasAndPropertyMap))
		{
			return "";
		}

		StringBuffer sb = new StringBuffer();
		sb.append(" ");
		for (Entry<String, String> propertyAndAlias : aliasAndPropertyMap
				.entrySet())
		{
			sb.append(propertyAndAlias.getValue());

			sb.append(",");
		}

		sb.deleteCharAt(sb.length() - 1);
		sb.append(" ");

		return sb.toString();
	}

	/**
	 * Set parameters value into the query.
	 * 
	 * @param parameters
	 *            Map containing key (i.e. key of the parameter in the query,
	 *            e.g. :key1 written in the hql).
	 * @param query
	 *            result query object.
	 */
	public static void setParameters(List<Object> parameters, Query query)
	{
		if ((parameters == null) || (query == null))
		{
			return;
		}

		for (int i = 0; i < parameters.size(); i++)
		{
			query.setParameter(i, parameters.get(i));
		}
	}

	/**
	 * Set parameters value into the query.
	 * 
	 * @param parameters
	 *            Map containing key (i.e. key of the parameter in the query,
	 *            e.g. :key1 written in the hql).
	 * @param query
	 *            result query object.
	 */
	public static void setParameters(Map<String, Object> parameters, Query query)
	{
		if ((parameters == null) || (query == null))
		{
			return;
		}

		for (Entry<String, Object> parameter : parameters.entrySet())
		{
			query.setParameter(parameter.getKey(), parameter.getValue());
		}
	}

	/**
	 * Append sorting property and ascending or descending value.
	 * 
	 * @param listProp
	 *            listProp object.
	 * @param aliasAndPropertyMap
	 *            alias and the associated property name map. This field is
	 *            optional. if it is null or not containing the property for the
	 *            sorting, then the sorting.property value will be used.
	 * 
	 * @return String after append the property and asc/desc.
	 */
	public static String generateSorting(ListProp listProp,
			Map<String, String> aliasAndPropertyMap)
	{
		if (listProp == null || StringUtils.isBlank(listProp.getSortProperty()))
		{
			return "";
		}

		String sortProperty = listProp.getSortProperty();

		if (aliasAndPropertyMap != null
				&& aliasAndPropertyMap.containsKey(sortProperty))
		{
			sortProperty = aliasAndPropertyMap.get(sortProperty);
		}

		return " order by " + sortProperty
				+ (listProp.isSortAscending() ? " asc " : " desc ");
	}

	/**
	 * generates the query with order by clause. Use it for the named queries.
	 * 
	 * @param listProp
	 *            list prop object
	 * @return query as a string
	 */
	private static String generateSorting(ListProp listProp)
	{
		if (listProp == null || StringUtils.isBlank(listProp.getSortProperty()))
		{
			return "";
		}

		String sortProperty = listProp.getSortProperty();

		return " order by " + sortProperty
				+ (listProp.isSortAscending() ? " asc" : " desc");
	}

	/**
	 * Set pagination values into the query object.
	 * 
	 * also calculates the number of pages.
	 * 
	 * @param listProp
	 *            listProp object.
	 * @param query
	 *            pagination values will be set into this query object.
	 */
	public static void setPagination(ListProp listProp, Query query)
	{
		if (listProp == null || query == null
				|| !(listProp.getStartIndex() > START_INDEX))
		{
			return;
		}

		if (listProp.getStartIndex() > -1)
		{
			if (listProp.getTotalRecords() == -1)
			{
				throw new IllegalStateException("Total records not set.");
			}

			if (listProp.getTotalRecords() == 0) // no record
			{
				listProp.setStartIndex(-1);
				listProp.setEndIndex(-1);
			}
			else
			{
				if (listProp.getStartIndex() >= listProp.getTotalRecords())
				{
					listProp.setStartIndex(listProp.getTotalRecords() - 1);
				}
				if (listProp.getEndIndex() >= listProp.getTotalRecords())
				{
					listProp.setEndIndex(listProp.getTotalRecords() - 1);
				}
			}

			query.setFirstResult((int) listProp.getStartIndex());
			query.setMaxResults((int) (listProp.getEndIndex()
					- listProp.getStartIndex() + 1));
		}
	}

	/**
	 * Group result by list
	 * 
	 * @param sessionFactory
	 *            must not be null.
	 * @param countQueryString
	 *            must not be blank. Must be the Named count query string
	 * @param selectAliasAndPropertyMap
	 *            alias and the associated property name map for query Select
	 *            section and also Sorting purposes.
	 * @param groupByAliasAndPropertyMap
	 *            alias and the associated property name map for group by Select
	 *            section and also Sorting purposes.
	 * @param queryStringWithoutSelect
	 *            must not be blank. Must not contain query Select section also.
	 * @param parameters
	 *            can be null if no parameter is required.
	 * @param listProp
	 *            can be null
	 * @param resultClass
	 *            can be null.
	 * @return List of the result object.
	 */
	public static List<?> groupByList(SessionFactory sessionFactory,
			final String countQueryString,
			final Map<String, String> selectAliasAndPropertyMap,
			final Map<String, String> groupByAliasAndPropertyMap,
			final String queryStringWithoutSelect,
			final Map<String, Object> parameters, final ListProp listProp,
			final Class<?> resultClass)
	{

		return groupByList(sessionFactory, countQueryString,
				generateSelect(selectAliasAndPropertyMap), " group by "
						+ generateSelectForGroupBy(groupByAliasAndPropertyMap),
				queryStringWithoutSelect, selectAliasAndPropertyMap,
				parameters, listProp, resultClass);
	}

	/**
	 * Group query result
	 * 
	 * @param sessionFactory
	 *            must not be null.
	 * @param countQueryString
	 *            will be used only when queryProp.pagination object is not
	 *            null. "select count(*)" will be used if this string is blank.
	 * @param selectQueryString
	 *            can be blank. Just the select section, for example:
	 *            "select a as a, b as b, ...".
	 * @param groupByQueryString
	 *            can be blank. just the group by clause of the query.
	 * @param queryStringWithoutSelect
	 *            must not be blank. Must not contain query Select section also.
	 * @param aliasAndPropertyMap
	 *            alias and the associated property name map for Sorting
	 *            purposes. Can be null if no sorting required.
	 * @param parameters
	 *            can be null if no parameter is required.
	 * @param resultClass
	 *            can be null.
	 * @param listProp
	 *            can be null
	 * 
	 * @return List of the result object.
	 */
	public static List<?> groupByList(SessionFactory sessionFactory,
			final String countQueryString, final String selectQueryString,
			final String groupByQueryString,
			final String queryStringWithoutSelect,
			final Map<String, String> aliasAndPropertyMap,
			final Map<String, Object> parameters, final ListProp listProp,
			final Class<?> resultClass)
	{
		if (sessionFactory == null
				|| StringUtils.isBlank(queryStringWithoutSelect))
		{
			return null;
		}

		Session session = sessionFactory.getCurrentSession();

		String select = "select ";

		String sortingString = "";
		if (listProp != null)
		{
			if (listProp.getStartIndex() > START_INDEX)
			{
				String finalCountQueryString = countQueryString;
				if (countQueryString == null
						|| countQueryString.trim().length() == 0)
				{
					finalCountQueryString = "select count(*) ";
				}

				Query query = session.createQuery(finalCountQueryString
						+ queryStringWithoutSelect + groupByQueryString);

				setParameters(parameters, query);

				List<?> countList = query.list();

				if (!CommonsUtil.isEmpty(countList))
				{
					listProp.setTotalRecords(countList.size());
				}
				else
				{
					listProp.setTotalRecords(0);
				}
			}

			sortingString = generateSorting(listProp, aliasAndPropertyMap);
		}

		Query query = session
				.createQuery(select
						+ (selectQueryString == null ? "" : selectQueryString)
						+ queryStringWithoutSelect + groupByQueryString
						+ sortingString);

		if (resultClass != null)
		{
			query.setResultTransformer(Transformers.aliasToBean(resultClass));
		}

		setParameters(parameters, query);

		setPagination(listProp, query);

		return query.list();
	}

	/**
	 * Helper method to ease the query process for named queries
	 * 
	 * @param sessionFactory
	 *            must not be null.
	 * @param query
	 *            must not be blank. Must be the Named query string.
	 * @return object
	 */
	public static Object namedQueryForCount(SessionFactory sessionFactory,
			final String query)
	{
		if (sessionFactory == null || query == null)
		{
			return null;
		}

		Session session = sessionFactory.getCurrentSession();
		Query namedQuery = session.getNamedQuery(query);

		return namedQuery.uniqueResult();
	}
}
