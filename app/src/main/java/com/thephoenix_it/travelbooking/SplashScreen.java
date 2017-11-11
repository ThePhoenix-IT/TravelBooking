package com.thephoenix_it.travelbooking;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.TypeUtilisateur;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.repositories.IAdminRepository;
import com.thephoenix_it.travelbooking.repositories.IAgenceRepository;
import com.thephoenix_it.travelbooking.repositories.IClientRepository;
import com.thephoenix_it.travelbooking.repositories.IVisiteurRepository;
import com.thephoenix_it.travelbooking.repositories.RealmFactory;
import com.thephoenix_it.travelbooking.repositories.TravelBookingRepository;

import java.util.Date;

public class SplashScreen extends AppCompatActivity {

    private IAdminRepository adminServices = new TravelBookingRepository(RealmFactory.with(this.getApplication()));
    private IAgenceRepository agenceServices = new TravelBookingRepository(RealmFactory.with(this.getApplication()));
    private IClientRepository clientServices = new TravelBookingRepository(RealmFactory.with(this.getApplication()));
    private IVisiteurRepository visiteurServices = new TravelBookingRepository(RealmFactory.with(this.getApplication()));
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
                if(adminServices.findCompteAdmin() == null) {
                    Utilisateur admin = new Utilisateur("Admin", "Admin", "Admin@Admin.com",
                            0, new Date(), adminServices.findOneTypeUtilisateurByDesc("Admin"));
                    admin.setCompte(new Compte("admin", "12345", true, admin));
                    adminServices.createCompteAdmin(admin);
                }
                for(Utilisateur u : adminServices.listUtilisateur()){
                    System.err.println(u);
                }
                Intent mainIntent = new Intent(SplashScreen.this, StartScreenActivity.class);
                mainIntent.putExtra("adminServices", adminServices);
                mainIntent.putExtra("agenceServices", agenceServices);
                mainIntent.putExtra("clientServices", clientServices);
                mainIntent.putExtra("visiteurServices", visiteurServices);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
