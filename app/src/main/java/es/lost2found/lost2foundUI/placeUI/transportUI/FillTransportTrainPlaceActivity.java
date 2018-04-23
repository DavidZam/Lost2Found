package es.lost2found.lost2foundUI.placeUI.transportUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import es.lost2found.R;
import es.lost2found.database.DB_transportPlace;

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
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner = findViewById(R.id.listStations);
        spinner.setAdapter(arrayAdapter);

        new stationsDB().execute();

        /*spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString() != null) {
                    lineChoice = parent.getItemAtPosition(position).toString();
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                        new FillTransportPlaceActivity.stationsDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, lineChoice);
                    else
                        new FillTransportPlaceActivity.stationsDB().execute(lineChoice);
                }
            }
        });*/
    }

    private class stationsDB extends AsyncTask<Void, String[], String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            return DB_transportPlace.getTrainStations();
        }

        @Override
        protected void onPostExecute(String[] result) {
            updateAdapter(result);
        }
    }

    public void updateAdapter(String[] result) {
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, result);
        spinner.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent place = new Intent(this, TransportPlaceActivity.class);
        startActivity(place);
        finish();
        return true;
    }

    /*public void saveTransportPlaceData(View view) {
        SharedPreferences sp = getSharedPreferences("transportPlace", 0);
        SharedPreferences.Editor ed = sp.edit();            // Saved the user color choice.
        ed.putString("stationChoice", stationChoice);
        ed.apply();
        if(stationChoice.equalsIgnoreCase("")) {
            TextView textView = findViewById(R.id.no_info);
            textView.setText(textView.getResources().getString(R.string.error_txt2));
        } else
            new FillTransportPlaceActivity.getTransportPlaceDB().execute(lineChoice, stationChoice);
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
            processTransportPlace(result);
        }
    }

    private void processTransportPlace(TransportPlace transportPlace) {
        Integer placeId = transportPlace.getId();
        if(placeId == null) {
            TextView textView = findViewById(R.id.wrong_info);
            textView.setText(textView.getResources().getString(R.string.error_txt4));
        } else {
            Intent intent = new Intent(this, NewAnnounceActivity.class);
            SharedPreferences sp = getSharedPreferences("placeId", 0);
            SharedPreferences.Editor ed = sp.edit();  // Saved the place data filled by the user.

            ed.putInt("idLugar", placeId);
            ed.apply();

            intent.putExtra("transportPlace", transportPlace);
            startActivity(intent);
            finish();
        }
    }*/
}
