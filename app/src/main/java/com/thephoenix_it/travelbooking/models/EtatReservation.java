package com.thephoenix_it.travelbooking.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 17/11/05.
 */

public class EtatReservation extends RealmObject {
    @PrimaryKey
    private int id_etat_reservation;
}
