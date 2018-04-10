package es.lost2found.lost2foundUI.placeUI.transportUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import es.lost2found.R;
import es.lost2found.database.DB_announce;
import es.lost2found.database.DB_transportPlace;
import es.lost2found.entities.Announce;
import es.lost2found.entities.TransportPlace;
import es.lost2found.lost2foundUI.announceUI.NewAnnounceActivity;
import es.lost2found.lost2foundUI.placeUI.PlaceActivity;

public class FillTransportPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_transport_place);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent place = new Intent(this, TransportPlaceActivity.class);
        startActivity(place);
        finish();
        return true;
    }

    public void saveTransportPlaceData(View view) {
        // Linea
        EditText line = findViewById(R.id.line);
        String lineText = line.getText().toString();

        // Estacion
        EditText station = findViewById(R.id.station);
        String stationText = station.getText().toString();

        /*SharedPreferences sp = getApplicationContext().getSharedPreferences("transportPlace", 0);
        SharedPreferences.Editor ed = sp.edit();            // Saved the user color choice.
        ed.putString("lineChoice", lineText);
        ed.putString("stationChoice", stationText);
        ed.apply();*/

        new transportPlaceDB().execute(lineText, stationText); // Falta el lugar
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
        SharedPreferences.Editor ed = sp.edit();            // Saved the user login credencials.
        ed.putInt("id", transportPlace.getId());
        ed.apply(); //ed.commit()
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
