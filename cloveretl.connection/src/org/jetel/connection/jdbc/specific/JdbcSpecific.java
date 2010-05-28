/*
 * jETeL/CloverETL - Java based ETL application framework.
 * Copyright (c) Javlin, a.s. (info@cloveretl.com)
 *  
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.jetel.connection.jdbc.specific;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jetel.connection.jdbc.DBConnection;
import org.jetel.connection.jdbc.SQLCloverStatement.QueryType;
import org.jetel.exception.JetelException;
import org.jetel.metadata.DataFieldMetadata;
import org.jetel.metadata.DataRecordMetadata;

/**
 * This interface represents customization in behaviour of a JDBC connection.
 * The class parameter of jdbcSpecific extension point has to implement this interface.
 * 
 * @author Martin Zatopek (martin.zatopek@javlinconsulting.cz)
 *         (c) Javlin Consulting (www.javlinconsulting.cz)
 *
 * @created Jun 3, 2008
 */
public interface JdbcSpecific {

	/**
	 * List of all considered database operations.
	 */
	public enum OperationType {
		READ,
		WRITE,
		CALL,
		TRANSACTION,
		UNKNOWN
	}
	
	/**
	 * List of all supported retrieving types of auto-generated keys. 
	 */
	public enum AutoGeneratedKeysType {
		MULTI,
		SINGLE,
		NONE
	}

	/**
	 * Creates java.sql.Connection, which should follow 
	 * all specific behaviour with the given operation type.
	 * @param connection
	 * @param operationType
	 * @return
	 * @throws JetelException
	 */
	public Connection createSQLConnection(DBConnection connection, OperationType operationType) throws JetelException;

	/**
	 * @return type of supported auto-generated key retrieving
	 */
	public AutoGeneratedKeysType getAutoKeyType();
	
	/**
	 * Via this method, it could be a result set optimized with the given operation type.
	 * @param resultSet
	 * @param operationType
	 */
	public void optimizeResultSet(ResultSet resultSet, OperationType operationType);
	
	/**
	 * Returns whether given Clover data type can be converted to given SQL data type
	 * (some conversions are ambiguous)
	 * 
	 * @param sqlType
	 * @param field
	 * @return
	 */
	
	public boolean isJetelTypeConvertible2sql(int sqlType, DataFieldMetadata field);
	
	/**
	 * Returns whether given Sql data type can be converted to given Clover data type
	 * (some conversions are ambiguous)
	 * 
	 * @param sqlType
	 * @param field
	 * @return
	 */
	public boolean isSqlTypeConvertible2jetel(int sqlType, DataFieldMetadata field);
	
	/**
	 * This method defines a conversion table from a sql type to a clover field type.	 * 
	 * @param sqlType
	 * @return
	 */
	public char sqlType2jetel(int sqlType);
	
	/**
	 * This method defines a conversion table from a clover field type to a sql type .
	 * @param field
	 * @return
	 */
	public int jetelType2sql(DataFieldMetadata field);
	
	/**
	 * Converts field Clover metadata into SQL DDL type...
	 * e.g. for a fixed length string Clover field it returns "CHAR(15)", etc.
	 * 
	 * Similar to sqlType2str but this one is more precise as it knows more about the particular clover field
	 * 
	 * @param field
	 * @return
	 */
	public String jetelType2sqlDDL(DataFieldMetadata field);
	
	/**
	 * @return class name where are constants with sql types
	 */
	public String getTypesClassName();
	
	/**
	 * @return constant for sql type, which will be regarded as "Result set"
	 */
	public String getResultSetParameterTypeField();

	/**
	 * @return a regex pattern matching all specific SQL comments
	 *
	 * @version 7th October 2009
	 * @since 7th October 2009
	 */
	public Pattern getCommentsPattern();

	/**
	 * This can be used to convert java sql types into real names of a data type instide the database
	 * @return Name of database specific data type corresponding to java.sql.Types type
	 */
	public String sqlType2str(int sqlType);

