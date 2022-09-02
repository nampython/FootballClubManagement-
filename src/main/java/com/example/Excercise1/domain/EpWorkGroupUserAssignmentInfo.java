package com.example.Excercise1.domain;

import com.example.Excercise1.utility.Money;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class EpWorkGroupUserAssignmentInfo {

    int priority=0;
    String priorityAbbrev= StringUtils.EMPTY;
    int workgroupId=0;
    String workGroupAbbrev= StringUtils.EMPTY;
    int errorCnt = 0;
    int accnCnt = 0;
    BigDecimal dueAmt;
    Money dueAmtAsMoney = new Money();
    int newErrCnt = 0;
    int newAccnCnt = 0;
    String userId;
    String superVisorId;


    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getWorkgroupId() {
        return workgroupId;
    }

    public void setWorkgroupId(int workgroupId) {
        this.workgroupId = workgroupId;
    }

    public String getPriorityAbbrev() {
        return priorityAbbrev;
    }

    public void setPriorityAbbrev(String priorityAbbrev) {
        this.priorityAbbrev = priorityAbbrev;
    }

    public String getWorkGroupAbbrev() {
        return workGroupAbbrev;
    }

    public void setWorkGroupAbbrev(String workGroupAbbrev) {
        this.workGroupAbbrev = workGroupAbbrev;
    }


    public int getErrorCnt() {
        return errorCnt;
    }

    public void setErrorCnt(int errorCnt) {
        this.errorCnt = errorCnt;
    }

    public int getAccnCnt() {
        return accnCnt;
    }

    public void setAccnCnt(int accnCnt) {
        this.accnCnt = accnCnt;
    }

    public BigDecimal getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(BigDecimal dueAmt) {
        this.dueAmt = dueAmt;
    }

    public int getNewErrCnt() {
        return newErrCnt;
    }

    public void setNewErrCnt(int newErrCnt) {
        this.newErrCnt = newErrCnt;
    }

    public int getNewAccnCnt() {
        return newAccnCnt;
    }

    public void setNewAccnCnt(int newAccnCnt) {
        this.newAccnCnt = newAccnCnt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSuperVisorId() {
        return superVisorId;
    }

    public void setSuperVisorId(String superVisorId) {
        this.superVisorId = superVisorId;
    }


    public Money getDueAmtAsMoney() {
        return dueAmtAsMoney;
    }

    public void setDueAmtAsMoney(Money dueAmtAsMoney) {
        this.dueAmtAsMoney = dueAmtAsMoney;
    }



}

