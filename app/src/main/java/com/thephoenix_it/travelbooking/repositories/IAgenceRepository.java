package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;

import java.util.List;

/**
 * Created by root on 17/11/04.
 */

public interface IAgenceRepository {

    Utilisateur creation_compte(Utilisateur utilisateur);
    List<Vol> listVol();
    Utilisateur login(String login, String password);
}
