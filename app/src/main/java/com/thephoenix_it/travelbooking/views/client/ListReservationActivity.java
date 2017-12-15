package com.thephoenix_it.travelbooking.views.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thephoenix_it.travelbooking.LoginActivity;
import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.repositories.IAgenceRepository;
import com.thephoenix_it.travelbooking.repositories.IClientRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;

import java.util.List;

public class ListReservationActivity extends AppCompatActivity {

    private IClientRepository service = new SQLiteTravelBookingRepository(this);
    private IAgenceRepository agenceService = new SQLiteTravelBookingRepository(this);
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservation);
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
        listView = (ListView) findViewById(R.id.listReservView);
        final List<Reservation> reservationList;
        if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("CLIENT"))
            reservationList = service.findAllReservationByIdClient(LoginActivity.connectedUser.getId_utilisateur());
        else {
            fab.setVisibility(View.INVISIBLE);
            reservationList = agenceService.findAllReservationByIdAgence(LoginActivity.connectedUser.getId_utilisateur());
        }
        CustomReservListAdapter whatever = new CustomReservListAdapter(this, reservationList);
        listView.setAdapter(whatever);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mainIntent = new Intent(ListReservationActivity.this, CreerReservActivity.class);
                mainIntent.putExtra("id_reservation", reservationList.get(position).getId_reservation());
                mainIntent.putExtra("id_vol", reservationList.get(position).getVol().getId_vol());
                ListReservationActivity.this.startActivity(mainIntent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final List<Reservation> reservationList;
        if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("CLIENT"))
            reservationList = service.findAllReservationByIdClient(LoginActivity.connectedUser.getId_utilisateur());
        else
            reservationList = agenceService.findAllReservationByIdAgence(LoginActivity.connectedUser.getId_utilisateur());
        CustomReservListAdapter whatever = new CustomReservListAdapter(this, reservationList);
        listView.setAdapter(whatever);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mainIntent = new Intent(ListReservationActivity.this, CreerReservActivity.class);
                mainIntent.putExtra("id_reservation", reservationList.get(position).getId_reservation());
                mainIntent.putExtra("id_vol", reservationList.get(position).getVol().getId_vol());
                ListReservationActivity.this.startActivity(mainIntent);
            }
        });
    }


}
