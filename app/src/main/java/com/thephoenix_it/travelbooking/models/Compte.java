package com.thephoenix_it.travelbooking.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 17/11/04.
 */

public class Compte extends RealmObject {
    @PrimaryKey
    private int id_compte;
    private String login;
    private String password;
}
