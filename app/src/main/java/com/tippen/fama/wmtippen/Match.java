package com.tippen.fama.wmtippen;

/**
 * Created by DEB681G on 19.03.2018.
 *
 * Spiegelt ein Spiel wider
 */



public class Match {
    private String team1;
    private String team2;
    private int goal1;
    private int goal2;
    private String group;

    public Match(String group, String team1, String team2){
        this.group = group;
        this.team1 = team1;
        this.team2 = team2;
    }

    public Match(String group, String team1, String team2, int goal1, int goal2){
        this.group = group;
        this.team1 = team1;
        this.team2 = team2;
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    public String getMatch(){
        return team1 + " : " + team2;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public int getGoal1() {
        return goal1;
    }

    public void setGoal1(int goal1) {
        this.goal1 = goal1;
    }

    public int getGoal2() {
        return goal2;
    }

    public void setGoal2(int goal2) {
        this.goal2 = goal2;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
