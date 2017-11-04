package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;

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
    public Compte creation_compte(Compte compte) {
        Compte result = null;
        try {
            realmFactory.getRealm().beginTransaction();
            realmFactory.getRealm().copyToRealm(compte.getUtilisateur());
            result = realmFactory.getRealm().copyToRealm(compte);
            realmFactory.getRealm().commitTransaction();
        } catch (Exception ex) {
            System.err.println("creation_compte : " + ex);
        }
        return result;
    }
}
