package es.lost2found.lost2found.announceUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.database.DB_announce;
import es.lost2found.entities.Announce;
import es.lost2found.lost2found.chatUI.ChatActivity;
import es.lost2found.lost2found.loginUI.LoginActivity;
import es.lost2found.lost2found.openDataUI.OpenDataActivity;
import es.lost2found.lost2found.otherUI.AboutUsActivity;
import es.lost2found.lost2found.otherUI.SettingsActivity;
import es.lost2found.lost2found.otherUI.ContactActivity;
import es.lost2found.lost2found.otherUI.HelpActivity;
import es.lost2found.lost2found.otherUI.RateActivity;
import es.lost2found.lost2found.placeUI.PlaceActivity;
import es.lost2found.lost2found.seekerUI.SeekerActivity;

public class AnnounceActivity extends AppCompatActivity implements FloatingActionButton.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private Integer listElements = 0;
    private AnnounceViewAdapter adapter;
    private RecyclerView recyclerView;
    private Integer userNumberAnnounces;
    private String typePlace;
    private String userName;
    private boolean connected;
    private TextView withoutAnnounces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);

        try {
            connected = isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }

        withoutAnnounces = findViewById(R.id.without_announces);

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
                String userName2 = spref.getString("nombre", "");
                nameUser.setText(userName2);
                userName = userName2;
            }
        }

        final Intent buscar = new Intent(this, SeekerActivity.class);
        final Intent chat = new Intent(this, ChatActivity.class);
        final Intent contact = new Intent(this, ContactActivity.class);
        final Intent aboutus = new Intent(this, AboutUsActivity.class);
        final Intent help = new Intent(this, HelpActivity.class);
        final Intent rate = new Intent(this, RateActivity.class);
        final Intent config = new Intent(this, SettingsActivity.class);
        final Intent openData = new Intent(this, OpenDataActivity.class);
        final Intent home = new Intent(this, AnnounceActivity.class);

        navView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();

                    if(menuItem.getItemId()== R.id.nav_search) {
                        SharedPreferences sp = getApplicationContext().getSharedPreferences("announcePlace", 0);
                        String place = sp.getString("place", "");
                        buscar.putExtra("place", place);
                        startActivity(buscar);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_chat) {
                        Bundle extras = getIntent().getExtras();
                        if(extras != null) {
                            chat.putExtra("nombre", userName);
                        }
                        startActivity(chat);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_open_data) {
                        startActivity(openData);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_contact) {
                        startActivity(contact);
                        finish();
                    } else if(menuItem.getItemId() == R.id.nav_settings){
                        startActivity(config);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_info) {
                        startActivity(aboutus);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_help) {
                        startActivity(help);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_feedback) {
                        startActivity(rate);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_logout) {
                        logoutUser();
                    } else if(menuItem.getItemId()== R.id.nav_home) {
                        startActivity(home);
                        finish();
                    }
                    return true;
                }
        );
        navView.setCheckedItem(R.id.nav_home);

        Announce delAnnounce = (Announce) getIntent().getSerializableExtra("delete");
        if(delAnnounce != null && connected) {
            String idAnuncio = String.valueOf(delAnnounce.getIdAnuncio());
            new deleteAnnounceFromDB().execute(idAnuncio, delAnnounce.getAnnounceCategorie());
            withoutAnnounces.setText("");
        } else {
            withoutAnnounces.setText(withoutAnnounces.getResources().getString(R.string.info_txt4));
        }

        if(spref != null  && connected) {
            String userEmail = spref.getString("email", "");
            new getNumberObjectAnnouncesDB().execute(userEmail); // Devuelve el numero de anuncios del usuario en cuestion
            withoutAnnounces.setText("");
        } else {
            withoutAnnounces.setText(withoutAnnounces.getResources().getString(R.string.info_txt4));
        }

        SharedPreferences spref2 = getApplicationContext().getSharedPreferences("Login", 0);
        String userName = spref2.getString("nombre", "");
        List<Announce> announceList = new ArrayList<>();
        String parentName = this.getClass().getSimpleName();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            typePlace = extras.getString("typePlace");
        }

        adapter = new AnnounceViewAdapter(announceList, userName, parentName, typePlace, withoutAnnounces);
        recyclerView = findViewById(R.id.announce_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton createAnnounce = findViewById(R.id.new_announce);
        createAnnounce.setOnClickListener(this);
    }

    private class deleteAnnounceFromDB extends AsyncTask<String, Void, Boolean> {

        private ProgressDialog dialog = new ProgressDialog(AnnounceActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            return DB_announce.deleteAnnounce(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            this.dialog.dismiss();
        }
    }

    private class getNumberObjectAnnouncesDB extends AsyncTask<String, Void, Integer> {

        private ProgressDialog dialog = new ProgressDialog(AnnounceActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            return DB_announce.getNumberAnnounces(strings[0]);
        }

        @Override
        protected void onPostExecute(Integer numAnnounce) {
            processAnnounceScreen(numAnnounce);
            this.dialog.dismiss();
        }
    }

    public void processAnnounceScreen(Integer numAnnounces) {
        if(numAnnounces != null) {
            if (numAnnounces == 0) {
                withoutAnnounces.setText(withoutAnnounces.getResources().getString(R.string.info_txt));
            } else {
                withoutAnnounces.setText("");
                userNumberAnnounces = numAnnounces;
                SharedPreferences spref = getApplicationContext().getSharedPreferences("Login", 0);
                String userEmail = spref.getString("email", "");

                new getObjectAnnouncesDB().execute(userEmail, String.valueOf(userNumberAnnounces)); // Devuelve una lista con los anuncios del usuario en cuestion
            }
        }
    }

    private class getObjectAnnouncesDB extends AsyncTask<String, Void, Announce[]> {

        private ProgressDialog dialog = new ProgressDialog(AnnounceActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected Announce[] doInBackground(String... strings) {
            return DB_announce.getAnnounces(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(Announce[] announces) {
            updateAdapter(announces, userNumberAnnounces);
            this.dialog.dismiss();
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
        try {
            connected = isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!connected) {
            adapter.getListAnnounce().clear();
            withoutAnnounces.setText(withoutAnnounces.getResources().getString(R.string.info_txt4));
        } else {
            withoutAnnounces.setText("");
            final Intent newannounce = new Intent(this, PlaceActivity.class);
            startActivity(newannounce);
        }
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

    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec (command).waitFor() == 0);
    }
}
