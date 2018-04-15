package es.lost2found.lost2foundUI.placeUI.transportUI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import es.lost2found.R;
import es.lost2found.database.DB_announce;
import es.lost2found.database.DB_place;
import es.lost2found.database.DB_transportPlace;
import es.lost2found.entities.Announce;
import es.lost2found.entities.Place;
import es.lost2found.entities.TransportPlace;
import es.lost2found.lost2foundUI.announceUI.NewAnnounceActivity;
import es.lost2found.lost2foundUI.placeUI.PlaceActivity;

public class FillTransportPlaceActivity extends AppCompatActivity  { // implements AdapterView.OnItemClickListener

    private ArrayAdapter<String> arrayAdapter;
    private MaterialBetterSpinner spinner;
    private ArrayAdapter<String> arrayAdapter2;
    private MaterialBetterSpinner spinner2;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_transport_place);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        //ArrayList<String> list = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner = findViewById(R.id.listLines);
        spinner.setAdapter(arrayAdapter);

        arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner2 = findViewById(R.id.listStations);
        spinner2.setAdapter(arrayAdapter2);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("transportButton", 0);
        boolean metro = sp.getBoolean("metro", false);
        boolean bus = sp.getBoolean("bus", false);

        if(metro) {
            String metroText = "metro";
            new linesDB().execute(metroText);
        } else {
            if (bus) {
                String busText = "bus";
                new linesDB().execute(busText);
            }
        }

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString() != null) {
                    String choice = parent.getItemAtPosition(position).toString();
                    AsyncTask<String, String[], String[]> stationsTask = new AsyncTask<String, String[], String[]>() {

                        @Override
                        protected void onPreExecute() {
                            /*arrayAdapter2.clear();
                            //arrayAdapter2.notifyDataSetChanged();
                            //spinner2.setAdapter(arrayAdapter2); // null
                            arrayAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                            spinner2.setAdapter(arrayAdapter2);
                            spinner2.setSelection(arrayAdapter2.getCount());        //set the hint the default selection so it appears on launch.
                            spinner2.setOnItemSelectedListener(this);
                            arrayAdapter2.notifyDataSetChanged();*/
                        }

                        @Override
                        protected String[] doInBackground(String... strings) {
                            return DB_transportPlace.getStations(choice);
                        }

                        @Override
                        protected void onPostExecute(String[] result) {
                            updateAdapter2(result);
                        } }; // ... your AsyncTask code goes here

                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                        stationsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        stationsTask.execute();
                }
            }
        });
    }

    private class linesDB extends AsyncTask<String, String[], String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            return DB_transportPlace.getLines(strings[0]);
        }

        @Override
        protected void onPostExecute(String[] result) {
            updateAdapter(result);
        }
    }

    /*private static class stationsDB extends AsyncTask<String, String[], String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            return DB_transportPlace.getStations(strings[0]);
        }

        @Override
        protected void onPostExecute(String[] result) {
            updateAdapter2(result);
        }
    }*/

    public void updateAdapter(String[] result) {
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, result);
        spinner.setAdapter(arrayAdapter);
    }

    public void updateAdapter2(String[] result) {
        arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, result);
        spinner2.setAdapter(arrayAdapter2);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent place = new Intent(this, TransportPlaceActivity.class);
        startActivity(place);
        finish();
        return true;
    }

    public void saveTransportPlaceData(View view) {
        /*// Linea
        EditText line = findViewById(R.id.line);
        //String lineText = line.getText().toString();

        // Estacion
        EditText station = findViewById(R.id.station);
        //String stationText = station.getText().toString();

        /*SharedPreferences sp = getApplicationContext().getSharedPreferences("transportPlace", 0);
        SharedPreferences.Editor ed = sp.edit();            // Saved the user color choice.
        ed.putString("lineChoice", lineText);
        ed.putString("stationChoice", stationText);
        ed.apply();*//*
        if(lineText.equalsIgnoreCase("") || stationText.equalsIgnoreCase("")) {
            TextView textView = findViewById(R.id.wrong_info);
            textView.setText(textView.getResources().getString(R.string.error_txt2));
        } else
            new transportPlaceDB().execute(lineText, stationText);*/
    }

    private class transportPlaceDB extends AsyncTask<String, Void, TransportPlace> {

        private ProgressDialog dialog = new ProgressDialog(FillTransportPlaceActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected TransportPlace doInBackground(String... strings) {
            return DB_transportPlace.insertTransportPlace(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(TransportPlace result) {
            this.dialog.dismiss();
            processNewTransportPlace(result);
        }
    }

    private void processNewTransportPlace(TransportPlace transportPlace) {
        Intent intent = new Intent(this, NewAnnounceActivity.class);
        SharedPreferences sp = getSharedPreferences("placeId", 0);
        SharedPreferences.Editor ed = sp.edit();  // Saved the place data filled by the user.
        Integer placeId = transportPlace.getId(); // transportPlace.getId();

        ed.putInt("idLugarTte", placeId); // Comprobar
        ed.apply();

        intent.putExtra("transportPlace", transportPlace);
        startActivity(intent);
        finish();


        //Intent intent = new Intent(this, AnnounceActivity.class);

        /*SharedPreferences sp = getApplicationContext().getSharedPreferences("Login", 0);
        String email = sp.getString("email", null);
        if(email != null) { // Login perform with SharedPreferences credentials.
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
        else {              // Login perform without SharedPreferences credentials.
            if(user != null) {
                SharedPreferences.Editor ed = sp.edit();            // Saved the user login credencials.
                ed.putString("email", user.getEmail());
                ed.putString("name", user.getName());
                ed.apply();

                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
            else {
                TextView textView = findViewById(R.id.wrong_user);
                textView.setText(textView.getResources().getString(R.string.error_txt1));
            }
        }*/
    }

    /*public void place(View view) {
        Intent intent = new Intent(this, PlaceActivity.class);
        startActivity(intent);
        finish();
    }*/

}
