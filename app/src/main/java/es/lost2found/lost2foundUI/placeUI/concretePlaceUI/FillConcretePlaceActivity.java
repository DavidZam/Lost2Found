package es.lost2found.lost2foundUI.placeUI.concretePlaceUI;

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
import es.lost2found.database.DB_concretePlace;
import es.lost2found.entities.ConcretePlace;
import es.lost2found.lost2foundUI.announceUI.NewAnnounceActivity;
import es.lost2found.lost2foundUI.placeUI.PlaceActivity;

public class FillConcretePlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concrete_place);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent place = new Intent(this, PlaceActivity.class);
        startActivity(place);
        finish();
        return true;
    }

    public void saveConcretePlaceData(View view) {
        // Calle
        EditText placeStreet = findViewById(R.id.street);
        String placeStreetText = placeStreet.getText().toString();

        // Numero
        EditText placeNumber = findViewById(R.id.number);
        String placeNumberText = placeNumber.getText().toString();

        // Cod Postal
        EditText placePostalCode = findViewById(R.id.postalCode);
        String placePostalCodeText = placePostalCode.getText().toString();

        new insertConcretePlaceDB().execute(placeStreetText, placeNumberText, placePostalCodeText);
    }

    private class insertConcretePlaceDB extends AsyncTask<String, Void, ConcretePlace> {

        private ProgressDialog dialog = new ProgressDialog(FillConcretePlaceActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected ConcretePlace doInBackground(String... strings) {
            return DB_concretePlace.insertConcretePlace(strings[0], strings[1], strings[2]);
        }

        @Override
        protected void onPostExecute(ConcretePlace result) {
            this.dialog.dismiss();
            processNewConcretePlace(result);
        }
    }

    private void processNewConcretePlace(ConcretePlace concretePlace) {
        Intent intent = new Intent(this, NewAnnounceActivity.class);
        SharedPreferences sp = getSharedPreferences("placeId", 0);
        SharedPreferences.Editor ed = sp.edit();  // Saved the place data filled by the user.
        Integer placeId = concretePlace.getId(); // transportPlace.getId();

        ed.putInt("idLugar", placeId); // Comprobar
        ed.apply();

        //intent.putExtra("concretePlace", concretePlace);
        startActivity(intent);
        finish();
    }
}
