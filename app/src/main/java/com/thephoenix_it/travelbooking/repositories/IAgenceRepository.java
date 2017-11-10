package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 17/11/04.
 */

public interface IAgenceRepository extends Serializable {

    Utilisateur creation_compte(Utilisateur utilisateur);
    Utilisateur login(String login, String password);
    Vol creer_vol(Vol vol);
    Vol update_vol(Vol vol);
    Boolean delete_vol(int id_vol);
    List<Vol> listVol();
}
