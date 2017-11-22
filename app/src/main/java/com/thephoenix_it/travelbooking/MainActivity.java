package com.thephoenix_it.travelbooking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thephoenix_it.travelbooking.models.Reservation;
import com.thephoenix_it.travelbooking.models.Utilisateur;
import com.thephoenix_it.travelbooking.models.Vol;
import com.thephoenix_it.travelbooking.repositories.IAdminRepository;
import com.thephoenix_it.travelbooking.repositories.IAgenceRepository;
import com.thephoenix_it.travelbooking.repositories.IClientRepository;
import com.thephoenix_it.travelbooking.repositories.SQLiteTravelBookingRepository;
import com.thephoenix_it.travelbooking.views.admin.CustomUtlisateursListAdapter;
import com.thephoenix_it.travelbooking.views.admin.GererClientActivity;
import com.thephoenix_it.travelbooking.views.agence.CreerVolActivity;
import com.thephoenix_it.travelbooking.views.agence.CustomVolsListAdapter;
import com.thephoenix_it.travelbooking.views.agence.ListVolActivity;
import com.thephoenix_it.travelbooking.views.client.CreerReservActivity;
import com.thephoenix_it.travelbooking.views.client.CustomReservListAdapter;
import com.thephoenix_it.travelbooking.views.client.ListReservationActivity;
import com.thephoenix_it.travelbooking.views.client.ListVolsActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private IAdminRepository adminServices = new SQLiteTravelBookingRepository(this);
    private IAgenceRepository agenceServices = new SQLiteTravelBookingRepository(this);
    private IClientRepository clientServices = new SQLiteTravelBookingRepository(this);
    ListView listView;
    private List<Utilisateur> utilisateurList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContentView();
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
    protected void onResume() {
        super.onResume();
        loadResumeContentView();
    }

    private void loadResumeContentView() {
        if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("ADMIN")) {
            utilisateurList = adminServices.listUtilisateur();
            CustomUtlisateursListAdapter whatever = new CustomUtlisateursListAdapter(this, utilisateurList);
            listView.setAdapter(whatever);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mainIntent = new Intent(MainActivity.this, GererClientActivity.class);
                    mainIntent.putExtra("id_utilisateur", utilisateurList.get(position).getId_utilisateur());
                    MainActivity.this.startActivity(mainIntent);
                }
            });
        }
        else if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("CLIENT")) {

            final List<Reservation> reservationList = clientServices.findAllReservation();
            CustomReservListAdapter whatever = new CustomReservListAdapter(this, reservationList);
            listView.setAdapter(whatever);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mainIntent = new Intent(MainActivity.this, CreerReservActivity.class);
                    mainIntent.putExtra("id_reservation", reservationList.get(position).getId_reservation());
                    mainIntent.putExtra("id_vol", reservationList.get(position).getVol().getId_vol());
                    MainActivity.this.startActivity(mainIntent);
                }
            });
        }
        else {
            final List<Vol> listVol = agenceServices.listVolByIdAgence(LoginActivity.connectedUser.getId_utilisateur());;
            CustomVolsListAdapter whatever = new CustomVolsListAdapter(this, listVol);
            listView.setAdapter(whatever);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mainIntent = new Intent(MainActivity.this, CreerVolActivity.class);
                    mainIntent.putExtra("id_vol", listVol.get(position).getId_vol());
                    MainActivity.this.startActivity(mainIntent);
                }
            });
        }
    }

    private void loadContentView() {
        if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("ADMIN")) {

            setContentView(R.layout.activity_admin_main);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View header = navigationView.getHeaderView(0);
            TextView textView = header.findViewById(R.id.adminTextView);
            textView.setText(LoginActivity.connectedUser.getNom_utilisateur());
            listView = (ListView) findViewById(R.id.listUtilisateurAdmin);
            utilisateurList = adminServices.listUtilisateur();
            CustomUtlisateursListAdapter whatever = new CustomUtlisateursListAdapter(this, utilisateurList);
            listView.setAdapter(whatever);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mainIntent = new Intent(MainActivity.this, GererClientActivity.class);
                    mainIntent.putExtra("id_utilisateur", utilisateurList.get(position).getId_utilisateur());
                    MainActivity.this.startActivity(mainIntent);
                }
            });
        }
        else if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("CLIENT")) {

            setContentView(R.layout.activity_main);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View header = navigationView.getHeaderView(0);
            TextView textView = header.findViewById(R.id.clientTextView);
            textView.setText(LoginActivity.connectedUser.getNom_utilisateur());
            listView = (ListView) findViewById(R.id.listReservUser);

            final List<Reservation> reservationList = clientServices.findAllReservation();
            CustomReservListAdapter whatever = new CustomReservListAdapter(this, reservationList);
            listView.setAdapter(whatever);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mainIntent = new Intent(MainActivity.this, CreerReservActivity.class);
                    mainIntent.putExtra("id_reservation", reservationList.get(position).getId_reservation());
                    mainIntent.putExtra("id_vol", reservationList.get(position).getVol().getId_vol());
                    MainActivity.this.startActivity(mainIntent);
                }
            });
        }
        else {

            setContentView(R.layout.activity_agence_main);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View header = navigationView.getHeaderView(0);
            TextView textView = header.findViewById(R.id.agenceTextView);
            textView.setText(LoginActivity.connectedUser.getNom_utilisateur());
            listView = (ListView) findViewById(R.id.listVolAgence);
            final List<Vol> listVol = agenceServices.listVolByIdAgence(LoginActivity.connectedUser.getId_utilisateur());;
            CustomVolsListAdapter whatever = new CustomVolsListAdapter(this, listVol);
            listView.setAdapter(whatever);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mainIntent = new Intent(MainActivity.this, CreerVolActivity.class);
                    mainIntent.putExtra("id_vol", listVol.get(position).getId_vol());
                    MainActivity.this.startActivity(mainIntent);
                }
            });
        }
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
        if (id == R.id.mainFind) {
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
            else if(LoginActivity.connectedUser != null && LoginActivity.connectedUser.getTypeUtilisateur().toString().equals("AGENCE")) {

                Intent mainIntent = new Intent(MainActivity.this, ListReservationActivity.class);
                MainActivity.this.startActivity(mainIntent);
            }
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(mainIntent);
            MainActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
