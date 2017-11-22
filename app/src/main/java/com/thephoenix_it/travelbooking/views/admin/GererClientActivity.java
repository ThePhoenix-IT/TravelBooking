package com.thephoenix_it.travelbooking.views.admin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.repositories.IAdminRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;

public class GererClientActivity extends AppCompatActivity {

    private Utilisateur utilisateur;
    private IAdminRepository adminServices = new SQLiteTravelBookingRepository(this);
    private EditText user_name1, user_name2, login, password, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_client);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                utilisateur = null;
            } else {
                utilisateur = adminServices.findOneUtilisateurById((extras.getInt("id_utilisateur")));
            }
        } else {
            utilisateur = adminServices.findOneUtilisateurById(savedInstanceState.getInt("id_utilisateur"));
        }
        user_name1 = findViewById(R.id.user_name1);
        user_name2 = findViewById(R.id.user_name2);
        login = findViewById(R.id.user_login);
        password = findViewById(R.id.user_pass);
        email = findViewById(R.id.user_email);
        if(utilisateur != null){
            if(utilisateur.getTypeUtilisateur().toString().equals("AGENCE"))
                user_name2.setVisibility(View.INVISIBLE);
            user_name1.setText(utilisateur.getNom_utilisateur());
            user_name2.setText(utilisateur.getPrenom_utilisateur());
            if(utilisateur.getCompte() != null) {
                login.setText(utilisateur.getCompte().getLogin());
                password.setText(utilisateur.getCompte().getPassword());
            }
            email.setText(utilisateur.getEmail_utilisateur());
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
