package com.example.android.tetanggaku;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import adapter.SharedPrefManager;
import entity.sharedWarga;
import navdrawer.EventWarga;
import navdrawer.ForumWarga;
import navdrawer.LokasiPenting;
import navdrawer.Pengaturan;
import navdrawer.TetanggaSekitar;


public class MynavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynav);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        sharedWarga user = SharedPrefManager.getInstance(this).getwarga();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //ganti header
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.nav_name);
        TextView nav_email = (TextView)hView.findViewById(R.id.nav_email);
        TextView id_warga = hView.findViewById(R.id.nav_id_warga);
        nav_user.setText(user.getNama());
        nav_email.setText(user.getAlamat());
        id_warga.setText(""+user.getId());

        //set agar tampil default di forum warga
        navigationView.setCheckedItem(R.id.nav_forum_warga);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        setTitle("Forum Warga");
        fragmentManager.beginTransaction().replace(R.id.content_frame, new ForumWarga()).commit();
    }

/*    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.mynav, menu);
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
        FragmentManager fragmentManager = getSupportFragmentManager();

        /*if (id == R.id.nav_urus_izin) {
            setTitle("Urus Izin");
            fragmentManager.beginTransaction().replace(R.id.content_frame, new UrusIzin()).commit();
        } else*/
        if (id == R.id.nav_forum_warga) {
            setTitle("Forum Warga");
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ForumWarga()).commit();
        } else if (id == R.id.nav_lokasi_penting) {
            setTitle("Lokasi Penting");
            fragmentManager.beginTransaction().replace(R.id.content_frame, new LokasiPenting()).commit();
        } else if (id == R.id.nav_pengaturan) {
            setTitle("Pengaturan");
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Pengaturan()).commit();
        } else if (id == R.id.nav_tetangga) {
            setTitle("Tetangga Sekitar");
            fragmentManager.beginTransaction().replace(R.id.content_frame, new TetanggaSekitar()).commit();
        }else if(id == R.id.nav_event_warga){
            setTitle("Event Warga");
            fragmentManager.beginTransaction().replace(R.id.content_frame, new EventWarga()).commit();
        } else if(id == R.id.logout){
            finish();
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
