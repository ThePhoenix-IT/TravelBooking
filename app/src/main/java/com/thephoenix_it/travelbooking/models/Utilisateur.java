package com.thephoenix_it.travelbooking.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 17/11/04.
 */

public class Utilisateur extends RealmObject {
    @PrimaryKey
    private int id_utilisateur;
    private String nom_utilisateur;
    private String prenom_utilisateur;
    private String email_utilisateur;
    private int cin;
    private Date date_naissance;
}
