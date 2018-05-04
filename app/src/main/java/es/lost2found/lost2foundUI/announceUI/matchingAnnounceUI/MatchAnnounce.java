package es.lost2found.lost2foundUI.announceUI.matchingAnnounceUI;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.database.DB_announce;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.seekerUI.SeekerAnnounceInfoActivity;

public class MatchAnnounce extends AppCompatActivity {

    private Integer numberAnnounces;
    private Integer listElements = 0;
    private MatchAnnounceViewAdapter adapter;
    private RecyclerView recyclerView;
    private Announce a;
    private Announce oldAnnounce;
    private String atributoDeterminante;
    private List<String>  colorPercentagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_announce);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);


        SharedPreferences spref2 = getApplicationContext().getSharedPreferences("Login", 0);
        String userEmail = spref2.getString("email", "");
        List<Announce> announceList = new ArrayList<>();

        if(getIntent().getBooleanExtra("oldAnnounceSet", false)) {
            oldAnnounce = (Announce) getIntent().getSerializableExtra("match");
            atributoDeterminante = getIntent().getStringExtra("atributoDeterminante");
        }

        adapter = new MatchAnnounceViewAdapter(announceList, getApplication(), userEmail, oldAnnounce, atributoDeterminante, colorPercentagesList);
        recyclerView = findViewById(R.id.match_announce_reyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(!adapter.getListAnnounce().isEmpty()) {
            adapter.getListAnnounce().clear();
            listElements = 0;
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        a = (Announce) getIntent().getSerializableExtra("match");

        new getNumberObjectAnnouncesDB().execute(userEmail, a.announceCategorie, a.announceType, a.announceDateText, String.valueOf(a.getIdAnuncio()), atributoDeterminante);
    }


    private class getNumberObjectAnnouncesDB extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            return DB_announce.getNumberMatchAnnounces(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5]);
        }

        @Override
        protected void onPostExecute(Integer numAnnounce) {
            processAnnounceScreen(numAnnounce);
        }
    }

    public void processAnnounceScreen(Integer numAnnounces) {
        if (numAnnounces == 0) {
            TextView noannounces = findViewById(R.id.without_match);
            noannounces.setText(noannounces.getResources().getString(R.string.info_txt2));
        } else {
            TextView noannounces = findViewById(R.id.without_match);
            noannounces.setText("");
            numberAnnounces = numAnnounces;
            SharedPreferences spref = getApplicationContext().getSharedPreferences("Login", 0);
            String userEmail = spref.getString("email", "");

            SharedPreferences sp = getApplicationContext().getSharedPreferences("announcePlace", 0);
            String place = sp.getString("place", "");

            new getObjectAnnouncesDB().execute(userEmail, a.announceCategorie, a.announceType, String.valueOf(numberAnnounces), place, a.announceDateText, atributoDeterminante);
        }
    }

    private class getObjectAnnouncesDB extends AsyncTask<String, Void, Announce[]> {

        @Override
        protected Announce[] doInBackground(String... strings) {
            // El error esta en el DB_announce
            return DB_announce.getAnnouncesMatch(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6]);
        }

        @Override
        protected void onPostExecute(Announce[] announces) {
            updateAdapter(announces, numberAnnounces);
        }
    }

    public void updateAdapter(Announce[] announces, Integer numAnnounces) {
        /*if(a != null) {
            color1 = a.getColor();
        }*/
        int color1;
        int color2 = Color.TRANSPARENT;
        Announce oldAnnounce = (Announce) getIntent().getSerializableExtra("match");
        if(oldAnnounce != null) {
            color2 = oldAnnounce.getColor();
        }
        double colorPercentage;
        String colorPercentajeDouble;
        String colorPercentageText;
        colorPercentagesList = new ArrayList<>();
        for(int i = 0; i < numAnnounces; i++) {
            color1 = announces[i].getColor();
            colorPercentage = getColorPercentage(color1, color2);
            colorPercentajeDouble = String.valueOf(colorPercentage);
            colorPercentageText = colorPercentajeDouble.substring(0, 5);
            colorPercentagesList.add(i, colorPercentageText);
            adapter.insert(listElements, announces[i]);
            listElements++;
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        adapter.setListPercentageColor(colorPercentagesList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent announceInfo = new Intent(this, SeekerAnnounceInfoActivity.class);
        if(oldAnnounce == null) {
            oldAnnounce = (Announce) getIntent().getSerializableExtra("oldAnnounce");
        }
        announceInfo.putExtra("oldAnnounce", oldAnnounce);
        announceInfo.putExtra("back", true);
        startActivity(announceInfo);
        finish();
        return true;
    }

    public double getColorPercentage(Integer matchAnnounceColorInt, Integer oldAnnounceColorInt) {
        double maxDistance = 765;
        double percentageTmp = 0;
        double percentage = 0;

        double red1 = (matchAnnounceColorInt >> 16) & 0xFF;
        double green1 = (matchAnnounceColorInt >> 8) & 0xFF;
        double blue1 = (matchAnnounceColorInt >> 0) & 0xFF;

        double red2 = (oldAnnounceColorInt >> 16) & 0xFF;
        double green2 = (oldAnnounceColorInt >> 8) & 0xFF;
        double blue2 = (oldAnnounceColorInt >> 0) & 0xFF;

        double redPowSubtraction = Math.pow(red1 - red2, 2);
        double greenPowSubtraction = Math.pow(green1 - green2, 2);
        double bluePowSubtraction = Math.pow(blue1 - blue2, 2);
        double sumatory = redPowSubtraction + greenPowSubtraction + bluePowSubtraction;
        double distance = Math.sqrt(sumatory);

        percentageTmp = distance * 100 / maxDistance;

        if(percentageTmp <= 1)
            percentage = 100;
        else if(percentageTmp < 10){
            percentage = 100 - percentageTmp;
        } else {
            percentage = percentageTmp;
        }

        return percentage;
    }

    /**
     *
     * @param view
     */
    public void matchingAnnounce(View view) {

    }

}
