package com.thephoenix_it.travelbooking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.thephoenix_it.travelbooking.views.agence.ListVolActivity;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
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
        Button btnCompte = findViewById(R.id.btn_compte);
        btnCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(StartScreenActivity.this, LoginActivity.class);
                /*mainIntent.putExtra("adminServices", adminServices);
                mainIntent.putExtra("agenceServices", agenceServices);
                mainIntent.putExtra("clientServices", clientServices);
                mainIntent.putExtra("visiteurServices", visiteurServices);*/
                StartScreenActivity.this.startActivity(mainIntent);
                StartScreenActivity.this.finish();
            }
        });
        Button btnVisiteur = findViewById(R.id.btn_visiteur);
        btnCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(StartScreenActivity.this, ListVolActivity.class);
                /*mainIntent.putExtra("adminServices", adminServices);
                mainIntent.putExtra("agenceServices", agenceServices);
                mainIntent.putExtra("clientServices", clientServices);
                mainIntent.putExtra("visiteurServices", visiteurServices);*/
                StartScreenActivity.this.startActivity(mainIntent);
                StartScreenActivity.this.finish();
            }
        });
    }

}
