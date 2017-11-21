package com.thephoenix_it.travelbooking.views.client;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thephoenix_it.travelbooking.LoginActivity;
import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.Vol;
import com.thephoenix_it.travelbooking.repositories.IClientRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;

public class CreerReservActivity extends AppCompatActivity {

    private IClientRepository clientServices = new SQLiteTravelBookingRepository(this);
    private Reservation reservation;
    private Vol vol;
    private TextView txtClient, txtNumVol, txtDepart, txtDestination, txtDateDep, txtDateArr,
            etatReserv, txtPrix, txtNbrPlaces, txtEtatReserv;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_reserv);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                vol = null;
            } else {
                reservation = clientServices.findOneReservationById(extras.getInt("id_vol"));
                if(reservation != null)
                    vol = reservation.getVol();
                else
                    vol = clientServices.findOneVolById(extras.getInt("id_vol"));
            }
        } else {
            reservation = clientServices.findOneReservationById(savedInstanceState.getInt("id_vol"));
            if(reservation != null)
                vol = reservation.getVol();
            else
                vol = clientServices.findOneVolById(savedInstanceState.getInt("id_vol"));
        }
        txtClient = findViewById(R.id.client);
        txtNumVol = findViewById(R.id.num_vol);
        txtDepart = findViewById(R.id.vol_depart);
        txtDestination = findViewById(R.id.vol_destination);
        txtDateDep = findViewById(R.id.date_depart);
        txtDateArr = findViewById(R.id.date_arrivee);
        txtPrix = findViewById(R.id.vol_prix);
        txtNbrPlaces = findViewById(R.id.nbr_places);
        txtEtatReserv = findViewById(R.id.txtEtatReserv);
        etatReserv = findViewById(R.id.etatReserv);

        radioGroup = findViewById(R.id.etatReservRG);
        if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("AGENCE")) {
            radioGroup.setVisibility(View.VISIBLE);
            txtClient.setVisibility(View.VISIBLE);
            etatReserv.setVisibility(View.VISIBLE);
            txtEtatReserv.setVisibility(View.INVISIBLE);
        }
        if(reservation != null)
            etatReserv.setVisibility(View.VISIBLE);

        if(vol != null){
            if(reservation != null) {
                txtClient.setText("Client: " + reservation.getClient().getNom_utilisateur() + " " + reservation.getClient().getPrenom_utilisateur());
                txtEtatReserv.setText(" " + reservation.getEtatReservation().getDesc_etat());
            }
            else
                txtClient.setText("Agence: " + vol.getAgence().getNom_utilisateur());
            txtNumVol.setText("Num Vol: " + vol.getNum_vol());
            txtDepart.setText("Depart: " + vol.getDepart());
            txtDestination.setText("Destination: " + vol.getDestination());
            txtDateDep.setText("Date Depart: " + vol.getDate_depart());
            txtDateArr.setText("Date Arrivee: " + vol.getDate_arrivee());
            txtPrix.setText("Prix: " + vol.getPrix());
            txtNbrPlaces.setText("Nbr Places: " + vol.getNbr_places());
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllEditText();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void clearAllEditText() {
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.creer_vol_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save_vol:
                System.err.println(reservation);
                if(reservation == null)
                    createReservation();
                else
                    updateReservation();
                return true;
            case R.id.delete_vol:
                deleteReservation(reservation.getId_reservation());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateReservation() {
        if(reservation != null) {
            reservation.setEtatReservation(radioGroup.getCheckedRadioButtonId() == R.id.encours ?
                    clientServices.findOneEtatReservationByDesc("Encours") : radioGroup.getCheckedRadioButtonId() == R.id.confirmer ?
                    clientServices.findOneEtatReservationByDesc("Confirmer") : clientServices.findOneEtatReservationByDesc("Annuler"));
            if(clientServices.update_reservation(reservation).getId_reservation() > 0)
                Toast.makeText(CreerReservActivity.this, "Reservation modifier avec succes.", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(CreerReservActivity.this, "Erreur modification Reservation.", Toast.LENGTH_LONG).show();
        }
    }

    private void deleteReservation(int id_reservation) {
    }

    private void createReservation() {
        if(vol != null) {
            reservation = new Reservation();
            reservation.setVol(vol);
            reservation.setClient(LoginActivity.connectedUser);
            reservation.setEtatReservation(clientServices.findOneEtatReservationByDesc("Encours"));
            if(clientServices.creer_reservation(reservation).getId_reservation() > 0)
                Toast.makeText(CreerReservActivity.this, "Reservation cree avec succes.", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(CreerReservActivity.this, "Erreur creation Reservation.", Toast.LENGTH_LONG).show();
        }
    }

}
