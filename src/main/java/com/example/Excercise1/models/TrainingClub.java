package com.example.Excercise1.models;

import com.example.Excercise1.mars.ValueObject;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainingClub implements Serializable, ValueObject {
    public String about;
    public String clubAbbrev;
    public String clubName;
    public Date foundingDate;
    public int clubId;
    public String stadium;
    public String tournament;
    private int resultCode = 101;
    private String resultCodeMessage = null;
    public boolean isDirty;
    private static Category log = Category.getInstance(TrainingClub.class.getName());
    private static final String UPDATE_SQL = "update training_club set ABOUT=?,CLUB_ABBREV=?,CLUB_NAME=?,FOUNDING_DATE=?,STADIUM=?,TOURNAMENT=? where PK_CLUB_ID=?";
    private static final String INSERT_SQL = "insert into training_club (ABOUT,CLUB_ABBREV,CLUB_NAME,FOUNDING_DATE,STADIUM,TOURNAMENT,PK_CLUB_ID) values (?,?,?,?,?,?,?)";
    private static final String DELETE_SQL = "delete training_club where PK_CLUB_ID=?";
    private static final String SELECT_SQL = "select * from training_club where PK_CLUB_ID=?";

    public TrainingClub() {
    }

    public TrainingClub(int clubId) {
        this.clubId = clubId;
    }

    public TrainingClub(TrainingClub trainingClub) {
        if (trainingClub.getAbout() != null) {
            this.about = trainingClub.getAbout();
        } else {
            this.about = null;
        }

        if (trainingClub.getClubAbbrev() != null) {
            this.clubAbbrev = trainingClub.getClubAbbrev();
        } else {
            this.clubAbbrev = null;
        }

        if (trainingClub.getClubName() != null) {
            this.clubName = trainingClub.getClubName();
        } else {
            this.clubName = null;
        }

        if (trainingClub.getFoundingDate() != null) {
            this.foundingDate = new Date(trainingClub.getFoundingDate().getTime());
        } else {
            this.foundingDate = null;
        }

        this.clubId = trainingClub.getClubId();
        if (trainingClub.getStadium() != null) {
            this.stadium = trainingClub.getStadium();
        } else {
            this.stadium = null;
        }

        if (trainingClub.getTournament() != null) {
            this.tournament = trainingClub.getTournament();
        } else {
            this.tournament = null;
        }

    }

    public void copy(TrainingClub trainingClub) {
        if (trainingClub.getAbout() != null) {
            this.about = trainingClub.getAbout();
        } else {
            this.about = null;
        }

        if (trainingClub.getClubAbbrev() != null) {
            this.clubAbbrev = trainingClub.getClubAbbrev();
        } else {
            this.clubAbbrev = null;
        }

        if (trainingClub.getClubName() != null) {
            this.clubName = trainingClub.getClubName();
        } else {
            this.clubName = null;
        }

        if (trainingClub.getFoundingDate() != null) {
            this.foundingDate = new Date(trainingClub.getFoundingDate().getTime());
        } else {
            this.foundingDate = null;
        }

        this.clubId = trainingClub.getClubId();
        if (trainingClub.getStadium() != null) {
            this.stadium = trainingClub.getStadium();
        } else {
            this.stadium = null;
        }

        if (trainingClub.getTournament() != null) {
            this.tournament = trainingClub.getTournament();
        } else {
            this.tournament = null;
        }

    }

    public void clear() {
        this.about = null;
        this.clubAbbrev = null;
        this.clubName = null;
        this.foundingDate = null;
        this.clubId = 0;
        this.stadium = null;
        this.tournament = null;
    }

    public void setModified(boolean flag) {
        this.isDirty = flag;
    }

    public boolean isModified() {
        return this.isDirty;
    }

    public String getAbout() {
        return this.about;
    }

    public String getClubAbbrev() {
        return this.clubAbbrev;
    }

    public String getClubName() {
        return this.clubName;
    }

    public Date getFoundingDate() {
        return this.foundingDate;
    }

    public int getClubId() {
        return this.clubId;
    }

    public String getStadium() {
        return this.stadium;
    }

    public String getTournament() {
        return this.tournament;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public String getResultCodeMessage() {
        return this.resultCodeMessage;
    }

    public void setAbout(String about) {
        if (about == null && this.about != null || about != null && !about.equals(this.about)) {
            this.about = about;
            this.setModified(true);
        }

    }

    public void setClubAbbrev(String clubAbbrev) {
        if (clubAbbrev == null && this.clubAbbrev != null || clubAbbrev != null && !clubAbbrev.equals(this.clubAbbrev)) {
            this.clubAbbrev = clubAbbrev;
            this.setModified(true);
        }

    }

    public void setClubName(String clubName) {
        if (clubName == null && this.clubName != null || clubName != null && !clubName.equals(this.clubName)) {
            this.clubName = clubName;
            this.setModified(true);
        }

    }

    public void setFoundingDate(Date foundingDate) {
        if (foundingDate == null && this.foundingDate != null || foundingDate != null && !foundingDate.equals(this.foundingDate)) {
            this.foundingDate = foundingDate;
            this.setModified(true);
        }

    }

    public void setClubId(int clubId) {
        if (clubId != this.clubId) {
            this.clubId = clubId;
            this.setModified(true);
        }

    }

    public void setStadium(String stadium) {
        if (stadium == null && this.stadium != null || stadium != null && !stadium.equals(this.stadium)) {
            this.stadium = stadium;
            this.setModified(true);
        }

    }

    public void setTournament(String tournament) {
        if (tournament == null && this.tournament != null || tournament != null && !tournament.equals(this.tournament)) {
            this.tournament = tournament;
            this.setModified(true);
        }

    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultCodeMessage(String resultCodeMessage) {
        this.resultCodeMessage = resultCodeMessage;
    }

    public String toString() {
        String str = "";
        str = str + "about: " + this.about;
        str = str + ", clubAbbrev: " + this.clubAbbrev;
        str = str + ", clubName: " + this.clubName;
        str = str + ", foundingDate: " + this.foundingDate;
        str = str + ", clubId: " + this.clubId;
        str = str + ", stadium: " + this.stadium;
        str = str + ", tournament: " + this.tournament;
        str = str + ", resultCode: " + this.resultCode;
        str = str + ", resultCodeMessage: " + this.resultCodeMessage;
        str = str + ", isDirty: " + this.isDirty;
        return str;
    }

    public String toXml() {
        StringBuffer str = new StringBuffer();
        String date = null;
        str.append("<TrainingClub>");
        if (this.about != null && this.about.length() > 0) {
            str.append("<about>").append(this.about).append("</about>");
        }

        if (this.clubAbbrev != null && this.clubAbbrev.length() > 0) {
            str.append("<clubAbbrev>").append(this.clubAbbrev).append("</clubAbbrev>");
        }

        if (this.clubName != null && this.clubName.length() > 0) {
            str.append("<clubName>").append(this.clubName).append("</clubName>");
        }

        date = this.foundingDate != null ? String.valueOf(this.foundingDate.getTime()) : " ";
        str.append("<foundingDate>").append(date).append("</foundingDate>");
        str.append("<clubId>").append(this.clubId).append("</clubId>");
        if (this.stadium != null && this.stadium.length() > 0) {
            str.append("<stadium>").append(this.stadium).append("</stadium>");
        }

        if (this.tournament != null && this.tournament.length() > 0) {
            str.append("<tournament>").append(this.tournament).append("</tournament>");
        }

        str.append("</TrainingClub>");
        return str.toString();
    }

    public void parseSql(ResultSet rs) throws SQLException {
        this.clear();

        try {
            this.about = rs.getString("ABOUT");
            this.clubAbbrev = rs.getString("CLUB_ABBREV");
            this.clubName = rs.getString("CLUB_NAME");
            this.foundingDate = rs.getDate("FOUNDING_DATE");
            this.clubId = rs.getInt("PK_CLUB_ID");
            this.stadium = rs.getString("STADIUM");
            this.tournament = rs.getString("TOURNAMENT");
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
        } else if (obj != null && obj instanceof TrainingClub) {
            TrainingClub that = (TrainingClub)obj;
            return isEqual(this.about, that.about) && isEqual(this.clubAbbrev, that.clubAbbrev) && isEqual(this.clubName, that.clubName) && isEqual(this.foundingDate, that.foundingDate) && this.clubId == that.clubId && isEqual(this.stadium, that.stadium) && isEqual(this.tournament, that.tournament);
        } else {
            return false;
        }
    }

    private static int getHashCode(Object obj) {
        return obj == null ? 0 : obj.hashCode();
    }

    public int hashCode() {
        long hashCode = (long)(getHashCode(this.about) + getHashCode(this.clubAbbrev) + getHashCode(this.clubName) + getHashCode(this.foundingDate) + this.clubId + getHashCode(this.stadium) + getHashCode(this.tournament));
        return (int)hashCode;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getUpdateSql() {
        return "update training_club set ABOUT=?,CLUB_ABBREV=?,CLUB_NAME=?,FOUNDING_DATE=?,STADIUM=?,TOURNAMENT=? where PK_CLUB_ID=?";
    }

    public String getInsertSql() {
        return "insert into training_club (ABOUT,CLUB_ABBREV,CLUB_NAME,FOUNDING_DATE,STADIUM,TOURNAMENT,PK_CLUB_ID) values (?,?,?,?,?,?,?)";
    }

    public String getDeleteSql() {
        return "delete training_club where PK_CLUB_ID=?";
    }

    public String getSelectSql() {
        return "select * from training_club where PK_CLUB_ID=?";
    }

    public String getExecuteSql() {
        String sql = null;
        if (this.resultCode == 101) {
            sql = "insert into training_club (ABOUT,CLUB_ABBREV,CLUB_NAME,FOUNDING_DATE,STADIUM,TOURNAMENT,PK_CLUB_ID) values (?,?,?,?,?,?,?)";
        } else if (this.resultCode == 0) {
            sql = "update training_club set ABOUT=?,CLUB_ABBREV=?,CLUB_NAME=?,FOUNDING_DATE=?,STADIUM=?,TOURNAMENT=? where PK_CLUB_ID=?";
        } else if (this.resultCode == 103) {
            sql = "delete training_club where PK_CLUB_ID=?";
        }

        return sql;
    }

    public List getPkParams() {
        List params = new ArrayList(1);
        params.add(new Integer(this.clubId));
        return params;
    }

    public List getParams() {
        List params = null;
        if (this.resultCode == 103) {
            params = this.getPkParams();
        } else {
            params = new ArrayList(5);
            ((List)params).add(this.about);
            ((List)params).add(this.clubAbbrev);
            ((List)params).add(this.clubName);
            ((List)params).add(this.foundingDate);
            ((List)params).add(this.stadium);
            ((List)params).add(this.tournament);
            ((List)params).addAll(this.getPkParams());
        }

        return (List)params;
    }
}
