package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.Utilisateur;

/**
 * Created by root on 17/11/04.
 */

public interface IClientRepository {
    Reservation creer_reservation(Reservation reservation);
    Reservation update_reservation(Reservation reservation);
    Boolean delete_reservation(int id_reservation);
    Utilisateur login(String login, String password);
}
