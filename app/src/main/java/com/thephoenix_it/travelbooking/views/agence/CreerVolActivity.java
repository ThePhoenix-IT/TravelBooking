package com.thephoenix_it.travelbooking.views.agence;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thephoenix_it.travelbooking.LoginActivity;
import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.RegistrationActivity;
import com.thephoenix_it.travelbooking.models.Vol;
import com.thephoenix_it.travelbooking.repositories.IAgenceRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;

import java.util.Date;

public class CreerVolActivity extends AppCompatActivity {
    private IAgenceRepository agenceServices = new SQLiteTravelBookingRepository(this);
    private Vol vol;
    private EditText txtNumVol, txtDepart, txtDestination, txtDateDep, txtDateArr, txtPrix, txtNbrPlaces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_vol);
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
        txtNumVol = findViewById(R.id.num_vol);
        txtDepart = findViewById(R.id.vol_depart);
        txtDestination = findViewById(R.id.vol_destination);
        if(vol != null){

            txtNumVol.setText("" + vol.getNum_vol());
            txtDestination.setText(vol.getDestination());
        }
        Button btn_reserv_vol = findViewById(R.id.btn_reserv_vol);
        btn_reserv_vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.connectedUser == null) {
                    Intent mainIntent = new Intent(CreerVolActivity.this, LoginActivity.class);
                    CreerVolActivity.this.startActivity(mainIntent);
                    CreerVolActivity.this.finish();

                } else {
                    volReservation();
                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(LoginActivity.connectedUser == null) {
            fab.setVisibility(View.INVISIBLE);
            btn_reserv_vol.setVisibility(View.VISIBLE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllEditText();
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void volReservation() {
    }

    private void clearAllEditText() {
        txtNumVol.getEditableText().clear();
        txtDestination.getEditableText().clear();
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(LoginActivity.connectedUser != null)
            getMenuInflater().inflate(R.menu.creer_vol_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save_vol:
                if(vol == null)
                    createVol();
                else
                    updateVol();
                return true;
            case R.id.delete_vol:
                deleteVol(vol.getId_vol());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateVol() {
    }

    private void deleteVol(int id_vol) {
    }

    private void createVol() {
        Vol vol = new Vol();
        vol.setNum_vol(Integer.parseInt(txtNumVol.getText().toString()));
        vol.setDepart(txtDepart.getText().toString());
        vol.setDestination(txtDestination.getText().toString());
        vol.setDate_creation(new Date());
        vol.setAgence(LoginActivity.connectedUser);
        if(agenceServices.creer_vol(vol).getId_vol() > 0)
            Toast.makeText(CreerVolActivity.this, "Vol cree avec succes.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(CreerVolActivity.this, "Erreur creation vol.", Toast.LENGTH_LONG).show();

    }
}
