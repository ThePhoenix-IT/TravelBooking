package com.thephoenix_it.travelbooking.models;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 17/11/05.
 */

public class Vol extends RealmObject implements Serializable {
    @PrimaryKey
    private int id_vol;
    private int num_vol;
    private int nbr_places;
    private Double duree;
    private String depart;
    private String destination;
    private Date date_depart;
    private Date date_arrivee;
    private Double prix;
    private Date date_creation;
    private Boolean disponible;
    private Utilisateur agence;
    private int id_agence;

    public Vol() {
    }

    public Vol(int num_vol, int nbr_places, Double duree, String depart, String destination,
               Date date_depart, Date date_arrivee, Double prix, Date date_creation, Boolean disponible, Utilisateur agence) {
        this.num_vol = num_vol;
        this.nbr_places = nbr_places;
        this.duree = duree;
        this.depart = depart;
        this.destination = destination;
        this.date_depart = date_depart;
        this.date_arrivee = date_arrivee;
        this.prix = prix;
        this.date_creation = date_creation;
        this.disponible = disponible;
        this.agence = agence;
        this.id_agence = agence.getId_type_utilisateur();
    }

    public int getId_vol() {
        return id_vol;
    }

    public void setId_vol(int id_vol) {
        this.id_vol = id_vol;
    }

    public int getNum_vol() {
        return num_vol;
    }

    public void setNum_vol(int num_vol) {
        this.num_vol = num_vol;
    }

    public Double getDuree() {
        return duree;
    }

    public void setDuree(Double duree) {
        this.duree = duree;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Utilisateur getAgence() {
        return agence;
    }

    public void setAgence(Utilisateur agence) {
        this.agence = agence;
    }

    public int getId_agence() {
        return id_agence;
    }

    public void setId_agence(int id_agence) {
        this.id_agence = id_agence;
    }

    public int getNbr_places() {
        return nbr_places;
    }

    public void setNbr_places(int nbr_places) {
        this.nbr_places = nbr_places;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public Date getDate_depart() {
        return date_depart;
    }

    public void setDate_depart(Date date_depart) {
        this.date_depart = date_depart;
    }

    public Date getDate_arrivee() {
        return date_arrivee;
    }

    public void setDate_arrivee(Date date_arrivee) {
        this.date_arrivee = date_arrivee;
    }

    @Override
    public String toString() {
        return "Vol{" +
                "id_vol=" + id_vol +
                ", num_vol=" + num_vol +
                ", nbr_places=" + nbr_places +
                ", duree=" + duree +
                ", depart='" + depart + '\'' +
                ", destination='" + destination + '\'' +
                ", date_depart=" + date_depart +
                ", date_arrivee=" + date_arrivee +
                ", prix=" + prix +
                ", date_creation=" + date_creation +
                ", disponible=" + disponible +
                ", agence=" + agence +
                ", id_agence=" + id_agence +
                '}';
    }
}
