package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.TypeUtilisateur;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by hamzajguerim on 2017-11-09.
 */

public interface IVisiteurRepository extends Serializable {
    Utilisateur creation_compte(Utilisateur utilisateur);
    List<Vol> listVol();
    List<Vol> listVolByDestination(String destination);
    Utilisateur login(String login, String password);
    Compte loginCompte(String login, String password);
    TypeUtilisateur findOneTypeUtilisateurByDesc(String desc_type_utilisateur);

    List<Vol> listVolFiltered(Map filter);
}
