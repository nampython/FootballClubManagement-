package com.example.Excercise1.models;

import com.example.Excercise1.mars.ValueObject;
import org.apache.log4j.Category;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class WorkgroupUserAssignment implements Serializable, ValueObject {
    public int actualDailyCapacity;
    public Timestamp audDt;
    public long audRecId;
    public String audUser;
    public int workgroupSeqId;
    public int seqId;
    public int targetDailyCapacity;
    public String userId;
    private int resultCode = 101;
    private String resultCodeMessage = null;
    public boolean isDirty;
    private static Category log = Category.getInstance(WorkgroupUserAssignment.class.getName());
    private static final String UPDATE_SQL = "update workgroup_user_assignment set ACTUAL_DAILY_CAPACITY=?,AUD_USER=?,FK_WORKGROUP_SEQ_ID=?,TARGET_DAILY_CAPACITY=?,VK_USER_ID=? where PK_SEQ_ID=?";
    private static final String INSERT_SQL = "insert into workgroup_user_assignment (ACTUAL_DAILY_CAPACITY,AUD_USER,FK_WORKGROUP_SEQ_ID,TARGET_DAILY_CAPACITY,VK_USER_ID,PK_SEQ_ID) values (?,?,?,?,?,?)";
    private static final String DELETE_SQL = "delete workgroup_user_assignment where PK_SEQ_ID=?";
    private static final String SELECT_SQL = "select * from workgroup_user_assignment where PK_SEQ_ID=?";

    public WorkgroupUserAssignment() {
    }

    public WorkgroupUserAssignment(int seqId) {
        this.seqId = seqId;
    }

    public WorkgroupUserAssignment(WorkgroupUserAssignment workgroupUserAssignment) {
        this.actualDailyCapacity = workgroupUserAssignment.getActualDailyCapacity();
        this.workgroupSeqId = workgroupUserAssignment.getWorkgroupSeqId();
        this.seqId = workgroupUserAssignment.getSeqId();
        this.targetDailyCapacity = workgroupUserAssignment.getTargetDailyCapacity();
        if (workgroupUserAssignment.getUserId() != null) {
            this.userId = workgroupUserAssignment.getUserId();
        } else {
            this.userId = null;
        }

    }

    public void copy(WorkgroupUserAssignment workgroupUserAssignment) {
        this.actualDailyCapacity = workgroupUserAssignment.getActualDailyCapacity();
        this.workgroupSeqId = workgroupUserAssignment.getWorkgroupSeqId();
        this.seqId = workgroupUserAssignment.getSeqId();
        this.targetDailyCapacity = workgroupUserAssignment.getTargetDailyCapacity();
        if (workgroupUserAssignment.getUserId() != null) {
            this.userId = workgroupUserAssignment.getUserId();
        } else {
            this.userId = null;
        }

    }

    public void clear() {
        this.actualDailyCapacity = 0;
        this.workgroupSeqId = 0;
        this.seqId = 0;
        this.targetDailyCapacity = 0;
        this.userId = null;
    }

    public void setModified(boolean flag) {
        this.isDirty = flag;
    }

    public boolean isModified() {
        return this.isDirty;
    }

    public int getActualDailyCapacity() {
        return this.actualDailyCapacity;
    }

    public Timestamp getAudDt() {
        return this.audDt;
    }

    public long getAudRecId() {
        return this.audRecId;
    }

    public String getAudUser() {
        return this.audUser;
    }

    public int getWorkgroupSeqId() {
        return this.workgroupSeqId;
    }

    public int getSeqId() {
        return this.seqId;
    }

    public int getTargetDailyCapacity() {
        return this.targetDailyCapacity;
    }

    public String getUserId() {
        return this.userId;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public String getResultCodeMessage() {
        return this.resultCodeMessage;
    }

    public void setActualDailyCapacity(int actualDailyCapacity) {
        if (actualDailyCapacity != this.actualDailyCapacity) {
            this.actualDailyCapacity = actualDailyCapacity;
            this.setModified(true);
        }

    }

    public void setAudUser(String audUser) {
        this.audUser = audUser;
    }

    public void setWorkgroupSeqId(int workgroupSeqId) {
        if (workgroupSeqId != this.workgroupSeqId) {
            this.workgroupSeqId = workgroupSeqId;
            this.setModified(true);
        }

    }

    public void setSeqId(int seqId) {
        if (seqId != this.seqId) {
            this.seqId = seqId;
            this.setModified(true);
        }

    }

    public void setTargetDailyCapacity(int targetDailyCapacity) {
        if (targetDailyCapacity != this.targetDailyCapacity) {
            this.targetDailyCapacity = targetDailyCapacity;
            this.setModified(true);
        }

    }

    public void setUserId(String userId) {
        if (userId == null && this.userId != null || userId != null && !userId.equals(this.userId)) {
            this.userId = userId;
            this.setModified(true);
        }

    }

    public void setResultCode(int resultCode) {
        if (resultCode == 101) {
            this.audRecId = 0L;
        }

        this.resultCode = resultCode;
    }

    public void setResultCodeMessage(String resultCodeMessage) {
        this.resultCodeMessage = resultCodeMessage;
    }

    public String toString() {
        String str = "";
        str = str + "actualDailyCapacity: " + this.actualDailyCapacity;
        str = str + ", audDt: " + this.audDt;
        str = str + ", audRecId: " + this.audRecId;
        str = str + ", audUser: " + this.audUser;
        str = str + ", workgroupSeqId: " + this.workgroupSeqId;
        str = str + ", seqId: " + this.seqId;
        str = str + ", targetDailyCapacity: " + this.targetDailyCapacity;
        str = str + ", userId: " + this.userId;
        str = str + ", resultCode: " + this.resultCode;
        str = str + ", resultCodeMessage: " + this.resultCodeMessage;
        str = str + ", isDirty: " + this.isDirty;
        return str;
    }

    public String toXml() {
        StringBuffer str = new StringBuffer();
        String date = null;
        str.append("<WorkgroupUserAssignment>");
        str.append("<actualDailyCapacity>").append(this.actualDailyCapacity).append("</actualDailyCapacity>");
        date = this.audDt != null ? String.valueOf(this.audDt.getTime()) : " ";
        str.append("<audDt>").append(date).append("</audDt>");
        str.append("<audRecId>").append(this.audRecId).append("</audRecId>");
        if (this.audUser != null && this.audUser.length() > 0) {
            str.append("<audUser>").append(this.audUser).append("</audUser>");
        }

        str.append("<workgroupSeqId>").append(this.workgroupSeqId).append("</workgroupSeqId>");
        str.append("<seqId>").append(this.seqId).append("</seqId>");
        str.append("<targetDailyCapacity>").append(this.targetDailyCapacity).append("</targetDailyCapacity>");
        if (this.userId != null && this.userId.length() > 0) {
            str.append("<userId>").append(this.userId).append("</userId>");
        }

        str.append("</WorkgroupUserAssignment>");
        return str.toString();
    }

    public void parseSql(ResultSet rs) throws SQLException {
        this.clear();

        try {
            this.actualDailyCapacity = rs.getInt("ACTUAL_DAILY_CAPACITY");
            this.audDt = rs.getTimestamp("AUD_DT");
            this.audRecId = rs.getLong("AUD_REC_ID");
            this.audUser = rs.getString("AUD_USER");
            this.workgroupSeqId = rs.getInt("FK_WORKGROUP_SEQ_ID");
            this.seqId = rs.getInt("PK_SEQ_ID");
            this.targetDailyCapacity = rs.getInt("TARGET_DAILY_CAPACITY");
            this.userId = rs.getString("VK_USER_ID");
        } catch (SQLException var3) {
            throw var3;
        }
    }

    private static boolean isEqual(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        } else {
            return obj1 != null && obj2 != null && obj1.equals(obj2);
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj != null && obj instanceof WorkgroupUserAssignment) {
            WorkgroupUserAssignment that = (WorkgroupUserAssignment)obj;
            return this.actualDailyCapacity == that.actualDailyCapacity && this.workgroupSeqId == that.workgroupSeqId && this.seqId == that.seqId && this.targetDailyCapacity == that.targetDailyCapacity && isEqual(this.userId, that.userId);
        } else {
            return false;
        }
    }

    private static int getHashCode(Object obj) {
        return obj == null ? 0 : obj.hashCode();
    }

    public int hashCode() {
        long hashCode = (long)(this.actualDailyCapacity + this.workgroupSeqId + this.seqId + this.targetDailyCapacity + getHashCode(this.userId));
        return (int)hashCode;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getUpdateSql() {
        return "update workgroup_user_assignment set ACTUAL_DAILY_CAPACITY=?,AUD_USER=?,FK_WORKGROUP_SEQ_ID=?,TARGET_DAILY_CAPACITY=?,VK_USER_ID=? where PK_SEQ_ID=?";
    }

    public String getInsertSql() {
        return "insert into workgroup_user_assignment (ACTUAL_DAILY_CAPACITY,AUD_USER,FK_WORKGROUP_SEQ_ID,TARGET_DAILY_CAPACITY,VK_USER_ID,PK_SEQ_ID) values (?,?,?,?,?,?)";
    }

    public String getDeleteSql() {
        return "delete workgroup_user_assignment where PK_SEQ_ID=?";
    }

    public String getSelectSql() {
        return "select * from workgroup_user_assignment where PK_SEQ_ID=?";
    }

    public String getExecuteSql() {
        String sql = null;
        if (this.resultCode == 101) {
            sql = "insert into workgroup_user_assignment (ACTUAL_DAILY_CAPACITY,AUD_USER,FK_WORKGROUP_SEQ_ID,TARGET_DAILY_CAPACITY,VK_USER_ID,PK_SEQ_ID) values (?,?,?,?,?,?)";
        } else if (this.resultCode == 0) {
            sql = "update workgroup_user_assignment set ACTUAL_DAILY_CAPACITY=?,AUD_USER=?,FK_WORKGROUP_SEQ_ID=?,TARGET_DAILY_CAPACITY=?,VK_USER_ID=? where PK_SEQ_ID=?";
        } else if (this.resultCode == 103) {
            sql = "delete workgroup_user_assignment where PK_SEQ_ID=?";
        }

        return sql;
    }

    public List getPkParams() {
        List params = new ArrayList(1);
        params.add(new Integer(this.seqId));
        return params;
    }

    public List getParams() {
        List params = null;
        if (this.resultCode == 103) {
            params = this.getPkParams();
        } else {
            params = new ArrayList(6);
            ((List)params).add(new Integer(this.actualDailyCapacity));
            ((List)params).add(SecurityManager.getUserId());
            ((List)params).add(new Integer(this.workgroupSeqId));
            ((List)params).add(new Integer(this.targetDailyCapacity));
            ((List)params).add(this.userId);
            ((List)params).addAll(this.getPkParams());
        }

        return (List)params;
    }
}

