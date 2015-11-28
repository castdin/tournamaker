package com.example.cstde037.tournamaker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cstde037 on 20/11/2015.
 */
public class Match implements Parcelable {
    private Team homeTeam;
    private Team awayTeam;
    private int homeScore;
    private int awayScore;
    boolean isComplete;

    public Match() {}

    private Match(Parcel in) {
        readFromParcel(in);
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(homeTeam, flags);
        out.writeParcelable(awayTeam, flags);
        out.writeInt(homeScore);
        out.writeInt(awayScore);
    }

    public void readFromParcel(Parcel in) {
        homeTeam = in.readParcelable(Team.class.getClassLoader());
        awayTeam = in.readParcelable(Team.class.getClassLoader());
        homeScore = in.readInt();
        awayScore = in.readInt();
    }

    public int describeContents(){ return 0; }

    public static final Parcelable.Creator<Match> CREATOR = new Parcelable.Creator<Match>() {

        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setHomeScore(int score) {
        homeScore = score;
    }

    public void setAwayScore(int score) {
        awayScore = score;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setTeams(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public void setScores(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public Team getWinner() {
        return (homeScore>awayScore) ? homeTeam : awayTeam;
    }

    public boolean isComplete() { return isComplete; }

}
