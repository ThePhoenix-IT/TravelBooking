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

import com.thephoenix_it.travelbooking.views.admin.GererClientActivity;
import com.thephoenix_it.travelbooking.views.agence.CreerVolActivity;
import com.thephoenix_it.travelbooking.views.client.ListReservationActivity;
import com.thephoenix_it.travelbooking.views.client.ListVolsActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(LoginActivity.connectedUser.getTypeUtilisateur().equals("ADMIN"))
            setContentView(R.layout.activity_admin_main);
        else if(LoginActivity.connectedUser.getTypeUtilisateur().equals("CLIENT"))
            setContentView(R.layout.activity_main);
        else
            setContentView(R.layout.activity_agence_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

            if(LoginActivity.connectedUser.getTypeUtilisateur().equals("ADMIN")) {

                Intent mainIntent = new Intent(MainActivity.this, GererClientActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
            else if(LoginActivity.connectedUser.getTypeUtilisateur().equals("AGENCE")) {

                Intent mainIntent = new Intent(MainActivity.this, CreerVolActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        } else if (id == R.id.nav_gallery) {

            if(LoginActivity.connectedUser.getTypeUtilisateur().equals("ADMIN")) {

                Intent mainIntent = new Intent(MainActivity.this, GererClientActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
            else if(LoginActivity.connectedUser.getTypeUtilisateur().equals("CLIENT")) {

                Intent mainIntent = new Intent(MainActivity.this, ListVolsActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
            else if(LoginActivity.connectedUser.getTypeUtilisateur().equals("AGENCE")) {

                Intent mainIntent = new Intent(MainActivity.this, CreerVolActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        } else if (id == R.id.nav_slideshow) {

            if(LoginActivity.connectedUser.getTypeUtilisateur().equals("ADMIN")) {

                Intent mainIntent = new Intent(MainActivity.this, GererClientActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
            else if(LoginActivity.connectedUser.getTypeUtilisateur().equals("CLIENT")) {

                Intent mainIntent = new Intent(MainActivity.this, ListReservationActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
