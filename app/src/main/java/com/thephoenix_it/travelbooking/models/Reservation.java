package com.thephoenix_it.travelbooking.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 17/11/05.
 */

public class Reservation extends RealmObject {
    @PrimaryKey
    private int id_reservation;
    private EtatReservation etatReservation;
    private int id_etat_reservation;
    private Date date_reservation;
    private Date date_annulation;
    private Utilisateur client;
    private int id_client;

    public Reservation() {
    }

    public Reservation(EtatReservation etatReservation, Date date_reservation, Utilisateur client) {
        this.etatReservation = etatReservation;
        this.id_etat_reservation = etatReservation.getId_etat_reservation();
        this.date_reservation = date_reservation;
        this.client = client;
        this.id_client = client.getId_utilisateur();
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public EtatReservation getEtatReservation() {
        return etatReservation;
    }

    public void setEtatReservation(EtatReservation etatReservation) {
        this.etatReservation = etatReservation;
    }

    public int getId_etat_reservation() {
        return id_etat_reservation;
    }

    public void setId_etat_reservation(int id_etat_reservation) {
        this.id_etat_reservation = id_etat_reservation;
    }

    public Date getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(Date date_reservation) {
        this.date_reservation = date_reservation;
    }

    public Date getDate_annulation() {
        return date_annulation;
    }

    public void setDate_annulation(Date date_annulation) {
        this.date_annulation = date_annulation;
    }

    public Utilisateur getClient() {
        return client;
    }

    public void setClient(Utilisateur client) {
        this.client = client;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }
}
