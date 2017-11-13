package com.thephoenix_it.travelbooking.views.client;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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
    private EditText txtNumVol, txtDestination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_reserv);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                vol = null;
            } else {
                vol = clientServices.findOneVolById(extras.getInt("id_vol"));
            }
        } else {
            vol = clientServices.findOneVolById(savedInstanceState.getInt("id_vol"));
        }
        txtNumVol = findViewById(R.id.num_vol);
        txtDestination = findViewById(R.id.vol_destination);
        if(vol != null){

            txtNumVol.setText("" + vol.getNum_vol());
            txtDestination.setText(vol.getDestination());
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
                createReservation();
                return true;
            case R.id.delete_vol:
                deleteReservation(reservation.getId_reservation());
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
            clientServices.creer_reservation(reservation);
        }
    }

}
