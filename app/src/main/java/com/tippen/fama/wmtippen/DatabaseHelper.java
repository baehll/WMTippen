package com.tippen.fama.wmtippen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.Date;

/**
 * Created by DEB681D on 22.03.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "matches.db";
    public static final int  DATABASE_VERSION = 1;

    public static final String TABLE_MATCHES = "matches";
    public static final String COL_M_ID = "ID";
    public static final String COL_M_TEAMONEID = "TeamOneID";
    public static final String COL_M_TEAMTWOID = "TeamTwoID";
    public static final String COL_M_MATCHDATE = "MatchDate";

    //



    public static final String TABLE_TEAMS = "teams";
    public static final String COL_T_ID = "ID";
    public static final String COL_T_TEAMNAME = "TeamName";
    public static final String COL_T_GROUPID = "GroupID";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MATCHES_TABLE = "CREATE TABLE" + TABLE_MATCHES + " (" +
                COL_M_ID + " INTEGER PRIMARY KEY, " +
                COL_M_TEAMONEID + " INTEGER, " +
                COL_M_TEAMTWOID + " INTEGER, " +
                COL_M_MATCHDATE + " date " +
                ") ";
        db.execSQL(CREATE_MATCHES_TABLE);

        //




        //


        String CREATE_TEAMS_TABLE = "CREATE TABLE " + TABLE_TEAMS + " (" +
                COL_T_ID + " INTEGER PRIMARY KEY, " +
                COL_T_TEAMNAME + "STRING, " +
                COL_T_GROUPID + "INTEGER, " +
                ") ";
        db.execSQL(CREATE_TEAMS_TABLE);

        String test = "INSERT INTO " +  TABLE_TEAMS + " (" + COL_T_ID + " , "+ COL_T_TEAMNAME +", "+ COL_T_GROUPID+") VALUES (0, \" RUS\", 2)";

        db.execSQL(test);


        Log.e(HandinFragment.class.getSimpleName(), "database hat geklappt");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        //db.execSQL("DROP TABLE IF EXIST  " + TABLE_NAME);
        //onCreate(db);
    }


}
