package com.example.Excercise1.MarDao;

import com.example.Excercise1.exceptions.XifinDataNotFoundException;
import com.example.Excercise1.mars.ValueObject;

import java.util.List;

public interface MarsDaoManager {
    <T extends ValueObject> T getValueObject(String sql, List params, Class<T> valueObjectClass) throws XifinDataNotFoundException;
}
