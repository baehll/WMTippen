package com.tippen.fama.wmtippen;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DEB681D on 22.03.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    //Datenbank Definition

    private static final String LOGTAG = "DatabaseHelper";

    public static final String DATABASE_NAME = "matches.db";
    public static final int  DATABASE_VERSION = 1;

    //Groupstable
    public static final String TABLE_GROUPS = "groups";
    public static final String COL_G_ID = "groupsID";
    public static final String COL_G_NAME = "GroupName";

    //Matchtable
    public static final String TABLE_MATCHES = "matches";
    public static final String COL_M_ID = "matchID";
    public static final String COL_M_TEAMONEID = "TeamOneID";
    public static final String COL_M_TEAMTWOID = "TeamTwoID";
    public static final String COL_M_MATCHDATE = "MatchDate";
    public static final String COL_M_STAGE = "Stage";

    //Stagetable
    public static final String TABLE_STAGES = "stages";
    public static final String COL_S_ID = "stageID";
    public static final String COL_S_NAME = "StageName";

    //Teamtable
    public static final String TABLE_TEAMS = "teams";
    public static final String COL_T_ID = "teamID";
    public static final String COL_T_TEAMNAME = "TeamName";
    public static final String COL_T_GROUPID = "GroupID";

    //Matchresults table
    public static final String TABLE_MATCH_RESULTS = "match_results";
    public static final String COL_MR_ID = "matchResultID";
    public static final String COL_MR_MATCHID = "MatchID";
    public static final String COL_MR_GOALONE = "GoalOne";
    public static final String COL_MR_GOALTWO = "GoalTwo";

    //Playerstable
    public static final String TABLE_PLAYERS = "players";
    public static final String COL_P_ID = "playerID";
    public static final String COL_P_NAME = "Name";
    public static final String COL_P_SCORE = "Score";

    //Playerresults table
    public static final String TABLE_PLAYER_RESULTS = "player_results";
    public static final String COL_PR_ID = "playerResultsID";
    public static final String COL_PR_PLAYERID = "PlayerID";
    public static final String COL_PR_MATCHID = "MatchID";
    public static final String COL_PR_TIPONE = "TipOne";
    public static final String COL_PR_TIPTWO = "TipTwo";

    public static final String[] tableList = {TABLE_GROUPS, TABLE_STAGES,TABLE_MATCHES, TABLE_PLAYER_RESULTS, TABLE_PLAYERS, TABLE_MATCH_RESULTS, TABLE_TEAMS};

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db = this.getWritableDatabase();
        //onCreate(db);
        //getTeamsTable();
        //getGroups();
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        //fillDb(db);
        //parsePlayerScores();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(LOGTAG, "HALLLLLLLOOOOOOOO");

        //zum debuggen
        dbDrop(db);

        //GROUPS TABLE
        String CREATE_GROUPS_TABLE =" CREATE TABLE " + TABLE_GROUPS + " (" +
                COL_G_ID + " INTEGER UNIQUE PRIMARY KEY, " +
                COL_G_NAME + " varchar(255)" +
                ") ";
        db.execSQL(CREATE_GROUPS_TABLE);

        //STAGES TABLE
        String CREATE_STAGES_TABLE = "CREATE TABLE " + TABLE_STAGES + " (" +
                COL_S_ID + " INTEGER UNIQUE PRIMARY KEY, " +
                COL_S_NAME + " varchar(255)" +
                ") ";
        db.execSQL(CREATE_STAGES_TABLE);

        //TEAMS TABLE

        String CREATE_TEAMS_TABLE = "CREATE TABLE " + TABLE_TEAMS + " (" +
                COL_T_ID + " INTEGER UNIQUE PRIMARY KEY, " +
                COL_T_TEAMNAME + " varchar(255), " +
                COL_T_GROUPID + " INTEGER, " +
                " FOREIGN KEY (" + COL_T_GROUPID + ") REFERENCES " +
                TABLE_GROUPS + "("+
                COL_G_ID + ")" +
                ") ";
        db.execSQL(CREATE_TEAMS_TABLE);

        //MATCH TABLE
        String CREATE_MATCHES_TABLE = "CREATE TABLE " + TABLE_MATCHES + " (" +
                COL_M_ID + " INTEGER UNIQUE PRIMARY KEY, " +
                COL_M_TEAMONEID + " INTEGER, " +
                COL_M_TEAMTWOID + " INTEGER, " +
                COL_M_MATCHDATE + " date, " +
                COL_M_STAGE + " INTEGER, " +
                " FOREIGN KEY (" + COL_M_TEAMONEID + ") REFERENCES " +
                TABLE_TEAMS + "("+
                COL_T_ID + "), " +
                " FOREIGN KEY (" + COL_M_STAGE + ") REFERENCES " +
                TABLE_STAGES + "("+
                COL_S_ID+ "), " +
                " FOREIGN KEY (" + COL_M_TEAMTWOID + ") REFERENCES " +
                TABLE_TEAMS + "("+
                COL_T_ID + ")" +
                ") ";
        db.execSQL(CREATE_MATCHES_TABLE);

        //MATCH RESULTS TABLE

        String CREATE_MATCH_RESULTS_TABLE = "CREATE TABLE " + TABLE_MATCH_RESULTS + " (" +
                COL_MR_ID + " INTEGER UNIQUE PRIMARY KEY, " +
                COL_MR_MATCHID + " INTEGER, " +
                COL_MR_GOALONE + " INTEGER, " +
                COL_MR_GOALTWO + " INTEGER, " +
                " FOREIGN KEY (" + COL_MR_MATCHID + ") REFERENCES " +
                TABLE_MATCHES + "("+
                COL_M_ID + ")" +
                ") ";
        db.execSQL(CREATE_MATCH_RESULTS_TABLE);

        //PLAYERS TABLE

        String CREATE_PLAYERS_TABLE = "CREATE TABLE " + TABLE_PLAYERS + " (" +
                COL_P_ID + " INTEGER UNIQUE PRIMARY KEY, " +
                COL_P_NAME + " varchar(255), " +
                COL_P_SCORE + " float " +
                ") ";
        db.execSQL(CREATE_PLAYERS_TABLE);

        //PLAYER RESULTS TABLE

        String CREATE_PLAYER_RESULTS_TABLE = "CREATE TABLE " + TABLE_PLAYER_RESULTS + " (" +
                COL_PR_ID + " INTEGER UNIQUE PRIMARY KEY, " +
                COL_PR_PLAYERID + " INTEGER, " +
                COL_PR_MATCHID + " INTEGER, " +
                COL_PR_TIPONE + " INTEGER, " +
                COL_PR_TIPTWO + " INTEGER, " +
                " FOREIGN KEY (" + COL_PR_PLAYERID + ") REFERENCES " +
                TABLE_PLAYERS + "("+
                COL_P_ID + "), " +
                " FOREIGN KEY (" + COL_PR_MATCHID + ") REFERENCES " +
                TABLE_MATCHES + "("+
                COL_M_ID + ")" +
                ") ";
        db.execSQL(CREATE_PLAYER_RESULTS_TABLE);


        Log.e(LOGTAG, "database hat geklappt");

    }


    //Sinnvoll verwenden?
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //db.execSQL("DROP TABLE IF EXIST " + e);
            //onCreate(db);
    }

    public void dbDrop(SQLiteDatabase db){
        for (String e: tableList) {
            db.execSQL("DROP TABLE IF EXISTS " + e);
        }
    }

    //test: zum Befüllen des Spielverlaufs von Alina und Melvin
    private void fillDb(SQLiteDatabase db){
        /*
        String[] fillTeams = {"INSERT INTO " +  TABLE_TEAMS + " (" + COL_T_ID + " , "+ COL_T_TEAMNAME +", "+ COL_T_GROUPID +") VALUES (0, \" RUS\", 0)",
                "INSERT INTO " +  TABLE_TEAMS + " (" + COL_T_ID + " , "+ COL_T_TEAMNAME +", "+ COL_T_GROUPID +") VALUES (1, \" DE\", 0)",
                "INSERT INTO " +  TABLE_TEAMS + " (" + COL_T_ID + " , "+ COL_T_TEAMNAME +", "+ COL_T_GROUPID +") VALUES (2, \" FR\", 0)",
                "INSERT INTO " +  TABLE_TEAMS + " (" + COL_T_ID + " , "+ COL_T_TEAMNAME +", "+ COL_T_GROUPID +") VALUES (3, \" SWE\", 0)"};
        /*
        String[] fillGroups = {"INSERT INTO " +  TABLE_GROUPS + " (" + COL_G_ID + " , "+ COL_G_NAME +") VALUES (0, \" GRUPPE A\")",
                "INSERT INTO " +  TABLE_GROUPS + " (" + COL_G_ID + " , "+ COL_G_NAME +") VALUES (1, \" GRUPPE B\")",
                "INSERT INTO " +  TABLE_GROUPS + " (" + COL_G_ID + " , "+ COL_G_NAME +") VALUES (2, \" GRUPPE C\")",
                "INSERT INTO " +  TABLE_GROUPS + " (" + COL_G_ID + " , "+ COL_G_NAME +") VALUES (3, \" GRUPPE D\")",};
                */
        /*
        String[] fillPlayers = {
                "INSERT INTO " +  TABLE_PLAYERS + " (" + COL_P_ID + " , "+ COL_P_NAME +", "+ COL_P_SCORE+") VALUES (0, \" ENTRY1\", 20)",
                "INSERT INTO " +  TABLE_PLAYERS + " (" + COL_P_ID + " , "+ COL_P_NAME +", "+ COL_P_SCORE+") VALUES (1, \" ENTRY2\", 70)",
                "INSERT INTO " +  TABLE_PLAYERS + " (" + COL_P_ID + " , "+ COL_P_NAME +", "+ COL_P_SCORE+") VALUES (2, \" ENTRY3\", 0)"
        };
        */

        String[] test = {
                //Stages
                "INSERT INTO " + TABLE_STAGES + " (" + COL_S_ID + ", " + COL_S_NAME+ ") VALUES (0, \"Gruppe\")",
                "INSERT INTO " + TABLE_STAGES + " (" + COL_S_ID + ", " + COL_S_NAME+ ") VALUES (1, \"Achtelfinale\")",
                "INSERT INTO " + TABLE_STAGES + " (" + COL_S_ID + ", " + COL_S_NAME+ ") VALUES (2, \"Viertelfinale\")",
                "INSERT INTO " + TABLE_STAGES + " (" + COL_S_ID + ", " + COL_S_NAME+ ") VALUES (3, \"Halbfinale\")",
                "INSERT INTO " + TABLE_STAGES + " (" + COL_S_ID + ", " + COL_S_NAME+ ") VALUES (4, \"Platz 3\")",
                "INSERT INTO " + TABLE_STAGES + " (" + COL_S_ID + ", " + COL_S_NAME+ ") VALUES (5, \"Finale\")",
                // Gruppe A
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (0, \"Russland\", 0)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (1, \"Saudi Arabien\", 0)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (2, \"Ägypten\", 0)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (3, \"Uruguay\", 0)",
                // Gruppe B
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (4, \"Portugal\", 1)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (5, \"Spanien\", 1)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (6, \"Marokko\", 1)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (7, \"Iran\", 1)",
                // Gruppe C
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (8, \"Frankreich\", 2)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (9, \"Australien\", 2)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (10, \"Peru\", 2)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (11, \"Dänemark\", 2)",
                // Gruppe D
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (12, \"Argentinien\", 3)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (13, \"Island\", 3)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (14, \"Kroatien\", 3)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (15, \"Nigeria\", 3)",
                // Gruppe E
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (16, \"Brasilien\", 4)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (17, \"Schweiz\", 4)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (18, \"Costa Rica\", 4)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (19, \"Serbien\", 4)",
                // Gruppe F
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (20, \"Deutschland\", 5)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (21, \"Mexiko\", 5)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (22, \"Schweden\", 5)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (23, \"Südkorea\", 5)",
                // Gruppe G
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (24, \"Belgien\", 6)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (25, \"Panama\", 6)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (26, \"Tunesien\", 6)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (27, \"England\", 6)",
                // GRUPPE H
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (28, \"Polen\", 7)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (29, \"Senegal\", 7)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (30, \"Kolumbien\", 7)",
                "INSERT INTO " + TABLE_TEAMS + " (" + COL_T_ID + " , " + COL_T_TEAMNAME + ", " + COL_T_GROUPID + ") VALUES (31, \"Japan\", 7)",
                //Gruppe A - done
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (0, 0, 1,2018-06-14, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (1, 2, 3,2018-06-15, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (2, 0, 2,2018-06-19, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (3, 3, 1,2018-06-20, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (4, 3, 0,2018-06-25, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (5, 1, 2,2018-06-25, 0)",
                //Gruppe B - done
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (6, 6 , 7 ,2018-06-15, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (7, 4 , 5 ,2018-06-15, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (8, 4 , 6 ,2018-06-20, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (9, 7 , 5 ,2018-06-20, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (10, 5 , 6 ,2018-06-25, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (11, 7 , 4 ,2018-06-25, 0)",
                //Gruppe C - done
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (12,   8 , 9 ,2018-06-15, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (13,   10 , 11 ,2018-06-16, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (14,   11 , 9 ,2018-06-21, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (15,   8 , 10 ,2018-06-21, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (16,   11 , 8 ,2018-06-26, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (17,   9 , 10 ,2018-06-26, 0)",
                //Gruppe D - done
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (18,   12 , 13 ,2018-06-16, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (19,   14 , 15 ,2018-06-16, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (20,   12 , 14 ,2018-06-21, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (21,   15 , 13 ,2018-06-22, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (22,   13 , 14 ,2018-06-26, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (23,   15 , 12 ,2018-06-26, 0)",
                //Gruppe E - done
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (24,   18 , 19 ,2018-06-17, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (25,   16 , 17 ,2018-06-17, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (26,   16 , 18 ,2018-06-22, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (27,   19 , 17 ,2018-06-22, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (28,   19 , 16 ,2018-06-27, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (29,   17 , 18 ,2018-06-27, 0)",
                //Gruppe F - done
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (30,   20 , 21 ,2018-06-17, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (31,   22 , 23 ,2018-06-18, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (32,   23 , 21 ,2018-06-23, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (33,   20 , 22 ,2018-06-23, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (34,   21 , 22 ,2018-06-27, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (35,   23 , 20 ,2018-06-27, 0)",
                //Gruppe G - done
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (36,   24 , 25 ,2018-06-18, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (37,   26 , 27 ,2018-06-18, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (38,   24 , 26 ,2018-06-23, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (39,   27 , 25 ,2018-06-24, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (40,   27 , 24 ,2018-06-28, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (41,   25 , 26 ,2018-06-28, 0)",
                //Gruppe H - done
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (42,   30 , 31 ,2018-06-19, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (43,   28 , 29 ,2018-06-19, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (44,   31 , 29 ,2018-06-24, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (45,   28 , 30 ,2018-06-24, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (46,   29 , 30 ,2018-06-28, 0)",
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (47,   31 , 28 ,2018-06-28, 0)",

                //Achtelfinale - done

                //Sieger C - Zweiter D
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (48,   11 , 12 ,2018-06-30, 1)",
                //Sieger A - Zweiter B
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (49,   3 , 4 ,2018-06-30, 1)",
                //Sieger B - Zweiter A
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (50,   7 , 0 ,2018-07-01, 1)",
                //Sieger D - Zweiter C
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (51,   15 , 8 ,2018-07-01, 1)",
                //Sieger E- Zweiter F
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (52,   19 , 20 ,2018-07-02, 1)",
                //Sieger G - Zweiter H
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (53,   27 , 28 ,2018-07-02, 1)",
                //Sieger F - Zweiter E
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (54,   23 , 16 ,2018-07-03, 1)",
                //Sieger H - Zweiter G
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (55,   31 , 24 ,2018-07-03, 1)",

                //Viertelfinale - done

                //Sieger AF1 - Sieger AF2
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (56,   11 , 4 ,2018-07-06, 2)",
                //Sieger AF3 - Sieger AF4
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (57,   27 , 19 ,2018-07-06, 2)",
                //Sieger AF6 - Sieger AF5
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (58,   31 , 23 ,2018-07-07, 2)",
                //Sieger AF8 - Sieger AF7
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (59,   7 , 15 ,2018-07-07, 2)",

                //Halbfinale - done

                //Sieger VF2 - Sieger VF1
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (60,   27 , 11 ,2018-07-10, 3)",
                //Sieger VF4 - Sieger AF3
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (61,   14 , 31 ,2018-07-11, 3)",

                //Spiel um Platz 3 - done

                //Verlierer HF1 - Verlierer HF2
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (62,   11 , 31 ,2018-07-14, 4)",

                //Finale - done

                //Sieger HF1 - Sieger HF2
                "INSERT INTO " +  TABLE_MATCHES + " (" + COL_M_ID + " , "+ COL_M_TEAMONEID +", "+ COL_M_TEAMTWOID +",  "+COL_M_MATCHDATE+ ", " + COL_M_STAGE+") VALUES (63,   27 , 15 ,2018-07-10, 5)",

                // Gruppen Tabelle befüllen


                "INSERT INTO " + TABLE_GROUPS + " (" + COL_G_ID + " , " + COL_G_NAME + ") VALUES (0, \" Gruppe A\")",
                "INSERT INTO " + TABLE_GROUPS + " (" + COL_G_ID + " , " + COL_G_NAME + ") VALUES (1, \" Gruppe B\")",
                "INSERT INTO " + TABLE_GROUPS + " (" + COL_G_ID + " , " + COL_G_NAME + ") VALUES (2, \" Gruppe C\")",
                "INSERT INTO " + TABLE_GROUPS + " (" + COL_G_ID + " , " + COL_G_NAME + ") VALUES (3, \" Gruppe D\")",
                "INSERT INTO " + TABLE_GROUPS + " (" + COL_G_ID + " , " + COL_G_NAME + ") VALUES (4, \" Gruppe E\")",
                "INSERT INTO " + TABLE_GROUPS + " (" + COL_G_ID + " , " + COL_G_NAME + ") VALUES (5, \" Gruppe F\")",
                "INSERT INTO " + TABLE_GROUPS + " (" + COL_G_ID + " , " + COL_G_NAME + ") VALUES (6, \" Gruppe G\")",
                "INSERT INTO " + TABLE_GROUPS + " (" + COL_G_ID + " , " + COL_G_NAME + ") VALUES (7, \" Gruppe H\")",


                // Gruppe A Results
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (0,0 ,2 ,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (1,1 ,0 ,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (2,2 , 3,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (3,3 , 5,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (4, 4, 2,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (5,5 , 0,0)",

                // Group B Results

                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (6,6 ,2 ,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (7,7 ,0 ,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (8,8 , 3,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (9,9 , 5,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (10, 10, 2,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (11,11 , 0,0)",
                // Group C Results
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (12,12 ,2 ,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (13,13 ,0 ,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (14,14 , 3,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (15,15 , 5,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (16, 16, 2,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (17,17 , 0,0)",

                // Group D Results
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (18,18 ,2 ,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (19,19 ,0 ,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (20,20 , 3,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (21,21 , 5,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (22, 22, 2,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (23,23 , 0,0)",
                // Group E Results
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (24,24 ,2 ,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (25,25 ,0 ,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (26,26 , 3,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (27,27 , 5,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (28, 28, 2,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (29,29 , 0,0)",
                // Group F Results
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (30,30 ,2 ,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (31,31 ,0 ,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (32,32 , 3,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (33,33 , 5,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (34, 34, 2,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (35,35 , 0,0)",
                // Group G Results
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (36,36,2 ,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (37,37 ,0 ,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (38,38 , 3,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (39,39 , 5,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (40, 40, 2,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (41,41 , 0,0)",
                // Group H Results
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (42,42 ,2 ,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (43,43 ,0 ,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (44,44 , 3,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (45,45 , 5,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (46, 46, 2,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (47,47 , 0,0)",

                // Achtelfinale Results
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (48,48 ,2 ,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (49,49 ,0 ,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (50,50 , 3,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (51,51 , 5,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (52, 52, 2,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (53,53 , 2,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (54, 54, 2,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (55,55 , 1,0)",

// Viertelfinale Result
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (56, 56, 2,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (57,57 , 2,0)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (58, 58, 2,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (59,59 , 0,1)",
                // Halbfinale Results
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (60, 60, 2,1)",
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (61,61 , 3,0)",
                //Finale Results
                "INSERT INTO " + TABLE_MATCH_RESULTS + " (" + COL_MR_ID + " , " + COL_MR_MATCHID + " , " + COL_MR_GOALONE + " , " + COL_MR_GOALTWO + ") VALUES (62,63 , 2,0)"};




        for (String e: test) {
            db.execSQL(e);
        }

    }
    //Gibt die ID des Spielers in TABLE_PLAYERS für den Namen s wieder (-1, wenn der Eintrag nicht gefunden wurde)
    public int getPlayerID(String s) {
        try (SQLiteDatabase buffDB = this.getReadableDatabase()){
            String[] args = {s};
            String sqlTransaction = "SELECT " +  COL_P_ID + " FROM " + TABLE_PLAYERS + " WHERE " + COL_P_NAME + " = ?";

            Cursor cursor = buffDB.rawQuery(sqlTransaction, args);

            if(cursor.getCount() == 0){
                return -1;
            }

            cursor.moveToNext();

            int COL_PlayerID = cursor.getColumnIndexOrThrow(COL_P_ID);

            return (int) cursor.getLong(COL_PlayerID);
        } catch (SQLException e){
            Log.e(LOGTAG, e.toString());
        }
        return -1;
    }
    //Gibt die Namen der Spielstufen wieder (Gruppe, Finale, Halbfinale..)
    public List<String> getStages(){
        List<String> outputList = new ArrayList<>();

        try (SQLiteDatabase buffDB = this.getReadableDatabase()){
            String[] args = {"1"};
            String sqlTransaction = "SELECT " +  COL_S_NAME + " FROM " + TABLE_STAGES + " WHERE ?";

            Cursor cursor = buffDB.rawQuery(sqlTransaction, args);

            int COL_stage = cursor.getColumnIndexOrThrow(COL_S_NAME);

            while(cursor.moveToNext()){
                String stageName = cursor.getString(COL_stage);
                outputList.add(stageName);
            }

            cursor.close();
            buffDB.close();
        } catch (SQLException e){
            Log.e(LOGTAG, e.toString());
        }
        return outputList;
    }
    //Gibt alle Spieler der Phase stage (Gruppe, Achtelfinale, Halbfinale...) wieder
    //Bei stage = 0 (Gruppenphase) gibt es auch die Prüfung auf die Gruppe A-H
    public List<Match> parseMatches(int stage, int group){
        List<Match> outputList = new ArrayList<>();
        String strSub1, strSub2;

        try (SQLiteDatabase buffDB = this.getReadableDatabase()){
            //Unterscheidung zwischen Gruppenphase und nicht Gruppenphase
            if(stage == 0){
                strSub1 = " AND " + TABLE_TEAMS + "." + COL_T_GROUPID + "= " + group + " ";
                strSub2 = " AND " + TABLE_TEAMS + "." + COL_T_GROUPID + "= " + group + " ";
            } else {
                strSub1 = " ";
                strSub2 = " ";
            }

            //match.team1id==teams.id, match.team2id==teams.id
            String sqlTransaction = " SELECT " + TABLE_MATCHES + "." + COL_M_ID + ", " + TABLE_MATCHES + "." + COL_M_STAGE + " AS STAGE" +
                    ", ( SELECT " + TABLE_TEAMS + "." + COL_T_TEAMNAME + " FROM " + TABLE_TEAMS + " WHERE "+ TABLE_MATCHES + "." + COL_M_TEAMONEID + "=" + TABLE_TEAMS + "." + COL_T_ID +
                     strSub1 + ") AS TEAM1 " +
                    ", ( SELECT " + TABLE_TEAMS + "." + COL_T_TEAMNAME + " FROM " + TABLE_TEAMS + " WHERE "+ TABLE_MATCHES + "." + COL_M_TEAMTWOID + "=" + TABLE_TEAMS + "." + COL_T_ID +
                    strSub2 + ") AS TEAM2 " +
                    "FROM " + TABLE_MATCHES +
                    " WHERE " + TABLE_MATCHES + "." + COL_M_STAGE + " = ? AND (TEAM1 IS NOT NULL OR TEAM2 IS NOT NULL)";
            String args[] = {String.valueOf(stage)};

            Cursor cursor = buffDB.rawQuery(sqlTransaction, args);
            int COL_matchID = cursor.getColumnIndexOrThrow(COL_M_ID);
            int COL_team1 = cursor.getColumnIndexOrThrow("TEAM1");
            int COL_team2 = cursor.getColumnIndexOrThrow("TEAM2");
            int COL_stage = cursor.getColumnIndexOrThrow("STAGE");

            Log.e(LOGTAG, "MatchDB:");
            while(cursor.moveToNext()){
                int matchID = (int) cursor.getLong(COL_matchID);
                int stageID = (int) cursor.getLong(COL_stage);
                String team1 = cursor.getString(COL_team1);
                String team2 = cursor.getString(COL_team2);
                Log.e(LOGTAG, matchID +" "+ team1 + " " + team2 + " " + stageID);
                outputList.add(new Match(team1, team2, matchID, group, stageID));
            }
            Log.e(LOGTAG, "Ende MatchDB");
            cursor.close();
            buffDB.close();
        } catch (SQLException e){
            Log.e(LOGTAG, e.toString());
        }
        return outputList;
    }
    //Gibt für das Scoreboard/den Overview alle Spieler nach Punktzahl absteigend wieder
    public List<Player> parsePlayers(){
        List<Player> outputList = new ArrayList<>();

        try (SQLiteDatabase buffDB = this.getReadableDatabase()) {
            String sqlTransaction = "SELECT " + COL_P_NAME + ", " + COL_P_SCORE + ", " + COL_P_ID + " FROM " + TABLE_PLAYERS + " WHERE ? ORDER BY " + COL_P_SCORE + " DESC";

            String[] kp = {"1"};
            Cursor cursor = buffDB.rawQuery(sqlTransaction, kp);

                //Log.e(LOGTAG, "Beginne mit cursor moving");
            int COL_PlayerName = cursor.getColumnIndexOrThrow(COL_P_NAME);
            int COL_PlayerScore = cursor.getColumnIndexOrThrow(COL_P_SCORE);
            int COL_PlayerID = cursor.getColumnIndexOrThrow(COL_P_ID);

            while (cursor.moveToNext()) {
                outputList.add(new Player(cursor.getString(COL_PlayerName), cursor.getFloat(COL_PlayerScore), cursor.getInt(COL_PlayerID)));
            }
            //Log.e(LOGTAG, "Fertig mit cursor moving");
            cursor.close();
            buffDB.close();
        } catch (SQLException e) {
            Log.e(LOGTAG, "parsePlayerScores Error: " + e);
        }
        return outputList;
    }
    //Fügt die Spielertipps in die Tabelle neu ein bzw. Updated sie, wenn vorhanden
    public void insertOrUpdatePlayerTipps(Player player){
        List<Tipp> inputList = player.getTippList().stream().filter(e -> (e.getTipp1() > 0 && e.getTipp2() > 0)).collect(Collectors.toList());

        //2. Tipps mit PlayerID in TABLE_PLAYER_RESULTS hinzufügen (MatchID beachten
        try (SQLiteDatabase buffDB = this.getWritableDatabase()){
            String sqlTransaction;
            for (int i = 0; i < inputList.size(); i++) {
                sqlTransaction = "INSERT OR REPLACE INTO " + TABLE_PLAYER_RESULTS +
                        "(" + COL_PR_PLAYERID + ", " + COL_PR_MATCHID + ", " + COL_PR_TIPONE + ", " + COL_PR_TIPTWO + ") " +
                        "VALUES (" + player.getPlayerID() + ", " + inputList.get(i).getMatch().getMatchId() + ", " + inputList.get(i).getTipp1() +
                        ", " + inputList.get(i).getTipp2() +")";
                buffDB.execSQL(sqlTransaction);
            }
            buffDB.close();
        } catch (SQLException e){
            Log.e(LOGTAG, e.toString());
        }
    }
    //Gibt die Tipps des Spielers spielerID für das Match matchID wieder
    public int[] parsePlayerTipps(int matchID, int playerID) {
        int[] outputArray = new int[2];
        try (SQLiteDatabase buffDB = this.getReadableDatabase()){
            String[] args = {String.valueOf(playerID), String.valueOf(matchID)};
            String sqlTransaction = "SELECT " + TABLE_PLAYER_RESULTS + "." + COL_PR_TIPONE + ", " + TABLE_PLAYER_RESULTS + "." + COL_PR_TIPTWO +
                    " FROM " + TABLE_PLAYER_RESULTS + " WHERE " + TABLE_PLAYER_RESULTS + "." + COL_PR_PLAYERID + " = ? AND " +
                    TABLE_PLAYER_RESULTS + "." + COL_PR_MATCHID + " = ?";

            Cursor cursor = buffDB.rawQuery(sqlTransaction, args);

            int COL_TipOne = cursor.getColumnIndexOrThrow(COL_PR_TIPONE);
            int COL_TipTwo = cursor.getColumnIndexOrThrow(COL_PR_TIPTWO);

            while(cursor.moveToNext()) {
                outputArray[0] = cursor.getInt(COL_TipOne);
                outputArray[1] = cursor.getInt(COL_TipTwo);
            }
            cursor.close();
        } catch(SQLException e){
            Log.e(LOGTAG, e.toString());
        }
        return outputArray;
    }
    //Gibt das eigentliche Ergebnis für das Match matchID wieder
    public int[] parseMatchResults(int matchID){
        int[] outputArray = new int[2];
        try (SQLiteDatabase buffDB = this.getReadableDatabase()){
            String[] args = {String.valueOf(matchID)};
            String sqlTransaction = "SELECT " + TABLE_MATCH_RESULTS + "." + COL_MR_GOALONE + ", " + TABLE_MATCH_RESULTS + "." + COL_MR_GOALTWO +
                    " FROM " + TABLE_MATCH_RESULTS + " WHERE " + TABLE_MATCH_RESULTS + "." + COL_MR_MATCHID + " = ?";

            Cursor cursor = buffDB.rawQuery(sqlTransaction, args);

            outputArray = new int[2];

            int COL_GoalOne = cursor.getColumnIndexOrThrow(COL_MR_GOALONE);
            int COL_GoalTwo = cursor.getColumnIndexOrThrow(COL_MR_GOALTWO);

            while(cursor.moveToNext()) {
                outputArray[0] = cursor.getInt(COL_GoalOne);
                outputArray[1] = cursor.getInt(COL_GoalTwo);
            }
            cursor.close();
        } catch(SQLException e){
            Log.e(LOGTAG, e.toString());
        }
        return outputArray;
    }
    //Fügt den Spieler playerName in die TABLE_PLAYERS hinzu und gibt die ID zurück
    public int insertPlayer(String playerName){
        try (SQLiteDatabase buffDB = this.getWritableDatabase()){
            String sqlTransaction = "INSERT INTO " +  TABLE_PLAYERS + " ("+ COL_P_NAME +", "+ COL_P_SCORE+") VALUES (?, 0)";
            String[] args = {playerName};
            buffDB.execSQL(sqlTransaction, args);

            buffDB.close();
            return getPlayerID(playerName);

        } catch (SQLException e){
            Log.e(LOGTAG, e.toString());
        }
        return -1;
    }
    //Setzt den Punktestand des Spielers playerID auf playerScore
    public void updatePlayerScore(int playerID, float playerScore){
        try (SQLiteDatabase buffDB = this.getWritableDatabase()){
            String sqlTransaction = "UPDATE " + TABLE_PLAYERS + " SET " + COL_P_SCORE + " = ?" + " WHERE " + TABLE_PLAYERS + "." + COL_P_ID +" = ?";
            String[] args = {String.valueOf(playerScore), String.valueOf(playerID)};

            buffDB.execSQL(sqlTransaction, args);

            buffDB.close();
        } catch (SQLException e){
            Log.e(LOGTAG, e.toString());
        }
    }
}
