package com.thephoenix_it.travelbooking.views.agence;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.thephoenix_it.travelbooking.LoginActivity;
import com.thephoenix_it.travelbooking.MainActivity;
import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.StartScreenActivity;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;
import com.thephoenix_it.travelbooking.repositories.IAgenceRepository;
import com.thephoenix_it.travelbooking.repositories.IVisiteurRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;
import com.thephoenix_it.travelbooking.views.client.CreerReservActivity;
import com.thephoenix_it.travelbooking.views.client.ListVolsActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListVolActivity extends AppCompatActivity {
    private IVisiteurRepository service = new SQLiteTravelBookingRepository(this);
    private IAgenceRepository agenceServices =  new SQLiteTravelBookingRepository(this);
    private ListView listView;

    private List<Vol> listVol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vol);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(LoginActivity.connectedUser == null) {

            fab.setVisibility(View.INVISIBLE);
            listVol= service.listVol();
        }
        else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            listVol= agenceServices.listVol();

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(ListVolActivity.this, CreerVolActivity.class);
                ListVolActivity.this.startActivity(mainIntent);
            }
        });

        listView = (ListView) findViewById(R.id.listVolsView);
        System.err.println(listVol.size());
        CustomVolsListAdapter whatever = new CustomVolsListAdapter(this, listVol);
        listView.setAdapter(whatever);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mainIntent = new Intent(ListVolActivity.this, CreerVolActivity.class);
                mainIntent.putExtra("id_vol", listVol.get(position).getId_vol());
                ListVolActivity.this.startActivity(mainIntent);
            }
        });
    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.list_vol_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.findVol:
                volLisFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listVol = service.listVol();
        listView = (ListView) findViewById(R.id.listVolsView);
        CustomVolsListAdapter whatever = new CustomVolsListAdapter(this, listVol);
        listView.setAdapter(whatever);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mainIntent = new Intent(ListVolActivity.this, CreerVolActivity.class);
                mainIntent.putExtra("id_vol", listVol.get(position).getId_vol());
                ListVolActivity.this.startActivity(mainIntent);
            }
        });
    }

    private void volLisFilter() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setTitle("Search...");
        dialog.setContentView(R.layout.dialog_search_vol);
        dialog.show();
        Button btnSearch = findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.connectedUser == null) {
                    final List<Vol> listVol = service.listVolFiltered(0, null, null, null);
                    System.err.println(listVol.size());
                    CustomVolsListAdapter whatever = new CustomVolsListAdapter(ListVolActivity.this, listVol);
                    listView.setAdapter(whatever);
                } else {

                    final List<Vol> listVol = agenceServices.listVolFiltered(0, null, null, null);
                    System.err.println(listVol.size());
                    CustomVolsListAdapter whatever = new CustomVolsListAdapter(ListVolActivity.this, listVol);
                    listView.setAdapter(whatever);
                }
            }
        });
        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
