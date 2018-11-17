package com.mk.mkfighterresultdb;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Result {

    @PrimaryKey
    private long id;

    private int idFirstFighter;

    private int idSecondFighter;

    private double firstFighterMatchWinner;

    private double secondFighterMatchWinner;

    private double firstRoundWinner;

    private double secondRoundWinner;

    private double fatality;

    private double brutality;

    private double withoutSpecialFinish;

    private double score;

    private String matchCourse;

    public Result(int idFirstFighter, int idSecondFighter, double firstFighterMatchWinner, double secondFighterMatchWinner, double firstRoundWinner,
                  double secondRoundWinner, double fatality, double brutality, double withoutSpecialFinish, double score, String matchCourse){
        this.idFirstFighter = idFirstFighter;
        this.idSecondFighter = idSecondFighter;
        this.firstFighterMatchWinner = firstFighterMatchWinner;
        this.secondFighterMatchWinner = secondFighterMatchWinner;
        this.firstRoundWinner = firstRoundWinner;
        this.secondRoundWinner = secondRoundWinner;
        this.fatality = fatality;
        this.brutality = brutality;
        this.withoutSpecialFinish = withoutSpecialFinish;
        this.score = score;
        this.matchCourse = matchCourse;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdFirstFighter(int idFirstFighter) {
        this.idFirstFighter = idFirstFighter;
    }

    public int getIdFirstFighter() {
        return idFirstFighter;
    }

    public int getIdSecondFighter() {
        return idSecondFighter;
    }

    public void setIdSecondFighter(int idSecondFighter) {
        this.idSecondFighter = idSecondFighter;
    }

    public double getFirstFighterMatchWinner() {
        return firstFighterMatchWinner;
    }

    public void setFirstFighterMatchWinner(double firstFighterMatchWinner) {
        this.firstFighterMatchWinner = firstFighterMatchWinner;
    }

    public double getSecondFighterMatchWinner() {
        return secondFighterMatchWinner;
    }

    public void setSecondFighterMatchWinner(double secondFighterMatchWinner) {
        this.secondFighterMatchWinner = secondFighterMatchWinner;
    }

    public double getFirstRoundWinner() {
        return firstRoundWinner;
    }

    public void setFirstRoundWinner(double firstRoundWinner) {
        this.firstRoundWinner = firstRoundWinner;
    }

    public double getSecondRoundWinner() {
        return secondRoundWinner;
    }

    public void setSecondRoundWinner(double secondRoundWinner) {
        this.secondRoundWinner = secondRoundWinner;
    }

    public double getBrutality() {
        return brutality;
    }

    public void setBrutality(double brutality) {
        this.brutality = brutality;
    }

    public double getFatality() {
        return fatality;
    }

    public void setFatality(double fatality) {
        this.fatality = fatality;
    }

    public double getWithoutSpecialFinish() {
        return withoutSpecialFinish;
    }

    public void setWithoutSpecialFinish(double withoutSpecialFinish) {
        this.withoutSpecialFinish = withoutSpecialFinish;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getMatchCourse() {
        return matchCourse;
    }

    public void setMatchCourse(String matchCourse) {
        this.matchCourse = matchCourse;
    }
}
