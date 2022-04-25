package com.example.Excercise1.models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Entity(name= "clubEntity")
@Table(name= "tranning_club")
@Data
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotNull(message = "Name should not be null")
    // @Size(max = 40)
    // @Pattern(regexp = "^[a-zA-Z0-9_ ]*$", message = "Player name (format: text box, length 40, only allow number, a-z, A-Z, white space, required field)")
    private String clubName;
    private String abbrev;
    private String date;
    private String stadium;
    private String tournament;

    private String about;   

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Player> players;

    public Club() {
    }

    public Club(Long clubId, String name, String abbrev, String date, String stadium, String tournament, String about, List<Player> players) {
        this.id = clubId;
        this.clubName = name;
        this.abbrev = abbrev;
        this.date = date;
        this.stadium = stadium;
        this.tournament = tournament;
        this.about = about;
        this.players = players;
    }

    public Long getClubId() {
        return this.id;
    }

    public void setClubId(Long clubId) {
        this.id = clubId;
    }

    public String getName() {
        return this.clubName;
    }

    public void setName(String name) {
        this.clubName = name;
    }

    public String getAbbrev() {
        return this.abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStadium() {
        return this.stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getTournament() {
        return this.tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    public String getAbout() {
        return this.about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Club clubId(Long clubId) {
        setClubId(clubId);
        return this;
    }

    public Club name(String name) {
        setName(name);
        return this;
    }

    public Club abbrev(String abbrev) {
        setAbbrev(abbrev);
        return this;
    }

    public Club date(String date) {
        setDate(date);
        return this;
    }

    public Club stadium(String stadium) {
        setStadium(stadium);
        return this;
    }

    public Club tournament(String tournament) {
        setTournament(tournament);
        return this;
    }

    public Club about(String about) {
        setAbout(about);
        return this;
    }

    public Club players(List<Player> players) {
        setPlayers(players);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " clubId='" + getClubId() + "'" +
            ", name='" + getName() + "'" +
            ", abbrev='" + getAbbrev() + "'" +
            ", date='" + getDate() + "'" +
            ", stadium='" + getStadium() + "'" +
            ", tournament='" + getTournament() + "'" +
            ", about='" + getAbout() + "'" +
            ", players='" + getPlayers() + "'" +
            "}";
    }
}
