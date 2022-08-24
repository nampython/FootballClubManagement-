package com.example.Excercise1.exceptions;

import org.springframework.dao.DataAccessException;

/**
 * Created by IntelliJ IDEA.
 * User: nchava
 * Date: 2/28/11
 * Time: 5:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class XifinDataAccessException extends DataAccessException{
    /**
     * Constructor for DataAccessException.
     *
     * @param msg the detail message
     */
    public XifinDataAccessException(String msg) {
        super(msg);
    }

    /**
     * Constructor for DataAccessException.
     *
     * @param msg   the detail message
     * @param cause the root cause (usually from using a underlying
     *              data access API such as JDBC)
     */
    public XifinDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

