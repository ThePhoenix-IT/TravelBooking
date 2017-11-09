package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.TypeUtilisateur;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.internal.Util;

/**
 * Created by root on 17/11/04.
 */

public class TravelBookingRepository implements IAdminRepository, IAgenceRepository, IClientRepository, IVisiteurRepository {
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
            result = realmFactory.getRealm().where(Utilisateur.class).equalTo("id_utilisateur", c.getId_utilisateur()).findFirst();
            if(result != null)
                result.setCompte(c);
        } catch (Exception ex) {
            System.err.println("login : " + ex);
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
