package com.thephoenix_it.travelbooking.models;

import java.io.Serializable;

/**
 * Created by root on 17/11/05.
 */

public class EtatReservation implements Serializable {
    private int id_etat_reservation;
    private String desc_etat;

    public EtatReservation() {
    }

    public EtatReservation(String desc_etat) {
        this.desc_etat = desc_etat;
    }

    public int getId_etat_reservation() {
        return id_etat_reservation;
    }

    public void setId_etat_reservation(int id_etat_reservation) {
        this.id_etat_reservation = id_etat_reservation;
    }

    public String getDesc_etat() {
        return desc_etat;
    }

    public void setDesc_etat(String desc_etat) {
        this.desc_etat = desc_etat;
    }
}
