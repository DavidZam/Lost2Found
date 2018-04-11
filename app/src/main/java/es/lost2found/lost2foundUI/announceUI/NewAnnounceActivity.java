package es.lost2found.lost2foundUI.announceUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.lost2found.database.DB_announce;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.pickerUI.ColorPickerUI;
import es.lost2found.lost2foundUI.pickerUI.DatePickerUI;
import es.lost2found.R;
import es.lost2found.lost2foundUI.pickerUI.TimePickerUI;
import es.lost2found.lost2foundUI.placeUI.PlaceActivity;

public class NewAnnounceActivity extends AppCompatActivity {
    //private DrawerLayout mDrawerLayout;
    private EditText dateText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_announce);

        String[] announceType = {"Perdida", "Hallazgo"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, announceType);
        MaterialBetterSpinner materialDesignSpinner = findViewById(R.id.announce_type);
        materialDesignSpinner.setAdapter(arrayAdapter);

        String[] categories = {"Tarjetas Bancarias", "Tarjetas Transporte Publico", "Carteras/Monederos", "Telefonos", "Otros"};
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
        DialogFragment hourFragment = new TimePickerUI();
        hourFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment dateFragment = new DatePickerUI();
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showColorPickerDialog(View v) {
        ColorPickerUI colorpicker = new ColorPickerUI();
        colorpicker.build()
                .title(R.string.color_dialog_title)
                .colorPreset(Color.BLACK)
                .allowCustom(true)
                .show(this, "dialog");
    }


    public void saveData(View view) {
        // Id

        // TipoAnuncio (Perdida o Hallazgo)
        MaterialBetterSpinner announceTypeSpinner = findViewById(R.id.announce_type);
        String announceType = announceTypeSpinner.getText().toString();

        // Dia anuncio
        EditText announceDay = findViewById(R.id.date_show);
        String announceDayText = announceDay.getText().toString();

        // Hora actual (de creacion del anuncio) FALTA capturar hora actual con date()) actualhour
        Date currentTime = Calendar.getInstance().getTime();
        String currentTimeText = String.valueOf(currentTime);

        // Hora de la pérdida o hallazgo
        EditText announceLostFoundHourHour = findViewById(R.id.hour_show);
        String announceLostFoundHourHourText = announceLostFoundHourHour.getText().toString();

        // Caducidad ¿? !!

        // Recompensa ¿? !!

        // Imagen ¿? !!

        // Categoria
        MaterialBetterSpinner announceCategorieSpinner = findViewById(R.id.listCategories);
        String announceCategorie = announceCategorieSpinner.getText().toString();

        // Marca
        EditText announceBrand = findViewById(R.id.marca);
        String announceBrandText = announceBrand.getText().toString();

        // Modelo
        EditText announceModel = findViewById(R.id.model);
        String announceModelText = announceModel.getText().toString();

        // Color
        SharedPreferences sp = getApplicationContext().getSharedPreferences("colorBtn", 0);
        String colorchoice = sp.getString("colorChoice", null);

        // PlaceId
        SharedPreferences sp2 = getApplicationContext().getSharedPreferences("placeId", 0);
        Integer placeId = sp2.getInt("idLugarTte", 0);
        String placeIdText = String.valueOf(placeId);

        // UserId
        SharedPreferences sp3 = getApplicationContext().getSharedPreferences("Login", 0);
        Integer userId = sp3.getInt("userId", 0);
        String userIdext = String.valueOf(userId);

        if(announceType.equalsIgnoreCase("") || announceDayText.equalsIgnoreCase("") || announceLostFoundHourHourText.equalsIgnoreCase("")
                || announceCategorie.equalsIgnoreCase("") || announceBrandText.equalsIgnoreCase("") || announceModelText.equalsIgnoreCase("")
                || colorchoice.equalsIgnoreCase("") || announceDayText.equalsIgnoreCase("")) {
            TextView textView = findViewById(R.id.wrong_information);
            textView.setText(textView.getResources().getString(R.string.error_txt3));
        } else {
            // Para obtener los datos del lugar puede que haya que llamar a otras funciones ya que se divide en transporte, mapa o direccion concreta.
            // Id, TipoAnuncio, HoraActual, DiaAnuncio, HoraPerdidaoHallazgo, Modelo, Marca, Color, idUsuario e idLugar, Categoria (NombreTabla)
            new announceDB().execute(announceType, currentTimeText, announceDayText, announceLostFoundHourHourText, announceModelText, announceBrandText, colorchoice, userIdext, placeIdText, announceCategorie); // Falta el lugar
        }
    }

    private class announceDB extends AsyncTask<String, Void, Announce> {

        private ProgressDialog dialog = new ProgressDialog(NewAnnounceActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected Announce doInBackground(String... strings) {
            return DB_announce.insertAnnounce(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6], strings[7], strings[8], strings[9]);
        }

        @Override
        protected void onPostExecute(Announce result) {
            this.dialog.dismiss();
            processNewAnnounce(result);
        }
    }

    private void processNewAnnounce(Announce announce) {
        Intent intent = new Intent(this, AnnounceActivity.class);
        startActivity(intent);
        finish();

        // El anuncio se crea, falta mostrarlo en la pagina inicial en el Recycler View.

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
