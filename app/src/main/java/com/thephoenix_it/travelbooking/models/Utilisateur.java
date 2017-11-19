package com.thephoenix_it.travelbooking.models;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 17/11/04.
 */

public class Utilisateur extends RealmObject implements Serializable {
    @PrimaryKey
    private int id_utilisateur;
    private String nom_utilisateur;
    private String prenom_utilisateur;
    private String pays;
    private String email_utilisateur;
    private int cin;
    private Date date_naissance;
    private TypeUtilisateur typeUtilisateur;
    private int id_type_utilisateur;
    private Compte compte;

    public Utilisateur() {
    }

    public Utilisateur(String nom_utilisateur, String prenom_utilisateur, String pays, String email_utilisateur,
                       int cin, Date date_naissance, TypeUtilisateur typeUtilisateur) {
        this.nom_utilisateur = nom_utilisateur;
        this.prenom_utilisateur = prenom_utilisateur;
        this.pays = pays;
        this.email_utilisateur = email_utilisateur;
        this.cin = cin;
        this.date_naissance = date_naissance;
        this.typeUtilisateur = typeUtilisateur;
        this.id_type_utilisateur = typeUtilisateur.getId_type_utilisateur();
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }

    public String getPrenom_utilisateur() {
        return prenom_utilisateur;
    }

    public void setPrenom_utilisateur(String prenom_utilisateur) {
        this.prenom_utilisateur = prenom_utilisateur;
    }

    public String getEmail_utilisateur() {
        return email_utilisateur;
    }

    public void setEmail_utilisateur(String email_utilisateur) {
        this.email_utilisateur = email_utilisateur;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public TypeUtilisateur getTypeUtilisateur() {
        return typeUtilisateur;
    }

    public void setTypeUtilisateur(TypeUtilisateur typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }

    public int getId_type_utilisateur() {
        return id_type_utilisateur;
    }

    public void setId_type_utilisateur(int id_type_utilisateur) {
        this.id_type_utilisateur = id_type_utilisateur;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id_utilisateur=" + id_utilisateur +
                ", nom_utilisateur='" + nom_utilisateur + '\'' +
                ", prenom_utilisateur='" + prenom_utilisateur + '\'' +
                ", email_utilisateur='" + email_utilisateur + '\'' +
                ", cin=" + cin +
                ", date_naissance=" + date_naissance +
                ", typeUtilisateur=" + typeUtilisateur +
                ", compte=" + compte +
                '}';
    }
}
