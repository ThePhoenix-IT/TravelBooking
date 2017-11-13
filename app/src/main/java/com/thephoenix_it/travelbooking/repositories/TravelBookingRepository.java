package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.TypeUtilisateur;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.internal.Util;

/**
 * Created by hamzajguerim on 2017-11-12.
 */

public class TravelBookingRepository implements IAdminRepository, IAgenceRepository, IClientRepository, IVisiteurRepository, Serializable {
    private RealmFactory realmFactory;
    public TravelBookingRepository(RealmFactory instance) {
        realmFactory = instance;
    }
    @Override
    public Compte activation_compte(Compte compte) {
        Compte result = null;
        try {
            realmFactory.getRealm().beginTransaction();
            if(!compte.getEtat_compte())
                compte.setEtat_compte(true);
            result = realmFactory.getRealm().copyToRealm(compte);
            realmFactory.getRealm().commitTransaction();
        } catch (Exception ex) {
            System.err.println("activation_compte : " + ex);
        }
        return result;
    }

    @Override
    public Compte getCompteByIdUtilisateur(int id_utilisateur) {
        return null;
    }

    @Override
    public TypeUtilisateur createTypeUtilisateur(final TypeUtilisateur typeUtilisateur) {
        TypeUtilisateur result = null;
        try {

            realmFactory.getRealm().executeTransaction(new Realm.Transaction() { // must be in transaction for this to work
                @Override
                public void execute(Realm realm) {
                    // increment index
                    Number currentIdNum = realm.where(TypeUtilisateur.class).max("id_type_utilisateur");
                    int nextId;
                    if(currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    typeUtilisateur.setId_type_utilisateur(nextId);
                    //...
                    realm.insertOrUpdate(typeUtilisateur); // using insert API
                }
            });
        } catch (Exception ex){
            System.err.println("createTypeUtilisateur : " + ex);
        }
        return result;
    }

    @Override
    public TypeUtilisateur updateTypeUtilisateur(TypeUtilisateur typeUtilisateur) {
        return null;
    }

    @Override
    public Boolean deleteTypeUtilisateur(int id_type_utilisateur) {
        return null;
    }

    @Override
    public List<TypeUtilisateur> findAllTypeUtilisateur() {
        List<TypeUtilisateur> result = new ArrayList<TypeUtilisateur>();
        try{

            result = realmFactory.getRealm().where(TypeUtilisateur.class).findAll();
        } catch (Exception ex) {
            System.err.println("listReservation : " + ex);
        }
        return result;
    }

    @Override
    public TypeUtilisateur findOneTypeUtilisateurByDesc(String desc_type_utilisateur) {
        TypeUtilisateur result = null;
        try{
            result = realmFactory.getRealm().where(TypeUtilisateur.class).equalTo("desc_type_utilisateur", desc_type_utilisateur).findFirst();
        } catch (Exception ex) {
            System.err.println("findOneTypeUtilisateurByDesc : " + ex);
        }
        return result;
    }

    @Override
    public Utilisateur createCompteAdmin(Utilisateur utilisateur) {
        return creation_compte(utilisateur);
    }

    @Override
    public Utilisateur findCompteAdmin() {
        Utilisateur result = null;
        try{
            result = realmFactory.getRealm().where(Utilisateur.class).equalTo("id_type_utilisateur", 1).findFirst();
        } catch (Exception ex) {
            System.err.println("findCompteAdmin : " + ex);
        }
        return result;
    }

    @Override
    public Utilisateur findOneUtilisateurById(int id_utilisateur) {
        Utilisateur result = null;
        try{
            result = realmFactory.getRealm().where(Utilisateur.class).equalTo("id_utilisateur", id_utilisateur).findFirst();
        } catch (Exception ex) {
            System.err.println("findOneUtilisateurById : " + ex);
        }
        return result;
    }

    @Override
    public List<Utilisateur> listUtilisateur() {
        List<Utilisateur> result = new ArrayList<Utilisateur>();
        try{

            result = realmFactory.getRealm().where(Utilisateur.class).findAll();
        } catch (Exception ex) {
            System.err.println("listUtilisateur : " + ex);
        }
        return result;
    }

    @Override
    public List<Reservation> listReservation() {

        List<Reservation> result = new ArrayList<Reservation>();
        try{

            result = realmFactory.getRealm().where(Reservation.class).findAll();
        } catch (Exception ex) {
            System.err.println("listReservation : " + ex);
        }
        return result;
    }

    @Override
    public Utilisateur login(String login, String password) {

        Utilisateur result = null;
        try{
            Compte c = realmFactory.getRealm().where(Compte.class)
                    .contains("login", login)
                    .contains("password", password).findFirst();
            System.err.println(c);
            assert c != null;
            result = c.getUtilisateur();
            System.err.println(result);
        } catch (Exception ex) {
            System.err.println("login : " + ex);
        }
        return result;
    }

    @Override
    public Compte loginCompte(String login, String password) {

        Compte result = null;
        try{
            result = realmFactory.getRealm().where(Compte.class)
                    .contains("login", login)
                    .contains("password", password).findFirst();
        } catch (Exception ex) {
            System.err.println("loginCompte : " + ex);
        }
        return result;
    }

    @Override
    public Vol creer_vol(final Vol vol) {
        Vol result = null;
        try {

            realmFactory.getRealm().executeTransaction(new Realm.Transaction() { // must be in transaction for this to work
                @Override
                public void execute(Realm realm) {
                    // increment index
                    Number currentIdNum = realm.where(Vol.class).max("id_vol");
                    int nextId;
                    if(currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    vol.setId_vol(nextId);
                    //...
                    realm.insertOrUpdate(vol); // using insert API
                }
            });
        } catch (Exception ex){
            System.err.println("creer_vol : " + ex);
        }
        return result;
    }

    @Override
    public Vol update_vol(Vol vol) {
        return null;
    }

    @Override
    public Vol findOneVolById(int id_vol) {
        return null;
    }

    @Override
    public Boolean delete_vol(int id_vol) {
        return false;
    }

    @Override
    public Utilisateur creation_compte(final Utilisateur utilisateur) {
        Utilisateur result = null;
        try {

            realmFactory.getRealm().executeTransaction(new Realm.Transaction() { // must be in transaction for this to work
                @Override
                public void execute(Realm realm) {
                    // increment index
                    Number currentIdNum = realm.where(Utilisateur.class).max("id_utilisateur");
                    int nextId;
                    if(currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    utilisateur.setId_utilisateur(nextId);
                    //...
                    realm.insertOrUpdate(utilisateur); // using insert API
                }
            });
            realmFactory.getRealm().executeTransaction(new Realm.Transaction() { // must be in transaction for this to work
                @Override
                public void execute(Realm realm) {
                    // increment index
                    Number currentIdNum = realm.where(Compte.class).max("id_compte");
                    int nextId;
                    if(currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    utilisateur.getCompte().setId_compte(nextId);
                    utilisateur.getCompte().setId_utilisateur(utilisateur.getId_utilisateur());
                    //...
                    realm.insertOrUpdate(utilisateur.getCompte()); // using insert API
                }
            });
        } catch (Exception ex){
            System.err.println("creation_compte : " + ex);
        }
        return result;
    }

    @Override
    public Reservation creer_reservation(Reservation reservation) {
        return null;
    }

    @Override
    public Reservation update_reservation(Reservation reservation) {
        return null;
    }

    @Override
    public Boolean delete_reservation(int id_reservation) {
        return null;
    }

    @Override
    public List<Vol> listVol() {

        List<Vol> result = new ArrayList<Vol>();
        try{

            result = realmFactory.getRealm().where(Vol.class).findAll();
        } catch (Exception ex) {
            System.err.println("listVol : " + ex);
        }
        return result;
    }

    @Override
    public List<Reservation> findAllReservation() {
        return null;
    }

    @Override
    public List<Vol> listVolByDestination(String destination) {
        List<Vol> result = new ArrayList<Vol>();
        try{

            result = realmFactory.getRealm().where(Vol.class).contains("destination", destination).findAll();
        } catch (Exception ex) {
            System.err.println("listVol : " + ex);
        }
        return result;
    }
}
