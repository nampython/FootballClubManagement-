package com.example.Excercise1.models;

import com.example.Excercise1.MarDao.XifinJdbcDaoManager;
import com.example.Excercise1.exceptions.XifinDataAccessFailureException;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * This MultipleMappedRowsIterator Iterator is used  to fetch bulk records from db and iterate through mapped objects
 * Fetch size can be specified by each caller
 * Caller must call close to release db connection.
 *
 *
 */
public class MultipleMappedRowsIterator<T extends Map> implements Iterator<List<T>> {

    private static final Logger logger = Logger.getLogger(MultipleMappedRowsIterator.class);

    private int fetchSize;
    List<Map<String, XifinJdbcDaoManager.Value>> rows = new ArrayList<>();
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private boolean hasMoreRecords = true;
    private DataSource dataSource;

    public MultipleMappedRowsIterator(DataSource dataSource, String sql, Object[] params, int fetchSize) {


        this.dataSource = dataSource;
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
            throw new XifinDataAccessFailureException("failure to get value objects, sql=" + sql + ",params=" + params, e);
        }
    }

    @Override
    public boolean hasNext() {
        return hasMoreRecords;
    }

    @Override
    public List<T> next() {

        rows = new ArrayList<>();
        try {
            while (resultSet.next()) {

                ResultSetMetaData meta = resultSet.getMetaData();
                int count = meta.getColumnCount();
                if (count <= 0) continue;

                Map<String, XifinJdbcDaoManager.Value> columnMap = new HashMap<>();
                for (int i = 1; i <= count; i++) {
                    Object obj = resultSet.getObject(i);
                    columnMap.put(meta.getColumnName(i).toLowerCase(), new XifinJdbcDaoManager.Value(obj));
                }
                rows.add(columnMap);

                if (rows.size() == fetchSize) {
                    break;
                }
            }
            hasMoreRecords = rows.size() == fetchSize;

        } catch (Exception e) {
            throw new XifinDataAccessFailureException("failed to load multiple mapped objects", e);

        }
        return (List<T>) rows;
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
