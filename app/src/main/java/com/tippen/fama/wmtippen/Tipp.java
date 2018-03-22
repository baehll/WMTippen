package com.tippen.fama.wmtippen;

/**
 * Created by DEB681G on 22.03.2018.
 */


public class Tipp{
    private Match match;
    private int tipp1;
    private int tipp2;

    public Tipp(){
        this.match = new Match();
        this.tipp1 = 0;
        this.tipp2 = 0;
    }

    public Tipp(Match match){
        this.match = match;
        this.tipp1 = 0;
        this.tipp2 = 0;
    }

    public Tipp(Match match, int tipp1, int tipp2){
        this.match = match;
        this.tipp1 = tipp1;
        this.tipp2 = tipp2;
    }



    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public int getTipp1() {
        return tipp1;
    }

    public void setTipp1(int tipp1) {
        this.tipp1 = tipp1;
    }

    public int getTipp2() {
        return tipp2;
    }

    public void setTipp2(int tipp2) {
        this.tipp2 = tipp2;
    }


}

