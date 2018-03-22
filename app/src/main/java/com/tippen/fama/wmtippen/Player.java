package com.tippen.fama.wmtippen;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by DEB681G on 20.03.2018.
 * dsffse
 */

public class Player {
    private int playerID;
    private String playerName;
    private ArrayList<Tipp> tippList;
    private int score;

    public ArrayList<Tipp> getTippList() {
        return tippList;
    }

    public void setTippList(ArrayList<Tipp> tippList) {
        this.tippList = tippList;
    }

    public Player(){}

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
