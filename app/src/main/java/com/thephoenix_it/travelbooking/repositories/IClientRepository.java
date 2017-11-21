package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.EtatReservation;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 17/11/04.
 */

public interface IClientRepository extends Serializable {
    Reservation creer_reservation(Reservation reservation);
    Reservation update_reservation(Reservation reservation);
    Reservation findOneReservationById(int id_reservation);
    EtatReservation findOneEtatReservationByDesc(String etat_reservation);
    void delete_reservation(int id_reservation);
    Utilisateur login(String login, String password);
    List<Reservation> findAllReservation();
    Vol findOneVolById(int id_vol);
    List<Vol> listVol();
    List<Vol> listVolFiltered(Map filter);
}
