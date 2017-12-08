package com.thephoenix_it.travelbooking.views.agence;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.thephoenix_it.travelbooking.LoginActivity;
import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.models.Vol;
import com.thephoenix_it.travelbooking.repositories.IAgenceRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        txtDateDep = findViewById(R.id.date_depart);
        txtDateArr = findViewById(R.id.date_arrivee);
        txtPrix = findViewById(R.id.vol_prix);
        txtNbrPlaces = findViewById(R.id.nbr_places);
        txtDateDep.setFocusable(false);
        txtDateArr.setFocusable(false);
        txtDateDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v, txtDateDep);
                showTruitonDatePickerDialog(v, txtDateDep);
            }
        });
        txtDateArr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonTimePickerDialog(v, txtDateArr);
                showTruitonDatePickerDialog(v, txtDateArr);
            }
        });
        if(vol != null) {
            txtNumVol.setText("" + vol.getNum_vol());
            txtDepart.setText(vol.getDepart());
            txtDestination.setText(vol.getDestination());
            if(vol.getDate_depart() != null)
                txtDateDep.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(vol.getDate_depart()));
            if(vol.getDate_arrivee() != null)
                txtDateArr.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(vol.getDate_arrivee()));
            txtPrix.setText("" + vol.getPrix());
            txtNbrPlaces.setText("" + vol.getNbr_places());
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
        vol.setNum_vol(Integer.parseInt(txtNumVol.getText().toString()));
        vol.setDepart(txtDepart.getText().toString());
        vol.setDestination(txtDestination.getText().toString());
        try {
            vol.setDate_depart(new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(txtDateDep.getText().toString()));
            vol.setDate_arrivee(new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(txtDateArr.getText().toString()));
            vol.setDate_creation(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(new Date().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        vol.setNbr_places(Integer.parseInt(txtNbrPlaces.getText().toString()));
        vol.setPrix(Double.valueOf(txtPrix.getText().toString()));
        vol.setAgence(LoginActivity.connectedUser);
        System.err.println(vol);
        if(agenceServices.creer_vol(vol).getId_vol() > 0)
            Toast.makeText(CreerVolActivity.this, "Vol cree avec succes.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(CreerVolActivity.this, "Erreur creation vol.", Toast.LENGTH_LONG).show();

    }
    public void showTruitonTimePickerDialog(View v, EditText txtDateDep) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setEdittext(txtDateDep);
        newFragment.show(this.getFragmentManager(), "timePicker");
    }

    public void showTruitonDatePickerDialog(View v, EditText txtDateDep) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setEdittext(txtDateDep);
        newFragment.show(this.getFragmentManager(), "datePicker");
    }
    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {
        private EditText editText;

        public void setEdittext(EditText editText) {
            this.editText = editText;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            editText.setText(editText.getText() + " " + (hourOfDay > 9 ? hourOfDay : "0" + hourOfDay) + ":"	+ (minute > 9 ? minute : "0" + minute));
        }
    }
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        private EditText editText;
        public void setEdittext(EditText editText) {
            this.editText = editText;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            editText.setText((day > 9 ? day : "0" + day) + "-" + ((month + 1 > 9 ? month + 1 : "0" + month + 1)) + "-" + year);
        }
    }

}
