package com.thephoenix_it.travelbooking.views.client;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thephoenix_it.travelbooking.LoginActivity;
import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.Vol;
import com.thephoenix_it.travelbooking.repositories.IClientRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;

import java.text.SimpleDateFormat;

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
                if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("AGENCE"))
                    reservation = clientServices.findOneReservationById(extras.getInt("id_reservation"));
                else
                    reservation = clientServices.findOneReservationByIdVolAndIdClient(extras.getInt("id_vol"), LoginActivity.connectedUser.getId_utilisateur());
                if(reservation != null)
                    vol = clientServices.findOneVolById(reservation.getVol().getId_vol());
                else
                    vol = clientServices.findOneVolById(extras.getInt("id_vol"));
            }
        } else {
            if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("AGENCE"))
                reservation = clientServices.findOneReservationById(savedInstanceState.getInt("id_reservation"));
            else
                reservation = clientServices.findOneReservationByIdVolAndIdClient(savedInstanceState.getInt("id_vol"), LoginActivity.connectedUser.getId_utilisateur());
            if(reservation != null)
                vol = clientServices.findOneVolById(reservation.getVol().getId_vol());
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

            if(reservation != null) {
                txtClient.setText("Client: " + reservation.getClient().getNom_utilisateur() + " " + reservation.getClient().getPrenom_utilisateur());
                if(reservation.getEtatReservation().getDesc_etat().equals("Confirmer"))
                    ((RadioButton) radioGroup.findViewById(R.id.confirmer)).setChecked(true);
                else if(reservation.getEtatReservation().getDesc_etat().equals("Annuler"))
                    ((RadioButton) radioGroup.findViewById(R.id.annuler)).setChecked(true);
                else
                    ((RadioButton) radioGroup.findViewById(R.id.encours)).setChecked(true);
            }
            txtClient.setVisibility(View.VISIBLE);
            etatReserv.setVisibility(View.VISIBLE);
            txtEtatReserv.setVisibility(View.INVISIBLE);
        } else if(reservation != null) {
            txtClient.setVisibility(View.VISIBLE);
            txtClient.setText("Agence: " + clientServices.findOneVolById(reservation.getVol().getId_vol()).getAgence().getNom_utilisateur());
            txtEtatReserv.setText(" " + reservation.getEtatReservation().getDesc_etat());
            etatReserv.setVisibility(View.VISIBLE);

        }

        if(vol != null){
            txtClient.setVisibility(View.VISIBLE);
            if(reservation == null)
                txtClient.setText("Agence: " + vol.getAgence().getNom_utilisateur());
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
        AlertDialog deleteDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.ic_menu_close)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        deleteReservation(reservation.getId_reservation());
                        dialog.dismiss();
                        finish();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        deleteDialogBox.show();
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.creer_vol_menu, menu);
        if(reservation == null){
            MenuItem item = menu.findItem(R.id.delete_vol);
            item.setEnabled(false);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save_vol:
                if(reservation == null) {
                    if(vol.getNbr_places() - clientServices.findAllReservationByIdVol(vol.getId_vol()).size() > 0)
                        createReservation();
                    else {
                        AlertDialog alertDialogBox =new AlertDialog.Builder(this)
                                //set message, title, and icon
                                .setTitle("Alert")
                                .setMessage("Il n'ya pas des places vide.")
                                .setIcon(R.drawable.ic_menu_close)
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                        alertDialogBox.show();
                    }
                }
                else
                    updateReservation();
                return true;
            case R.id.delete_vol:
                clearAllEditText();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateReservation() {
        System.err.println("=================" + reservation.getId_reservation());
        reservation.setEtatReservation(radioGroup.getCheckedRadioButtonId() == R.id.encours ?
                clientServices.findOneEtatReservationByDesc("Encours") : radioGroup.getCheckedRadioButtonId() == R.id.confirmer ?
                clientServices.findOneEtatReservationByDesc("Confirmer") : clientServices.findOneEtatReservationByDesc("Annuler"));
        if(clientServices.update_reservation(reservation).getId_reservation() > 0)
            Toast.makeText(CreerReservActivity.this, "Reservation modifier avec succes.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(CreerReservActivity.this, "Erreur modification Reservation.", Toast.LENGTH_LONG).show();
    }

    private void deleteReservation(int id_reservation) {
        clientServices.delete_reservation(id_reservation);
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
