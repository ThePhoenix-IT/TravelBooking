package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.Utilisateur;

import java.util.List;

/**
 * Created by root on 17/11/04.
 */

public interface IAdminRepository {

    Compte activation_compte(Compte compte);
    Compte getCompteByIdUtilisateur(int id_utilisateur);
    List<Utilisateur> listUtilisateur();
    List<Reservation> listReservation();
    Utilisateur login(String login, String password);
}
