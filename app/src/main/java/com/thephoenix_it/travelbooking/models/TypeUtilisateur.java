package com.thephoenix_it.travelbooking.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 17/11/04.
 */

public class TypeUtilisateur extends RealmObject {
    @PrimaryKey
    private int id_type_utilisateur;
    private String desc_type_utilisateur;

    public TypeUtilisateur(String desc_type_utilisateur) {
        this.desc_type_utilisateur = desc_type_utilisateur;
    }

    public int getId_type_utilisateur() {
        return id_type_utilisateur;
    }

    public void setId_type_utilisateur(int id_type_utilisateur) {
        this.id_type_utilisateur = id_type_utilisateur;
    }

    public String getDesc_type_utilisateur() {
        return desc_type_utilisateur;
    }

    public void setDesc_type_utilisateur(String desc_type_utilisateur) {
        this.desc_type_utilisateur = desc_type_utilisateur;
    }

    @Override
    public String toString() {
        return desc_type_utilisateur.toUpperCase();
    }
}
