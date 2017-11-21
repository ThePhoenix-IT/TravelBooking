package com.thephoenix_it.travelbooking.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.EtatReservation;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.TypeUtilisateur;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by bouba on 10-Nov-17.
 */

public class DatabaseDAO extends DatabaseHandler implements Serializable {

    public DatabaseDAO(Context context) {
        super(context);
    }
    public boolean create_compte(Compte objectCompte) {

        ContentValues values = new ContentValues();
        //values.put("Id_account", objectCompte.getId_compte());
        values.put("Login", objectCompte.getLogin());
        values.put("Password", objectCompte.getPassword());
        values.put("State", objectCompte.getEtat_compte());
        values.put("Id_user", objectCompte.getId_utilisateur());
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("Account", null, values) > 0;
        db.close();
        return createSuccessful;
    }
    public TypeUtilisateur create_user_type(TypeUtilisateur objectUserType) {

        ContentValues values = new ContentValues();
        values.put("desc_user_type", objectUserType.getDesc_type_utilisateur());
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("UserType", null, values) > 0;
        if (createSuccessful) {
            String sql = "SELECT MAX(Id_user_type) FROM UserType";
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                do {

                    objectUserType.setId_type_utilisateur(cursor.getInt(0));

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        db.close();
        return objectUserType;
    }
    public Utilisateur create_user(Utilisateur objectUser) {

        ContentValues values = new ContentValues();
        values.put("Nom_user", objectUser.getNom_utilisateur());
        values.put("Prenom_user", objectUser.getPrenom_utilisateur());
        values.put("CIN", objectUser.getCin());
        values.put("Pays", objectUser.getPays());
        values.put("Date_b", String.valueOf(objectUser.getDate_naissance()));
        values.put("Id_user_type", String.valueOf(objectUser.getTypeUtilisateur().getId_type_utilisateur()));

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("User", null, values) > 0;

        if (createSuccessful) {
            String sql = "SELECT MAX(Id_user) FROM User";
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                do {

                    objectUser.setId_utilisateur(cursor.getInt(0));
                    objectUser.getCompte().setId_utilisateur(cursor.getInt(0));
                    create_compte(objectUser.getCompte());

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        db.close();
        return objectUser;
    }

    public void remove_vol(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Vol where Id_vol= " + i);
        db.close();
    }

    public void remove_user(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from User where Id_vol= " + i);
        db.close();

    }

    public void remove_res(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from Reserve where Id_vol = " + i);
        db.close();

    }

    public boolean update_vol(Vol objectVol, int i) {
        ContentValues values = new ContentValues();
        values.put("Num_vol", objectVol.getNum_vol());
        values.put("Destination", objectVol.getDestination());
        values.put("Duration", objectVol.getDuree());
        values.put("Price", objectVol.getPrix());
        values.put("Disponibility", objectVol.getDisponible());
        values.put("Creation_date", String.valueOf(objectVol.getDate_creation()));
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.update("Vol", values, "Id_vol = i", null)> 0;
        db.close();
        return createSuccessful;
    }

    public boolean update_res(Reservation objectReserv, int i) {
        ContentValues values = new ContentValues();
        values.put("Date_res", String.valueOf(objectReserv.getDate_reservation()));
        values.put("date_ann", String.valueOf(objectReserv.getDate_annulation()));
        values.put("Id_vol", objectReserv.getVol().getId_vol());
        values.put("Id_user", objectReserv.getClient().getId_utilisateur());
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.update("Reserve", values, "Id_vol = i", null) > 0;
        db.close();
        return createSuccessful;
    }
    public Vol create_vol(Vol objectVol) {

        ContentValues values = new ContentValues();
        values.put("Num_vol", objectVol.getNum_vol());
        values.put("Depart", objectVol.getDepart());
        values.put("Destination", objectVol.getDestination());
        values.put("Duration", objectVol.getDuree());
        values.put("Price", objectVol.getPrix());
        values.put("Disponibility", objectVol.getDisponible());
        values.put("Creation_date", String.valueOf(objectVol.getDate_creation()));
        values.put("Id_user", objectVol.getAgence().getId_utilisateur());
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("Vol", null, values) > 0;
        if (createSuccessful) {
            String sql = "SELECT MAX(Id_vol) FROM Vol";
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                do {

                    objectVol.setId_vol(cursor.getInt(0));

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        db.close();
        return objectVol;
    }

    public Vol findOneVolByIdVol(int id_vol) {
        Vol result = null;

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM Vol WHERE Id_vol = " + id_vol;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                result = new Vol();
                int Id_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_vol")));
                int Num_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Num_vol")));
                String Destination = cursor.getString(cursor.getColumnIndex("Destination"));
                double Duration = 0.0D;
                if(cursor.getString(cursor.getColumnIndex("Duration")) != null && !cursor.getString(cursor.getColumnIndex("Duration")).isEmpty())
                    Duration = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Duration")));
                double Price = 0.0D;
                if(cursor.getString(cursor.getColumnIndex("Price")) != null && !cursor.getString(cursor.getColumnIndex("Price")).isEmpty() )
                    Price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Price")));
                boolean Disponibility = false;
                if(cursor.getString(cursor.getColumnIndex("Disponibility")) != null && !cursor.getString(cursor.getColumnIndex("Disponibility")).isEmpty())
                    Disponibility = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("Disponibility")));
                Date Creation_date = new Date();
                if(cursor.getString(cursor.getColumnIndex("Creation_date")) != null && !cursor.getString(cursor.getColumnIndex("Creation_date")).isEmpty())
                    try {
                        Creation_date = new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("Creation_date")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                result.setId_vol(Id_vol);
                result.setNum_vol(Num_vol);
                result.setDestination(Destination);
                result.setDuree(Duration);
                result.setPrix(Price);
                result.setDisponible(Disponibility);
                result.setDate_creation(Creation_date);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return result;
    }
    public Reservation create_res(Reservation objectReserv) {

        ContentValues values = new ContentValues();
        values.put("Date_res", String.valueOf(objectReserv.getDate_reservation()));
        values.put("date_ann", String.valueOf(objectReserv.getDate_annulation()));
        values.put("Id_vol", objectReserv.getVol().getId_vol());
        values.put("Id_user", objectReserv.getClient().getId_utilisateur());
        values.put("Id_etat", objectReserv.getEtatReservation().getId_etat_reservation());
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("Reserve", null, values) > 0;
        if (createSuccessful) {
            String sql = "SELECT MAX(Id_reserve) FROM Reserve";
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                do {

                    objectReserv.setId_reservation(cursor.getInt(0));

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        db.close();
        return objectReserv;
    }
    public boolean create_Stat(EtatReservation objectStatus) {

        ContentValues values = new ContentValues();
        values.put("Desc_stat", String.valueOf(objectStatus.getDesc_etat()));
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("Status", null, values) > 0;
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

    public TypeUtilisateur createTypeUtilisateur(TypeUtilisateur objectTypeUtilisateur) {

        ContentValues values = new ContentValues();
        values.put("desc_user_type", String.valueOf(objectTypeUtilisateur.getDesc_type_utilisateur()));
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("UserType", null, values) > 0;
        if (createSuccessful) {
            String sql = "SELECT MAX(Id_user_type) FROM UserType";
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                do {

                    objectTypeUtilisateur.setId_type_utilisateur(cursor.getInt(0));

                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        db.close();
        return objectTypeUtilisateur;
    }


    public List<EtatReservation> findAllEtatReservation() {


        List<EtatReservation> result = new ArrayList<>();
        String sql = "SELECT * FROM Status";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                EtatReservation er = new EtatReservation();
                int Id_etat = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_etat")));
                String desc_stat = cursor.getString(cursor.getColumnIndex("Desc_stat"));
                er.setId_etat_reservation(Id_etat);
                er.setDesc_etat(desc_stat);
                result.add(er);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }
    public Utilisateur findCompteAdmin() {

        Utilisateur result = null;

        String sql = "SELECT * FROM User u, Account a, UserType ut " +
                "WHERE u.Id_user = a.Id_user AND " +
                "u.Id_user_type = ut.Id_user_type AND " +
                "ut.desc_user_type = \"Admin\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                result = new Utilisateur();
                int id_user_type = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user_type")));
                String desc_user_type = cursor.getString(cursor.getColumnIndex("desc_user_type"));
                int id_user = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user")));
                String nom = cursor.getString(cursor.getColumnIndex("Nom_user"));
                String prenom = cursor.getString(cursor.getColumnIndex("Prenom_user"));
                String pays = cursor.getString(cursor.getColumnIndex("Pays"));
                int cin = Integer.parseInt(cursor.getString(cursor.getColumnIndex("CIN")));
                Date date_b = new Date();
                try {
                    date_b = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(cursor.getString(cursor.getColumnIndex("Date_b")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int id_compte = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_account")));
                String login = cursor.getString(cursor.getColumnIndex("Login"));
                TypeUtilisateur objectUserType = new TypeUtilisateur();
                objectUserType.setId_type_utilisateur(id_user_type);
                objectUserType.setDesc_type_utilisateur(desc_user_type);
                result.setTypeUtilisateur(objectUserType);
                result.setId_utilisateur(id_user);
                result.setNom_utilisateur(nom);
                result.setPrenom_utilisateur(prenom);
                result.setCin(cin);
                result.setDate_naissance(date_b);
                result.setPays(pays);
                Compte objectCompte = new Compte();
                objectCompte.setId_compte(id_compte);
                objectCompte.setLogin(login);
                result.setCompte(objectCompte);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }
    public Utilisateur findOneUtilisateurById(int id_utilisateur) {

        Utilisateur result = null;

        String sql = "SELECT * FROM User u, Account a, UserType ut " +
                "WHERE u.Id_user = a.Id_user AND " +
                "u.Id_user_type = ut.Id_user_type AND " +
                "u.Id_user = \"" + id_utilisateur +"\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                result = new Utilisateur();
                int id_user_type = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user_type")));
                String desc_user_type = cursor.getString(cursor.getColumnIndex("desc_user_type"));
                int id_user = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user")));
                String nom = cursor.getString(cursor.getColumnIndex("Nom_user"));
                String prenom = cursor.getString(cursor.getColumnIndex("Prenom_user"));
                String pays = cursor.getString(cursor.getColumnIndex("Pays"));
                int cin = Integer.parseInt(cursor.getString(cursor.getColumnIndex("CIN")));
                Date date_b = new Date();
                try {
                    date_b = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(cursor.getString(cursor.getColumnIndex("Date_b")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int id_compte = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_account")));
                String login = cursor.getString(cursor.getColumnIndex("Login"));
                TypeUtilisateur objectUserType = new TypeUtilisateur();
                objectUserType.setId_type_utilisateur(id_user_type);
                objectUserType.setDesc_type_utilisateur(desc_user_type);
                result.setTypeUtilisateur(objectUserType);
                result.setId_utilisateur(id_user);
                result.setNom_utilisateur(nom);
                result.setPrenom_utilisateur(prenom);
                result.setCin(cin);
                result.setDate_naissance(date_b);
                result.setPays(pays);
                Compte objectCompte = new Compte();
                objectCompte.setId_compte(id_compte);
                objectCompte.setLogin(login);
                result.setCompte(objectCompte);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }

    public TypeUtilisateur findOneTypeUtilisateurByDesc(String type) {

        TypeUtilisateur result = new TypeUtilisateur();

        String sql = "SELECT * FROM UserType ut WHERE ut.desc_user_type = \"" + type + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id_user_type = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user_type")));
                String desc_user_type = cursor.getString(cursor.getColumnIndex("desc_user_type"));
                System.err.println(desc_user_type);
                result.setId_type_utilisateur(id_user_type);
                result.setDesc_type_utilisateur(desc_user_type);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }

    public EtatReservation findOneEtatReservationByDesc(String desc_etat) {

        EtatReservation result = new EtatReservation();

        String sql = "SELECT * FROM Status s WHERE s.Desc_stat = \"" + desc_etat +"\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int Id_etat = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_etat")));
                String desc_stat = cursor.getString(cursor.getColumnIndex("Desc_stat"));
                result.setId_etat_reservation(Id_etat);
                result.setDesc_etat(desc_stat);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }

    public List<TypeUtilisateur> findAllTypeUtilisateur() {

        List<TypeUtilisateur> recordsList = new ArrayList<TypeUtilisateur>();

        String sql = "SELECT * FROM UserType ORDER BY Id_user_type DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int id_user_type = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user_type")));
                String desc_user_type = cursor.getString(cursor.getColumnIndex("desc_user_type"));
                TypeUtilisateur objectVol = new TypeUtilisateur();
                objectVol.setId_type_utilisateur(id_user_type);
                objectVol.setDesc_type_utilisateur(desc_user_type);
                recordsList.add(objectVol);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public Utilisateur login(String login, String password) {

        Utilisateur result = null;

        String sql = "SELECT * FROM User u, UserType ut, Account a WHERE a.Id_user = u.Id_user AND " +
                " u.Id_user_type = ut.Id_user_type AND " +
                " a.login = \"" + login + "\" AND a.password = \"" + password + "\" ORDER BY Id_user DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                result = new Utilisateur();
                int id_user_type = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user_type")));
                String desc_user_type = cursor.getString(cursor.getColumnIndex("desc_user_type"));
                int id_user = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user")));
                String nom = cursor.getString(cursor.getColumnIndex("Nom_user"));
                String prenom = cursor.getString(cursor.getColumnIndex("Prenom_user"));
                String pays = cursor.getString(cursor.getColumnIndex("Pays"));
                int cin = Integer.parseInt(cursor.getString(cursor.getColumnIndex("CIN")));
                Date date_b = new Date();
                try {
                    date_b = new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("Date_b")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int id_compte = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_account")));
                String loginr = cursor.getString(cursor.getColumnIndex("Login"));
                String passwordr = cursor.getString(cursor.getColumnIndex("Password"));
                int etatCompte = cursor.getInt(cursor.getColumnIndex("State"));
                TypeUtilisateur objectUserType = new TypeUtilisateur();
                objectUserType.setId_type_utilisateur(id_user_type);
                objectUserType.setDesc_type_utilisateur(desc_user_type);
                result.setTypeUtilisateur(objectUserType);
                result.setId_utilisateur(id_user);
                result.setNom_utilisateur(nom);
                result.setPrenom_utilisateur(prenom);
                result.setCin(cin);
                result.setDate_naissance(date_b);
                result.setPays(pays);
                Compte objectCompte = new Compte();
                objectCompte.setId_compte(id_compte);
                objectCompte.setLogin(loginr);
                objectCompte.setPassword(passwordr);
                objectCompte.setEtat_compte(etatCompte > 0);
                result.setCompte(objectCompte);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }
    public List<Utilisateur> listUtilisateur() {

        List<Utilisateur> result = new ArrayList<>();

        String sql = "SELECT * FROM User u, UserType ut WHERE" +
                " u.Id_user_type = ut.Id_user_type ORDER BY Id_user DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Utilisateur user = new Utilisateur();
                int id_user_type = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user_type")));
                String desc_user_type = cursor.getString(cursor.getColumnIndex("desc_user_type"));
                int id_user = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user")));
                String nom = cursor.getString(cursor.getColumnIndex("Nom_user"));
                String prenom = cursor.getString(cursor.getColumnIndex("Prenom_user"));
                String pays = cursor.getString(cursor.getColumnIndex("Pays"));
                int cin = Integer.parseInt(cursor.getString(cursor.getColumnIndex("CIN")));
                Date date_b = new Date();
                try {
                    date_b = new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("Date_b")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TypeUtilisateur objectUserType = new TypeUtilisateur();
                objectUserType.setId_type_utilisateur(id_user_type);
                objectUserType.setDesc_type_utilisateur(desc_user_type);
                user.setTypeUtilisateur(objectUserType);
                user.setId_utilisateur(id_user);
                user.setNom_utilisateur(nom);
                user.setPrenom_utilisateur(prenom);
                user.setCin(cin);
                user.setDate_naissance(date_b);
                user.setPays(pays);
                result.add(user);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }


    public List<Utilisateur> listUtilisateurNotAdmin() {

        List<Utilisateur> result = new ArrayList<>();

        String sql = "SELECT * FROM User u, UserType ut WHERE" +
                " u.Id_user_type = ut.Id_user_type AND ut.desc_user_type <> \"Admin\" ORDER BY Id_user DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Utilisateur user = new Utilisateur();
                int id_user_type = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user_type")));
                String desc_user_type = cursor.getString(cursor.getColumnIndex("desc_user_type"));
                int id_user = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user")));
                String nom = cursor.getString(cursor.getColumnIndex("Nom_user"));
                String prenom = cursor.getString(cursor.getColumnIndex("Prenom_user"));
                String pays = cursor.getString(cursor.getColumnIndex("Pays"));
                int cin = Integer.parseInt(cursor.getString(cursor.getColumnIndex("CIN")));
                Date date_b = new Date();
                try {
                    date_b = new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("Date_b")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TypeUtilisateur objectUserType = new TypeUtilisateur();
                objectUserType.setId_type_utilisateur(id_user_type);
                objectUserType.setDesc_type_utilisateur(desc_user_type);
                user.setTypeUtilisateur(objectUserType);
                user.setId_utilisateur(id_user);
                user.setNom_utilisateur(nom);
                user.setPrenom_utilisateur(prenom);
                user.setCin(cin);
                user.setDate_naissance(date_b);
                user.setPays(pays);
                result.add(user);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }
    public List<Reservation> findAllReservation() {

        List<Reservation> recordsList = new ArrayList<Reservation>();

        //String sql = "SELECT * FROM Reserve r, Status s, Vol v WHERE r.Id_vol = v.Id_vol AND r.Id_etat = s.Id_etat ORDER BY Id_reserve DESC";
        String sql = "SELECT * FROM Reserve r, Vol v WHERE r.Id_vol = v.Id_vol ORDER BY Id_reserve DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int Id_reserve = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_reserve")));
                int Id_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_vol")));
                int Num_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Num_vol")));
                String Destination = cursor.getString(cursor.getColumnIndex("Destination"));
                double Duration = 0.0D;
                if(cursor.getString(cursor.getColumnIndex("Duration")) != null && !cursor.getString(cursor.getColumnIndex("Duration")).isEmpty())
                    Duration = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Duration")));
                double Price = 0.0D;
                if(cursor.getString(cursor.getColumnIndex("Price")) != null && !cursor.getString(cursor.getColumnIndex("Price")).isEmpty() )
                    Price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Price")));
                boolean Disponibility = false;
                if(cursor.getString(cursor.getColumnIndex("Disponibility")) != null && !cursor.getString(cursor.getColumnIndex("Disponibility")).isEmpty())
                    Disponibility = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("Disponibility")));
                Date Creation_date = new Date();
                if(cursor.getString(cursor.getColumnIndex("Creation_date")) != null && !cursor.getString(cursor.getColumnIndex("Creation_date")).isEmpty())
                    try {
                        Creation_date = new SimpleDateFormat("yyyy-mm-dd").parse(cursor.getString(cursor.getColumnIndex("Creation_date")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                Reservation objectReserve = new Reservation();
                Vol objectVol = new Vol();
                objectVol.setId_vol(Id_vol);
                objectVol.setNum_vol(Num_vol);
                objectVol.setDestination(Destination);
                objectVol.setDuree(Duration);
                objectVol.setPrix(Price);
                objectVol.setDisponible(Disponibility);
                objectVol.setDate_creation(Creation_date);
                objectReserve.setId_reservation(Id_reserve);
                objectReserve.setVol(objectVol);
                objectReserve.setEtatReservation(new EtatReservation("Encours"));
                recordsList.add(objectReserve);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }
    public List<Vol> findAllVol() {

        List<Vol> recordsList = new ArrayList<Vol>();

        String sql = "SELECT * FROM Vol v, User u  WHERE " +
                "u.Id_user = v.Id_user" +
                " ORDER BY Id_vol DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int Id_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_vol")));
                int Num_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Num_vol")));
                String Destination = cursor.getString(cursor.getColumnIndex("Destination"));
                String depart = cursor.getString(cursor.getColumnIndex("Depart"));
                double Duration = 0.0D;
                if(cursor.getString(cursor.getColumnIndex("Duration")) != null && !cursor.getString(cursor.getColumnIndex("Duration")).isEmpty())
                    Duration = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Duration")));
                double Price = 0.0D;
                if(cursor.getString(cursor.getColumnIndex("Price")) != null && !cursor.getString(cursor.getColumnIndex("Price")).isEmpty() )
                    Price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Price")));
                boolean Disponibility = false;
                if(cursor.getString(cursor.getColumnIndex("Disponibility")) != null && !cursor.getString(cursor.getColumnIndex("Disponibility")).isEmpty())
                    Disponibility = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("Disponibility")));
                Date Creation_date = new Date();
                if(cursor.getString(cursor.getColumnIndex("Creation_date")) != null && !cursor.getString(cursor.getColumnIndex("Creation_date")).isEmpty())
                    try {
                        Creation_date = new SimpleDateFormat("yyyy-mm-dd").parse(cursor.getString(cursor.getColumnIndex("Creation_date")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                int id_user = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user")));
                String nom = cursor.getString(cursor.getColumnIndex("Nom_user"));
                String prenom = cursor.getString(cursor.getColumnIndex("Prenom_user"));
                String pays = cursor.getString(cursor.getColumnIndex("Pays"));
                int cin = Integer.parseInt(cursor.getString(cursor.getColumnIndex("CIN")));
                Date date_b = new Date();
                try {
                    date_b = new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("Date_b")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Vol objectVol = new Vol();
                objectVol.setId_vol(Id_vol);
                objectVol.setNum_vol(Num_vol);
                objectVol.setDepart(depart);
                objectVol.setDestination(Destination);
                objectVol.setDuree(Duration);
                objectVol.setPrix(Price);
                objectVol.setDisponible(Disponibility);
                objectVol.setDate_creation(Creation_date);
                Utilisateur user = new Utilisateur();
                //TypeUtilisateur objectUserType = new TypeUtilisateur();
                //objectUserType.setId_type_utilisateur(id_user_type);
                //objectUserType.setDesc_type_utilisateur(desc_user_type);
                //user.setTypeUtilisateur(objectUserType);
                user.setId_utilisateur(id_user);
                user.setNom_utilisateur(nom);
                user.setPrenom_utilisateur(prenom);
                user.setCin(cin);
                user.setDate_naissance(date_b);
                user.setPays(pays);
                objectVol.setAgence(user);
                recordsList.add(objectVol);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public Utilisateur createCompteAdmin(Utilisateur utilisateur) {
        create_user(utilisateur);
        return utilisateur;
    }

    public List<Vol> listVolByIdAgence(int id_utilisateur) {
        List<Vol> recordsList = new ArrayList<Vol>();

        String sql = "SELECT * FROM Vol v, User u  WHERE " +
                "v.Id_user = " + id_utilisateur +
                " AND v.Id_user = u.Id_user ORDER BY Id_vol DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int Id_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_vol")));
                int Num_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Num_vol")));
                String Destination = cursor.getString(cursor.getColumnIndex("Destination"));
                String depart = cursor.getString(cursor.getColumnIndex("Depart"));
                double Duration = 0.0D;
                if(cursor.getString(cursor.getColumnIndex("Duration")) != null && !cursor.getString(cursor.getColumnIndex("Duration")).isEmpty())
                    Duration = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Duration")));
                double Price = 0.0D;
                if(cursor.getString(cursor.getColumnIndex("Price")) != null && !cursor.getString(cursor.getColumnIndex("Price")).isEmpty() )
                    Price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Price")));
                boolean Disponibility = false;
                if(cursor.getString(cursor.getColumnIndex("Disponibility")) != null && !cursor.getString(cursor.getColumnIndex("Disponibility")).isEmpty())
                    Disponibility = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("Disponibility")));
                Date Creation_date = new Date();
                if(cursor.getString(cursor.getColumnIndex("Creation_date")) != null && !cursor.getString(cursor.getColumnIndex("Creation_date")).isEmpty())
                    try {
                        Creation_date = new SimpleDateFormat("yyyy-mm-dd").parse(cursor.getString(cursor.getColumnIndex("Creation_date")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                int id_user = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user")));
                String nom = cursor.getString(cursor.getColumnIndex("Nom_user"));
                String prenom = cursor.getString(cursor.getColumnIndex("Prenom_user"));
                String pays = cursor.getString(cursor.getColumnIndex("Pays"));
                int cin = Integer.parseInt(cursor.getString(cursor.getColumnIndex("CIN")));
                Date date_b = new Date();
                try {
                    date_b = new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("Date_b")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Vol objectVol = new Vol();
                objectVol.setId_vol(Id_vol);
                objectVol.setNum_vol(Num_vol);
                objectVol.setDepart(depart);
                objectVol.setDestination(Destination);
                objectVol.setDuree(Duration);
                objectVol.setPrix(Price);
                objectVol.setDisponible(Disponibility);
                objectVol.setDate_creation(Creation_date);
                Utilisateur user = new Utilisateur();
                //TypeUtilisateur objectUserType = new TypeUtilisateur();
                //objectUserType.setId_type_utilisateur(id_user_type);
                //objectUserType.setDesc_type_utilisateur(desc_user_type);
                //user.setTypeUtilisateur(objectUserType);
                user.setId_utilisateur(id_user);
                user.setNom_utilisateur(nom);
                user.setPrenom_utilisateur(prenom);
                user.setCin(cin);
                user.setDate_naissance(date_b);
                user.setPays(pays);
                objectVol.setAgence(user);
                recordsList.add(objectVol);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }
    public List<Vol> listVolFiltered(Map filter) {
        List<Vol> recordsList = new ArrayList<Vol>();

        String sql = "SELECT * FROM Vol v, User u WHERE " +
                "Num_vol = " + filter.get("num_vol") +
                " OR Destination = \"" + (filter.get("destination") != "" ? filter.get("destination") : "%") + "\"" +
                " OR Price = " + (filter.get("prix") != "" ? filter.get("prix") : "%") +
                " OR dateDep = \"" + (filter.get("dateDep") != "" ? filter.get("dateDep") : "%") + "\"" +
                " OR dateArr = \"" + (filter.get("dateArr") != "" ? filter.get("dateArr") : "%") + "\"" +
                " OR Nom_user = \"" + (filter.get("Nom_user") != "" ? filter.get("Nom_user") : "%") + "\" " +
                "And u.Id_user = v.Id_user " +
                "ORDER BY Id_vol DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int Id_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_vol")));
                int Num_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Num_vol")));
                String Destination = cursor.getString(cursor.getColumnIndex("Destination"));
                String depart = cursor.getString(cursor.getColumnIndex("Depart"));
                double Duration = 0.0D;
                if(cursor.getString(cursor.getColumnIndex("Duration")) != null && !cursor.getString(cursor.getColumnIndex("Duration")).isEmpty())
                    Duration = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Duration")));
                double Price = 0.0D;
                if(cursor.getString(cursor.getColumnIndex("Price")) != null && !cursor.getString(cursor.getColumnIndex("Price")).isEmpty() )
                    Price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Price")));
                boolean Disponibility = false;
                if(cursor.getString(cursor.getColumnIndex("Disponibility")) != null && !cursor.getString(cursor.getColumnIndex("Disponibility")).isEmpty())
                    Disponibility = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("Disponibility")));
                Date Creation_date = new Date();
                if(cursor.getString(cursor.getColumnIndex("Creation_date")) != null && !cursor.getString(cursor.getColumnIndex("Creation_date")).isEmpty())
                    try {
                        Creation_date = new SimpleDateFormat("yyyy-mm-dd").parse(cursor.getString(cursor.getColumnIndex("Creation_date")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                int id_user = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user")));
                String nom = cursor.getString(cursor.getColumnIndex("Nom_user"));
                String prenom = cursor.getString(cursor.getColumnIndex("Prenom_user"));
                String pays = cursor.getString(cursor.getColumnIndex("Pays"));
                int cin = Integer.parseInt(cursor.getString(cursor.getColumnIndex("CIN")));
                Date date_b = new Date();
                try {
                    date_b = new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("Date_b")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Vol objectVol = new Vol();
                objectVol.setId_vol(Id_vol);
                objectVol.setNum_vol(Num_vol);
                objectVol.setDepart(depart);
                objectVol.setDestination(Destination);
                objectVol.setDuree(Duration);
                objectVol.setPrix(Price);
                objectVol.setDisponible(Disponibility);
                objectVol.setDate_creation(Creation_date);
                Utilisateur user = new Utilisateur();
                //TypeUtilisateur objectUserType = new TypeUtilisateur();
                //objectUserType.setId_type_utilisateur(id_user_type);
                //objectUserType.setDesc_type_utilisateur(desc_user_type);
                //user.setTypeUtilisateur(objectUserType);
                user.setId_utilisateur(id_user);
                user.setNom_utilisateur(nom);
                user.setPrenom_utilisateur(prenom);
                user.setCin(cin);
                user.setDate_naissance(date_b);
                user.setPays(pays);
                objectVol.setAgence(user);
                recordsList.add(objectVol);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public Reservation findOneReservationById(int id_reservation) {
        Reservation result = null;

        //String sql = "SELECT * FROM Reserve r, Status s, Vol v WHERE r.Id_vol = v.Id_vol AND r.Id_etat = s.Id_etat ORDER BY Id_reserve DESC";
        String sql = "SELECT * FROM Reserve r, Vol v, User u, Status s WHERE r.Id_vol = v.Id_vol AND " +
                "r.Id_reserve = " + id_reservation + " AND " +
                "r.Id_user = u.Id_user AND r.Id_etat = s.Id_etat ORDER BY Id_reserve DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int Id_reserve = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_reserve")));
                int Id_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_vol")));
                int Num_vol = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Num_vol")));
                String Destination = cursor.getString(cursor.getColumnIndex("Destination"));
                double Duration = 0.0D;
                if(cursor.getString(cursor.getColumnIndex("Duration")) != null && !cursor.getString(cursor.getColumnIndex("Duration")).isEmpty())
                    Duration = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Duration")));
                double Price = 0.0D;
                if(cursor.getString(cursor.getColumnIndex("Price")) != null && !cursor.getString(cursor.getColumnIndex("Price")).isEmpty() )
                    Price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("Price")));
                boolean Disponibility = false;
                if(cursor.getString(cursor.getColumnIndex("Disponibility")) != null && !cursor.getString(cursor.getColumnIndex("Disponibility")).isEmpty())
                    Disponibility = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("Disponibility")));
                Date Creation_date = new Date();
                if(cursor.getString(cursor.getColumnIndex("Creation_date")) != null && !cursor.getString(cursor.getColumnIndex("Creation_date")).isEmpty())
                    try {
                        Creation_date = new SimpleDateFormat("yyyy-mm-dd").parse(cursor.getString(cursor.getColumnIndex("Creation_date")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                int Id_etat = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_etat")));
                String desc_stat = cursor.getString(cursor.getColumnIndex("Desc_stat"));
                EtatReservation er = new EtatReservation();
                er.setId_etat_reservation(Id_etat);
                er.setDesc_etat(desc_stat);
                result = new Reservation();
                Vol objectVol = new Vol();
                objectVol.setId_vol(Id_vol);
                objectVol.setNum_vol(Num_vol);
                objectVol.setDestination(Destination);
                objectVol.setDuree(Duration);
                objectVol.setPrix(Price);
                objectVol.setDisponible(Disponibility);
                objectVol.setDate_creation(Creation_date);
                result.setId_reservation(Id_reserve);
                result.setVol(objectVol);
                result.setEtatReservation(er);
                //int id_user_type = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user_type")));
                //String desc_user_type = cursor.getString(cursor.getColumnIndex("desc_user_type"));
                int id_user = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id_user")));
                String nom = cursor.getString(cursor.getColumnIndex("Nom_user"));
                String prenom = cursor.getString(cursor.getColumnIndex("Prenom_user"));
                String pays = cursor.getString(cursor.getColumnIndex("Pays"));
                int cin = Integer.parseInt(cursor.getString(cursor.getColumnIndex("CIN")));
                Date date_b = new Date();
                try {
                    date_b = new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndex("Date_b")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Utilisateur user = new Utilisateur();
                //TypeUtilisateur objectUserType = new TypeUtilisateur();
                //objectUserType.setId_type_utilisateur(id_user_type);
                //objectUserType.setDesc_type_utilisateur(desc_user_type);
                //user.setTypeUtilisateur(objectUserType);
                user.setId_utilisateur(id_user);
                user.setNom_utilisateur(nom);
                user.setPrenom_utilisateur(prenom);
                user.setCin(cin);
                user.setDate_naissance(date_b);
                user.setPays(pays);
                result.setClient(user);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return result;
    }
}