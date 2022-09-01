package com.example.Excercise1.MarDao;

import com.example.Excercise1.exceptions.XifinDataAccessFailureException;
import com.example.Excercise1.exceptions.XifinDataNotFoundException;
import com.example.Excercise1.mars.ValueObject;
import com.example.Excercise1.models.MultipleMappedRowsIterator;
import com.example.Excercise1.models.ValueObjectIterator;
import com.example.Excercise1.persistence.ErrorCodeMap;
import com.example.Excercise1.utility.Money;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;


public class XifinJdbcDaoManager implements MarsDaoManager{
    private DataSource dataSource;

    private static final int MAX_NUM_OF_PARAMS = 1000;
    private static final Logger log = Logger.getLogger(XifinJdbcDaoManager.class);
    private static final int FETCH_SIZE = 20;


    @Override
    public <T extends ValueObject> List<T> getValueObject(String sql, Class<T> valueObjectClass) {
        List<T> valueObjects = new ArrayList<T>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            stmt = connection.createStatement();
            stmt.setFetchSize(getFetchSize());

            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ValueObject valueObject = valueObjectClass.newInstance();
                valueObject.parseSql(rs);
                valueObject.setResultCode(0); // ErrorCodeMap.RECORD_FOUND
                valueObjects.add((T) valueObject);
            }
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("failure to get value objects " + sql,e);
        }
        finally {
            closeStatement(stmt);
            closeConnection(connection);
        }

        return valueObjects;
    }

    /**
     * method overrid provides easier calls with variable parameter list. The client code will no longer have to create a list,
     * add to it, and pass it in.
     *
     * @param valueObjectClass The the value object.
     * @param sql              The sql to retrieve the records
     * @param params           The parameters for the sql
     * @return a list of ValueObjects
     */
    public <T extends ValueObject> List<T> getValueObjects(Class<T> valueObjectClass, String sql, Object... params) {
        return getValueObjects(sql, Arrays.asList(params), valueObjectClass);
    }

    /**
     * Load a ValueObject using its representative record in the database.
     *
     * @param valueObject The the value object.
     * @throws
     *          if the record was not found
     */
    @Override
    public void loadValueObject(ValueObject valueObject) throws XifinDataNotFoundException {
        loadValueObject(valueObject.getSelectSql(), valueObject.getPkParams(), valueObject);
    }

    /**
     * Gets a list of ValueObjects.
     *
     * @param sql              The sql to retrieve the records
     * @param valueObjectClass The class of the value object.
     * @return a list of ValueObjects
     */
    @Override
    public <T extends ValueObject> List<T> getValueObjects(String sql, Class<T> valueObjectClass) {
        List<T> valueObjects = new ArrayList<T>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            stmt = connection.createStatement();
            stmt.setFetchSize(getFetchSize());

            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ValueObject valueObject = valueObjectClass.newInstance();
                valueObject.parseSql(rs);
                valueObject.setResultCode(ErrorCodeMap.RECORD_FOUND);
                valueObjects.add((T) valueObject);
            }
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("failure to get value objects " + sql,e);
        }
        finally {
            closeStatement(stmt);
            closeConnection(connection);
        }

        return valueObjects;
    }

    @Override
    public <T extends ValueObject> List<T> getValueObjects(String sql, Class<T> valueObjectClass, int fetchSize) {
        List<T> valueObjects = new ArrayList<T>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            stmt = connection.createStatement();
            stmt.setFetchSize(fetchSize);

            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ValueObject valueObject = valueObjectClass.newInstance();
                valueObject.parseSql(rs);
                valueObject.setResultCode(ErrorCodeMap.RECORD_FOUND);
                valueObjects.add((T) valueObject);
            }
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("failure to get value objects " + sql,e);
        }
        finally {
            closeStatement(stmt);
            closeConnection(connection);
        }

        return valueObjects;
    }

    /**
     * Uses a <code>ValueObject</code> to make an update, insert, or delete in the datasource.
     *
     * @param valueObject value object to set
     */
    public void setValueObject(ValueObject valueObject) {

        if ((valueObject.getResultCode() != ErrorCodeMap.DELETED_NEW_RECORD && valueObject.isModified()) || valueObject.getResultCode() == ErrorCodeMap.NEW_RECORD ||
                valueObject.getResultCode() == ErrorCodeMap.DELETED_RECORD) {
            Connection connection = null;
            try {
                connection = getConnection();
                setValueObject(connection, valueObject);
            }
            finally {
                closeConnection(connection);
            }
        }
    }

    /**
     * Uses a <code>ValueObject</code> to make an update, insert, or delete in the datasource.
     *
     * @param connection  connection
     * @param valueObject value object
     */
    public void setValueObject(Connection connection, ValueObject valueObject) {
        if ((valueObject.getResultCode() != ErrorCodeMap.DELETED_NEW_RECORD && valueObject.isModified()) || valueObject.getResultCode() == ErrorCodeMap.NEW_RECORD ||
                valueObject.getResultCode() == ErrorCodeMap.DELETED_RECORD) {

            executeSQL(connection, valueObject.getExecuteSql(), valueObject.getParams());
        }
    }


    /**
     * Executes an sql INSERT, UPDATE, or DELETE command.
     *
     * @param connection connection
     * @param sql        The sql to perform the action
     * @param params     The parameters to set.
     * @return the number of rows affected
     */
    public int executeSQL(Connection connection, String sql, List params) {
        int rowsAffected = 0;
        PreparedStatement ps = null;
        try {
            sql = processParams(sql, params);
            ps = connection.prepareStatement(sql);
            setSearchParams(ps, params);
            rowsAffected = ps.executeUpdate();
        } catch (Exception e) {

            throw new XifinDataAccessFailureException("failure to  executeSQL " + sql + "params " + params, e);
        } finally {
            closeStatement(ps);
        }
        return rowsAffected;
    }

    /**
     * Uses  <code>ValueObject</code>s to make updates, inserts, or deletes in the datasource.
     *
     * @param valueObjects list of value objects
     */
    public void setValueObjects(List valueObjects) {
        Connection connection = null;
        try {
            connection = getConnection();
            setValueObjects(connection, valueObjects);
        }
        finally {
            closeConnection(connection);
        }
    }

    /**
     * Uses  <code>ValueObject</code>s to make updates, inserts, or deletes in the datasource.
     *
     * @param connection   connection
     * @param valueObjects list of value objects
     */
    public void setValueObjects(Connection connection, List valueObjects) {
        ValueObject vo;
        for (int i = 0; i < valueObjects.size(); i++) {
            vo = (ValueObject) valueObjects.get(i);
            setValueObject(connection, vo);
        }
    }

    /**
     * Gets a list of ValueObjects.
     *
     * @param sql              The sql to retrieve the records
     * @param params           The parameters for the sql
     * @param valueObjectClass The class of the value object.
     * @return a list of ValueObjects
     */
    public <T extends ValueObject> List<T> getValueObjects(String sql, List params, Class<T> valueObjectClass) {
        List<T> valueObjects = new ArrayList<T>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        log.debug("message=getValueObjects,sql=" + sql + " ,params=" + params);

        try {
            connection = getConnection();
            sql = processParams(sql, params);
            ps = connection.prepareStatement(sql);
            ps.setFetchSize(getFetchSize());
            setSearchParams(ps, params);
            rs = ps.executeQuery();
            while (rs.next()) {
                ValueObject valueObject = valueObjectClass.newInstance();
                valueObject.parseSql(rs);
                valueObject.setResultCode(ErrorCodeMap.RECORD_FOUND);
                valueObjects.add((T) valueObject);
            }
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("failure to get value objects " + sql + " params" + params, e);
        } finally {
            closeStatement(ps);
            closeConnection(connection);
        }

        return valueObjects;
    }

    /**
     * Gets a list of ValueObjects.
     *
     * @param sql              The sql to retrieve the records
     * @param startAt          starting index
     * @param recordsToReturn  number of records to return
     * @param valueObjectClass The class of the value object.
     * @return a list of ValueObjects
     */
    public <T extends ValueObject> List<T> getValueObjectsStartingAtRecord(String sql, long startAt, int recordsToReturn, Class<T> valueObjectClass) {
        List<T> valueObjects = new ArrayList<T>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        long skipCounter = 1;
        int returnCounter = 0;

        // Treat rows as starting at 1 instead of 0
        if(startAt <= 0) {
            startAt = 1;
        }

        try {
            connection = getConnection();
            stmt = connection.createStatement();

            rs = stmt.executeQuery(sql);
            while (skipCounter < startAt && rs.next()) {
                skipCounter++;
            }

            while (rs.next() && returnCounter < recordsToReturn) {
                ValueObject valueObject = valueObjectClass.newInstance();
                valueObject.parseSql(rs);
                valueObject.setResultCode(ErrorCodeMap.RECORD_FOUND);
                valueObjects.add((T) valueObject);
                returnCounter++;
            }
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("failure to get value objects " + sql + " start " + startAt + " recordsToReturn " + recordsToReturn);
        } finally {
            closeStatement(stmt);
            closeConnection(connection);
        }

        return valueObjects;
    }

    /**
     * Gets a list of ValueObjects.
     *
     * @param sql              The sql to retrieve the records
     * @param startAt          starting index
     * @param recordsToReturn  number of records to return
     * @param valueObjectClass The class of the value object.
     * @return a list of ValueObjects
     */
    public <T extends ValueObject> List<T> getValueObjectsStartingAtRecord(String sql, List params, long startAt, int recordsToReturn, Class<T> valueObjectClass) {
        List<T> valueObjects = new ArrayList<T>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        long skipCounter = 1;
        int returnCounter = 0;

        // Treat rows as starting at 1 instead of 0
        if(startAt <= 0) {
            startAt = 1;
        }

        try {
            connection = getConnection();
            sql = processParams(sql, params);
            ps = connection.prepareStatement(sql);
            setSearchParams(ps, params);

            rs = ps.executeQuery();
            while (skipCounter < startAt && rs.next()) {
                skipCounter++;
            }

            while (rs.next() && returnCounter < recordsToReturn) {
                ValueObject valueObject = valueObjectClass.newInstance();
                valueObject.parseSql(rs);
                valueObject.setResultCode(ErrorCodeMap.RECORD_FOUND);
                valueObjects.add((T) valueObject);
                returnCounter++;
            }
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("failure to  getValueObjectsStartingAtRecord " + sql + " start " + startAt + " recordsToReturn " + recordsToReturn);
        } finally {
            closeStatement(ps);
            closeConnection(connection);
        }

        return valueObjects;
    }

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
    public List<Value> getSingleRow(String sql) {
        List<Value> values = new ArrayList<Value>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            stmt = connection.createStatement();

            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ResultSetMetaData meta = rs.getMetaData();
                int count = meta.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    Object obj = rs.getObject(i);
                    values.add(new Value(obj));
                }
            }
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("failure to  getSingleRow " + sql, e);
        }
        finally {
            closeStatement(stmt);
            closeConnection(connection);
        }

        return values;
    }

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
    public List<Value> getSingleRow(String sql, List params) {
        List<Value> values = new ArrayList<Value>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            sql = processParams(sql, params);
            ps = connection.prepareStatement(sql);
            setSearchParams(ps, params);
            rs = ps.executeQuery();
            if (rs.next()) {
                ResultSetMetaData meta = rs.getMetaData();
                int count = meta.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    Object obj = rs.getObject(i);
                    values.add(new Value(obj));
                }
            }
        } catch (Exception e) {

            throw new XifinDataAccessFailureException("failure to  getSingleRow " + sql + " params" + params, e);

        }
        finally {
            closeStatement(ps);
            closeConnection(connection);
        }

        return values;
    }

    @Override
    public List<List<Value>> getMultipleRows(String sql, List params) {
        return null;
    }

    @Override
    public List<List<Value>> getMultipleRows(String sql) {
        return null;
    }

    @Override
    public List<Map<String, Value>> getMultipleMappedRows(String sql) {
        return null;
    }

    @Override
    public List<Map<String, Value>> getMultipleMappedRows(String sql, List params) {
        return null;
    }

    @Override
    public List<Map<String, Value>> getMultipleMappedRowsStartingAtRecord(String sql, long startAt, int recordsToReturn) {
        return null;
    }

    @Override
    public List<Map<String, Value>> getMultipleMappedRowsStartingAtRecord(String sql, List params, long startAt, int recordsToReturn) {
        return null;
    }

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
    public List<List<Value>> getMultipleRowsStartingAtRecord(String sql, List params, long startAt, int recordsToReturn) {
        List<List<Value>> rows = new ArrayList<List<Value>>();
        Connection connection = null;
        PreparedStatement ps;
        ResultSet rs;
        Statement stmt = null;
        long skipCounter = 0;
        int returnCounter = 0;

        try {
            connection = getConnection();
            sql = processParams(sql, params);
            ps = connection.prepareStatement(sql);
            ps.setFetchSize(getFetchSize());
            setSearchParams(ps, params);

            rs = ps.executeQuery();


            while (skipCounter < startAt && rs.next()) {
                skipCounter++;
            }

            while (rs.next() && returnCounter < recordsToReturn) {
                ResultSetMetaData meta = rs.getMetaData();
                int count = meta.getColumnCount();
                if (count <= 0) {
                    continue;
                }

                List<Value> columns = new ArrayList<Value>();
                for (int i = 1; i <= count; i++) {
                    Object obj = rs.getObject(i);
                    columns.add(new Value(obj));
                }
                returnCounter++;
                rows.add(columns);
            }
        } catch (Exception e) {

            throw new XifinDataAccessFailureException("failure to  getMultipleRows " + sql + " params" + params + " startAt" + startAt + " recordsToReturn" + recordsToReturn, e);
        }
        finally {
            closeStatement(stmt);
            closeConnection(connection);
        }
        return rows;
    }

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
    public List<List<Value>> getMultipleRowsStartingAtRecord(String sql, long startAt, int recordsToReturn) {
        List<List<Value>> rows = new ArrayList<List<Value>>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        long skipCounter = 0;
        int returnCounter = 0;

        try {
            connection = getConnection();
            stmt = connection.createStatement();

            rs = stmt.executeQuery(sql);
            while (skipCounter < startAt && rs.next()) {
                skipCounter++;
            }
            while (rs.next() && returnCounter < recordsToReturn) {

                ResultSetMetaData meta = rs.getMetaData();
                int count = meta.getColumnCount();
                if (count <= 0) continue;

                List<Value> columns = new ArrayList<Value>();
                for (int i = 1; i <= count; i++) {
                    Object obj = rs.getObject(i);
                    columns.add(new Value(obj));
                }
                returnCounter++;
                rows.add(columns);
            }
        } catch (Exception e) {

            throw new XifinDataAccessFailureException("failure to  getMultipleRowsStartingAtRecord " + sql + " startAt" + startAt + " recordsToReturn " + recordsToReturn, e);
        } finally {
            closeStatement(stmt);
            closeConnection(connection);
        }

        return rows;
    }

    /**
     * Executes an sql INSERT, UPDATE, or DELETE command.
     *
     * @param sql    The sql to perform the action
     * @param params The parameters to set.
     * @return the number of rows affected
     */
    public int executeSQL(String sql, List params) {
        int rowsAffected = 0;
        Connection connection = null;
        PreparedStatement ps = null;

        log.debug("message=executeSQL,sql=" + sql + " ,params=" + params);

        try {
            connection = getConnection();
            sql = processParams(sql, params);
            ps = connection.prepareStatement(sql);
            setSearchParams(ps, params);
            rowsAffected = ps.executeUpdate();
        } catch (Exception e) {

            throw new XifinDataAccessFailureException("failure to  executeSQL " + sql + "params " + params, e);
        }
        finally {
            closeStatement(ps);
            closeConnection(connection);
        }

        return rowsAffected;
    }

    /**
     * Executes an sql INSERT, UPDATE, or DELETE command.
     *
     * @param sql the sql to perform the action
     * @return the number of rows affected
     */
    public int executeSQL(String sql) {
        int rowsAffected = 0;
        Connection connection = null;
        Statement stmt = null;

        log.debug("messaged=executeSQL,sql=" + sql);

        try {
            connection = getConnection();
            stmt = connection.createStatement();

            rowsAffected = stmt.executeUpdate(sql);
        } catch (Exception e) {

            throw new XifinDataAccessFailureException("failure to  executeSQL " + sql, e);
        }
        finally {
            closeStatement(stmt);
            closeConnection(connection);
        }

        return rowsAffected;
    }

    /**
     * This method will perform batch select. In other words, it will re-use prepared statement
     * to execute different set of parameters from the batch list.
     *
     * @param sql         statment that will be executed multiple times with different parameters.
     * @param batchParams is a List of batch parameters; each batch will contain list of parameters.
     *                    NOTE: The number of parameters must be the same in each batch list.
     * @return List of rows from all batch parameters.
     */
    public List<List<Value>> getMultipleRowsBatch(String sql, List<List<Object>> batchParams) {
        List<List<Value>> rows = new ArrayList<List<Value>>();
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = getConnection();
            //loop over each batch list and execute prepared statement
            for (List<Object> batchParam : batchParams) {
                String newSql = processParams(sql, batchParam);
                ps = connection.prepareStatement(newSql);

                setSearchParams(ps, batchParam);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ResultSetMetaData meta = rs.getMetaData();
                    int count = meta.getColumnCount();
                    if (count <= 0) continue;

                    List<Value> columns = new ArrayList<Value>();
                    for (int i = 1; i <= count; i++) {
                        Object obj = rs.getObject(i);
                        columns.add(new Value(obj));
                    }

                    rows.add(columns);
                }
                //clear prepared statement parameters between execution
                ps.clearParameters();
            }
        } catch (Exception e) {

            throw new XifinDataAccessFailureException("failure to  executeSQL " + sql + "batchParams " + batchParams, e);
        }
        finally {
            closeStatement(ps);
            closeConnection(connection);
        }

        return rows;
    }

    /**
     * This method will perform batch update. In other words, it will batch up INSERT, UPDATE, or MERGE statements
     * with different parameters, and then execute all updates at once.
     *
     * @param sql         is a String representings statement that will execute the batch update (i.e. INSERT, UPDATE, oe MERGE)
     * @param batchParams is a List where each element is a List of parameters to be added to the batch.
     * @return int[] array of update counts.
     * @see java.sql.Statement#executeBatch() for mor information
     */
    public int[] batchUpdate(String sql, List batchParams) {
        int[] rowsAffected;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = getConnection();
            //create prepared statement based on the first set of parameters.
            sql = processParams(sql, (List) batchParams.get(0));
            ps = connection.prepareStatement(sql);
            //loop over each batch list and add its parameters to the prepared statement batch.
            for (Iterator listIter = batchParams.iterator(); listIter.hasNext();) {
                setSearchParams(ps, (List) listIter.next());
                ps.addBatch();
            }
            //execute all batch updates at once.
            rowsAffected = ps.executeBatch();
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("failure to  batchUpdate " + sql + "params " + batchParams, e);
        }
        finally {
            closeStatement(ps);
            closeConnection(connection);
        }
        return rowsAffected;
    }


    @Override
    public <T extends ValueObject> T getValueObject(String sql, List params, Class<T> valueObjectClass) throws XifinDataNotFoundException {
        ValueObject valueObject = null;
        try {
            valueObject = valueObjectClass.newInstance();
        } catch (Exception e){
            throw new XifinDataAccessFailureException("Unable to create instance of valueObjectClass" + valueObjectClass, e);
        }
        loadValueObject(sql, params, valueObject);

        return (T) valueObject;
    }


    public void loadValueObject(String sql, List params, ValueObject valueObject) throws XifinDataNotFoundException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        log.debug("message=loadValueObject,sql=" + sql + " ,params=" + params);
        try {
            connection = getConnection();
            sql = processParams(sql, params);
            ps = connection.prepareStatement(sql);
            setSearchParams(ps, params);
            rs = ps.executeQuery();
            if (rs.next()) {
                valueObject.parseSql(rs);
                valueObject.setResultCode(0);
            } else {
                String errMsg = "Data Not Found.";
                throw new XifinDataNotFoundException(errMsg);
            }
        } catch (XifinDataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("failed to load value object", e);
        }
        finally {
            closeStatement(ps);
            closeConnection(connection);
        }
    }

    public Connection getConnection() {

        return DataSourceUtils.getConnection(dataSource);

//        try {
//            return dataSource.getConnection();
//        } catch (SQLException e) {
//            throw new XifinDataAccessFailureException(" Caould not get connection", e);
//        }

    }

    public int getFetchSize()    {
        return FETCH_SIZE;
    }

    public void closeStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception ignored) {
            log.warn("Error closing Statement.", ignored);
        }
    }

    @Override
    public void executeProcedure(String storedProcedureName, List<Object> params) {

        Connection conn = null;
        String procSql = "{call  " + storedProcedureName + "(";

        for (int i = 0; i < params.size(); i++) {
            if (i == params.size() - 1) {
                procSql = procSql + "?";
            } else {
                procSql = procSql + "? ,";
            }
        }
        procSql = procSql + ")}";


        try {
            log.debug("message=Calling Stored Procedure= " + storedProcedureName + " ,Argument= " + params);
            conn = getConnection();
            CallableStatement cstmt = conn.prepareCall(procSql);
            setSearchParams(cstmt, params);
            cstmt.executeUpdate();
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("Failed to execute procedure " + storedProcedureName + " params" + params, e);
        }
        finally {
            //close JDBC connection
            closeConnection(conn);
        }
    }

    @Override
    public String executeFunction(String functionName) {

        Connection conn = null;
        String procSql = "{? = call  " + functionName + "()}";
        String status;

        try {
            log.debug("message=Calling function = " + functionName);
            conn = getConnection();
            CallableStatement cstmt = conn.prepareCall(procSql);
            cstmt.registerOutParameter(1, java.sql.Types.VARCHAR);
            cstmt.execute();
            status = cstmt.getString(1);

        } catch (Exception e) {
            throw new XifinDataAccessFailureException("Failed to call function " + functionName, e);
        } finally {
            //close JDBC connection
            closeConnection(conn);
        }
        return status;
    }

    @Override
    public Map<Integer, Object> executeProcedure(String procedureName, Map<Integer, Object> inputParams, Map<Integer, Integer> outputParams) {

        Connection conn = null;
        CallableStatement cstmt = null;
        Map<Integer, Object> outputData = new HashMap<>();
        String procSql = "{call  " + procedureName + "(";
        int numOfParams = inputParams.size() + outputParams.size();
        for (int i = 0; i < numOfParams; i++) {
            if (i == numOfParams - 1) {
                procSql = procSql + "?";
            } else {
                procSql = procSql + "? ,";
            }
        }
        procSql = procSql + ")}";


        try {
            log.debug("Calling Stored Procedure: " + procedureName + " InputParams: " + inputParams + " OutParams: " + outputParams);
            conn = getConnection();
            cstmt = conn.prepareCall(procSql);
            //Register output params
            for (Map.Entry<Integer, Integer> entry : outputParams.entrySet()) {
                cstmt.registerOutParameter(entry.getKey(), entry.getValue());
            }
            //Set input params
            for (Map.Entry<Integer, Object> entry : inputParams.entrySet()) {
                setParam(cstmt, entry.getValue(), entry.getKey());
            }
            //execute store procedure
            cstmt.executeUpdate();
            //Get data returned
            for (Integer key : outputParams.keySet()) {
                outputData.put(key, cstmt.getObject(key));
            }
        } catch (Exception e) {
            throw new XifinDataAccessFailureException("Failed to execute procedure " + procedureName + " InputParams: " + inputParams + " OutParams: " + outputParams, e);
        }
        finally {
            //close JDBC connection
            closeConnection(conn);
        }

        return outputData;
    }

    public int getNextSequenceFromOracle(String seqName) {
        String GET_NEXT_SEQUENCE = "select " + seqName + ".nextVal from dual";
        List<Value> results;

        results = getSingleRow(GET_NEXT_SEQUENCE);

        if (results.size() > 0) {
            return results.get(0).toIntValue();
        } else {
            throw new XifinDataAccessFailureException("Failed to get next " + seqName);
        }
    }

    /**
     *  Get bulk value Objects
     * @param valueObjectClass valueObjectClass
     * @param sql sql
     * @param fetchSize fetchSize
     * @param params params
     * @param <T>
     * @return ValueObjectIterator
     */
    public  <T extends  ValueObject> ValueObjectIterator getValueObjectsIterator(Class<T> valueObjectClass, String sql, int fetchSize,Object... params) {

        return new ValueObjectIterator(dataSource,sql, params,valueObjectClass,fetchSize);
    }

    public  <T extends  Map> MultipleMappedRowsIterator getMultipleMappedRowsIterator(String sql, int fetchSize, Object... params) {

        return new MultipleMappedRowsIterator(dataSource,sql, params,fetchSize);
    }

    public void closeConnection(Connection connection) {

        DataSourceUtils.releaseConnection(connection, dataSource);
//        try {
//            if (connection != null) {
//                connection.close();
//            }
//        }
//        catch (Exception ignored) {
//            log.warn("Error closing Connection.", ignored);
//        }
    }


    /**
     * This method will process parameters by tokenizing through the SQL based on "?"
     * and appand eanough "?" for each parameter if it is a list
     * NOTE: Currently, this method only supports IN as a receptor of list of parameters.
     * The list is will be split into maximum 1000 parameters (e.g. Oracle Limitation.)
     *
     * @param sql    Original SQL
     * @param params List of parameters
     * @return New SQL with appanded "?"
     */
    public static String processParams(String sql, List params)  {
        int paramCount = 0;
        StringBuilder newSql = new StringBuilder();
        StringTokenizer sqlTokenizer = new StringTokenizer(sql, "?", true);
        String token = "";

        // Handle first case, no params
        if (sqlTokenizer.hasMoreTokens()) {
            token = sqlTokenizer.nextToken();
            newSql.append(token);
        }

        String prevToken = token;
        while (sqlTokenizer.hasMoreTokens()) {
            token = sqlTokenizer.nextToken();
            //check if we have reached paarameter mark
            if (token.compareTo("?") == 0) {
                // Check if the parameter is a list
                if (params.get(paramCount) instanceof Collection || params.get(paramCount) instanceof Map) {
                    Object param = params.get(paramCount);
                    List paramList = new ArrayList();
                    Map paramMap = new TreeMap();
                    if (param instanceof List) {
                        paramList = (List) param;
                    } else if (param instanceof Map) {
                        paramMap = (Map) param;
                    }
                    if (paramList.size() > 0 || paramMap.size() > 0) {
                        // the name of the parameter
                        String subParam = "";

                        //create subtoken list so that we can use it
                        //to identify if we need to fill enough parameter marks for IN clause
                        List subTtokens = new ArrayList();
                        StringTokenizer tokenizer = new StringTokenizer(prevToken, " ");
                        while (tokenizer.hasMoreTokens()) {
                            subTtokens.add(tokenizer.nextToken().trim().toLowerCase());
                        }

                        //loop over each subtokren
                        StringBuffer tokenBuffer = new StringBuffer();
                        boolean isTokenFound = false;
                        for (ListIterator iter = subTtokens.listIterator(subTtokens.size()); iter.hasPrevious() && !isTokenFound;) {
                            String strToken = (String) iter.previous();
                            //determin if we need to fill in for IN or CASE clause
                            if (strToken.endsWith("in")) {
                                isTokenFound = true;
                                if (iter.hasPrevious()) {
                                    strToken = (String) iter.previous();
                                    if (strToken.endsWith("not")) {
                                        if (iter.hasPrevious()) {
                                            strToken = (String) iter.previous();
                                            if (strToken.startsWith("(")) {
                                                subParam = strToken.substring(1) + " not ";
                                            } else {
                                                subParam = strToken + " not ";
                                            }
                                        }
                                    } else {
                                        if (strToken.startsWith("(")) {
                                            subParam = strToken.substring(1);
                                        } else {
                                            subParam = strToken;
                                        }
                                    }
                                }
                                int index = 0;
                                ListIterator paramIter;

                                // Do an initial check for if we're splitting up the token list into two lists due to Oracle constraints.
                                // If so, surround with an additional set of parentheses to treat it as one clause.  We need to backtrack
                                // to the first part of the clause to put the opening parenthesis.
                                if (paramList.size() > MAX_NUM_OF_PARAMS) {
                                    int subParamIndex = newSql.lastIndexOf(subParam);
                                    newSql.insert(subParamIndex, "(");
                                }

                                while (index < paramList.size()) {
                                    tokenBuffer.append(index < MAX_NUM_OF_PARAMS ? "(" : " or " + subParam + " in (");
                                    for (paramIter = paramList.listIterator(index); paramIter.hasNext() && paramIter.nextIndex() < index + MAX_NUM_OF_PARAMS;) {
                                        paramIter.next();
                                        tokenBuffer.append("?").append(paramIter.nextIndex() < index + MAX_NUM_OF_PARAMS ? paramIter.hasNext() ? "," : "" : "");
                                    }
                                    tokenBuffer.append(")");
                                    index = paramIter.nextIndex();
                                }

                                // Need to close the clause with an ending parenthesis if we had to split up the list
                                if (paramList.size() > MAX_NUM_OF_PARAMS) {
                                    tokenBuffer.append(")");
                                }
                            } else if (strToken.endsWith("case")) {
                                isTokenFound = true;
                                for (Iterator paramIter = paramMap.keySet().iterator(); paramIter.hasNext();) {
                                    paramIter.next();
                                    tokenBuffer.append(" when ").append("?").append(" then ").append("? ");
                                }
                            }
                            token = tokenBuffer.toString();
                        }
                    } else {
                        throw new IllegalArgumentException("Passed in list has no values.  Sql =  " + sql + " Params = " + params);
                    }
                }
                paramCount++;
            }
            newSql.append(token);
            prevToken = token;
        }

        return newSql.toString();
    }


    public static void setSearchParams(PreparedStatement ps, List params) throws SQLException {

        if (params == null) {
            throw new NullPointerException("Invalid parameter: params is NULL!");
        }

        for (int i = 0, j = 1; i < params.size(); i++, j++) {
            Object obj = params.get(i);
            if (obj instanceof Collection) {
                Collection collection = (Collection) obj;
                Iterator collectionIter = collection.iterator();
                // Insert list parameters
                if (collectionIter.hasNext()) {
                    Object collectionObject = collectionIter.next();
                    setParam(ps, collectionObject, j);
                }
                while (collectionIter.hasNext()) {
                    j++;
                    Object collectionObject = collectionIter.next();
                    setParam(ps, collectionObject, j);
                }
            } else if (obj instanceof Map) {
                Map dataMap = (Map) obj;
                for (Iterator mapIter = dataMap.keySet().iterator(); mapIter.hasNext();) {
                    Integer dataId = (Integer) mapIter.next();
                    setParam(ps, dataId, j++);
                    setParam(ps, dataMap.get(dataId), !mapIter.hasNext() ? j : j++);
                }
            } else {
                setParam(ps, obj, j);
            }
        }
    }

    /**
     * set parameters
     *
     * @param ps  prepared statement object
     * @param obj object
     * @param j   index of paramter to set
     */
    private static void setParam(PreparedStatement ps, Object obj, int j) throws SQLException
    {
        if (obj == null)
        {
            //throw new NullPointerException("Inavlid param. params.size()="+size+", i="+i);
            ps.setString(j, null);
        }
        else if (obj instanceof String)
        {
            ps.setString(j, (String) obj);   // JDBC is 1 based
        }
        else if (obj instanceof Integer)
        {
            ps.setInt(j, ((Integer) obj).intValue());
        }
        else if (obj instanceof Long)
        {
            ps.setLong(j, ((Long) obj).longValue());
        }
        else if (obj instanceof Double)
        {
            ps.setDouble(j, ((Double) obj).doubleValue());
        }
        else if (obj instanceof Float)
        {
            ps.setFloat(j, ((Float) obj).floatValue());
        }
        else if (obj instanceof BigDecimal)
        {
            ps.setBigDecimal(j, ((BigDecimal) obj));

        }
//        else if (obj instanceof Boolean)
//        {
//            int value = BooleanConversion.booleanToSQLInt(((Boolean) obj).booleanValue());
//            ps.setInt(j, value);
//        }
        else if (obj instanceof Character)
        {
            String value = obj.toString();

            ps.setString(j, value);
        }
        else if (obj instanceof Date)
        {
            ps.setDate(j, (Date) obj);
        }
        else if (obj instanceof Timestamp)
        {
            ps.setTimestamp(j, (Timestamp) obj);
        }
        else if (obj instanceof java.util.Date)
        {
            Date date = new Date(((java.util.Date) obj).getTime());
            ps.setDate(j, date);
        }
        else if (obj instanceof Money)
        {
            String value = obj.toString();
            ps.setString(j, value);
        }
        else if (obj instanceof java.sql.Clob)
        {
            ps.setClob(j, (Clob) obj);
        }
        else if (obj instanceof java.io.StringReader)
        {
            ps.setCharacterStream(j, (StringReader)obj);
        }
        else {
            throw new UnsupportedOperationException("The type " + obj.getClass().getName() + " is currently not supported.");
        }
    }



    public static class Value implements Serializable {
        private Object value = null;

        public Value(Object obj) {
            value = obj;
        }

        public int toIntValue() throws NumberFormatException {
            if (value instanceof Number) {
                return ((Number) value).intValue();
            } else {
                return Integer.parseInt(value.toString());
            }
        }

        public int toIntValue(int defaultValue) throws NumberFormatException {
            int returnValue;
            if (value == null) {
                returnValue = defaultValue;
            } else {
                returnValue = toIntValue();
            }

            return returnValue;
        }

        public Integer toInteger() throws NumberFormatException {
            if (value == null) return null;

            if (value instanceof Integer) {
                return (Integer) value;
            }

            return Integer.valueOf(value.toString());
        }

        public float toFloatValue() throws NumberFormatException {
            if (value instanceof Number) {
                return ((Number) value).floatValue();
            } else {
                return Float.parseFloat(value.toString());
            }
        }

        public Float toFloat() throws NumberFormatException {
            if (value == null) return null;

            if (value instanceof Float) {
                return (Float) value;
            }

            return Float.valueOf(value.toString());
        }

        public double toDoubleValue() throws NumberFormatException {
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            } else {
                return Double.parseDouble(value.toString());
            }
        }

        public Double toDouble() throws NumberFormatException {
            if (value == null) return null;

            if (value instanceof Double) {
                return (Double) value;
            }

            return Double.valueOf(value.toString());
        }

        public long toLongValue() throws NumberFormatException {
            if (value instanceof Number) {
                return ((Number) value).longValue();
            } else {
                return Long.parseLong(value.toString());
            }
        }

