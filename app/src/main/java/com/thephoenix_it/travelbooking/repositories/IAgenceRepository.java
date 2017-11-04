package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Utilisateur;

/**
 * Created by root on 17/11/04.
 */

public interface IAgenceRepository {

    Compte creation_compte(Compte compte);
}
