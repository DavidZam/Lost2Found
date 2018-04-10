package es.lost2found.lost2foundUI.placeUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import es.lost2found.R;
import es.lost2found.database.DB_place;
import es.lost2found.entities.Place;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;
import es.lost2found.lost2foundUI.placeUI.transportUI.TransportPlaceActivity;

public class PlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

    }

    public void transportPlace(View view) {
        Intent intent = new Intent(this, TransportPlaceActivity.class);
        startActivity(intent);
        finish();
    }

    public void map(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent newannounce = new Intent(this, AnnounceActivity.class);
        startActivity(newannounce);
        finish();
        return true;
    }

    public void savePlaceData(View view) {
        // Place de transporte

        // Ubicacion del mapa

        // Direccion concreta

        // Id, Nombre
        new placeDB().execute();
    }

    private class placeDB extends AsyncTask<String, Void, Place> {

        private ProgressDialog dialog = new ProgressDialog(PlaceActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected Place doInBackground(String... strings) {
            return DB_place.insertPlace();
        }

        @Override
        protected void onPostExecute(Place result) {
            this.dialog.dismiss();
            processNewPlace(result);
        }
    }

    private void processNewPlace(Place place) {
        Intent intent = new Intent(this, PlaceActivity.class);
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
