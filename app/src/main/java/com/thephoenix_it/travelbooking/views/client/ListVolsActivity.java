package com.thephoenix_it.travelbooking.views.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thephoenix_it.travelbooking.R;
import com.thephoenix_it.travelbooking.models.Vol;
import com.thephoenix_it.travelbooking.repositories.IClientRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;
import com.thephoenix_it.travelbooking.views.agence.CustomVolsListAdapter;

import java.util.List;

public class ListVolsActivity extends AppCompatActivity {

    private IClientRepository service = new SQLiteTravelBookingRepository(this);
    private ListView listView;
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
        final List<Vol> listVol = service.listVol();
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

}
