package com.thephoenix_it.travelbooking.repositories;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Utilisateur;

/**
 * Created by root on 17/11/04.
 */

public interface IAdminRepository {

    Compte activation_compte(Compte compte);
}
