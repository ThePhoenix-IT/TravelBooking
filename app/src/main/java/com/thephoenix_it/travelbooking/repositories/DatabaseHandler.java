package com.thephoenix_it.travelbooking.repositories;

/**
 * Created by boubaker on 10-Nov-17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "Traveling";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String usr = "CREATE TABLE User " +
                "( Id_user INTEGER AUTOINCREMENT PRIMARY KEY , " +
                "Nom_user TEXT, " +
                "Prenom_user TEXT, " +
                "CIN INTEGER, " +
                "Date_b DATE) ";

        String account = "CREATE TABLE Account " +
                "( Id_account INTEGER AUTOINCREMENT PRIMARY KEY , " +
                "Login TEXT, " +
                "Password TEXT, " +
                "State INTEGER DEFAULT 0) ";

        String reserve = "CREATE TABLE Reserve " +
                "( Id_reserve INTEGER AUTOINCREMENT PRIMARY KEY , " +
                "Date_res DATE, " +
                "date_ann DATE) ";

        String vol = "CREATE TABLE Vol " +
                "( Id_vol INTEGER AUTOINCREMENT PRIMARY KEY , " +
                "Num_vol INTEGER, " +
                "Destination TEXT, " +
                "Duration REAL, " +
                "Price REAL, " +
                "Disponibility INTEGER DEFAULT 0, " +
                "Creation_date DATE) ";

        String Stat_res = "CREATE TABLE Status " +
                "( Id_etat INTEGER AUTOINCREMENT PRIMARY KEY , " +
                "Desc_stat TEXT) ";

        db.execSQL(usr);
        db.execSQL(account);
        db.execSQL(reserve);
        db.execSQL(vol);
        db.execSQL(Stat_res);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS Today_rec";
        db.execSQL(sql);
        onCreate(db);
    }

}