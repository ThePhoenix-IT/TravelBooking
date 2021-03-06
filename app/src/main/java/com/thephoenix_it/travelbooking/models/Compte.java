package com.thephoenix_it.travelbooking.models;

import java.io.Serializable;

/**
 * Created by root on 17/11/04.
 */

public class Compte implements Serializable {
    private int id_compte;
    private String login;
    private String password;
    private Boolean etat_compte;
    private Utilisateur utilisateur;
    private int id_utilisateur;

    public Compte() {
    }

    public Compte(String login, String password, Boolean etat_compte, Utilisateur utilisateur) {
        this.login = login;
        this.password = password;
        this.etat_compte = etat_compte;
        this.utilisateur = utilisateur;
        this.id_utilisateur = utilisateur.getId_utilisateur();
    }

    public int getId_compte() {
        return id_compte;
    }

    public void setId_compte(int id_compte) {
        this.id_compte = id_compte;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Boolean getEtat_compte() {
        return etat_compte;
    }

    public void setEtat_compte(Boolean etat_compte) {
        this.etat_compte = etat_compte;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id_compte=" + id_compte +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", etat_compte=" + etat_compte +
                ", id_utilisateur=" + id_utilisateur +
                '}';
    }
}
