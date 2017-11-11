package com.thephoenix_it.travelbooking.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.EtatReservation;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bouba on 10-Nov-17.
 */

public class TableControllerStudent extends DatabaseHandler {

    public TableControllerStudent(Context context) {
        super(context);
    }
    public boolean create_compte(Compte objectCompte) {

        ContentValues values = new ContentValues();
        //values.put("Id_account", objectCompte.getId_compte());
        values.put("Login", objectCompte.getLogin());
        values.put("Password", objectCompte.getPassword());
        values.put("State", objectCompte.getEtat_compte());
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("Account", null, values) > 0;
        db.close();
        return createSuccessful;
    }
    public boolean create_user(Utilisateur objectUser) {

        ContentValues values = new ContentValues();
        values.put("Nom_user", objectUser.getNom_utilisateur());
        values.put("Prenom_user", objectUser.getPrenom_utilisateur());
        values.put("CIN", objectUser.getCin());
        values.put("Date_b", String.valueOf(objectUser.getDate_naissance()));
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("User", null, values) > 0;
        db.close();
        return createSuccessful;
    }
    public boolean create_vol(Vol objectVol) {

        ContentValues values = new ContentValues();
        values.put("Num_vol", objectVol.getNum_vol());
        values.put("Destination", objectVol.getDestination());
        values.put("Duration", objectVol.getDuree());
        values.put("Price", objectVol.getPrix());
        values.put("Disponibility", objectVol.getDesponible());
        values.put("Creation_date", String.valueOf(objectVol.getDate_creation()));
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("Vol", null, values) > 0;
        db.close();
        return createSuccessful;
    }
    public boolean create_res(Reservation objectReserv) {

        ContentValues values = new ContentValues();
        values.put("Date_res", String.valueOf(objectReserv.getDate_reservation()));
        values.put("date_ann", String.valueOf(objectReserv.getDate_annulation()));
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("Reserve", null, values) > 0;
        db.close();
        return createSuccessful;
    }
    public boolean create_Stat(EtatReservation objectStatus) {

        ContentValues values = new ContentValues();
        values.put("Date_res", String.valueOf(objectStatus.getDesc_etat()));
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("Desc_stat", null, values) > 0;
        db.close();
        return createSuccessful;
    }
    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM Today_rec";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }
    public List<Vol> read() {

        List<Vol> recordsList = new ArrayList<Vol>();

        String sql = "SELECT * FROM Vol ORDER BY Id_vol DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int Id_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_vol")));
                int Num_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Num_vol")));
                String Destination = cursor.getString(cursor.getColumnIndex("Destination"));
                double Duration= Double.parseDouble(cursor.getString(cursor.getColumnIndex("Duration")));
                double Price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Price")));
                boolean Disponibility= Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("Disponibility")));
                String Creation_date= cursor.getString(cursor.getColumnIndex("Creation_date"));
                Vol objectVol = new Vol();
                objectVol.setId_vol(Id_vol);
                objectVol.setNum_vol(Num_vol);
                objectVol.setDestination(Destination);
                objectVol.setDuree(Duration);
                objectVol.setPrix(Price);
                objectVol.setDesponible(Disponibility);
                objectVol.setDate_creation(Creation_date);
                recordsList.add(objectVol);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }
}