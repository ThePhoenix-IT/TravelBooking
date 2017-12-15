package com.thephoenix_it.travelbooking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.EtatReservation;
import com.thephoenix_it.travelbooking.models.TypeUtilisateur;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.repositories.IAdminRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;

import java.util.Date;

public class SplashScreen extends AppCompatActivity {

    private IAdminRepository adminServices = new SQLiteTravelBookingRepository(this);
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if(adminServices.findAllTypeUtilisateur().size() == 0){
                    adminServices.createTypeUtilisateur(new TypeUtilisateur("Admin"));
                    adminServices.createTypeUtilisateur(new TypeUtilisateur("Client"));
                    adminServices.createTypeUtilisateur(new TypeUtilisateur("Agence"));
                }
                if(adminServices.findAllEtatReservation().size() == 0){
                    adminServices.createEtatReservation(new EtatReservation("Annuler"));
                    adminServices.createEtatReservation(new EtatReservation("Encours"));
                    adminServices.createEtatReservation(new EtatReservation("Confirmer"));
                }
                System.err.println(adminServices.findAllEtatReservation().size());
                if(adminServices.findCompteAdmin() == null) {
                    Utilisateur admin = new Utilisateur("Admin", "Admin", null, "Admin@Admin.com",
                                0, new Date(), adminServices.findOneTypeUtilisateurByDesc("Admin"));
                    admin.setCompte(new Compte("admin", "12345", true, admin));
                    adminServices.createCompteAdmin(admin);
                }
                Intent mainIntent = new Intent(SplashScreen.this, StartScreenActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
