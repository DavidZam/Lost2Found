package es.lost2found.lost2found.seekerUI;

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
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.database.DB_announce;
import es.lost2found.entities.Announce;
import es.lost2found.lost2found.announceUI.AnnounceActivity;
import es.lost2found.lost2found.announceUI.AnnounceViewAdapter;
import es.lost2found.lost2found.chatUI.ChatActivity;
import es.lost2found.lost2found.loginUI.LoginActivity;
import es.lost2found.lost2found.openDataUI.OpenDataActivity;
import es.lost2found.lost2found.otherUI.AboutUsActivity;
import es.lost2found.lost2found.otherUI.SettingsActivity;
import es.lost2found.lost2found.otherUI.ContactActivity;
import es.lost2found.lost2found.otherUI.HelpActivity;
import es.lost2found.lost2found.otherUI.RateActivity;

public class SeekerActivity extends AppCompatActivity implements FloatingActionButton.OnClickListener{
    private DrawerLayout mDrawerLayout;
    private AnnounceViewAdapter adapter;
    private Integer listElements = 0;
    private RecyclerView recyclerView;
    private Integer numberAnnounces;
    private String typePlace;
    MaterialBetterSpinner categoriaSeleccionada;
    MaterialBetterSpinner tipoAnuncionSeleccionado;
    private boolean connected;
    private TextView withoutSearch;
    private TextView withoutSearch2;
    private List<Announce> announceList;
    private String userName;
    private String parentName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker);

        try {
            connected = new checkIfDeviceIsConnected().execute().get(); // Check if device is connected
        } catch (Exception e) {
            e.printStackTrace();
        }

        withoutSearch = findViewById(R.id.without_search);
        withoutSearch2 = findViewById(R.id.without_search2);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));

        NavigationView navView = findViewById(R.id.nav_view);

        String[] categorias = {"Cartera", "Telefono", "Tarjeta bancaria", "Tarjeta transporte", "Otro"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categorias);
        categoriaSeleccionada = findViewById(R.id.listaCategorias);
        categoriaSeleccionada.setAdapter(arrayAdapter);

        String[] tipo = {"Perdida", "Hallazgo"};
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, tipo);
        tipoAnuncionSeleccionado =  findViewById(R.id.tipoAnuncio);
        tipoAnuncionSeleccionado.setAdapter(arrayAdapter2);

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

        final Intent home = new Intent(this, AnnounceActivity.class);
        final Intent chat = new Intent(this, ChatActivity.class);
        final Intent contact = new Intent(this, ContactActivity.class);
        final Intent aboutus = new Intent(this, AboutUsActivity.class);
        final Intent help = new Intent(this, HelpActivity.class);
        final Intent rate = new Intent(this, RateActivity.class);
        final Intent config = new Intent(this, SettingsActivity.class);
        final Intent openData = new Intent(this, OpenDataActivity.class);
        final Intent buscar = new Intent(this, SeekerActivity.class);

        navView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();

                    if(menuItem.getItemId()== R.id.nav_home) {
                        startActivity(home);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_chat) {
                        startActivity(chat);
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
                    } else if(menuItem.getItemId()== R.id.nav_open_data) {
                        startActivity(openData);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_logout) {
                        logoutUser();
                    } else if(menuItem.getItemId()== R.id.nav_search) {
                        startActivity(buscar);
                        finish();
                    }
                    return true;
                }
        );
        navView.setCheckedItem(R.id.nav_search);

        FloatingActionButton search = findViewById(R.id.search);
        search.setOnClickListener(this);

        Announce delAnnounce = (Announce) getIntent().getSerializableExtra("delete");
        if(connected) {
            if(delAnnounce != null) {
                String idAnuncio = String.valueOf(delAnnounce.getIdAnuncio());
                new deleteAnnounceFromDB().execute(idAnuncio, delAnnounce.getAnnounceCategorie());
                withoutSearch2.setText("");
            }
        } else {
            withoutSearch2.setText(withoutSearch2.getResources().getString(R.string.info_txt4));
        }


        SharedPreferences spref2 = getApplicationContext().getSharedPreferences("Login", 0);
        userName = spref2.getString("nombre", "");
        announceList = new ArrayList<>();
        parentName = this.getClass().getSimpleName();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            typePlace = extras.getString("typePlace");
        }

        adapter = new AnnounceViewAdapter(announceList, userName, parentName, typePlace, withoutSearch2);
        recyclerView = findViewById(R.id.search_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class deleteAnnounceFromDB extends AsyncTask<String, Void, Boolean> {

        private ProgressDialog dialog = new ProgressDialog(SeekerActivity.this);

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

    @Override
    public void onClick(View v) {
        try {
            connected = new checkIfDeviceIsConnected().execute().get(); // Check if device is connected
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!connected) {
            adapter.getListAnnounce().clear();
            TextView textView = findViewById(R.id.without_search2);
            textView.setText(textView.getResources().getString(R.string.info_txt4));
        } else {
            TextView textView = findViewById(R.id.without_search2);
            textView.setText("");
            if(!adapter.getListAnnounce().isEmpty()) {
                adapter.getListAnnounce().clear();
            }
            listElements = 0;
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            if(categoriaSeleccionada.getText().toString().equalsIgnoreCase("") || tipoAnuncionSeleccionado.getText().toString().equalsIgnoreCase("")) {
                withoutSearch.setText(textView.getResources().getString(R.string.error_txt5));
            } else {
                withoutSearch2.setText("");
                String cat = categoriaSeleccionada.getText().toString();
                String tipoA = tipoAnuncionSeleccionado.getText().toString();
                new getNumberObjectAnnouncesDB().execute(cat, tipoA);
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class getNumberObjectAnnouncesDB extends AsyncTask<String, Void, Integer> {

        private ProgressDialog dialog = new ProgressDialog(SeekerActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            return DB_announce.getNumberSeekerAnnounces(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(Integer numAnnounce) {
            processAnnounceScreen(numAnnounce);
            this.dialog.dismiss();
        }
    }

    public void processAnnounceScreen(Integer numAnnounces) {
        if (numAnnounces == 0) {
            TextView noAnnounces = findViewById(R.id.without_search);
            noAnnounces.setText(getResources().getString(R.string.seeker_error));
        } else {
            TextView noAnnounces = findViewById(R.id.without_search);
            noAnnounces.setText("");
            numberAnnounces = numAnnounces;

            String cat = categoriaSeleccionada.getText().toString();
            String tipoA = tipoAnuncionSeleccionado.getText().toString();

            new getObjectAnnouncesDB().execute(cat, tipoA, String.valueOf(numberAnnounces));
        }
    }

    private class getObjectAnnouncesDB extends AsyncTask<String, Void, Announce[]> {

        private ProgressDialog dialog = new ProgressDialog(SeekerActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected Announce[] doInBackground(String... strings) {
            return DB_announce.getAnnouncesSeeker(strings[0], strings[1], strings[2]);
        }

        @Override
        protected void onPostExecute(Announce[] announces) {
            updateAdapter(announces, numberAnnounces);
            this.dialog.dismiss();
        }
    }

    public void updateAdapter(Announce[] announces, Integer numAnnounces) {
        for(int i = 0; i < numAnnounces; i++) {
            adapter.insert(listElements, announces[i]);
            listElements++;
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    public boolean isInternetAvailable(String address, int port, int timeoutMs) {
        try {
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress(address, port);

            sock.connect(sockaddr, timeoutMs); // This will block no more than timeoutMs
            sock.close();

            return true;

        } catch (IOException e) { return false; }
    }

    private class checkIfDeviceIsConnected extends AsyncTask<Void, Void, Boolean> {

        private ProgressDialog dialog = new ProgressDialog(SeekerActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Comprobando conexion...");
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (isInternetAvailable("8.8.8.8", 53, 1000)) {
                // Internet available, do something
                connected = true;
            } else {
                // Internet not available
                connected = false;
            }
            return connected;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            this.dialog.dismiss();
        }
    }
}
