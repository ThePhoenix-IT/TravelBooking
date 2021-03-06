package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 17/11/04.
 */

public interface IAgenceRepository extends Serializable {

    Utilisateur creation_compte(Utilisateur utilisateur);
    Utilisateur login(String login, String password);
    Vol creer_vol(Vol vol);
    Vol update_vol(Vol vol, int id_vol);
    Vol findOneVolById(int id_vol);
    void delete_vol(int id_vol);
    List<Vol> listVol();
    List<Vol> listVolByIdAgence(int id_utilisateur);
    List<Vol> listVolFiltered(Map filter);
    List<Reservation> findAllReservationByIdAgence(int id_utilisateur);
    List<Reservation> findAllReservationByIdVol(int id_vol);

}
