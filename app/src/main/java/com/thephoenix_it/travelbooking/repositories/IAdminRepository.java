package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.TypeUtilisateur;
import com.thephoenix_it.travelbooking.models.Utilisateur;

import java.util.List;

/**
 * Created by root on 17/11/04.
 */

public interface IAdminRepository {

    Compte activation_compte(Compte compte);
    Compte getCompteByIdUtilisateur(int id_utilisateur);
    TypeUtilisateur createTypeUtilisateur(TypeUtilisateur typeUtilisateur);
    TypeUtilisateur updateTypeUtilisateur(TypeUtilisateur typeUtilisateur);
    Boolean deleteTypeUtilisateur(int id_type_utilisateur);
    List<TypeUtilisateur> findAllTypeUtilisateur();
    TypeUtilisateur findOneTypeUtilisateurByDesc(String desc_type_utilisateur);
    Utilisateur createCompteAdmin(Utilisateur utilisateur);
    Utilisateur findCompteAdmin();
    List<Utilisateur> listUtilisateur();
    List<Reservation> listReservation();
    Utilisateur login(String login, String password);
}
