package com.example.Excercise1.models;

import com.example.Excercise1.mars.ValueObject;
import org.apache.log4j.Category;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Label implements Serializable, ValueObject {
    public String abbrev;
    public Timestamp audDt;
    public long audRecId;
    public String audUser;
    public boolean isDeleted;
    public String descr;
    public int labelTypSeqId;
    public int seqId;
    private int resultCode = 101;
    private String resultCodeMessage = null;
    public boolean isDirty;
    private static Category log = Category.getInstance(Label.class.getName());
    private static final String UPDATE_SQL = "update label set ABBREV=?,AUD_USER=?,B_DELETED=?,DESCR=?,FK_LABEL_TYP_SEQ_ID=? where PK_SEQ_ID=?";
    private static final String INSERT_SQL = "insert into label (ABBREV,AUD_USER,B_DELETED,DESCR,FK_LABEL_TYP_SEQ_ID,PK_SEQ_ID) values (?,?,?,?,?,?)";
    private static final String DELETE_SQL = "delete label where PK_SEQ_ID=?";
    private static final String SELECT_SQL = "select * from label where PK_SEQ_ID=?";

    public Label() {
    }

    public Label(int seqId) {
        this.seqId = seqId;
    }

    public Label(Label label) {
        if (label.getAbbrev() != null) {
            this.abbrev = label.getAbbrev();
        } else {
            this.abbrev = null;
        }

        this.isDeleted = label.getIsDeleted();
        if (label.getDescr() != null) {
            this.descr = label.getDescr();
        } else {
            this.descr = null;
        }

        this.labelTypSeqId = label.getLabelTypSeqId();
        this.seqId = label.getSeqId();
    }

    public void copy(Label label) {
        if (label.getAbbrev() != null) {
            this.abbrev = label.getAbbrev();
        } else {
            this.abbrev = null;
        }

        this.isDeleted = label.getIsDeleted();
        if (label.getDescr() != null) {
            this.descr = label.getDescr();
        } else {
            this.descr = null;
        }

        this.labelTypSeqId = label.getLabelTypSeqId();
        this.seqId = label.getSeqId();
    }

    public void clear() {
        this.abbrev = null;
        this.isDeleted = false;
        this.descr = null;
        this.labelTypSeqId = 0;
        this.seqId = 0;
    }

    public void setModified(boolean flag) {
        this.isDirty = flag;
    }

    public boolean isModified() {
        return this.isDirty;
    }

    public String getAbbrev() {
        return this.abbrev;
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

    public String getDescr() {
        return this.descr;
    }

    public int getLabelTypSeqId() {
        return this.labelTypSeqId;
    }

    public int getSeqId() {
        return this.seqId;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public String getResultCodeMessage() {
        return this.resultCodeMessage;
    }

    public void setAbbrev(String abbrev) {
        if (abbrev == null && this.abbrev != null || abbrev != null && !abbrev.equals(this.abbrev)) {
            this.abbrev = abbrev;
            this.setModified(true);
        }

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

    public void setDescr(String descr) {
        if (descr == null && this.descr != null || descr != null && !descr.equals(this.descr)) {
            this.descr = descr;
            this.setModified(true);
        }

    }

    public void setLabelTypSeqId(int labelTypSeqId) {
        if (labelTypSeqId != this.labelTypSeqId) {
            this.labelTypSeqId = labelTypSeqId;
            this.setModified(true);
        }

    }

    public void setSeqId(int seqId) {
        if (seqId != this.seqId) {
            this.seqId = seqId;
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
        str = str + "abbrev: " + this.abbrev;
        str = str + ", audDt: " + this.audDt;
        str = str + ", audRecId: " + this.audRecId;
        str = str + ", audUser: " + this.audUser;
        str = str + ", isDeleted: " + this.isDeleted;
        str = str + ", descr: " + this.descr;
        str = str + ", labelTypSeqId: " + this.labelTypSeqId;
        str = str + ", seqId: " + this.seqId;
        str = str + ", resultCode: " + this.resultCode;
        str = str + ", resultCodeMessage: " + this.resultCodeMessage;
        str = str + ", isDirty: " + this.isDirty;
        return str;
    }

    public String toXml() {
        StringBuffer str = new StringBuffer();
        String date = null;
        str.append("<Label>");
        if (this.abbrev != null && this.abbrev.length() > 0) {
            str.append("<abbrev>").append(this.abbrev).append("</abbrev>");
        }

        date = this.audDt != null ? String.valueOf(this.audDt.getTime()) : " ";
        str.append("<audDt>").append(date).append("</audDt>");
        str.append("<audRecId>").append(this.audRecId).append("</audRecId>");
        if (this.audUser != null && this.audUser.length() > 0) {
            str.append("<audUser>").append(this.audUser).append("</audUser>");
        }

        str.append("<isDeleted>").append(this.isDeleted).append("</isDeleted>");
        if (this.descr != null && this.descr.length() > 0) {
            str.append("<descr>").append(this.descr).append("</descr>");
        }

        str.append("<labelTypSeqId>").append(this.labelTypSeqId).append("</labelTypSeqId>");
        str.append("<seqId>").append(this.seqId).append("</seqId>");
        str.append("</Label>");
        return str.toString();
    }

    public void parseSql(ResultSet rs) throws SQLException {
        this.clear();

        try {
            this.abbrev = rs.getString("ABBREV");
            this.audDt = rs.getTimestamp("AUD_DT");
            this.audRecId = rs.getLong("AUD_REC_ID");
            this.audUser = rs.getString("AUD_USER");
            this.isDeleted = IntToBooleanConverter.intToBoolean(rs.getInt("B_DELETED"));
            this.descr = rs.getString("DESCR");
            this.labelTypSeqId = rs.getInt("FK_LABEL_TYP_SEQ_ID");
            this.seqId = rs.getInt("PK_SEQ_ID");
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
        } else if (obj != null && obj instanceof Label) {
            Label that = (Label)obj;
            return isEqual(this.abbrev, that.abbrev) && this.isDeleted == that.isDeleted && isEqual(this.descr, that.descr) && this.labelTypSeqId == that.labelTypSeqId && this.seqId == that.seqId;
        } else {
            return false;
        }
    }

    private static int getHashCode(Object obj) {
        return obj == null ? 0 : obj.hashCode();
    }

    public int hashCode() {
        long hashCode = (long)(getHashCode(this.abbrev) + (this.isDeleted ? 1 : 0) + getHashCode(this.descr) + this.labelTypSeqId + this.seqId);
        return (int)hashCode;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getUpdateSql() {
        return "update label set ABBREV=?,AUD_USER=?,B_DELETED=?,DESCR=?,FK_LABEL_TYP_SEQ_ID=? where PK_SEQ_ID=?";
    }

    public String getInsertSql() {
        return "insert into label (ABBREV,AUD_USER,B_DELETED,DESCR,FK_LABEL_TYP_SEQ_ID,PK_SEQ_ID) values (?,?,?,?,?,?)";
    }

    public String getDeleteSql() {
        return "delete label where PK_SEQ_ID=?";
    }

    public String getSelectSql() {
        return "select * from label where PK_SEQ_ID=?";
    }

    public String getExecuteSql() {
        String sql = null;
        if (this.resultCode == 101) {
            sql = "insert into label (ABBREV,AUD_USER,B_DELETED,DESCR,FK_LABEL_TYP_SEQ_ID,PK_SEQ_ID) values (?,?,?,?,?,?)";
        } else if (this.resultCode == 0) {
            sql = "update label set ABBREV=?,AUD_USER=?,B_DELETED=?,DESCR=?,FK_LABEL_TYP_SEQ_ID=? where PK_SEQ_ID=?";
        } else if (this.resultCode == 103) {
            sql = "delete label where PK_SEQ_ID=?";
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
            ((List)params).add(this.abbrev);
            ((List)params).add(SecurityManager.getUserId());
            ((List)params).add(this.isDeleted ? Boolean.TRUE : Boolean.FALSE);
            ((List)params).add(this.descr);
            ((List)params).add(new Integer(this.labelTypSeqId));
            ((List)params).addAll(this.getPkParams());
        }

        return (List)params;
    }
}
