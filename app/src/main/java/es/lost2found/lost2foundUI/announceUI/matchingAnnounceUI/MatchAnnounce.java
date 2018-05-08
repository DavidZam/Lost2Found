package es.lost2found.lost2foundUI.announceUI.matchingAnnounceUI;


import android.app.ProgressDialog;
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
    private Double[] oldAnnounceLatLng;
    private Double[] matchAnnounceLatLng;

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

        private ProgressDialog dialog = new ProgressDialog(MatchAnnounce.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            return DB_announce.getNumberMatchAnnounces(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5]);
        }

        @Override
        protected void onPostExecute(Integer numAnnounce) {
            this.dialog.dismiss();
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

        private ProgressDialog dialog = new ProgressDialog(MatchAnnounce.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected Announce[] doInBackground(String... strings) {
            return DB_announce.getAnnouncesMatch(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6]);
        }

        @Override
        protected void onPostExecute(Announce[] announces) {
            this.dialog.dismiss();
            updateAdapter(announces, numberAnnounces);
        }
    }

    public void updateAdapter(Announce[] announces, Integer numAnnounces) {
        int color1, color2 = Color.TRANSPARENT;
        String coordinates1 = "", coordinates2 = "";
        Announce oldAnnounce = (Announce) getIntent().getSerializableExtra("match");
        if(oldAnnounce != null) {
            color2 = oldAnnounce.getColor();
            coordinates2 = oldAnnounce.getPlace();
        }
        double colorPercentage, distanceDouble;
        String colorPercentajeDouble, colorPercentageText;
        distancePercentagesList = new ArrayList<>();
        distancesList = new ArrayList<>();
        String distanceMetres, distancePercentage;
        colorPercentagesList = new ArrayList<>();
        try {
            Integer idOldAnnounce = oldAnnounce.getIdAnuncio(); // Obtenemos el idAnuncio
            placeIdOldAnnounce = new getPlaceIdByAnnounceIdDB().execute(idOldAnnounce).get(); // Funcion que devuelve el idLugar dado el idAnuncio
            typePlaceOldAnnounce = new getTypePlaceByIdDB().execute(placeIdOldAnnounce).get(); // Funcion que devuelve el tipo de lugar (map, concrete o transport) dado el idLugar
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0; i < numAnnounces; i++) { // Para cada uno de los anuncios que hacen match:
            color1 = announces[i].getColor();
            colorPercentage = getColorPercentage(color1, color2); // Funcion que obtiene el porcentaje de proximidad de dos colores según la distancia euclidea entre ambos
            colorPercentajeDouble = String.valueOf(colorPercentage);
            colorPercentageText = colorPercentajeDouble.substring(0, 5);
            colorPercentagesList.add(i, colorPercentageText);
            try {
                placeIdMatchAnnounce = new getPlaceIdByAnnounceIdDB().execute(announces[i].getIdAnuncio()).get(); // Funcion que devuelve el idLugar dado el idAnuncio
                typePlaceMatchAnnounce = new getTypePlaceByIdDB().execute(placeIdMatchAnnounce).get(); // Funcion que devuelve el tipo de lugar (map, concrete o transport) dado el idLugar
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(typePlaceOldAnnounce != null && typePlaceMatchAnnounce != null) {
                /* Posibilidades tipo de lugar: Transporte, Concrete o Map
                    OldAnnounce:     MatchAnnounce:     Cálculo:
                    Transporte      Transporte          (Cálculo LatLng, Cálculo Distancia)
                    Concrete        Concrete            (Cálculo LatLng, Cálculo Distancia)
                    Map             Map                 (Cálculo Distancia)
                    Transporte      Concrete            (Cálculo LatLng, Cálculo Distancia)
                    Transporte      Map                 (Cálculo LatLng, Cálculo Distancia)
                    Concrete        Map                 (Cálculo LatLng, Cálculo Distancia)
                */
                if (typePlaceOldAnnounce.equals("map") && typePlaceMatchAnnounce.equals("map")) { // Ambos map:
                    // Calculo distancia
                    coordinates1 = announces[i].getPlace();
                    distanceDouble = getDistance(coordinates1, coordinates2);
                    distanceMetres = String.valueOf(distanceDouble);
                    distanceMetres = distanceMetres.substring(0, 6);
                    distancePercentage = getDistancePercentage(distanceMetres);
                    distancePercentagesList.add(i, distancePercentage);
                    distancesList.add(i, distanceMetres);
                } else { // Cualquier otro caso menos ambos map:
                    if(typePlaceOldAnnounce.equals("map")) { // Si oldAnnounce es map
                        coordinates2 = oldAnnounce.getPlace();
                    } else { // Si oldAnnounce no es map
                        // Calculo LatLng para oldAnnounce
                        try {
                            Double[] tmpArray = new getLatitudeAndLongitudeByAdress().execute(oldAnnounce.getPlace(), typePlaceOldAnnounce).get(); // Conseguimos lat y long a partir de address
                            oldAnnounceLatLng = Arrays.copyOf(tmpArray, tmpArray.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        coordinates2 = oldAnnounceLatLng[0] + "," + oldAnnounceLatLng[1];
                    }
                    if(typePlaceMatchAnnounce.equals("map")) { // Si matchAnnounce es map:
                        coordinates1 = announces[i].getPlace();
                    } else { // Si matchAnnounce no es map
                        // Calculo LatLng para matchAnnounce
                        try {
                            Double[] tmpArray = new getLatitudeAndLongitudeByAdress().execute(announces[i].getPlace(), typePlaceMatchAnnounce).get(); // Conseguimos lat y long a partir de address
                            matchAnnounceLatLng = Arrays.copyOf(tmpArray, tmpArray.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        coordinates1 = matchAnnounceLatLng[0] + "," + matchAnnounceLatLng[1];
                    }
                    // Calculo distancia
                    distanceDouble = getDistance(coordinates1, coordinates2);
                    distanceMetres = String.valueOf(distanceDouble);
                    distanceMetres = distanceMetres.substring(0, 6);
                    distancePercentage = getDistancePercentage(distanceMetres);
                    distancePercentagesList.add(i, distancePercentage);
                    distancesList.add(i, distanceMetres);
                }
            }


                /*else if(typePlaceOldAnnounce.equals("concrete") && typePlaceMatchAnnounce.equals("concrete") || // Uno map, otro concrete o ambos concrete
                            typePlaceOldAnnounce.equals("concrete") && typePlaceMatchAnnounce.equals("map") ||
                                typePlaceOldAnnounce.equals("map") && typePlaceMatchAnnounce.equals("concrete")) {
                    if(typePlaceOldAnnounce.equals("concrete") && typePlaceMatchAnnounce.equals("concrete")) { // ambos concrete
                        // Calculo LatLng para ambos
                        try {
                            Double[] tmpArray = new getLatitudeAndLongitudeByAdress().execute(oldAnnounce.getPlace(), typePlaceOldAnnounce).get(); // Conseguimos lat y long a partir de address
                            oldAnnounceLatLng = Arrays.copyOf(tmpArray, tmpArray.length);
                            Double[] tmpArray2 = new getLatitudeAndLongitudeByAdress().execute(announces[i].getPlace(), typePlaceMatchAnnounce).get(); // Conseguimos lat y long a partir de address
                            matchAnnounceLatLng = Arrays.copyOf(tmpArray2, tmpArray2.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        coordinates1 = matchAnnounceLatLng[0] + "," + matchAnnounceLatLng[1];
                        coordinates2 = oldAnnounceLatLng[0] + "," + oldAnnounceLatLng[1];
                    } else if(typePlaceOldAnnounce.equals("concrete")) { // OldAnnounce concrete
                        // Calculo LatLng para oldAnnounce
                        try {
                            Double[] tmpArray = new getLatitudeAndLongitudeByAdress().execute(oldAnnounce.getPlace(), typePlaceOldAnnounce).get(); // Conseguimos lat y long a partir de address
                            oldAnnounceLatLng = Arrays.copyOf(tmpArray, tmpArray.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        coordinates2 = oldAnnounceLatLng[0] + "," + oldAnnounceLatLng[1];
                    } else if(typePlaceMatchAnnounce.equals("concrete")) { // matchAnnounce concrete
                        // Calculo LatLng para matchAnnounce
                        try {
                            Double[] tmpArray = new getLatitudeAndLongitudeByAdress().execute(announces[i].getPlace(), typePlaceMatchAnnounce).get(); // Conseguimos lat y long a partir de address
                            matchAnnounceLatLng = Arrays.copyOf(tmpArray, tmpArray.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        coordinates1 = matchAnnounceLatLng[0] + "," + matchAnnounceLatLng[1];
                    }
                    // Calculo distancia
                    distanceDouble = getDistance(coordinates1, coordinates2);
                    distanceMetres = String.valueOf(distanceDouble);
                    distanceMetres = distanceMetres.substring(0, 6);
                    distancePercentage = getDistancePercentage(distanceMetres);
                    distancePercentagesList.add(i, distancePercentage);
                    distancesList.add(i, distanceMetres);
                    }
                } else { // Alguno es transporte

                }*/


                /*if(typePlaceOldAnnounce.equals("map") && typePlaceMatchAnnounce.equals("map") || // Ambos son map
                        typePlaceOldAnnounce.equals("concrete") && typePlaceMatchAnnounce.equals("concrete") || // Ambos son concrete
                            typePlaceOldAnnounce.equals("map") && typePlaceMatchAnnounce.equals("concrete") || // Uno es map y otro concrete
                                typePlaceOldAnnounce.equals("concrete") && typePlaceMatchAnnounce.equals("map")) { // Uno es concrete y otro map
                    coordinates1 = announces[i].getPlace();
                    distanceDouble = getDistance(coordinates1, coordinates2);
                    distanceMetres = String.valueOf(distanceDouble);
                    distanceMetres = distanceMetres.substring(0, 6);
                    distancePercentage = getDistancePercentage(distanceMetres);
                    distancePercentagesList.add(i, distancePercentage);
                    distancesList.add(i, distanceMetres);
                } else { // En otro caso: alguno es transport
                    if(!typePlaceOldAnnounce.equals("map")) {
                        try {
                            Double[] tmpArray = new getLatitudeAndLongitudeByAdress().execute(oldAnnounce.getPlace()).get(); // Conseguimos lat y long a partir de address
                            oldAnnounceLatLng = Arrays.copyOf(tmpArray, tmpArray.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if(!typePlaceMatchAnnounce.equals("map")) {
                        try {
                            matchAnnounceLatLng = new getLatitudeAndLongitudeByAdress().execute(announces[i].getPlace()).get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }*/

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

        private ProgressDialog dialog = new ProgressDialog(MatchAnnounce.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(Integer... params) {
            return DB_place.getTypePlaceById(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            this.dialog.dismiss();
        }
    }

    private class getPlaceIdByAnnounceIdDB extends AsyncTask<Integer, Void, Integer> {

        private ProgressDialog dialog = new ProgressDialog(MatchAnnounce.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            return DB_announce.getPlaceIdByAnnounceId(params[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            this.dialog.dismiss();
        }
    }

    private class getLatitudeAndLongitudeByAdress extends AsyncTask<String, Void, Double[]> {

        private ProgressDialog dialog = new ProgressDialog(MatchAnnounce.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected Double[] doInBackground(String... strings) {
            return DB_place.callGeocodingGoogleMapsApi(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(Double[] result) {
            this.dialog.dismiss();
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
