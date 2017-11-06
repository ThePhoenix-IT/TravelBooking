package com.thephoenix_it.travelbooking.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 17/11/05.
 */

public class Vol extends RealmObject {
    @PrimaryKey
    private int id_vol;
    private int num_vol;
    private Double duree;
    private String destination;
    private Double prix;
    private Date date_creation;
    private Boolean desponible;


}
