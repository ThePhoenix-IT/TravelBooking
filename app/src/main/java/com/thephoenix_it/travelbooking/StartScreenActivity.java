package com.thephoenix_it.travelbooking;

import android.content.Intent;
import android.os.Bundle;
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
        getSupportActionBar().hide();
        Button btnCompte = findViewById(R.id.btn_compte);
        btnCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(StartScreenActivity.this, LoginActivity.class);
                StartScreenActivity.this.startActivity(mainIntent);
                StartScreenActivity.this.finish();
            }
        });
        Button btnVisiteur = findViewById(R.id.btn_visiteur);
        btnVisiteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(StartScreenActivity.this, ListVolActivity.class);
                StartScreenActivity.this.startActivity(mainIntent);
            }
        });
    }

}
