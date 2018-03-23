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
    private float score;

    public ArrayList<Tipp> getTippList() {
        return tippList;
    }

    public void setTippList(ArrayList<Tipp> tippList) {
        this.tippList = tippList;
    }

    public Player(){}

    public Player(String name, float f, int ID){
        this.playerName = name;
        this.playerID = ID;
        this.score = f;
        this.tippList = null;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public float getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public float getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
