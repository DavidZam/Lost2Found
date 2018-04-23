package es.lost2found.lost2foundUI.placeUI.transportUI;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        spinner = findViewById(R.id.listLines);
        spinner.setAdapter(arrayAdapter);

        /*SharedPreferences sp = getApplicationContext().getSharedPreferences("transportButton", 0);
        boolean metro = sp.getBoolean("metro", false);
        boolean bus = sp.getBoolean("bus", false);

        if(metro) {
            String metroText = "metro";
            new FillTransportPlaceActivity.linesDB().execute(metroText);
        } else if (bus) {
            String busText = "bus";
            new FillTransportPlaceActivity.linesDB().execute(busText);
        }*/

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*if(parent.getItemAtPosition(position).toString() != null) {
                    lineChoice = parent.getItemAtPosition(position).toString();
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                        new FillTransportPlaceActivity.stationsDB().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, lineChoice);
                    else
                        new FillTransportPlaceActivity.stationsDB().execute(lineChoice);
                }*/
            }
        });
    }

    private class stationsDB extends AsyncTask<String, String[], String[]> {
        @Override
        protected String[] doInBackground(String... strings) {
            return DB_transportPlace.getStations(strings[0]);
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

    public void saveTransportPlaceData(View view) {
        SharedPreferences sp = getSharedPreferences("transportPlace", 0);
        SharedPreferences.Editor ed = sp.edit();            // Saved the user color choice.
        ed.putString("stationChoice", stationChoice);
        ed.apply();
        if(stationChoice.equalsIgnoreCase("")) {
            TextView textView = findViewById(R.id.no_info);
            textView.setText(textView.getResources().getString(R.string.error_txt2));
        } /*else
            new FillTransportPlaceActivity.getTransportPlaceDB().execute(lineChoice, stationChoice);*/
    }
}
