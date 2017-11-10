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
    private Double duree;
    private String destination;
    private Double prix;
    private Date date_creation;
    private Boolean desponible;
    private Utilisateur agence;
    private int id_agence;

    public Vol() {
    }

    public Vol(int num_vol, Double duree, String destination, Double prix, Date date_creation, Boolean desponible, Utilisateur agence) {
        this.num_vol = num_vol;
        this.duree = duree;
        this.destination = destination;
        this.prix = prix;
        this.date_creation = date_creation;
        this.desponible = desponible;
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

    public Boolean getDesponible() {
        return desponible;
    }

    public void setDesponible(Boolean desponible) {
        this.desponible = desponible;
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
}
