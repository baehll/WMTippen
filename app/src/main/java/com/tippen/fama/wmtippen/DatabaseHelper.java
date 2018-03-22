package com.tippen.fama.wmtippen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DEB681D on 22.03.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
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

    public static final String[] tableList = {TABLE_GROUPS, TABLE_MATCHES, TABLE_PLAYER_RESULTS, TABLE_PLAYERS, TABLE_MATCH_RESULTS, TABLE_TEAMS};

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db = this.getWritableDatabase();
        onCreate(db);
        getTeamsTable();
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
                " FOREIGN KEY (" + COL_M_TEAMONEID + ") REFERENCES " +
                TABLE_TEAMS + "("+
                COL_T_ID + "), " +
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

        fillDb(db);
        Log.e(LOGTAG, "database hat geklappt");

    }

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

    //test: initiales befüllen der teams tabelle
    private void fillDb(SQLiteDatabase db){
        String[] test = {"INSERT INTO " +  TABLE_TEAMS + " (" + COL_T_ID + " , "+ COL_T_TEAMNAME +", "+ COL_T_GROUPID +") VALUES (0, \" RUS\", 0)",
                "INSERT INTO " +  TABLE_TEAMS + " (" + COL_T_ID + " , "+ COL_T_TEAMNAME +", "+ COL_T_GROUPID +") VALUES (1, \" DE\", 0)",
                "INSERT INTO " +  TABLE_TEAMS + " (" + COL_T_ID + " , "+ COL_T_TEAMNAME +", "+ COL_T_GROUPID +") VALUES (2, \" FR\", 0)",
                "INSERT INTO " +  TABLE_TEAMS + " (" + COL_T_ID + " , "+ COL_T_TEAMNAME +", "+ COL_T_GROUPID +") VALUES (3, \" SWE\", 0)"};
        for (String e: test) {
            db.execSQL(e);
        }

    }

//funktioniert
    public void getTeamsTable(){
        SQLiteDatabase buffDb = getReadableDatabase();

        String selection = COL_T_ID + " = ? ";
        String[] selectionArgs = {"0"};

        String[] projection = {
                COL_T_ID,
                COL_T_TEAMNAME,
                COL_T_GROUPID
        };

        Cursor cursor = buffDb.query(
                TABLE_TEAMS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                COL_T_ID
        );

        List buffList = new ArrayList<>();
        Log.e(LOGTAG, "Beginne mit cursor moving");
        while (cursor.moveToNext()){
            Log.e(LOGTAG, cursor.getString(cursor.getColumnIndexOrThrow(COL_T_TEAMNAME)));
            buffList.add(cursor.getString(cursor.getColumnIndexOrThrow(COL_T_ID)));
        }
        Log.e(LOGTAG, "Fertig mit cursor moving");
        cursor.close();
        //return buffList;
    }
}
