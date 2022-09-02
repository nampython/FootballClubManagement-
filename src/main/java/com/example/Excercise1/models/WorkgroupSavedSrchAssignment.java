package com.example.Excercise1.models;

import com.example.Excercise1.mars.ValueObject;
import org.apache.log4j.Category;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class WorkgroupSavedSrchAssignment implements Serializable, ValueObject {
    public Timestamp audDt;
    public long audRecId;
    public String audUser;
    public boolean isDeleted;
    public String epSrchId;
    public int workgroupSeqId;
    public int seqId;
    public int priority;
    private int resultCode = 101;
    private String resultCodeMessage = null;
    public boolean isDirty;
    private static Category log = Category.getInstance(WorkgroupSavedSrchAssignment.class.getName());
    private static final String UPDATE_SQL = "update workgroup_saved_srch_assignment set AUD_USER=?,B_DELETED=?,FK_EP_SRCH_ID=?,FK_WORKGROUP_SEQ_ID=?,PRIORITY=? where PK_SEQ_ID=?";
    private static final String INSERT_SQL = "insert into workgroup_saved_srch_assignment (AUD_USER,B_DELETED,FK_EP_SRCH_ID,FK_WORKGROUP_SEQ_ID,PRIORITY,PK_SEQ_ID) values (?,?,?,?,?,?)";
    private static final String DELETE_SQL = "delete workgroup_saved_srch_assignment where PK_SEQ_ID=?";
    private static final String SELECT_SQL = "select * from workgroup_saved_srch_assignment where PK_SEQ_ID=?";

    public WorkgroupSavedSrchAssignment() {
    }

    public WorkgroupSavedSrchAssignment(int seqId) {
        this.seqId = seqId;
    }

    public WorkgroupSavedSrchAssignment(WorkgroupSavedSrchAssignment workgroupSavedSrchAssignment) {
        this.isDeleted = workgroupSavedSrchAssignment.getIsDeleted();
        if (workgroupSavedSrchAssignment.getEpSrchId() != null) {
            this.epSrchId = workgroupSavedSrchAssignment.getEpSrchId();
        } else {
            this.epSrchId = null;
        }

        this.workgroupSeqId = workgroupSavedSrchAssignment.getWorkgroupSeqId();
        this.seqId = workgroupSavedSrchAssignment.getSeqId();
        this.priority = workgroupSavedSrchAssignment.getPriority();
    }

    public void copy(WorkgroupSavedSrchAssignment workgroupSavedSrchAssignment) {
        this.isDeleted = workgroupSavedSrchAssignment.getIsDeleted();
        if (workgroupSavedSrchAssignment.getEpSrchId() != null) {
            this.epSrchId = workgroupSavedSrchAssignment.getEpSrchId();
        } else {
            this.epSrchId = null;
        }

        this.workgroupSeqId = workgroupSavedSrchAssignment.getWorkgroupSeqId();
        this.seqId = workgroupSavedSrchAssignment.getSeqId();
        this.priority = workgroupSavedSrchAssignment.getPriority();
    }

    public void clear() {
        this.isDeleted = false;
        this.epSrchId = null;
        this.workgroupSeqId = 0;
        this.seqId = 0;
        this.priority = 0;
    }

    public void setModified(boolean flag) {
        this.isDirty = flag;
    }

    public boolean isModified() {
        return this.isDirty;
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

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public String getEpSrchId() {
        return this.epSrchId;
    }

    public int getWorkgroupSeqId() {
        return this.workgroupSeqId;
    }

    public int getSeqId() {
        return this.seqId;
    }

    public int getPriority() {
        return this.priority;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public String getResultCodeMessage() {
        return this.resultCodeMessage;
    }

    public void setAudUser(String audUser) {
        this.audUser = audUser;
    }

    public void setIsDeleted(boolean isDeleted) {
        if (isDeleted != this.isDeleted) {
            this.isDeleted = isDeleted;
            this.setModified(true);
        }

    }

    public void setEpSrchId(String epSrchId) {
        if (epSrchId == null && this.epSrchId != null || epSrchId != null && !epSrchId.equals(this.epSrchId)) {
            this.epSrchId = epSrchId;
            this.setModified(true);
        }

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

    public void setPriority(int priority) {
        if (priority != this.priority) {
            this.priority = priority;
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
        str = str + "audDt: " + this.audDt;
        str = str + ", audRecId: " + this.audRecId;
        str = str + ", audUser: " + this.audUser;
        str = str + ", isDeleted: " + this.isDeleted;
        str = str + ", epSrchId: " + this.epSrchId;
        str = str + ", workgroupSeqId: " + this.workgroupSeqId;
        str = str + ", seqId: " + this.seqId;
        str = str + ", priority: " + this.priority;
        str = str + ", resultCode: " + this.resultCode;
        str = str + ", resultCodeMessage: " + this.resultCodeMessage;
        str = str + ", isDirty: " + this.isDirty;
        return str;
    }

    public String toXml() {
        StringBuffer str = new StringBuffer();
        String date = null;
        str.append("<WorkgroupSavedSrchAssignment>");
        date = this.audDt != null ? String.valueOf(this.audDt.getTime()) : " ";
        str.append("<audDt>").append(date).append("</audDt>");
        str.append("<audRecId>").append(this.audRecId).append("</audRecId>");
        if (this.audUser != null && this.audUser.length() > 0) {
            str.append("<audUser>").append(this.audUser).append("</audUser>");
        }

        str.append("<isDeleted>").append(this.isDeleted).append("</isDeleted>");
        if (this.epSrchId != null && this.epSrchId.length() > 0) {
            str.append("<epSrchId>").append(this.epSrchId).append("</epSrchId>");
        }

        str.append("<workgroupSeqId>").append(this.workgroupSeqId).append("</workgroupSeqId>");
        str.append("<seqId>").append(this.seqId).append("</seqId>");
        str.append("<priority>").append(this.priority).append("</priority>");
        str.append("</WorkgroupSavedSrchAssignment>");
        return str.toString();
    }

    public void parseSql(ResultSet rs) throws SQLException {
        this.clear();

        try {
            this.audDt = rs.getTimestamp("AUD_DT");
            this.audRecId = rs.getLong("AUD_REC_ID");
            this.audUser = rs.getString("AUD_USER");
            this.isDeleted = IntToBooleanConverter.intToBoolean(rs.getInt("B_DELETED"));
            this.epSrchId = rs.getString("FK_EP_SRCH_ID");
            this.workgroupSeqId = rs.getInt("FK_WORKGROUP_SEQ_ID");
            this.seqId = rs.getInt("PK_SEQ_ID");
            this.priority = rs.getInt("PRIORITY");
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
        } else if (obj != null && obj instanceof WorkgroupSavedSrchAssignment) {
            WorkgroupSavedSrchAssignment that = (WorkgroupSavedSrchAssignment)obj;
            return this.isDeleted == that.isDeleted && isEqual(this.epSrchId, that.epSrchId) && this.workgroupSeqId == that.workgroupSeqId && this.seqId == that.seqId && this.priority == that.priority;
        } else {
            return false;
        }
    }

    private static int getHashCode(Object obj) {
        return obj == null ? 0 : obj.hashCode();
    }

    public int hashCode() {
        long hashCode = (long)((this.isDeleted ? 1 : 0) + getHashCode(this.epSrchId) + this.workgroupSeqId + this.seqId + this.priority);
        return (int)hashCode;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getUpdateSql() {
        return "update workgroup_saved_srch_assignment set AUD_USER=?,B_DELETED=?,FK_EP_SRCH_ID=?,FK_WORKGROUP_SEQ_ID=?,PRIORITY=? where PK_SEQ_ID=?";
    }

    public String getInsertSql() {
        return "insert into workgroup_saved_srch_assignment (AUD_USER,B_DELETED,FK_EP_SRCH_ID,FK_WORKGROUP_SEQ_ID,PRIORITY,PK_SEQ_ID) values (?,?,?,?,?,?)";
    }

    public String getDeleteSql() {
        return "delete workgroup_saved_srch_assignment where PK_SEQ_ID=?";
    }

    public String getSelectSql() {
        return "select * from workgroup_saved_srch_assignment where PK_SEQ_ID=?";
    }

    public String getExecuteSql() {
        String sql = null;
        if (this.resultCode == 101) {
            sql = "insert into workgroup_saved_srch_assignment (AUD_USER,B_DELETED,FK_EP_SRCH_ID,FK_WORKGROUP_SEQ_ID,PRIORITY,PK_SEQ_ID) values (?,?,?,?,?,?)";
        } else if (this.resultCode == 0) {
            sql = "update workgroup_saved_srch_assignment set AUD_USER=?,B_DELETED=?,FK_EP_SRCH_ID=?,FK_WORKGROUP_SEQ_ID=?,PRIORITY=? where PK_SEQ_ID=?";
        } else if (this.resultCode == 103) {
            sql = "delete workgroup_saved_srch_assignment where PK_SEQ_ID=?";
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
            ((List)params).add(SecurityManager.getUserId());
            ((List)params).add(this.isDeleted ? Boolean.TRUE : Boolean.FALSE);
            ((List)params).add(this.epSrchId);
            ((List)params).add(new Integer(this.workgroupSeqId));
            ((List)params).add(new Integer(this.priority));
            ((List)params).addAll(this.getPkParams());
        }

        return (List)params;
    }
}
