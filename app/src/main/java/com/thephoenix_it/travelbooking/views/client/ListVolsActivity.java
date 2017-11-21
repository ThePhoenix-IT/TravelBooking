package com.thephoenix_it.travelbooking.views.client;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.thephoenix_it.travelbooking.LoginActivity;
import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.models.Vol;
import com.thephoenix_it.travelbooking.repositories.IClientRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;
import com.thephoenix_it.travelbooking.views.agence.CustomVolsListAdapter;
import com.thephoenix_it.travelbooking.views.agence.ListVolActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListVolsActivity extends AppCompatActivity {

    private IClientRepository service = new SQLiteTravelBookingRepository(this);
    private ListView listView;
    private List<Vol> listVol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vols);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.listVolsUser);
        listVol = service.listVol();
        CustomVolsListAdapter whatever = new CustomVolsListAdapter(this, listVol);
        listView.setAdapter(whatever);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mainIntent = new Intent(ListVolsActivity.this, CreerReservActivity.class);
                mainIntent.putExtra("id_vol", listVol.get(position).getId_vol());
                ListVolsActivity.this.startActivity(mainIntent);
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
    private EditText txtDestination, txtDateDep, txtDateArr, txtDepart, txtPrix, txtNumVol, txtNomUser;
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
        txtNomUser = dialog.findViewById(R.id.txtNomUser);
        Button btnSearch = dialog.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.connectedUser == null) {
                    Map filter = new HashMap<String, Object>();
                    if(!txtNumVol.getText().toString().isEmpty())
                        filter.put("num_vol", Integer.parseInt(txtNumVol.getText().toString()));
                    else
                        filter.put("num_vol", 0);
                    filter.put("depart", txtDepart.getText().toString());
                    filter.put("destination", txtDestination.getText().toString());
                    if(!txtPrix.getText().toString().isEmpty())
                        filter.put("prix", Double.valueOf(txtPrix.getText().toString()));
                    else
                        filter.put("prix", 0.0D);
                    filter.put("dateDep", txtDateDep.getText().toString());
                    filter.put("dateArr", txtDateArr.getText().toString());
                    filter.put("Nom_user", txtNomUser.getText().toString());
                    final List<Vol> listVol = service.listVolFiltered(filter);
                    System.err.println(listVol.size());
                    CustomVolsListAdapter whatever = new CustomVolsListAdapter(ListVolsActivity.this, listVol);
                    listView.setAdapter(whatever);
                } else {
                    Map filter = new HashMap<String, Object>();
                    if(!txtNumVol.getText().toString().isEmpty())
                        filter.put("num_vol", Integer.parseInt(txtNumVol.getText().toString()));
                    else
                        filter.put("num_vol", 0);
                    filter.put("depart", txtDepart.getText().toString());
                    filter.put("destination", txtDestination.getText().toString());
                    if(!txtPrix.getText().toString().isEmpty())
                        filter.put("prix", Double.valueOf(txtPrix.getText().toString()));
                    else
                        filter.put("prix", 0.0D);
                    filter.put("dateDep", txtDateDep.getText().toString());
                    filter.put("dateArr", txtDateArr.getText().toString());
                    filter.put("Nom_user", txtNomUser.getText().toString());
                    System.err.println(listVol.size());
                    CustomVolsListAdapter whatever = new CustomVolsListAdapter(ListVolsActivity.this, listVol);
                    listView.setAdapter(whatever);
                }
                dialog.dismiss();
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
