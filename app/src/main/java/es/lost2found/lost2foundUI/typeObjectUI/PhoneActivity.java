package es.lost2found.lost2foundUI.typeObjectUI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import es.lost2found.R;
import es.lost2found.database.DB_typeObject;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;

public class PhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_phone);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
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

        // Marca
        EditText announceBrand = findViewById(R.id.marca);
        String announceBrandText = announceBrand.getText().toString();

        // Modelo
        EditText announceModel = findViewById(R.id.model);
        String announceModelText = announceModel.getText().toString();

        // IMEI
        EditText announceIMEI = findViewById(R.id.IMEI);
        String announceIMEIText = announceIMEI.getText().toString();

        // tara
        EditText announceTara = findViewById(R.id.tara);
        String announceTaraText = announceTara.getText().toString();

        if(announceBrandText.equalsIgnoreCase("") || announceModelText.equalsIgnoreCase("") || announceIMEIText.equalsIgnoreCase("") || announceTaraText.equalsIgnoreCase("")) {
            TextView textView = findViewById(R.id.wrong_information);
            textView.setText(textView.getResources().getString(R.string.error_txt3));
        } else {
            new saveObjectDB().execute(objectIdText, categorie, announceBrandText, announceModelText, announceIMEIText, announceTaraText);
        }
    }

    private class saveObjectDB extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return DB_typeObject.insertObject(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5]);
        }

        @Override
        protected void onPostExecute(String result) {
            showAnnounceScreen(result);
        }
    }

    private void showAnnounceScreen(String announce) {
        Intent intent = new Intent(this, AnnounceActivity.class);
        startActivity(intent);
    }

}
