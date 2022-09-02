package com.example.Excercise1.models;

public class IntToBooleanConverter {
    public IntToBooleanConverter() {
    }

    public static boolean intToBoolean(int parm) {
        boolean bool = parm != 0;
        return bool;
    }
}
