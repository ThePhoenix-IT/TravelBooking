package com.thephoenix_it.travelbooking.repositories;

import android.content.Context;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.EtatReservation;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.TypeUtilisateur;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hamzajguerim on 2017-11-12.
 */

public class SQLiteTravelBookingRepository implements IAdminRepository, IAgenceRepository, IClientRepository, IVisiteurRepository, Serializable {

    private DatabaseDAO dao;

    public SQLiteTravelBookingRepository(Context context) {
        dao = new DatabaseDAO(context);
    }

    @Override
    public Reservation creer_reservation(Reservation reservation) {
        return dao.create_res(reservation);
    }

    @Override
    public Reservation update_reservation(Reservation reservation) {
        return null;
    }

    @Override
    public Reservation findOneReservationById(int id_reservation) {
        return dao.findOneReservationById(id_reservation);
    }

    @Override
    public EtatReservation findOneEtatReservationByDesc(String etat_reservation) {
        return dao.findOneEtatReservationByDesc(etat_reservation);
    }

    @Override
    public Boolean delete_reservation(int id_reservation) {
        return null;
    }

    @Override
    public Utilisateur creation_compte(Utilisateur utilisateur) {
        return dao.create_user(utilisateur);
    }

    @Override
    public List<Vol> listVolByDestination(String destination) {
        return null;
    }

    @Override
    public Compte loginCompte(String login, String password) {
        return null;
    }

    @Override
    public Vol creer_vol(Vol vol) {
        return dao.create_vol(vol);
    }

    @Override
    public Vol update_vol(Vol vol) {
        return null;
    }

    @Override
    public Vol findOneVolById(int id_vol) {

        return dao.findOneVolByIdVol(id_vol);
    }

    @Override
    public Boolean delete_vol(int id_vol) {
        return null;
    }

    @Override
    public List<Vol> listVol() {
        List<Vol> result = new ArrayList<>();
        try {
            result = dao.findAllVol();
        } catch (Exception ex) {
            System.err.println("listVol: " + ex);
        }
        return result;
    }

    @Override
    public List<Vol> listVolFiltered(Map filter) {
        return dao.listVolFiltered(filter);
    }

    @Override
    public List<Reservation> findAllReservation() {
        return dao.findAllReservation();
    }

    @Override
    public Compte activation_compte(Compte compte) {
        return null;
    }

    @Override
    public Compte getCompteByIdUtilisateur(int id_utilisateur) {
        return null;
    }

    @Override
    public TypeUtilisateur createTypeUtilisateur(TypeUtilisateur typeUtilisateur) {
        return dao.create_user_type(typeUtilisateur);
    }

    @Override
    public TypeUtilisateur updateTypeUtilisateur(TypeUtilisateur typeUtilisateur) {
        return null;
    }

    @Override
    public Boolean deleteTypeUtilisateur(int id_type_utilisateur) {
        return null;
    }

    @Override
    public List<TypeUtilisateur> findAllTypeUtilisateur() {
        List<TypeUtilisateur> result = new ArrayList<>();
        try {
            result = dao.findAllTypeUtilisateur();
        } catch (Exception ex) {
            System.err.println("findAllTypeUtilisateur" + ex);
        }
        return result;
    }

    @Override
    public TypeUtilisateur findOneTypeUtilisateurByDesc(String desc_type_utilisateur) {
        return dao.findOneTypeUtilisateurByDesc(desc_type_utilisateur);
    }

    @Override
    public Utilisateur createCompteAdmin(Utilisateur utilisateur) {
        return dao.createCompteAdmin(utilisateur);
    }

    @Override
    public Utilisateur findCompteAdmin() {
        return dao.findCompteAdmin();
    }

    @Override
    public Utilisateur findOneUtilisateurById(int id_utilisateur) {
        return dao.findOneUtilisateurById(id_utilisateur);
    }

    @Override
    public List<Utilisateur> listUtilisateur() {
        return dao.listUtilisateurNotAdmin();
    }

    @Override
    public List<Reservation> listReservation() {
        return dao.findAllReservation();
    }

    @Override
    public EtatReservation createEtatReservation(EtatReservation etatReservation) {
        dao.create_Stat(etatReservation);
        return etatReservation;
    }

    @Override
    public List<EtatReservation> findAllEtatReservation() {
        return dao.findAllEtatReservation();
    }

    @Override
    public Utilisateur login(String login, String password) {
        return dao.login(login, password);
    }
}
