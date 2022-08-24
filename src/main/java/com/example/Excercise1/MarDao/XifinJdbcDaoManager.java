package com.example.Excercise1.MarDao;

import com.example.Excercise1.exceptions.XifinDataAccessFailureException;
import com.example.Excercise1.exceptions.XifinDataNotFoundException;
import com.example.Excercise1.mars.ValueObject;

import java.util.List;

public class XifinJdbcDaoManager implements MarsDaoManager{

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

    public void loadValueObject(String sql, List params, ValueObject valueObject) {

    }
}
