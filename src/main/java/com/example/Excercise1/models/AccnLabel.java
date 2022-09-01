package com.example.Excercise1.models;

import com.example.Excercise1.mars.ValueObject;
import org.apache.log4j.Category;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AccnLabel implements Serializable, ValueObject {
    public Timestamp audDt;
    public long audRecId;
    public String audUser;
    public String accnId;
    public int labelSeqId;
    public int seqId;
    private int resultCode = 101;
    private String resultCodeMessage = null;
    public boolean isDirty;
    private static Category log = Category.getInstance(AccnLabel.class.getName());
    private static final String UPDATE_SQL = "update accn_label set AUD_USER=?,FK_ACCN_ID=?,FK_LABEL_SEQ_ID=? where PK_SEQ_ID=?";
    private static final String INSERT_SQL = "insert into accn_label (AUD_USER,FK_ACCN_ID,FK_LABEL_SEQ_ID,PK_SEQ_ID) values (?,?,?,?)";
    private static final String DELETE_SQL = "delete accn_label where PK_SEQ_ID=?";
    private static final String SELECT_SQL = "select * from accn_label where PK_SEQ_ID=?";

    public AccnLabel() {
    }

    public AccnLabel(int seqId) {
        this.seqId = seqId;
    }

    public AccnLabel(AccnLabel accnLabel) {
        if (accnLabel.getAccnId() != null) {
            this.accnId = accnLabel.getAccnId();
        } else {
            this.accnId = null;
        }

        this.labelSeqId = accnLabel.getLabelSeqId();
        this.seqId = accnLabel.getSeqId();
    }

    public void copy(AccnLabel accnLabel) {
        if (accnLabel.getAccnId() != null) {
            this.accnId = accnLabel.getAccnId();
        } else {
            this.accnId = null;
        }

        this.labelSeqId = accnLabel.getLabelSeqId();
        this.seqId = accnLabel.getSeqId();
    }

    public void clear() {
        this.accnId = null;
        this.labelSeqId = 0;
        this.seqId = 0;
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

    public String getAccnId() {
        return this.accnId;
    }

    public int getLabelSeqId() {
        return this.labelSeqId;
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

    public void setAudUser(String audUser) {
        this.audUser = audUser;
    }

    public void setAccnId(String accnId) {
        if (accnId == null && this.accnId != null || accnId != null && !accnId.equals(this.accnId)) {
            this.accnId = accnId;
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
        str = str + ", accnId: " + this.accnId;
        str = str + ", labelSeqId: " + this.labelSeqId;
        str = str + ", seqId: " + this.seqId;
        str = str + ", resultCode: " + this.resultCode;
        str = str + ", resultCodeMessage: " + this.resultCodeMessage;
        str = str + ", isDirty: " + this.isDirty;
        return str;
    }

    public String toXml() {
        StringBuffer str = new StringBuffer();
        String date = null;
        str.append("<AccnLabel>");
        date = this.audDt != null ? String.valueOf(this.audDt.getTime()) : " ";
        str.append("<audDt>").append(date).append("</audDt>");
        str.append("<audRecId>").append(this.audRecId).append("</audRecId>");
        if (this.audUser != null && this.audUser.length() > 0) {
            str.append("<audUser>").append(this.audUser).append("</audUser>");
        }

        if (this.accnId != null && this.accnId.length() > 0) {
            str.append("<accnId>").append(this.accnId).append("</accnId>");
        }

        str.append("<labelSeqId>").append(this.labelSeqId).append("</labelSeqId>");
        str.append("<seqId>").append(this.seqId).append("</seqId>");
        str.append("</AccnLabel>");
        return str.toString();
    }

    public void parseSql(ResultSet rs) throws SQLException {
        this.clear();

        try {
            this.audDt = rs.getTimestamp("AUD_DT");
            this.audRecId = rs.getLong("AUD_REC_ID");
            this.audUser = rs.getString("AUD_USER");
            this.accnId = rs.getString("FK_ACCN_ID");
            this.labelSeqId = rs.getInt("FK_LABEL_SEQ_ID");
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
        } else if (obj != null && obj instanceof AccnLabel) {
            AccnLabel that = (AccnLabel)obj;
            return isEqual(this.accnId, that.accnId) && this.labelSeqId == that.labelSeqId && this.seqId == that.seqId;
        } else {
            return false;
        }
    }

    private static int getHashCode(Object obj) {
        return obj == null ? 0 : obj.hashCode();
    }

    public int hashCode() {
        long hashCode = (long)(getHashCode(this.accnId) + this.labelSeqId + this.seqId);
        return (int)hashCode;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getUpdateSql() {
        return "update accn_label set AUD_USER=?,FK_ACCN_ID=?,FK_LABEL_SEQ_ID=? where PK_SEQ_ID=?";
    }

    public String getInsertSql() {
        return "insert into accn_label (AUD_USER,FK_ACCN_ID,FK_LABEL_SEQ_ID,PK_SEQ_ID) values (?,?,?,?)";
    }

    public String getDeleteSql() {
        return "delete accn_label where PK_SEQ_ID=?";
    }

    public String getSelectSql() {
        return "select * from accn_label where PK_SEQ_ID=?";
    }

    public String getExecuteSql() {
        String sql = null;
        if (this.resultCode == 101) {
            sql = "insert into accn_label (AUD_USER,FK_ACCN_ID,FK_LABEL_SEQ_ID,PK_SEQ_ID) values (?,?,?,?)";
        } else if (this.resultCode == 0) {
            sql = "update accn_label set AUD_USER=?,FK_ACCN_ID=?,FK_LABEL_SEQ_ID=? where PK_SEQ_ID=?";
        } else if (this.resultCode == 103) {
            sql = "delete accn_label where PK_SEQ_ID=?";
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
            params = new ArrayList(4);
            ((List)params).add(SecurityManager.getUserId());
            ((List)params).add(this.accnId);
            ((List)params).add(new Integer(this.labelSeqId));
            ((List)params).addAll(this.getPkParams());
        }

        return (List)params;
    }
}

