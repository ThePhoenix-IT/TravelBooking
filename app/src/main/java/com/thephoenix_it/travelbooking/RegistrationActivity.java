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

import com.thephoenix_it.travelbooking.models.Compte;
import com.thephoenix_it.travelbooking.models.TypeUtilisateur;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.repositories.IClientRepository;
import com.thephoenix_it.travelbooking.repositories.IVisiteurRepository;
import com.thephoenix_it.travelbooking.repositories.RealmFactory;
import com.thephoenix_it.travelbooking.repositories.TravelBookingRepository;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class RegistrationActivity extends AppCompatActivity {
    private IVisiteurRepository service = new TravelBookingRepository(RealmFactory.with(RegistrationActivity.this));
    LinearLayout client, agence;
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

    Utilisateur user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        client = findViewById(R.id.client_layout);
        agence = findViewById(R.id.agence_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Button btnValider1 = findViewById(R.id.button);
        btnValider1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = new Utilisateur("Test", "Test", "Test",
                        9999, new Date(), new TypeUtilisateur("Client"));
                user.setCompte(new Compte("TestLogin", "12345", true, user));
                //new TravelBookingRepository(RealmFactory.with(RegistrationActivity.this)).creation_compte(user);
                service.creation_compte(user);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
