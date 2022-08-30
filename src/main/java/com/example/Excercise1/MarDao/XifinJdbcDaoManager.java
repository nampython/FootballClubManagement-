package com.example.Excercise1.MarDao;

import com.example.Excercise1.exceptions.XifinDataAccessFailureException;
import com.example.Excercise1.exceptions.XifinDataNotFoundException;
import com.example.Excercise1.mars.ValueObject;
import com.example.Excercise1.models.MultipleMappedRowsIterator;
import com.example.Excercise1.models.ValueObjectIterator;
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

    @Override
    public <T extends ValueObject> List<T> getValueObjects(Class<T> valueObjectClass, String sql, Object... params) {
        return null;
    }

    @Override
    public void loadValueObject(ValueObject valueObject) throws XifinDataNotFoundException {

    }

    @Override
    public <T extends ValueObject> List<T> getValueObjects(String sql, Class<T> valueObjectClass) {
        return null;
    }

    @Override
    public <T extends ValueObject> List<T> getValueObjects(String sql, Class<T> valueObjectClass, int fetchSize) {
        return null;
    }

    @Override
    public void setValueObject(ValueObject valueObject) {

    }

    @Override
    public void setValueObject(Connection connection, ValueObject valueObject) {

    }

    @Override
    public void setValueObjects(List valueObjects) {

    }

    @Override
    public <T extends ValueObject> List<T> getValueObjects(String sql, List params, Class<T> valueObjectClass) {
        return null;
    }

    @Override
    public <T extends ValueObject> List<T> getValueObjectsStartingAtRecord(String sql, long startAt, int recordsToReturn, Class<T> valueObjectClass) {
        return null;
    }

    @Override
    public <T extends ValueObject> List<T> getValueObjectsStartingAtRecord(String sql, List params, long startAt, int recordsToReturn, Class<T> valueObjectClass) {
        return null;
    }

    @Override
    public List<Value> getSingleRow(String sql) {
        return null;
    }

    @Override
    public List<Value> getSingleRow(String sql, List params) {
        return null;
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

    @Override
    public List<List<Value>> getMultipleRowsStartingAtRecord(String sql, List params, long startAt, int recordsToReturn) {
        return null;
    }

    @Override
    public List<List<Value>> getMultipleRowsStartingAtRecord(String sql, long startAt, int recordsToReturn) {
        return null;
    }

    @Override
    public int executeSQL(String sql, List params) {
        return 0;
    }

    @Override
    public int executeSQL(String sql) {
        return 0;
    }

    @Override
    public List<List<Value>> getMultipleRowsBatch(String sql, List<List<Object>> batchParams) {
        return null;
    }

    @Override
    public int[] batchUpdate(String sql, List batchParams) {
        return new int[0];
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
    public void executeProcedure(String procedureName, List<Object> params) {

    }

    @Override
    public String executeFunction(String procedureName) {
        return null;
    }

    @Override
    public Map<Integer, Object> executeProcedure(String procedureName, Map<Integer, Object> inputParams, Map<Integer, Integer> outputParams) {
        return null;
    }

    @Override
    public int getNextSequenceFromOracle(String seqName) {
        return 0;
    }

    @Override
    public <T extends ValueObject> ValueObjectIterator getValueObjectsIterator(Class<T> valueObjectClass, String sql, int fetchSize, Object... params) {
        return null;
    }

    @Override
    public <T extends Map> MultipleMappedRowsIterator getMultipleMappedRowsIterator(String sql, int fetchSize, Object... params) {
        return null;
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
