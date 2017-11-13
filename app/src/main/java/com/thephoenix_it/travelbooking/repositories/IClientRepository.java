package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.Utilisateur;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 17/11/04.
 */

public interface IClientRepository extends Serializable {
    Reservation creer_reservation(Reservation reservation);
    Reservation update_reservation(Reservation reservation);
    Boolean delete_reservation(int id_reservation);
    Utilisateur login(String login, String password);
    List<Reservation> findAllReservation();
}
