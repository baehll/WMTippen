package com.tippen.fama.wmtippen;

import java.util.ArrayList;

/**
 * Created by DEB681G on 20.03.2018.
 */

public class PlayerMatch{
    private String playerName;
    private ArrayList<Match> matches = new ArrayList<>();
    private int tip1;
    private int tip2;
    private int score;

    public PlayerMatch(){}

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void addToList(Match m){
        matches.add(m);
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public int getTip1() {
        return tip1;
    }

    public void setTip1(int tip1) {
        this.tip1 = tip1;
    }

    public int getTip2() {
        return tip2;
    }

    public void setTip2(int tip2) {
        this.tip2 = tip2;
    }
}
