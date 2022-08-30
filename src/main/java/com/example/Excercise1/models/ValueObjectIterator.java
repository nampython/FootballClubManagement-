package com.example.Excercise1.models;

import com.example.Excercise1.MarDao.XifinJdbcDaoManager;
import com.example.Excercise1.exceptions.XifinDataAccessFailureException;
import com.example.Excercise1.mars.ValueObject;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ValueObjectIterator<T extends ValueObject> implements Iterator<List<T>> {

    private static final Logger logger = Logger.getLogger(ValueObjectIterator.class);

    private Class<T> valueObjectClass;
    private int fetchSize;
    private List<ValueObject> valueObjects = new ArrayList<>();
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private boolean hasMoreRecords = true;
    private DataSource dataSource;

    public ValueObjectIterator(DataSource dataSource, String sql, Object[] params, Class<T> valueObjectClass, int fetchSize) {

        this.dataSource = dataSource;
        this.valueObjectClass = valueObjectClass;
        this.fetchSize = fetchSize;

        List paramsList = Arrays.asList(params);
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            sql = XifinJdbcDaoManager.processParams(sql, paramsList);
            preparedStatement = connection.prepareStatement(sql);
            XifinJdbcDaoManager.setSearchParams(preparedStatement, paramsList);
            this.preparedStatement.setFetchSize(fetchSize);
            this.resultSet = preparedStatement.executeQuery();

        } catch (Exception e) {
            throw new XifinDataAccessFailureException("failure to get value objects " + sql + " params" + params, e);
        }
    }

    @Override
    public boolean hasNext() {
        return hasMoreRecords;
    }

    @Override
    public List<T> next() {

        valueObjects = new ArrayList<>();
        try {
            while (resultSet.next()) {
                ValueObject valueObject = valueObjectClass.newInstance();
                valueObject.parseSql(resultSet);
                valueObject.setResultCode(0);

                valueObjects.add(valueObject);

                if (valueObjects.size() == fetchSize) {
                    break;
                }
            }
            hasMoreRecords = valueObjects.size() == fetchSize;

        } catch (Exception e) {
            throw new XifinDataAccessFailureException("failed to load value objects", e);

        }
        return (List<T>) valueObjects;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("This method is not implemented");
    }


    public void close() {

        logger.debug("closing connection");
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            logger.warn("failed to close prepared statement", e);
        }
        DataSourceUtils.releaseConnection(connection, dataSource);
    }
}

