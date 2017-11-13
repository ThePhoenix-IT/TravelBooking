package com.thephoenix_it.travelbooking.views.agence;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.models.Vol;
import com.thephoenix_it.travelbooking.repositories.IAgenceRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;

public class CreerVolActivity extends AppCompatActivity {
    private IAgenceRepository agenceServices = new SQLiteTravelBookingRepository(this);
    private Vol vol;
    private TextView txtNumVol, txtDestination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_vol);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                vol = null;
            } else {
                vol = agenceServices.findOneVolById((Integer) extras.get("id_vol"));
            }
        } else {
        }
        txtNumVol = findViewById(R.id.num_vol);
        txtDestination = findViewById(R.id.vol_destination);
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
                createVol();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createVol() {
        Vol vol = new Vol();
        vol.setNum_vol(Integer.parseInt(txtNumVol.getText().toString()));
        vol.setDestination(txtDestination.getText().toString());
        agenceServices.creer_vol(vol);
    }
}
