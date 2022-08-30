package com.example.Excercise1.MarDao;

import com.example.Excercise1.exceptions.XifinDataNotFoundException;
import com.example.Excercise1.mars.ValueObject;
import com.example.Excercise1.models.MultipleMappedRowsIterator;
import com.example.Excercise1.models.ValueObjectIterator;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public interface MarsDaoManager {
    <T extends ValueObject> T getValueObject(String sql, List params, Class<T> valueObjectClass) throws XifinDataNotFoundException;
    <T extends ValueObject> List<T> getValueObject(String sql, Class<T> valueObjectClass) throws XifinDataNotFoundException;


    <T extends ValueObject> List<T> getValueObjects(Class<T> valueObjectClass, String sql, Object... params);
    void loadValueObject(ValueObject valueObject) throws XifinDataNotFoundException;

    <T extends ValueObject> List<T> getValueObjects(String sql, Class<T> valueObjectClass);


    /**
     * Gets a list of ValueObjects using fectSize for better performance
     *
     * @param sql              The sql to retrieve the records
     * @param valueObjectClass The class of the value object.
     * @param fetchSize  in order to reduce round trips between client and server we need to set proper fecth size to increase performance
     * @return a list of ValueObjects
     */
    <T extends ValueObject> List<T> getValueObjects(String sql, Class<T> valueObjectClass,int fetchSize);


    /**
     * Uses a <code>ValueObject</code> to make an update, insert, or delete in the datasource.
     *
     * @param valueObject value object to set
     */
    void setValueObject(ValueObject valueObject);


    /**
     * Uses a <code>ValueObject</code> to make an update, insert, or delete in the datasource.
     *
     * @param connection  connection
     * @param valueObject value object
     */
    void setValueObject(Connection connection, ValueObject valueObject);


    /**
     * Uses  <code>ValueObject</code>s to make updates, inserts, or deletes in the datasource.
     *
     * @param valueObjects list of value objects
     */
    void setValueObjects(List valueObjects);

    /**
     * Gets a list of ValueObjects.
     *
     * @param sql              The sql to retrieve the records
     * @param params           The parameters for the sql
     * @param valueObjectClass The class of the value object.
     * @return a list of ValueObjects
     */
    <T extends ValueObject> List<T> getValueObjects(String sql, List params, Class<T> valueObjectClass);

    /**
     * Gets a list of ValueObjects.
     *
     * @param sql              The sql to retrieve the records
     * @param startAt          starting index
     * @param recordsToReturn  number of records to return
     * @param valueObjectClass The class of the value object.
     * @return a list of ValueObjects
     */
    <T extends ValueObject> List<T> getValueObjectsStartingAtRecord(String sql, long startAt, int recordsToReturn, Class<T> valueObjectClass);

    /**
     * Gets a list of ValueObjects.
     *
     * @param sql              The sql to retrieve the records
     * @param startAt          starting index
     * @param recordsToReturn  number of records to return
     * @param valueObjectClass The class of the value object.
     * @return a list of ValueObjects
     */
    <T extends ValueObject> List<T> getValueObjectsStartingAtRecord(String sql, List params, long startAt, int recordsToReturn, Class<T> valueObjectClass);

    /**
     * Executes a simple sql and returns a single row of data.  If the query returns
     * multiple rows, only the first row is returned. If a column in the row returned is null,
     * that column will be included in the list. Each value is represented as a
     * XifinJdbcDaoManager.Value object
     *
     * @param sql The sql to retrieve the data.
     * @return A list of XifinJdbcDaoManager.Value objects representing the value of each column
     *         returned. NULL objects will possibly be returned in the list.
     */
    List<XifinJdbcDaoManager.Value> getSingleRow(String sql);

    /**
     * Executes a simple sql and returns a single row of data.  If the query returns
     * multiple rows, only the first row is returned. If a column in the row returned is null,
     * that column will be included in the list. Each value is represented as a
     * XifinJdbcDaoManager.Value object
     *
     * @param sql    The sql to retrieve the data.
     * @param params The parameters to set.
     * @return A list of XifinJdbcDaoManager.Value objects representing the value of each column
     *         returned. NULL objects will possibly be returned in the list.
     */
    List<XifinJdbcDaoManager.Value> getSingleRow(String sql, List params);

    /**
     * Executes a sql and returns multiple rows of mapped data. If a column in the row returned is null,
     * that column will be included in the list. Each value is represented as a
     * XifinJdbcDaoManager.Value object.  This should be a bit safer in retrieving values since they're by column name instead
     * of column order.
     *
     * @param sql    The sql to retrieve the data.
     * @param params The parameters to set.
     * @return A List of List of XifinJdbcDaoManager.Value objects representing the values returned.
     *         The outer list represents the rows, and the inner Map represents the columns in each row.  This method
     *         gurantees not to return empty rows. NULL objects will possibly be in the inner Map.
     */
    List<List<XifinJdbcDaoManager.Value>> getMultipleRows(String sql, List params);

    /**
     * Executes a sql and returns multiple rows of data. If a column in the row returned is null,
     * that column will be included in the list. Each value is represented as a
     * XifinJdbcDaoManager.Value object
     *
     * @param sql The sql to retrieve the data.
     * @return A List of List of XifinJdbcDaoManager.Value objects representing the values returned.
     *         The outer list represents the rows, and the inner list represents the columns in each row.  This method
     *         gurantees not to return empty rows. NULL objects will possibly be in the inner list.
     */
    List<List<XifinJdbcDaoManager.Value>> getMultipleRows(String sql);

    /**
     * Executes a sql and returns multiple rows of mapped data. If a column in the row returned is null,
     * that column will be included in the list. Each value is represented as a
     * XifinJdbcDaoManager.Value object.  This should be a bit safer in retrieving values since they're by column name instead
     * of column order.
     *
     * @param sql The sql to retrieve the data.
     * @return A List of Maps keyed by the column name to XifinJdbcDaoManager.Value objects representing the values returned.
     *         The outer list represents the rows, and the inner Map represents the columns in each row.  This method
     *         gurantees not to return empty rows. NULL objects will possibly be in the inner Map.
     */
    List<Map<String, XifinJdbcDaoManager.Value>> getMultipleMappedRows(String sql);

    /**
     * Executes a sql and returns multiple rows of mapped data. If a column in the row returned is null,
     * that column will be included in the list. Each value is represented as a
     * XifinJdbcDaoManager.Value object.  This should be a bit safer in retrieving values since they're by column name instead
     * of column order.
     *
     * @param sql    The sql to retrieve the data.
     * @param params List of params
     * @return A List of Maps keyed by the column name to XifinJdbcDaoManager.Value objects representing the values returned.
     *         The outer list represents the rows, and the inner Map represents the columns in each row.  This method
     *         gurantees not to return empty rows. NULL objects will possibly be in the inner Map.
     */
    List<Map<String, XifinJdbcDaoManager.Value>> getMultipleMappedRows(String sql, List params);


    /**
     * Executes a sql and returns multiple rows of mapped data.
     * @param sql
     * @param startAt
     * @param recordsToReturn
     * @return
     */
    List<Map<String,XifinJdbcDaoManager.Value>> getMultipleMappedRowsStartingAtRecord(String sql, long startAt, int recordsToReturn);

    /**
     * Please use this method only if you cannot create DTO /domain objects and sqls are very complex.
     * Executes a sql and returns multiple rows of mapped data.
     * @param sql
     * @param params
     * @param startAt
     * @param recordsToReturn
     * @return
     */
    List<Map<String,XifinJdbcDaoManager.Value>> getMultipleMappedRowsStartingAtRecord(String sql,List params, long startAt, int recordsToReturn);

    /**
     * Executes a sql and returns multiple rows of data. If a column in the row returned is null,
     * that column will be included in the list. Each value is represented as a XifinJdbcDaoManager.Value object
     *
     * @param sql             sql string
     * @param params          The parameters to set.
     * @param startAt         start wih record
     * @param recordsToReturn number of records to return
     * @return A List of List of XifinJdbcDaoManager.Value objects representing the values returned.
     *         The outer list represents the rows, and the inner list represents the columns in each row.  This method
     *         gurantees not to return empty rows. NULL objects will possibly be in the inner list.
     */
    List<List<XifinJdbcDaoManager.Value>> getMultipleRowsStartingAtRecord(String sql, List params, long startAt, int recordsToReturn);

    /**
     * Executes a sql and returns multiple rows of data. If a column in the row returned is null,
     * that column will be included in the list. Each value is represented as a XifinJdbcDaoManager.Value object
     *
     * @param sql             string
     * @param startAt         starting index
     * @param recordsToReturn number of records to return
     * @return A List of List of XifinJdbcDaoManager.Value objects representing the values returned.
     *         The outer list represents the rows, and the inner list represents the columns in each row.  This method
     *         gurantees not to return empty rows. NULL objects will possibly be in the inner list.
     */
    List<List<XifinJdbcDaoManager.Value>> getMultipleRowsStartingAtRecord(String sql, long startAt, int recordsToReturn);


    /**
     * Executes an sql INSERT, UPDATE, or DELETE command.
     *
     * @param sql    The sql to perform the action
     * @param params The parameters to set.
     * @return the number of rows affected
     */
    int executeSQL(String sql, List params);

    /**
     * Executes an sql INSERT, UPDATE, or DELETE command.
     *
     * @param sql the sql to perform the action
     * @return the number of rows affected
     */

    int executeSQL(String sql);


    /**
     * This method will perform batch select. In other words, it will re-use prepared statement
     * to execute different set of parameters from the batch list.
     *
     * @param sql         statment that will be executed multiple times with different parameters.
     * @param batchParams is a List of batch parameters; each batch will contain list of parameters.
     *                    NOTE: The number of parameters must be the same in each batch list.
     * @return List of rows from all batch parameters.
     */
    List<List<XifinJdbcDaoManager.Value>> getMultipleRowsBatch(String sql, List<List<Object>> batchParams);

    /**
     * This method will perform batch update. In other words, it will batch up INSERT, UPDATE, or MERGE statements
     * with different parameters, and then execute all updates at once.
     *
     * @param sql         is a String representings statement that will execute the batch update (i.e. INSERT, UPDATE, oe MERGE)
     * @param batchParams is a List where each element is a List of parameters to be added to the batch.
     * @return int[] array of update counts.
     * @see java.sql.Statement#executeBatch() for mor information
     */
    int[] batchUpdate(String sql, List batchParams);

    Connection getConnection();

    void closeConnection(Connection connection);

    void closeStatement(Statement stmt);

    /**
     * Execute oralce procedure
     *
     * @param procedureName procedureName
     * @param params
     */
    void executeProcedure(String procedureName, List<Object> params);
    /**
     * Execute oracle function with return value
     *
     * @param procedureName procedureName
     */
    String executeFunction(String procedureName);

    /**
     * Execute oralce procedure return value
     *
     * @param procedureName procedureName
     * @param inputParams
     * @param outputParams
     * @return
     */
    Map<Integer, Object> executeProcedure(String procedureName, Map<Integer, Object> inputParams, Map<Integer, Integer> outputParams);

    /**
     * get next oracle sequence based on sequence name
     * @param seqName sequence name
     * @return id
     */
    int getNextSequenceFromOracle(String seqName) ;

    /**
     *  Get bulk value Objects
     * @param valueObjectClass valueObjectClass
     * @param sql sql
     * @param fetchSize fetchSize
     * @param params params
     * @param <T>
     * @return ValueObjectIterator
     */
    <T extends  ValueObject> ValueObjectIterator getValueObjectsIterator(Class<T> valueObjectClass, String sql, int fetchSize, Object... params) ;

    /**
     *  Get bulk Mapped Rows Objects
     * @param sql sql
     * @param fetchSize fetchSize
     * @param params params
     * @param <T>
     * @return MultipleMappedRowsIterator
     */
    <T extends  Map> MultipleMappedRowsIterator getMultipleMappedRowsIterator(String sql, int fetchSize, Object... params);
}
