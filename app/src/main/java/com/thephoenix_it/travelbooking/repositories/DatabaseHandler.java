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

        String user_type = "CREATE TABLE UserType " +
                "( Id_user_type INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "desc_user_type TEXT ) ";
        String usr = "CREATE TABLE User " +
                "( Id_user INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nom_user TEXT, " +
                "Prenom_user TEXT, " +
                "CIN INTEGER, " +
                "Date_b DATE, " +
                "Id_user_type INTEGER) ";

        String account = "CREATE TABLE Account " +
                "( Id_account INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Login TEXT UNIQUE, " +
                "Password TEXT, " +
                "State INTEGER DEFAULT 0, " +
                "Id_user INTEGER) ";

        String reserve = "CREATE TABLE Reserve " +
                "( Id_reserve INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Date_res DATE, " +
                "date_ann DATE, " +
                "Id_etat INTEGER, " +
                "Id_user INTEGER, " +
                "Id_vol INTEGER) ";

        String vol = "CREATE TABLE Vol " +
                "( Id_vol INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Num_vol INTEGER, " +
                "Destination TEXT, " +
                "Duration REAL, " +
                "Price REAL, " +
                "Disponibility INTEGER DEFAULT 0, " +
                "Creation_date DATE, " +
                "Id_user INTEGER) ";

        String Stat_res = "CREATE TABLE Status " +
                "( Id_etat INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Desc_stat TEXT) ";

        db.execSQL(user_type);
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