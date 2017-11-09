package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;

import java.util.List;

/**
 * Created by hamzajguerim on 2017-11-09.
 */

public interface IVisiteurRepository {
    Utilisateur creation_compte(Utilisateur utilisateur);
    List<Vol> listVol();
    List<Vol> listVolByDestination(String destination);
    Utilisateur login(String login, String password);
    Compte loginCompte(String login, String password);
}
