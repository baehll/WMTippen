package com.tippen.fama.wmtippen;

import android.util.Log;

import java.util.List;

/**
 * Created by DEB681G on 27.03.2018.
 */

public class ScoreCalculator {
    private static int WIN = 1;
    private int[][] playerResults;
    private int[][] matchResults;
    private static String LOGTAG = "ScoreCalculator";

    public ScoreCalculator() {
        this.playerResults = null;
        this.matchResults = null;
    }

    public ScoreCalculator(int[][] pr, int[][] mr){
        this.playerResults = pr;
        this.matchResults = mr;
    }

    public float start(){
        float playerScore = 0;
        int tip1;
        int tip2;
        int result1;
        int result2;
        if(playerResults.length != matchResults.length) {
            Log.e(LOGTAG, "PlayerResults ungleich MatchResults");
            return -1;
        }

        for(int match = 0; match < playerResults.length;match++){

            tip1 = playerResults[match][0];
            tip2 = playerResults[match][1];
            result1 = matchResults[match][0];
            result2 = matchResults[match][1];
            //Exakter Treffer
            if(tip1 == result1 && tip2 == result2){
                playerScore += WIN;
                continue;
            }
            if(!(tip1>tip2 && result1<result2) || !(tip1<tip2 && result1>result2)) {
                playerScore += WIN * (1 / d(tip1, result1) + d(tip2, result2));
            }

        }

        return playerScore;
    }

    private float d(int x, int y){
        return (float) Math.sqrt(Math.pow(x - y, 2));
    }
}
