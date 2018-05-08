package es.lost2found.lost2foundUI.announceUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.database.DB_announce;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.chatUI.ChatActivity;
import es.lost2found.lost2foundUI.loginregisterUI.LoginActivity;
import es.lost2found.lost2foundUI.openDataUI.OpenDataActivity;
import es.lost2found.lost2foundUI.otherUI.AboutUsActivity;
import es.lost2found.lost2foundUI.otherUI.ConfigurationActivity;
import es.lost2found.lost2foundUI.otherUI.ContactActivity;
import es.lost2found.lost2foundUI.otherUI.HelpActivity;
import es.lost2found.lost2foundUI.otherUI.RateActivity;
import es.lost2found.lost2foundUI.placeUI.PlaceActivity;
import es.lost2found.lost2foundUI.seekerUI.SeekerActivity;

public class AnnounceActivity extends AppCompatActivity implements FloatingActionButton.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private Integer listElements = 0;
    private AnnounceViewAdapter adapter;
    private RecyclerView recyclerView;
    private Integer userNumberAnnounces;
    private String typePlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nav_view);

        View headerLayout = navView.getHeaderView(0);
        TextView emailUser = headerLayout.findViewById(R.id.user_mail);
        TextView nameUser = headerLayout.findViewById(R.id.user_name);
        SharedPreferences spref = getApplicationContext().getSharedPreferences("Login", 0);

        if(spref != null) {
            if (spref.contains("email")) {
                String userEmail = spref.getString("email", "");
                emailUser.setText(userEmail);
            }
            if (spref.contains("nombre")) {
                String userName = spref.getString("nombre", "");
                nameUser.setText(userName);
            }
        }

        final Intent buscar = new Intent(this, SeekerActivity.class);
        final Intent chat = new Intent(this, ChatActivity.class);
        final Intent contact = new Intent(this, ContactActivity.class);
        final Intent aboutus = new Intent(this, AboutUsActivity.class);
        final Intent help = new Intent(this, HelpActivity.class);
        final Intent rate = new Intent(this, RateActivity.class);
        final Intent config = new Intent(this, ConfigurationActivity.class);
        final Intent openData = new Intent(this, OpenDataActivity.class);

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        if(menuItem.getItemId()== R.id.nav_search) {
                            SharedPreferences sp = getApplicationContext().getSharedPreferences("announcePlace", 0);
                            String place = sp.getString("place", "");
                            buscar.putExtra("place", place);
                            startActivity(buscar);
                        }else if(menuItem.getItemId()== R.id.nav_chat) {
                            startActivity(chat);
                        }else if(menuItem.getItemId()== R.id.nav_open_data) {
                            startActivity(openData);
                        }else if(menuItem.getItemId()== R.id.nav_contact) {
                            startActivity(contact);
                        } else if(menuItem.getItemId() == R.id.nav_settings){
                            startActivity(config);
                        } else if(menuItem.getItemId()== R.id.nav_info) {
                            startActivity(aboutus);
                        } else if(menuItem.getItemId()== R.id.nav_help) {
                            startActivity(help);
                        }else if(menuItem.getItemId()== R.id.nav_feedback) {
                            startActivity(rate);
                        } else if(menuItem.getItemId()== R.id.nav_logout) {
                            logoutUser();
                        }
                        return true;
                    }
                }
        );
        navView.setCheckedItem(R.id.nav_home);

        Announce delAnnounce = (Announce) getIntent().getSerializableExtra("delete");
        if(delAnnounce != null) {
            //adapter.remove(delAnnounce);
            //listElements--;
            //recyclerView.setAdapter(adapter);
            //recyclerView.setLayoutManager(new LinearLayoutManager(this));
            String idAnuncio = String.valueOf(delAnnounce.getIdAnuncio());
            new deleteAnnounceFromDB().execute(idAnuncio, delAnnounce.getAnnounceCategorie());
        }

        if(spref != null) {
            String userEmail = spref.getString("email", "");
            new getNumberObjectAnnouncesDB().execute(userEmail); // Devuelve el numero de anuncios del usuario en cuestion
        }

        SharedPreferences spref2 = getApplicationContext().getSharedPreferences("Login", 0);
        String userName = spref2.getString("nombre", "");
        List<Announce> announceList = new ArrayList<>();
        String parentName = this.getClass().getSimpleName();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            typePlace = extras.getString("typePlace");
        }

        adapter = new AnnounceViewAdapter(announceList, getApplication(), userName, parentName, typePlace);
        recyclerView = findViewById(R.id.announce_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton createAnnounce = findViewById(R.id.new_announce);
        createAnnounce.setOnClickListener(this);
    }

    private class deleteAnnounceFromDB extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            return DB_announce.deleteAnnounce(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(Boolean removed) {

            //processAnnounceScreen(numAnnounce);
        }
    }

    private class getNumberObjectAnnouncesDB extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            return DB_announce.getNumberAnnounces(strings[0]);
        }

        @Override
        protected void onPostExecute(Integer numAnnounce) {
            processAnnounceScreen(numAnnounce);
        }
    }

    public void processAnnounceScreen(Integer numAnnounces) {
        if (numAnnounces == 0) {
            TextView noannounces = findViewById(R.id.without_announces);
            noannounces.setText(noannounces.getResources().getString(R.string.info_txt));
        } else {
            TextView noannounces = findViewById(R.id.without_announces);
            noannounces.setText("");
            userNumberAnnounces = numAnnounces;
            SharedPreferences spref = getApplicationContext().getSharedPreferences("Login", 0);
            String userEmail = spref.getString("email", "");

            new getObjectAnnouncesDB().execute(userEmail, String.valueOf(userNumberAnnounces)); // Devuelve una lista con los anuncios del usuario en cuestion
        }
    }

    private class getObjectAnnouncesDB extends AsyncTask<String, Void, Announce[]> {

        @Override
        protected Announce[] doInBackground(String... strings) {
            return DB_announce.getAnnounces(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(Announce[] announces) {
            updateAdapter(announces, userNumberAnnounces);
        }
    }

    public void updateAdapter(Announce[] announces, Integer numAnnounces) {
        for(int i = 0; i < numAnnounces; i++) {
            adapter.insert(listElements, announces[i]);
            listElements++;
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        final Intent newannounce = new Intent(this, PlaceActivity.class);
        startActivity(newannounce);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        SharedPreferences sp = getSharedPreferences("Login", 0);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("email", null);
        ed.putString("nombre", null);
        ed.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
