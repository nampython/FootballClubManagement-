package com.example.Excercise1.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Data;



@Entity(name="PlayerEntity")
@Table(name = "tranning_player")
@Data
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String playerName;
    private String dateOfBirth;
    private String national;
    private String height;
    public Boolean leftFooted;
    public Boolean rightFooted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    public Player() {
    }

    public Player(Long playerId, String playerName, String dateOfBirth, String national, String height, Boolean leftFooted, Boolean rightFooted, Club clubId) {
        this.id = playerId;
        this.playerName = playerName;
        this.dateOfBirth = dateOfBirth;
        this.national = national;
        this.height = height;
        this.leftFooted = leftFooted;
        this.rightFooted = rightFooted;
        this.club = clubId;
    }

    public Long getPlayerId() {
        return this.id;
    }

    public void setPlayerId(Long playerId) {
        this.id = playerId;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNational() {
        return this.national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Boolean isLeftFooted() {
        return this.leftFooted;
    }

    public Boolean getLeftFooted() {
        return this.leftFooted;
    }

    public void setLeftFooted(Boolean leftFooted) {
        this.leftFooted = leftFooted;
    }

    public Boolean isRightFooted() {
        return this.rightFooted;
    }

    public Boolean getRightFooted() {
        return this.rightFooted;
    }

    public void setRightFooted(Boolean rightFooted) {
        this.rightFooted = rightFooted;
    }

    public Club getClubId() {
        return this.club;
    }

    public void setClubId(Club clubId) {
        this.club = clubId;
    }

    public Player playerId(Long playerId) {
        setPlayerId(playerId);
        return this;
    }

    public Player playerName(String playerName) {
        setPlayerName(playerName);
        return this;
    }

    public Player dateOfBirth(String dateOfBirth) {
        setDateOfBirth(dateOfBirth);
        return this;
    }

    public Player national(String national) {
        setNational(national);
        return this;
    }

    public Player height(String height) {
        setHeight(height);
        return this;
    }

    public Player leftFooted(Boolean leftFooted) {
        setLeftFooted(leftFooted);
        return this;
    }

    public Player rightFooted(Boolean rightFooted) {
        setRightFooted(rightFooted);
        return this;
    }

    public Player clubId(Club clubId) {
        setClubId(clubId);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " playerId='" + getPlayerId() + "'" +
            ", playerName='" + getPlayerName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", national='" + getNational() + "'" +
            ", height='" + getHeight() + "'" +
            ", leftFooted='" + isLeftFooted() + "'" +
            ", rightFooted='" + isRightFooted() + "'" +
            ", clubId='" + getClubId() + "'" +
            "}";
    }
}
