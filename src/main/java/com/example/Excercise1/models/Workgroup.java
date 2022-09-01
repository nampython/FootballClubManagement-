package com.example.Excercise1.models;

import com.example.Excercise1.mars.ValueObject;
import org.apache.log4j.Category;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Workgroup implements Serializable, ValueObject {
    public Timestamp audDt;
    public long audRecId;
    public String audUser;
    public boolean isDeleted;
    public int labelSeqId;
    public int seqId;
    public String workgroupAbbrev;
    public String workgroupDescr;
    private int resultCode = 101;
    private String resultCodeMessage = null;
    public boolean isDirty;
    private static Category log = Category.getInstance(Workgroup.class.getName());
    private static final String UPDATE_SQL = "update workgroup set AUD_USER=?,B_DELETED=?,FK_LABEL_SEQ_ID=?,WORKGROUP_ABBREV=?,WORKGROUP_DESCR=? where PK_SEQ_ID=?";
    private static final String INSERT_SQL = "insert into workgroup (AUD_USER,B_DELETED,FK_LABEL_SEQ_ID,WORKGROUP_ABBREV,WORKGROUP_DESCR,PK_SEQ_ID) values (?,?,?,?,?,?)";
    private static final String DELETE_SQL = "delete workgroup where PK_SEQ_ID=?";
    private static final String SELECT_SQL = "select * from workgroup where PK_SEQ_ID=?";

    public Workgroup() {
    }

    public Workgroup(int seqId) {
        this.seqId = seqId;
    }

    public Workgroup(Workgroup workgroup) {
        this.isDeleted = workgroup.getIsDeleted();
        this.labelSeqId = workgroup.getLabelSeqId();
        this.seqId = workgroup.getSeqId();
        if (workgroup.getWorkgroupAbbrev() != null) {
            this.workgroupAbbrev = workgroup.getWorkgroupAbbrev();
        } else {
            this.workgroupAbbrev = null;
        }

        if (workgroup.getWorkgroupDescr() != null) {
            this.workgroupDescr = workgroup.getWorkgroupDescr();
        } else {
            this.workgroupDescr = null;
        }

    }

    public void copy(Workgroup workgroup) {
        this.isDeleted = workgroup.getIsDeleted();
        this.labelSeqId = workgroup.getLabelSeqId();
        this.seqId = workgroup.getSeqId();
        if (workgroup.getWorkgroupAbbrev() != null) {
            this.workgroupAbbrev = workgroup.getWorkgroupAbbrev();
        } else {
            this.workgroupAbbrev = null;
        }

        if (workgroup.getWorkgroupDescr() != null) {
            this.workgroupDescr = workgroup.getWorkgroupDescr();
        } else {
            this.workgroupDescr = null;
        }

    }

    public void clear() {
        this.isDeleted = false;
        this.labelSeqId = 0;
        this.seqId = 0;
        this.workgroupAbbrev = null;
        this.workgroupDescr = null;
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

    public int getLabelSeqId() {
        return this.labelSeqId;
    }

    public int getSeqId() {
        return this.seqId;
    }

    public String getWorkgroupAbbrev() {
        return this.workgroupAbbrev;
    }

    public String getWorkgroupDescr() {
        return this.workgroupDescr;
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

    public void setLabelSeqId(int labelSeqId) {
        if (labelSeqId != this.labelSeqId) {
            this.labelSeqId = labelSeqId;
            this.setModified(true);
        }

    }

    public void setSeqId(int seqId) {
        if (seqId != this.seqId) {
            this.seqId = seqId;
            this.setModified(true);
        }

    }

    public void setWorkgroupAbbrev(String workgroupAbbrev) {
        if (workgroupAbbrev == null && this.workgroupAbbrev != null || workgroupAbbrev != null && !workgroupAbbrev.equals(this.workgroupAbbrev)) {
            this.workgroupAbbrev = workgroupAbbrev;
            this.setModified(true);
        }

    }

    public void setWorkgroupDescr(String workgroupDescr) {
        if (workgroupDescr == null && this.workgroupDescr != null || workgroupDescr != null && !workgroupDescr.equals(this.workgroupDescr)) {
            this.workgroupDescr = workgroupDescr;
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
        str = str + ", labelSeqId: " + this.labelSeqId;
        str = str + ", seqId: " + this.seqId;
        str = str + ", workgroupAbbrev: " + this.workgroupAbbrev;
        str = str + ", workgroupDescr: " + this.workgroupDescr;
        str = str + ", resultCode: " + this.resultCode;
        str = str + ", resultCodeMessage: " + this.resultCodeMessage;
        str = str + ", isDirty: " + this.isDirty;
        return str;
    }

    public String toXml() {
        StringBuffer str = new StringBuffer();
        String date = null;
        str.append("<Workgroup>");
        date = this.audDt != null ? String.valueOf(this.audDt.getTime()) : " ";
        str.append("<audDt>").append(date).append("</audDt>");
        str.append("<audRecId>").append(this.audRecId).append("</audRecId>");
        if (this.audUser != null && this.audUser.length() > 0) {
            str.append("<audUser>").append(this.audUser).append("</audUser>");
        }

        str.append("<isDeleted>").append(this.isDeleted).append("</isDeleted>");
        str.append("<labelSeqId>").append(this.labelSeqId).append("</labelSeqId>");
        str.append("<seqId>").append(this.seqId).append("</seqId>");
        if (this.workgroupAbbrev != null && this.workgroupAbbrev.length() > 0) {
            str.append("<workgroupAbbrev>").append(this.workgroupAbbrev).append("</workgroupAbbrev>");
        }

        if (this.workgroupDescr != null && this.workgroupDescr.length() > 0) {
            str.append("<workgroupDescr>").append(this.workgroupDescr).append("</workgroupDescr>");
        }

        str.append("</Workgroup>");
        return str.toString();
    }

    public void parseSql(ResultSet rs) throws SQLException {
        this.clear();

        try {
            this.audDt = rs.getTimestamp("AUD_DT");
            this.audRecId = rs.getLong("AUD_REC_ID");
            this.audUser = rs.getString("AUD_USER");
            this.isDeleted = IntToBooleanConverter.intToBoolean(rs.getInt("B_DELETED"));
            this.labelSeqId = rs.getInt("FK_LABEL_SEQ_ID");
            this.seqId = rs.getInt("PK_SEQ_ID");
            this.workgroupAbbrev = rs.getString("WORKGROUP_ABBREV");
            this.workgroupDescr = rs.getString("WORKGROUP_DESCR");
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
        } else if (obj != null && obj instanceof Workgroup) {
            Workgroup that = (Workgroup)obj;
            return this.isDeleted == that.isDeleted && this.labelSeqId == that.labelSeqId && this.seqId == that.seqId && isEqual(this.workgroupAbbrev, that.workgroupAbbrev) && isEqual(this.workgroupDescr, that.workgroupDescr);
        } else {
            return false;
        }
    }

    private static int getHashCode(Object obj) {
        return obj == null ? 0 : obj.hashCode();
    }

    public int hashCode() {
        long hashCode = (long)((this.isDeleted ? 1 : 0) + this.labelSeqId + this.seqId + getHashCode(this.workgroupAbbrev) + getHashCode(this.workgroupDescr));
        return (int)hashCode;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getUpdateSql() {
        return "update workgroup set AUD_USER=?,B_DELETED=?,FK_LABEL_SEQ_ID=?,WORKGROUP_ABBREV=?,WORKGROUP_DESCR=? where PK_SEQ_ID=?";
    }

    public String getInsertSql() {
        return "insert into workgroup (AUD_USER,B_DELETED,FK_LABEL_SEQ_ID,WORKGROUP_ABBREV,WORKGROUP_DESCR,PK_SEQ_ID) values (?,?,?,?,?,?)";
    }

    public String getDeleteSql() {
        return "delete workgroup where PK_SEQ_ID=?";
    }

    public String getSelectSql() {
        return "select * from workgroup where PK_SEQ_ID=?";
    }

    public String getExecuteSql() {
        String sql = null;
        if (this.resultCode == 101) {
            sql = "insert into workgroup (AUD_USER,B_DELETED,FK_LABEL_SEQ_ID,WORKGROUP_ABBREV,WORKGROUP_DESCR,PK_SEQ_ID) values (?,?,?,?,?,?)";
        } else if (this.resultCode == 0) {
            sql = "update workgroup set AUD_USER=?,B_DELETED=?,FK_LABEL_SEQ_ID=?,WORKGROUP_ABBREV=?,WORKGROUP_DESCR=? where PK_SEQ_ID=?";
        } else if (this.resultCode == 103) {
            sql = "delete workgroup where PK_SEQ_ID=?";
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
            ((List)params).add(new Integer(this.labelSeqId));
            ((List)params).add(this.workgroupAbbrev);
            ((List)params).add(this.workgroupDescr);
            ((List)params).addAll(this.getPkParams());
        }

        return (List)params;
    }
}

