package es.lost2found.lost2foundUI.placeUI.transportPlaceUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import es.lost2found.R;
import es.lost2found.database.DB_transportPlace;
import es.lost2found.entities.TransportPlace;
import es.lost2found.lost2foundUI.announceUI.NewAnnounceActivity;

public class FillTransportTrainPlaceActivity extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapter;
    private MaterialBetterSpinner spinner;
    private ArrayList<String> list = new ArrayList<>();
    private String stationChoice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_transport_train_place);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        spinner = findViewById(R.id.listStations);
        spinner.setAdapter(arrayAdapter);

        new stationsDB().execute();

        spinner.setOnItemClickListener((parent, view, position, id) -> {
            if(parent.getItemAtPosition(position).toString() != null) {
                stationChoice = parent.getItemAtPosition(position).toString();
            }
        });
    }

    private class stationsDB extends AsyncTask<Void, String[], String[]> {

        private ProgressDialog dialog = new ProgressDialog(FillTransportTrainPlaceActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected String[] doInBackground(Void... params) {
            return DB_transportPlace.getTrainStations();
        }

        @Override
        protected void onPostExecute(String[] result) {
            updateAdapter(result);
            this.dialog.dismiss();
        }
    }

    public void updateAdapter(String[] result) {
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, result);
        spinner.setAdapter(arrayAdapter);
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
        ed.putString("stationChoice", stationChoice);
        ed.apply();
        if(stationChoice.equalsIgnoreCase("")) {
            TextView textView = findViewById(R.id.no_info);
            textView.setText(textView.getResources().getString(R.string.error_txt2));
        } else
            new newTransportTrainPlaceDB().execute(stationChoice);
    }

    private class newTransportTrainPlaceDB extends AsyncTask<String, Void, TransportPlace> {

        private ProgressDialog dialog = new ProgressDialog(FillTransportTrainPlaceActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected TransportPlace doInBackground(String... strings) {
            return DB_transportPlace.insertTransportTrainPlace(strings[0]);
        }

        @Override
        protected void onPostExecute(TransportPlace result) {
            processTransportTrainPlace(result);
            this.dialog.dismiss();
        }
    }

    private void processTransportTrainPlace(TransportPlace transportTrainPlace) {
        Integer placeId = transportTrainPlace.getId();
        Intent intent = new Intent(this, NewAnnounceActivity.class);
        SharedPreferences sp = getSharedPreferences("placeId", 0);
        SharedPreferences.Editor ed = sp.edit();  // Saved the place data filled by the user.

        ed.putInt("idLugar", placeId);
        ed.apply();

        intent.putExtra("transportPlace", transportTrainPlace);
        startActivity(intent);
        finish();
    }
}
