package com.thephoenix_it.travelbooking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.thephoenix_it.travelbooking.models.Vol;
import com.thephoenix_it.travelbooking.views.admin.GererClientActivity;
import com.thephoenix_it.travelbooking.views.agence.CreerVolActivity;
import com.thephoenix_it.travelbooking.views.agence.CustomVolsListAdapter;
import com.thephoenix_it.travelbooking.views.agence.ListVolActivity;
import com.thephoenix_it.travelbooking.views.client.ListReservationActivity;
import com.thephoenix_it.travelbooking.views.client.ListVolsActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("ADMIN")) {

            setContentView(R.layout.activity_admin_main);
            listView = (ListView) findViewById(R.id.listUtilisateurAdmin);

            String[] listItwms = new String[]{"ListView Example", "ListView with FAB", "FAB with Simple List View in Android", "ListView Adapter with Floating Action Button",
                    "Android FAB and ListView Example", "List View and FAB Source Code", "FAB and List View Array", "Floating Action Button FAB", "ListView Example",
                    "ListView with FAB", "FAB with Simple List View in Android", "ListView Adapter with Floating Action Button",
                    "Android FAB and ListView Example", "List View and FAB Source Code", "FAB and List View Array"
            };

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, listItwms);
            listView.setAdapter(adapter);
        }
        else if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("CLIENT")) {

            setContentView(R.layout.activity_main);
            listView = (ListView) findViewById(R.id.listReservUser);

            String[] listItwms = new String[]{"ListView Example", "ListView with FAB", "FAB with Simple List View in Android", "ListView Adapter with Floating Action Button",
                    "Android FAB and ListView Example", "List View and FAB Source Code", "FAB and List View Array", "Floating Action Button FAB", "ListView Example",
                    "ListView with FAB", "FAB with Simple List View in Android", "ListView Adapter with Floating Action Button",
                    "Android FAB and ListView Example", "List View and FAB Source Code", "FAB and List View Array"
            };

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, listItwms);
            listView.setAdapter(adapter);
        }
        else {

            setContentView(R.layout.activity_agence_main);
            listView = (ListView) findViewById(R.id.listVolAgence);
            List<Vol> listVol = new ArrayList<Vol>();
            listVol.add(new Vol(1, 30.0D, "Vol Des 1", 3.0D, new Date(), true, null));
            listVol.add(new Vol(2, 30.0D, "Vol Des 2", 3.0D, new Date(), true, null));
            listVol.add(new Vol(3, 30.0D, "Vol Des 3", 3.0D, new Date(), true, null));
            listVol.add(new Vol(4, 30.0D, "Vol Des 4", 3.0D, new Date(), true, null));
            CustomVolsListAdapter whatever = new CustomVolsListAdapter(this, listVol);
            listView.setAdapter(whatever);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mainIntent = new Intent(MainActivity.this, CreerVolActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                }
            });
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("ADMIN")) {

                    Intent mainIntent = new Intent(MainActivity.this, GererClientActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                }
                else if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("CLIENT")) {
                    Intent mainIntent = new Intent(MainActivity.this, ListVolsActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                }
                else if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("AGENCE")) {
                    Intent mainIntent = new Intent(MainActivity.this, CreerVolActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                }
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("ADMIN")) {

                Intent mainIntent = new Intent(MainActivity.this, GererClientActivity.class);
                MainActivity.this.startActivity(mainIntent);
            }
            else if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("AGENCE")) {

                Intent mainIntent = new Intent(MainActivity.this, ListVolActivity.class);
                MainActivity.this.startActivity(mainIntent);
            }
        } else if (id == R.id.nav_gallery) {

            if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("ADMIN")) {

                Intent mainIntent = new Intent(MainActivity.this, GererClientActivity.class);
                MainActivity.this.startActivity(mainIntent);
            }
            else if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("CLIENT")) {

                Intent mainIntent = new Intent(MainActivity.this, ListVolsActivity.class);
                MainActivity.this.startActivity(mainIntent);
            }
            else if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("AGENCE")) {

                Intent mainIntent = new Intent(MainActivity.this, CreerVolActivity.class);
                MainActivity.this.startActivity(mainIntent);
            }
        } else if (id == R.id.nav_slideshow) {

            if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("ADMIN")) {

                Intent mainIntent = new Intent(MainActivity.this, GererClientActivity.class);
                MainActivity.this.startActivity(mainIntent);
            }
            else if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("CLIENT")) {

                Intent mainIntent = new Intent(MainActivity.this, ListReservationActivity.class);
                MainActivity.this.startActivity(mainIntent);
            }
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
