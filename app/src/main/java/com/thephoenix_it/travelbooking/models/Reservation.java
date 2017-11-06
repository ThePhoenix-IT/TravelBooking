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
    private Date date_reservation;
    private Date date_annulation;
}
