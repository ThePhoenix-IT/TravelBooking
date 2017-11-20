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
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import com.thephoenix_it.travelbooking.views.client.VolViewActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(LoginActivity.connectedUser == null) {

            fab.setVisibility(View.INVISIBLE);
            listVol= service.listVol();
        }
        else {
            listVol= agenceServices.listVol();

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(ListVolActivity.this, CreerVolActivity.class);
                ListVolActivity.this.startActivity(mainIntent);
            }
        });

        listView = (ListView) findViewById(R.id.listVolsView);
        CustomVolsListAdapter whatever = new CustomVolsListAdapter(this, listVol);
        listView.setAdapter(whatever);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mainIntent;
                if(LoginActivity.connectedUser == null)
                    mainIntent = new Intent(ListVolActivity.this, VolViewActivity.class);
                else
                    mainIntent = new Intent(ListVolActivity.this, CreerVolActivity.class);
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
                Intent mainIntent;
                if(LoginActivity.connectedUser == null)
                    mainIntent = new Intent(ListVolActivity.this, VolViewActivity.class);
                else
                    mainIntent = new Intent(ListVolActivity.this, CreerVolActivity.class);
                mainIntent.putExtra("id_vol", listVol.get(position).getId_vol());
                ListVolActivity.this.startActivity(mainIntent);
            }
        });
    }
    private EditText txtDestination, txtDateDep, txtDateArr, txtDepart, txtPrix, txtNumVol;
    private void volLisFilter() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setTitle("Search...");
        dialog.setContentView(R.layout.dialog_search_vol);
        dialog.show();
        txtDestination = dialog.findViewById(R.id.txtDestination);
        txtDateDep = dialog.findViewById(R.id.txtDateDep);
        txtDateArr = dialog.findViewById(R.id.txtDateArr);
        txtDepart = dialog.findViewById(R.id.txtDepart);
        txtPrix = dialog.findViewById(R.id.txtPrix);
        txtNumVol = dialog.findViewById(R.id.txtNumVol);
        Button btnSearch = dialog.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.connectedUser == null) {
                    Map filter = new HashMap<String, Object>();
                    filter.put("numVol", Integer.parseInt(txtNumVol.getText().toString()));
                    filter.put("destination", txtDestination.getText().toString());
                    filter.put("prix", Double.valueOf(txtPrix.getText().toString()));
                    filter.put("dateDep", txtDateDep.getText().toString());
                    filter.put("dateArr", txtDateArr.getText().toString());
                    final List<Vol> listVol = service.listVolFiltered(filter);
                    System.err.println(listVol.size());
                    CustomVolsListAdapter whatever = new CustomVolsListAdapter(ListVolActivity.this, listVol);
                    listView.setAdapter(whatever);
                } else {
                    Map filter = new HashMap<String, Object>();
                    filter.put("numVol", Integer.parseInt(txtNumVol.getText().toString()));
                    filter.put("destination", txtDestination.getText().toString());
                    filter.put("prix", Double.valueOf(txtPrix.getText().toString()));
                    filter.put("dateDep", txtDateDep.getText().toString());
                    filter.put("dateArr", txtDateArr.getText().toString());
                    final List<Vol> listVol = agenceServices.listVolFiltered(filter);
                    System.err.println(listVol.size());
                    CustomVolsListAdapter whatever = new CustomVolsListAdapter(ListVolActivity.this, listVol);
                    listView.setAdapter(whatever);
                }
            }
        });
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
