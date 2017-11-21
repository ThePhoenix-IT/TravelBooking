package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.EtatReservation;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.TypeUtilisateur;
import com.thephoenix_it.travelbooking.models.Utilisateur;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 17/11/04.
 */

public interface IAdminRepository extends Serializable {

    Compte activation_compte(Compte compte);
    Compte getCompteByIdUtilisateur(int id_utilisateur);
    TypeUtilisateur createTypeUtilisateur(TypeUtilisateur typeUtilisateur);
    TypeUtilisateur updateTypeUtilisateur(TypeUtilisateur typeUtilisateur);
    void deleteTypeUtilisateur(int id_type_utilisateur);
    List<TypeUtilisateur> findAllTypeUtilisateur();
    TypeUtilisateur findOneTypeUtilisateurByDesc(String desc_type_utilisateur);
    Utilisateur createCompteAdmin(Utilisateur utilisateur);
    Utilisateur findCompteAdmin();
    Utilisateur findOneUtilisateurById(int id_utilisateur);
    List<Utilisateur> listUtilisateur();
    List<Reservation> listReservation();
    EtatReservation createEtatReservation(EtatReservation etatReservation);
    List<EtatReservation> findAllEtatReservation();
    Utilisateur login(String login, String password);
}
