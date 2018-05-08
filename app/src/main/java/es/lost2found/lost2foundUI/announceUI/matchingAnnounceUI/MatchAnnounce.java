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
import java.util.Arrays;
import java.util.List;

import es.lost2found.R;
import es.lost2found.database.DB_announce;
import es.lost2found.database.DB_place;
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
    private List<String> colorPercentagesList;
    private List<String> distancePercentagesList;
    private List<String> distancesList;
    private String typePlace;
    private String typePlaceOldAnnounce;
    private String typePlaceMatchAnnounce;
    private int placeIdOldAnnounce;
    private int placeIdMatchAnnounce;
    private Integer[] oldAnnounceLocation;
    private Integer[] matchAnnounceLocation;

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

        //typePlace = getIntent().getExtras().getString("typePlace");
        adapter = new MatchAnnounceViewAdapter(announceList, getApplication(), userEmail, oldAnnounce, atributoDeterminante, colorPercentagesList, distancePercentagesList, distancesList, typePlaceOldAnnounce, typePlaceMatchAnnounce);
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

            String place = ""; // Se calcula en la llamada getAnnouncesMatch() de la AsyncTask getObjectAnnouncesDB

            new getObjectAnnouncesDB().execute(userEmail, a.announceCategorie, a.announceType, String.valueOf(numberAnnounces), place, a.announceDateText, atributoDeterminante);
        }
    }

    private class getObjectAnnouncesDB extends AsyncTask<String, Void, Announce[]> {

        @Override
        protected Announce[] doInBackground(String... strings) {
            return DB_announce.getAnnouncesMatch(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6]);
        }

        @Override
        protected void onPostExecute(Announce[] announces) {
            updateAdapter(announces, numberAnnounces);
        }
    }

    public void updateAdapter(Announce[] announces, Integer numAnnounces) {
        int color1;
        int color2 = Color.TRANSPARENT;
        String coordinates1;
        String coordinates2 = "";
        Announce oldAnnounce = (Announce) getIntent().getSerializableExtra("match");
        if(oldAnnounce != null) {
            color2 = oldAnnounce.getColor();
            coordinates2 = oldAnnounce.getPlace();
        }

        double colorPercentage;
        String colorPercentajeDouble;
        String colorPercentageText;
        distancePercentagesList = new ArrayList<>();
        distancesList = new ArrayList<>();
        String distanceMetres;
        double distanceDouble;
        String distancePercentage;
        colorPercentagesList = new ArrayList<>();
        try {
            // Hacer una funcion que dando el id devuelva el idLugar
            Integer idOldAnnounce = oldAnnounce.getIdAnuncio();
            placeIdOldAnnounce = new getPlaceIdByAnnounceIdDB().execute(idOldAnnounce).get();
            typePlaceOldAnnounce = new getTypePlaceByIdDB().execute(placeIdOldAnnounce).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0; i < numAnnounces; i++) {
            color1 = announces[i].getColor();
            colorPercentage = getColorPercentage(color1, color2);
            colorPercentajeDouble = String.valueOf(colorPercentage);
            colorPercentageText = colorPercentajeDouble.substring(0, 5);
            colorPercentagesList.add(i, colorPercentageText);
            try {
                placeIdMatchAnnounce = new getPlaceIdByAnnounceIdDB().execute(announces[i].getIdAnuncio()).get();
                typePlaceMatchAnnounce = new getTypePlaceByIdDB().execute(placeIdMatchAnnounce).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(typePlaceOldAnnounce != null && typePlaceMatchAnnounce != null) {
                if(typePlaceOldAnnounce.equals("map") && typePlaceMatchAnnounce.equals("map")) { // Si los dos son mapa calculamos la distancia
                    coordinates1 = announces[i].getPlace();
                    distanceDouble = getDistance(coordinates1, coordinates2);
                    distanceMetres = String.valueOf(distanceDouble);
                    distanceMetres = distanceMetres.substring(0, 6);
                    distancePercentage = getDistancePercentage(distanceMetres);
                    distancePercentagesList.add(i, distancePercentage);
                    distancesList.add(i, distanceMetres);
                } else { // Si alguno o los dos no son mapa:
                    if(!typePlaceOldAnnounce.equals("map")) {
                        try {
                            Integer[] tmpArray = new getLatitudeAndLongitudeByAdress().execute(oldAnnounce.getPlace()).get(); // COMPROBAR QUE SE GUARDA
                            oldAnnounceLocation = Arrays.copyOf(tmpArray, tmpArray.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(!typePlaceMatchAnnounce.equals("map")) {
                        try {
                            matchAnnounceLocation = new getLatitudeAndLongitudeByAdress().execute(announces[i].getPlace()).get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            adapter.insert(listElements, announces[i]);
            listElements++;
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        adapter.setListPercentageColor(colorPercentagesList);
        adapter.setListPercentageDistance(distancePercentagesList);
        adapter.setListDistance(distancesList);
        adapter.setTypePlaceOldAnnounce(typePlaceOldAnnounce);
        adapter.setTypePlaceMatchAnnounce(typePlaceMatchAnnounce);
    }

    private class getTypePlaceByIdDB extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... params) {
            return DB_place.getTypePlaceById(params[0]);
        }
    }

    private class getPlaceIdByAnnounceIdDB extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            return DB_announce.getPlaceIdByAnnounceId(params[0]);
        }
    }

    private class getLatitudeAndLongitudeByAdress extends AsyncTask<String, Void, Integer[]> {

        @Override
        protected Integer[] doInBackground(String... strings) {
            return DB_place.callGeocodingGoogleMapsApi(strings[0]);
        }

        @Override
        protected void onPostExecute(Integer[] latLong) {
            /*for(int i = 0; i < 1; i++) {
                oldAnnounceLocation[i] = latLong[i];
            }*/
        }
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

    public double getDistance(String matchAnnounceDistance, String oldAnnounceDistance) {
        String[] coordinates1 = matchAnnounceDistance.split(",");
        String[] coordinates2 = oldAnnounceDistance.split(",");
        double lat1 = Double.parseDouble(coordinates1[0]);
        double long1 = Double.parseDouble(coordinates1[1]);
        double lat2 = Double.parseDouble(coordinates2[0]);
        double long2 = Double.parseDouble(coordinates2[1]);

        int earthRadius = 6378137; // Earth’s mean radius in meter
        double dLatitude = radius(lat2 - lat1);
        double dLongitude = radius(long2 - long1);
        double a = Math.sin(dLatitude / 2) * Math.sin(dLatitude / 2) + Math.cos(radius(lat1)) * Math.cos(radius(lat2)) *
                Math.sin(dLongitude / 2) * Math.sin(dLongitude / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = earthRadius * c;

        return d; // Nos da la distancia faltaria calcular el porcentaje de aproximación
    }

    public double radius(double num) {
        return num * Math.PI / 180;
    }

    public String getDistancePercentage(String distance) {
        String percentageText = "";
        Double percentage;
        Double Ddistance = Double.parseDouble(distance);
        if(Ddistance <= 20) {
            percentage = 100.00;
            percentageText = String.valueOf(percentage);
            percentageText = percentageText.substring(0, 4);
        } else if (Ddistance > 20 && Ddistance < 1000){
            percentage = 1000 - Ddistance;
            percentage /= 10;
            percentageText = String.valueOf(percentage);
            percentageText = percentageText.substring(0, 4);
        } else {
            percentage = 0.00;
            percentageText = String.valueOf(percentage);
        }
        return percentageText; // Nos da el porcentaje de aproximación
    }
}
