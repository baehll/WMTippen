package com.tippen.fama.wmtippen;

/**
 * Created by DEB681G on 19.03.2018.
 *
 * Spiegelt ein Spiel wider
 */



public class Match{
    private String team1;
    private String team2;
    private String group;
    private int GROUP_ID;
    private int matchId;

    public Match(){}

    public Match(String group, String team1, String team2, int matchId){
        this.group = group;
        this.team1 = team1;
        this.team2 = team2;
        this.matchId = matchId;
        setGroupID(group);
    }

    private void setGroupID(String s){
        switch(s) {
            case "A":
                this.GROUP_ID = 0;
                break;
            case "B":
                this.GROUP_ID = 1;
                break;
            case "C":
                this.GROUP_ID = 2;
                break;
            case "D":
                this.GROUP_ID = 3;
                break;
        }
    }

    public int getGROUP_ID() {
        return GROUP_ID;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
