package es.lost2found.lost2foundUI.seekerUI;

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
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.database.DB_announce;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;
import es.lost2found.lost2foundUI.chatUI.ChatActivity;
import es.lost2found.lost2foundUI.loginregisterUI.LoginActivity;
import es.lost2found.lost2foundUI.otherUI.AboutUsActivity;
import es.lost2found.lost2foundUI.otherUI.ConfigurationActivity;
import es.lost2found.lost2foundUI.otherUI.ContactActivity;
import es.lost2found.lost2foundUI.otherUI.HelpActivity;
import es.lost2found.lost2foundUI.otherUI.RateActivity;


public class SeekerActivity extends AppCompatActivity implements FloatingActionButton.OnClickListener{
    private DrawerLayout mDrawerLayout;
    private SeekerAnnounceViewAdapter adapter;
    private Integer listElements = 0;
    private RecyclerView recyclerView;
    private Integer numberAnnounces;

    MaterialBetterSpinner categoriaSeleccionada;
    MaterialBetterSpinner tipoAnuncionSeleccionado;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker);

        String[] categorias = {"Tarjetas Bancarias", "Tarjetas Transporte Público", "Carteras/Monederos", "Teléfonos", "Otros"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categorias);
        categoriaSeleccionada = (MaterialBetterSpinner) findViewById(R.id.listaCategorias);
        categoriaSeleccionada.setAdapter(arrayAdapter);

        String[] tipo = {"Pérdida", "Hallazgo"};
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, tipo);
        tipoAnuncionSeleccionado = (MaterialBetterSpinner) findViewById(R.id.tipoAnuncio);
        tipoAnuncionSeleccionado.setAdapter(arrayAdapter2);

        //Menú
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

        final Intent home = new Intent(this, AnnounceActivity.class);
        final Intent chat = new Intent(this, ChatActivity.class);
        final Intent contact = new Intent(this, ContactActivity.class);
        final Intent aboutus = new Intent(this, AboutUsActivity.class);
        final Intent help = new Intent(this, HelpActivity.class);
        final Intent rate = new Intent(this, RateActivity.class);
        final Intent config = new Intent(this, ConfigurationActivity.class);
        final Intent openData = new Intent(this, ConfigurationActivity.class);

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected

                        if(menuItem.getItemId()== R.id.nav_home) {
                            startActivity(home);
                        }else if(menuItem.getItemId()== R.id.nav_chat) {
                            startActivity(chat);
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
                        } else if(menuItem.getItemId()== R.id.nav_open_data) {
                            startActivity(openData);
                        } else if(menuItem.getItemId()== R.id.nav_logout) {
                            logoutUser();
                        }
                        return true;
                    }
                }
        );
        navView.setCheckedItem(R.id.nav_search);

        // In this example we fill announceList with a function fill_with_data(), in the future we'll do it with the database info
        /*List<Announce> announceList = new ArrayList<>();
        Announce announce = new Announce();
        announce.fill_with_data(announceList);

        RecyclerView recyclerView = findViewById(R.id.search_recyclerview);
        SeekerAnnounceViewAdapter adapter = new SeekerAnnounceViewAdapter(announceList, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adding a ItemAnimator to the RecyclerView (Optional)
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        /*recyclerView.setItemAnimator(itemAnimator);*/

        FloatingActionButton search = findViewById(R.id.search);
        search.setOnClickListener(this);

        List<Announce> announceList = new ArrayList<>();
        adapter = new SeekerAnnounceViewAdapter(announceList, getApplication());
        recyclerView = findViewById(R.id.search_recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adding a ItemAnimator to the RecyclerView (Optional)
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

    }

    @Override
    public void onClick(View v) {

        if(!adapter.listAnnounce.isEmpty()) {
            adapter.listAnnounce.clear();
            listElements = 0;
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        if(categoriaSeleccionada.getText().toString().equalsIgnoreCase("") || tipoAnuncionSeleccionado.getText().toString().equalsIgnoreCase("")){
            TextView textView = findViewById(R.id.without_search);
            textView.setText("Debes seleccionar la categoría del objeto y el tipo de anuncio");

        }else{

            String cat = cambiarNombreCategoria();
            String tipoA = cambiarNombreTipo();
            new getNumberObjectAnnouncesDB().execute(cat, tipoA);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private String cambiarNombreCategoria(){
        String cat;
        if(categoriaSeleccionada.getText().toString().equals("Tarjetas Bancarias")){
            cat = "Tarjeta bancaria";
        }else if(categoriaSeleccionada.getText().toString().equals("Tarjetas Transporte Público")){
            cat = "Tarjeta transporte";
        }else if(categoriaSeleccionada.getText().toString().equals("Carteras/Monederos")){
            cat = "Cartera";
        }else if(categoriaSeleccionada.getText().toString().equals("Teléfonos")){
            cat = "Telefono";
        }else{
            cat = "Otro";
        }

        return cat;
    }

    private String cambiarNombreTipo(){
        String tipoA = "Hallazgo";
        if(tipoAnuncionSeleccionado.getText().toString().equals("Pérdida")){
            tipoA = "Perdida";
        }
        return tipoA;
    }

    private class getNumberObjectAnnouncesDB extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            return DB_announce.getNumberSeekerAnnounces(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(Integer numAnnounce) {
            processAnnounceScreen(numAnnounce);
        }
    }

    public void processAnnounceScreen(Integer numAnnounces) {
        if (numAnnounces == 0) {
            TextView noannounces = findViewById(R.id.without_search);
            noannounces.setText("No hay objetos que cumplan con tus requisitos");
        } else {
            TextView noannounces = findViewById(R.id.without_search);
            noannounces.setText("");
            numberAnnounces = numAnnounces;

            String cat = cambiarNombreCategoria();
            String tipoA = cambiarNombreTipo();

            new getObjectAnnouncesDB().execute(cat, tipoA, String.valueOf(numberAnnounces));
        }
    }

    private class getObjectAnnouncesDB extends AsyncTask<String, Void, Announce[]> {

        @Override
        protected Announce[] doInBackground(String... strings) {
            return DB_announce.getAnnouncesSeeker(strings[0], strings[1], strings[2]);
        }

        @Override
        protected void onPostExecute(Announce[] announces) {
            updateAdapter(announces, numberAnnounces);
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

    /**
     *
     * @param view
     */
    public void seeker(View view) {

    }
}
