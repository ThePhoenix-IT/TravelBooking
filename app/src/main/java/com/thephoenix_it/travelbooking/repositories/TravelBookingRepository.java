package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.TypeUtilisateur;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by root on 17/11/04.
 */

public class TravelBookingRepository implements IAdminRepository, IAgenceRepository, IClientRepository {
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
        return null;
    }

    @Override
    public List<Reservation> listReservation() {
        return null;
    }

    @Override
    public Utilisateur login(String login, String password) {
        return null;
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
            utilisateur.getCompte().setUtilisateur(utilisateur);
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
    public List<Vol> listVol() {
        return null;
    }
}
