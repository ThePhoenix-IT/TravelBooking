package com.thephoenix_it.travelbooking.views.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thephoenix_it.travelbooking.LoginActivity;
import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.models.Vol;
import com.thephoenix_it.travelbooking.repositories.IAgenceRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;
import com.thephoenix_it.travelbooking.views.agence.CreerVolActivity;

import java.text.SimpleDateFormat;

public class VolViewActivity extends AppCompatActivity {

    private IAgenceRepository agenceServices = new SQLiteTravelBookingRepository(this);
    private Vol vol;
    private TextView txtNomAgence, txtNumVol, txtDepart, txtDestination, txtDateDep, txtDateArr, txtPrix, txtNbrPlaces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vol_view);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                vol = null;
            } else {
                vol = agenceServices.findOneVolById(extras.getInt("id_vol"));
            }
        } else {
            vol = agenceServices.findOneVolById(savedInstanceState.getInt("id_vol"));
        }
        txtNomAgence = findViewById(R.id.nom_agence);
        txtNumVol = findViewById(R.id.num_vol);
        txtDepart = findViewById(R.id.vol_depart);
        txtDestination = findViewById(R.id.vol_destination);
        txtDateDep = findViewById(R.id.date_depart);
        txtDateArr = findViewById(R.id.date_arrivee);
        txtPrix = findViewById(R.id.vol_prix);
        txtNbrPlaces = findViewById(R.id.nbr_places);
        if(vol != null) {
            txtNomAgence.setText("Agence: " + vol.getAgence().getNom_utilisateur());
            txtNumVol.setText("Num Vol: " + vol.getNum_vol());
            txtDepart.setText("Depart: " + vol.getDepart());
            txtDestination.setText("Destination: " + vol.getDestination());
            txtDateDep.setText("Date Depart: " + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(vol.getDate_depart()));
            txtDateArr.setText("Date Arrivee: " + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(vol.getDate_arrivee()));
            txtPrix.setText("Prix: " + vol.getPrix());
            txtNbrPlaces.setText("Nbr Places: " + vol.getNbr_places());
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button btn_reserv_vol = findViewById(R.id.btn_reserv_vol);
        btn_reserv_vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.connectedUser == null) {
                    Intent mainIntent = new Intent(VolViewActivity.this, LoginActivity.class);
                    VolViewActivity.this.startActivity(mainIntent);
                    VolViewActivity.this.finish();

                } else {
                    volReservation();
                }
            }
        });
        if(LoginActivity.connectedUser == null) { 
            btn_reserv_vol.setVisibility(View.VISIBLE);
        }
    }

    private void volReservation() {
    }

}
