package com.example.Excercise1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FileMaintEPWorkgroup {
    List<EPWorkgroup> epWorkgroups;

    @JsonIgnore
    private ErrorCode errorCode;

    public List<EPWorkgroup> getEpWorkgroups() {
        return epWorkgroups;
    }

    public void setEpWorkgroups(List<EPWorkgroup> epWorkgroups) {
        this.epWorkgroups = epWorkgroups;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}