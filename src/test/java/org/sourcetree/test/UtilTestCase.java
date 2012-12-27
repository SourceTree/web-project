/* 
 * Copyright © 2012, Source Tree Solutions (M) SDN BHD, All Rights Reserved
 * 
 * UtilTestCase.java
 * Modification History
 * ***********************************************************
 * Date				Author						Comment
 * Dec 27, 2012		Venkaiah Chowdary Koneru	Created
 * ***********************************************************
 */
package org.sourcetree.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.JDBCException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author Venkaiah Chowdary Koneru
 * 
 */
public final class UtilTestCase
{
	/**
	 * 
	 * @param datasource
	 * @throws Exception
	 */
	public static void setupContext(BasicDataSource datasource)
			throws Exception
	{
		// START - dump test dummy data into HSQLDB
		Connection connection = null;
		Statement statement = null;
		long lineNo = 0L;
		BufferedReader reader = null;

		try
		{
			Resource re = new ClassPathResource("test-data.sql");
			connection = datasource.getConnection();
			statement = connection.createStatement();

			File file = re.getFile();

			lineNo = 0L;
			reader = new BufferedReader(new FileReader(file));

			for (String sql = reader.readLine(); sql != null; sql = reader
					.readLine())
			{
				String trimmedSql;

				lineNo++;
				trimmedSql = sql.trim();
				if (trimmedSql.length() == 0 || trimmedSql.startsWith("--")
						|| trimmedSql.startsWith("//")
						|| trimmedSql.startsWith("/*"))
				{
					continue;
				}
				else
				{
					statement.executeUpdate(trimmedSql);
				}
			}
			if (reader != null)
			{
				reader.close();
			}
		}
		catch (SQLException e)
		{
			throw new JDBCException("line no " + lineNo, e);
		}
		finally
		{
			if (statement != null)
			{
				statement.close();
			}
			if (connection != null)
			{
				connection.close();
			}
			if (reader != null)
			{
				reader.close();
			}
		}
		// FINISH - dump dummy test data
	}

	/**
	 * 
	 * @param datasource
	 * @param redisTemplate
	 * @throws Exception
	 */
	public static void afterTestMethod(BasicDataSource datasource)
			throws Exception
	{
		Connection connection = null;
		Statement statement = null;

		try
		{
			// Flush the HSQL DB
			connection = datasource.getConnection();
			statement = connection.createStatement();
			statement.execute("TRUNCATE SCHEMA public AND COMMIT");
		}
		catch (SQLException e)
		{
			throw new JDBCException("", e);
		}
		finally
		{
			if (statement != null)
			{
				statement.close();
			}
			if (connection != null)
			{
				connection.close();
			}
		}
	}
}
