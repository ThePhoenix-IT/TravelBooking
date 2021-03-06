package com.thephoenix_it.travelbooking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.repositories.IVisiteurRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;

import java.util.Date;

public class RegistrationActivity extends AppCompatActivity {
    private IVisiteurRepository service = new SQLiteTravelBookingRepository(this);
    private LinearLayout client, agence;

    private Utilisateur user;

    private TextView txtClientLogin;
    private TextView txtClientPassword1;
    private TextView txtClientPassword2;
    private TextView txtClientNom;
    private TextView txtClientPrenom;
    private TextView txtClientEmail;

    private TextView txtAgenceLogin;
    private TextView txtAgencePassword1;
    private TextView txtAgencePassword2;
    private TextView txtAgenceNom;
    private TextView txtAgencePays;
    private TextView txtAgenceEmail;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    agence.setVisibility(View.INVISIBLE);
                    client.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    client.setVisibility(View.INVISIBLE);
                    agence.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        client = findViewById(R.id.client_layout);
        //Client UI Filed
        txtClientLogin = findViewById(R.id.client_login);
        txtClientPassword1 = findViewById(R.id.client_password1);
        txtClientPassword2 = findViewById(R.id.client_password2);
        txtClientNom = findViewById(R.id.client_nom);
        txtClientPrenom = findViewById(R.id.client_prenom);
        txtClientEmail = findViewById(R.id.client_email);
        Button btnValider1 = findViewById(R.id.button);
        btnValider1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((txtClientPassword1.getText().toString().equals(txtClientPassword2.getText().toString()))&&(txtClientPassword1.length()>4)) {
                    user = new Utilisateur(txtClientNom.getText().toString(), txtClientPrenom.getText().toString(), null,
                            txtClientEmail.getText().toString(), 9999, new Date(), service.findOneTypeUtilisateurByDesc("Client"));
                    user.setCompte(new Compte(txtClientLogin.getText().toString(), txtClientPassword2.getText().toString(), true, user));
                    service.creation_compte(user);
                    Toast.makeText(RegistrationActivity.this, "Compte cree avec succes.", Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                    Toast.makeText(RegistrationActivity.this, "Vérifier votre mot de passe.", Toast.LENGTH_LONG).show();
            }
        });
        agence = findViewById(R.id.agence_layout);
        //Agence UI Filed
        txtAgenceLogin = findViewById(R.id.agence_login);
        txtAgencePassword1 = findViewById(R.id.agence_password1);
        txtAgencePassword2 = findViewById(R.id.agence_password2);
        txtAgenceNom = findViewById(R.id.agence_nom);
        txtAgencePays = findViewById(R.id.agence_pays);
        txtAgenceEmail = findViewById(R.id.agence_email);
        Button btnValider2 = findViewById(R.id.agence_button);
        btnValider2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtAgencePassword1.getText().toString().equals(txtAgencePassword2.getText().toString())&&txtAgencePassword2.length()>4) {
                user = new Utilisateur(txtAgenceNom.getText().toString(), null, txtAgencePays.getText().toString(),
                        txtAgenceEmail.getText().toString(), 9999, new Date(), service.findOneTypeUtilisateurByDesc("Agence"));
                user.setCompte(new Compte(txtAgenceLogin.getText().toString(), txtAgencePassword2.getText().toString(), true, user));
                service.creation_compte(user);
                    Toast.makeText(RegistrationActivity.this, "Compte cree avec succes.", Toast.LENGTH_LONG).show();
                finish();
                }
                else
                    Toast.makeText(RegistrationActivity.this, "Vérifier votre mot de passe.", Toast.LENGTH_LONG).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
