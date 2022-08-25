package com.example.Excercise1.MarDao;

import com.example.Excercise1.exceptions.XifinDataAccessFailureException;
import com.example.Excercise1.exceptions.XifinDataNotFoundException;
import com.example.Excercise1.mars.ValueObject;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class XifinJdbcDaoManager implements MarsDaoManager{
    private DataSource dataSource;

    private static final int MAX_NUM_OF_PARAMS = 1000;
    private static final Logger log = Logger.getLogger(XifinJdbcDaoManager.class);
    private static final int FETCH_SIZE = 20;

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

    @Override
    public <T extends ValueObject> T getValueObject(String sql, Class<T> valueObjectClass) throws XifinDataNotFoundException {
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

        return (T) valueObjects;
    }

    public void loadValueObject(String sql, List params, ValueObject valueObject) {

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

}