//        public boolean toBooleanValue() throws NumberFormatException {
//            if (value instanceof Boolean) {
//                return ((Boolean) value).booleanValue();
//            } else {
//                // value.ToString is 1/0 not  "true"\"false".
//                return BooleanConversion.intToBoolean(Integer.parseInt(value.toString()));
//
//            }
//        }

//        public boolean toBooleanValue(boolean defaultValue) throws NumberFormatException {
//            boolean booleanValue;
//            if (value == null) {
//                booleanValue = defaultValue;
//            } else {
//                booleanValue = toBooleanValue();
//            }
//
//            return booleanValue;
//        }

        public Long toLong() throws NumberFormatException {
            if (value == null) return null;

            if (value instanceof Long) {
                return (Long) value;
            }

            return Long.valueOf(value.toString());
        }

        public Date toSqlDate() {
            if (value == null) return null;

            if (value instanceof Date) {
                return (Date) value;
            } else if (value instanceof java.util.Date) {
                return new Date(((java.util.Date) value).getTime());
            } else if (value instanceof Timestamp) {
                return new Date(((Timestamp) value).getTime());
            }

            throw new IllegalArgumentException("Not a valid java.sql.Date object. object class=" + value.getClass().getName());
        }

        public java.util.Date toUtilDate() {
            if (value == null) return null;

            if (value instanceof java.util.Date) {
                return (java.util.Date) value;
            } else if (value instanceof Timestamp) {
                return new java.util.Date(((Timestamp) value).getTime());
            }

            throw new IllegalArgumentException("Not a valid java.util.Date object. object class=" + value.getClass().getName());
        }

        public Timestamp toTimestamp() {
            if (value == null) return null;

            if (value instanceof Timestamp) {
                return (Timestamp) value;
            } else if (value instanceof Date) {
                Date date = (Date) value;
                return new Timestamp(date.getTime());
            } else if (value instanceof java.util.Date) {
                java.util.Date date = (java.util.Date) value;
                return new Timestamp(date.getTime());
            }

            throw new IllegalArgumentException("Not a valid Timestamp object. object class=" + value.getClass().getName());
        }

        public String toString() {
            if (value == null) return null;

            if (value instanceof String) {
                return (String) value;
            }

            return value.toString();
        }

        public Object toObject() {
            return value;
        }
    }
}
