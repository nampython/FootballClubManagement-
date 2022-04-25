package com.example.Excercise1.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponObject {
    private Date timestamp;
    private  String status;
    private String message;
    private Object data;
}
