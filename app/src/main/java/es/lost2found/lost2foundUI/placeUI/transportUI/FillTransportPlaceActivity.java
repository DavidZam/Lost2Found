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

public class FillTransportPlaceActivity extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapter;
    private MaterialBetterSpinner spinner;
    private ArrayAdapter<String> arrayAdapter2;
    private MaterialBetterSpinner spinner2;
    private ArrayList<String> list = new ArrayList<>();
    private String lineChoice = "";
    private String stationChoice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_transport_place);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

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
                    lineChoice = parent.getItemAtPosition(position).toString();
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                        new stationsDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, lineChoice);
                    else
                        new stationsDB().execute(lineChoice);
                }
            }
        });
        spinner2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString() != null) {
                    stationChoice = parent.getItemAtPosition(position).toString();
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

    private class stationsDB extends AsyncTask<String, String[], String[]> {
        @Override
        protected String[] doInBackground(String... strings) {
            return DB_transportPlace.getStations(strings[0]);
        }

        @Override
        protected void onPostExecute(String[] result) {
            updateAdapter2(result);
        }
    }

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
        SharedPreferences sp = getSharedPreferences("transportPlace", 0);
        SharedPreferences.Editor ed = sp.edit();            // Saved the user color choice.
        ed.putString("lineChoice", lineChoice);
        ed.putString("stationChoice", stationChoice);
        ed.apply();
        if(lineChoice.equalsIgnoreCase("") || stationChoice.equalsIgnoreCase("")) {
            TextView textView = findViewById(R.id.wrong_info);
            textView.setText(textView.getResources().getString(R.string.error_txt2));
        } else
            new getTransportPlaceDB().execute(lineChoice, stationChoice);

    }

    private class getTransportPlaceDB extends AsyncTask<String, Void, TransportPlace> {

        private ProgressDialog dialog = new ProgressDialog(FillTransportPlaceActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected TransportPlace doInBackground(String... strings) {
            return DB_transportPlace.getTransportPlace(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(TransportPlace result) {
            this.dialog.dismiss();
            processNewTransportPlace(result);
        }
    }

    private void processNewTransportPlace(TransportPlace transportPlace) {
        // PENDIENTE: ASEGURARSE QUE SE METEN VALORES CONSISTENTES EN LOS DOS SPINNER
        Intent intent = new Intent(this, NewAnnounceActivity.class);
        SharedPreferences sp = getSharedPreferences("placeId", 0);
        SharedPreferences.Editor ed = sp.edit();  // Saved the place data filled by the user.
        Integer placeId = transportPlace.getId(); // transportPlace.getId();

        ed.putInt("idLugar", placeId); // Comprobar
        ed.apply();

        intent.putExtra("transportPlace", transportPlace);
        startActivity(intent);
        finish();
    }
}
