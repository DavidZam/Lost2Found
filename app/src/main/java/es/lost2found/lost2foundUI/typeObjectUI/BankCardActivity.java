package es.lost2found.lost2foundUI.typeObjectUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import es.lost2found.R;
import es.lost2found.database.DB_typeObject;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;

public class BankCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bank_card);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));
    }

    public void saveData(View view) {
        new getObjectIdDB().execute();
    }

    private class getObjectIdDB extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            return DB_typeObject.getObjectId();
        }

        @Override
        protected void onPostExecute(Integer result) {
            processNewObject(result);
        }
    }

    private void processNewObject(Integer objectId) {
        String objectIdText = String.valueOf(objectId);
        String categorie = getIntent().getExtras().getString("categorie");

        // Banco
        EditText announceBank = findViewById(R.id.bank);
        String announceBankText = announceBank.getText().toString();

        // Datos Propietario
        EditText announceOwner = findViewById(R.id.owner);
        String announceOwnerText = announceOwner.getText().toString();

        if(announceBankText.equalsIgnoreCase("") || announceOwnerText.equalsIgnoreCase("")) {
            TextView textView = findViewById(R.id.wrong_information);
            textView.setText(textView.getResources().getString(R.string.error_txt3));
        } else {
            new saveObjectDB().execute(objectIdText, categorie, announceBankText, announceOwnerText, null, null);
        }
    }

    private class saveObjectDB extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return DB_typeObject.insertObject(strings[0], strings[1], strings[2], strings[3], strings[4]);
        }

        @Override
        protected void onPostExecute(String result) {
            showAnnounceScreen(result);
        }
    }

    private void showAnnounceScreen(String announce) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("announcePlace", 0);
        String place = sp.getString("place", "");
        String typePlace = getIntent().getExtras().getString("typePlace");
        SharedPreferences sp2 = getSharedPreferences("announcePlace", 0);
        SharedPreferences.Editor ed = sp2.edit();            // Saved the user login credencials.
        ed.putString("place", place);
        ed.apply();

        Intent intent = new Intent(this, AnnounceActivity.class);
        intent.putExtra("typePlace", typePlace);
        startActivity(intent);
        finish();
    }

}
