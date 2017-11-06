package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.Utilisateur;

import java.util.List;

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
    public Utilisateur creation_compte(Utilisateur utilisateur) {
        Utilisateur result = null;
        try {
            realmFactory.getRealm().beginTransaction();
            result = realmFactory.getRealm().copyToRealm(utilisateur);
            realmFactory.getRealm().copyToRealm(utilisateur.getCompte());
            realmFactory.getRealm().commitTransaction();
        } catch (Exception ex) {
            System.err.println("creation_compte : " + ex);
        }
        return result;
    }
}