	/**
	 * Quotes (escapes) a given identifier according to the database specifics.
	 *
	 * @param identifier the identifier to be quoted
	 *
	 * @return the quoted identifier
	 */
	public String quoteIdentifier(String identifier);

	/**
	 * Transforms `query` into another query, which can be used to validate the original `query`
	 * Typically somehow adds some always failing where clause so that the query is never executed
	 * @param query Original query to be validated
	 * @param queryType Type of query
	 * @return A query that can be executed to validate original `query`
	 * @throws SQLException In can query cannot be generated or is otherwise invalid
	 */
	public String getValidateQuery(String query, QueryType queryType) throws SQLException;
	
	/**
	 * Returns whether given string is a literal in given db engine
	 * Examples:
	 * 'string' - true
	 * fieldName - false
	 * 123 - true
	 * SELECT - false
	 * , - false
	 * `name` - false
	 * etc.
	 * @return
	 */
	public boolean isLiteral(String s);
	
	/**
	 * Returns a ResultSet representing schemas
	 * @param dbMeta
	 * @return ArrayList<String[]> Returns arraylist of rows, each contains a pair of strings CATALOG, SCHEMA
	 * @throws SQLException
	 */
	public ArrayList<String> getSchemas(java.sql.Connection connection) throws SQLException;
	
	/**
	 * Returns a ResultSet representing tables in given database
	 * It has to extract it from dbMeta object
	 * 
	 * @param connection
	 * @param schema
	 * @return
	 */
	public ResultSet getTables(java.sql.Connection connection, String schema) throws SQLException;

	/**
	 * Returns columns metadata in given table.
	 * 
	 * @param connection
	 * @param schema
	 * @param table
	 * @return
	 */
	public ResultSetMetaData getColumns(java.sql.Connection connection, String schema, String table) throws SQLException;

    /**
     * Return select sql statement for given table.
     * Usually returns <code>select * from tablename</code>.
     * @param schema
     * @param table
     * @return
     */
    public String compileSelectQuery4Table(String schema, String table);

	/**
	 * Returns list of column names from DB
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> getColumns(java.sql.Connection connection) throws SQLException;
	
	/**
	 * Returns whether schema should be explicitly set to address table correctly in given db engine. 
	 * 
	 * @return
	 */
	public boolean isSchemaRequired();
	
	/**
	 * Returns table prefix, so it can be correctly fully qualified.
	 * 
	 * @param schema - table schema
	 * @param owner - table owner
	 * @param quoteIdentifiers - identifiers will be quoted if true. 
	 * @return
	 */
	public String getTablePrefix(String schema, String owner, boolean quoteIdentifiers);
	
	/**
	 * Returns ResultSetMetaData for table to obtain info about columns. This method should be
	 * overriden when getting columns info has to be done some special way.
	 * @param targetName
	 * @return
	 */
	public ResultSetMetaData getColumnsMetadata(java.sql.Connection connection, String targetName) throws SQLException;
	
	/**
	 * Created as a substitute for DatabaseMetaData.supportsGetGeneratedKeys called from
	 * DBOutputTable. According to JDBC specification, DatabaseMetaData.supportsGetGeneratedKeys
	 * returns true only if multi-row inserts are supported by auto-generated keys retrieval.
	 * In contrast, this (JdbcSpecific) method returns true also for databases which can return
	 * auto-generated keys only for single-row inserts.
	 * 
	 * @param metadata - metadata of a corresponding JDBC connection
	 * @return true iff a database supports at least single-row auto-generated keys retrieval
	 * @throws SQLException
	 */
	public boolean supportsGetGeneratedKeys(DatabaseMetaData metadata) throws SQLException;
	
	/**
	 * Returns list of java.sql.Types values - types of each column in resultset
	 * @param resultSetMetadata metadata of DB ResultSet
	 * @param cloverMetadata clover metadata
	 * @return list of constants for types. Constants are from java.sql.Types
	 * @throws SQLException
	 */
	public List<Integer> getFieldTypes(ResultSetMetaData resultSetMetadata, DataRecordMetadata cloverMetadata) throws SQLException;	
}
