package es.lost2found.lost2foundUI.announceUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import es.lost2found.database.DB_announce;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.ColorPickerUI;
import es.lost2found.lost2foundUI.DatePickerUI;
import es.lost2found.R;
import es.lost2found.lost2foundUI.TimePickerUI;
import es.lost2found.lost2foundUI.placeUI.PlaceActivity;

public class NewAnnounceActivity extends AppCompatActivity {
    //private DrawerLayout mDrawerLayout;
    private EditText dateText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_announce);

        String[] announceType = {"Pérdida", "Hallazgo"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, announceType);
        MaterialBetterSpinner materialDesignSpinner = findViewById(R.id.announce_type);
        materialDesignSpinner.setAdapter(arrayAdapter);

        String[] categories = {"Tarjetas Bancarias", "Tarjetas Transporte Público", "Carteras/Monederos", "Teléfonos", "Otros"};
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categories);
        MaterialBetterSpinner materialDesignSpinner2 = findViewById(R.id.listCategories);
        materialDesignSpinner2.setAdapter(arrayAdapter2);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent announce = new Intent(this, AnnounceActivity.class);
        startActivity(announce);
        finish();
        return true;
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerUI();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerUI();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showColorPickerDialog(View v) {
        ColorPickerUI colorpicker = new ColorPickerUI();
        //colorpicker.show();
    }

    public void place(View view) {
        Intent intent = new Intent(this, PlaceActivity.class);
        startActivity(intent);
        finish();
    }

    public void createAnnounce(View view) {

        // Id

        // TipoAnuncio (Perdida o Hallazgo)
        MaterialBetterSpinner announceTypeSpinner = findViewById(R.id.announce_type);
        String announceType = announceTypeSpinner.getText().toString();

        // Dia anuncio
        EditText announceDay = findViewById(R.id.date_show);
        String announceDayText = announceDay.getText().toString();

        // Hora actual (de creacion del anuncio) FALTA capturar hora actual con date()) actualhour

        // Hora de la pérdida o hallazgo
        EditText announceLostFoundHourHour = findViewById(R.id.hour_show);
        String announceLostFoundHourHourText = announceLostFoundHourHour.getText().toString();

        // Caducidad ¿? !!

        // Recompensa ¿? !!

        // Categoria
        MaterialBetterSpinner announceCategorieSpinner = findViewById(R.id.listCategories);
        String announceCategorie = announceCategorieSpinner.getText().toString();

        // Marca

        // Modelo

        // Color

        // Lugar
        //Button announcePlace = findViewById(R.id.place_button);
        //announcePlace.get
        // Para obtener los datos del lugar puede que haya que llamar a otras funciones ya que se divide en transporte, mapa o direccion concreta.

        new announceDB().execute(announceType, announceDayText, announceLostFoundHourHourText, announceCategorie); // Falta el lugar

    }

    private class announceDB extends AsyncTask<String, Void, Announce> {

        private ProgressDialog dialog = new ProgressDialog(NewAnnounceActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Creando anuncio...");
            this.dialog.show();
        }

        @Override
        protected Announce doInBackground(String... strings) {
            return DB_announce.insertAnnounce(strings[0], strings[1], strings[2], strings[3]);
        }

        @Override
        protected void onPostExecute(Announce result) {
            this.dialog.dismiss();
            processNewAnnounce(result);
        }
    }

    private void processNewAnnounce(Announce announce) {
        Intent intent = new Intent(this, AnnounceActivity.class);

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
}
