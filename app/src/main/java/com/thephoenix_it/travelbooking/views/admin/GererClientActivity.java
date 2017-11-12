package com.thephoenix_it.travelbooking.views.admin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.repositories.IAdminRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;

public class GererClientActivity extends AppCompatActivity {

    private Utilisateur utilisateur;
    private IAdminRepository adminServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_client);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                adminServices= new SQLiteTravelBookingRepository(this);
                utilisateur = null;
            } else {
                adminServices = (IAdminRepository) extras.get("adminServices");
                if(adminServices != null)
                utilisateur = adminServices.findOneUtilisateurById((Integer) extras.get("id_utilisateur"));
            }
        } else {
            utilisateur = (Utilisateur) savedInstanceState.getSerializable("utilisateur");
            adminServices = (IAdminRepository) savedInstanceState.getSerializable("adminServices");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
