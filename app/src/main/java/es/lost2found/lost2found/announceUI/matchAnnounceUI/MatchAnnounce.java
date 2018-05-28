package es.lost2found.lost2found.announceUI.matchAnnounceUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.lost2found.R;
import es.lost2found.database.DB_announce;
import es.lost2found.database.DB_place;
import es.lost2found.entities.Announce;
import es.lost2found.entities.OpenDataAnnounce;
import es.lost2found.lost2found.seekerUI.SeekerAnnounceInfoActivity;

public class MatchAnnounce extends AppCompatActivity {

    private Integer numberAnnounces;
    private Integer listElements = 0;
    private Integer openDataListElements = 0;
    private MatchAnnounceViewAdapter adapter;
    private RecyclerView recyclerView;
    private OpenDataMatchAnnounceViewAdapter openDataAdapter;
    private Announce a;
    private Announce oldAnnounce;
    private String atributoDeterminante;
    private List<String> colorPercentagesList;
    private List<String> distancePercentagesList;
    private List<String> openDataDistancePercentagesList;
    private List<String> matchPercentagesList;
    private List<String> openDataMatchPercentagesList;
    private List<String> distancesList;
    private List<String> openDataDistancesList;
    private String typePlaceOldAnnounce;
    private String typePlaceMatchAnnounce;
    private int placeIdOldAnnounce;
    private Double[] oldAnnounceLatLng;
    private Double[] matchAnnounceLatLng;
    private List<Announce>  announceList;
    private List<OpenDataAnnounce> openDataAnnounceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_announce);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));

        SharedPreferences spref2 = getApplicationContext().getSharedPreferences("Login", 0);
        String userEmail = spref2.getString("email", "");
        announceList = new ArrayList<>();
        openDataAnnounceList = new ArrayList<>();

        if(getIntent().getBooleanExtra("oldAnnounceSet", false)) {
            oldAnnounce = (Announce) getIntent().getSerializableExtra("match");
            atributoDeterminante = getIntent().getStringExtra("atributoDeterminante");
        }

        a = (Announce) getIntent().getSerializableExtra("match");
        boolean openDataMatching = getIntent().getBooleanExtra("openDataMatching", false);

        if(openDataMatching) {
            openDataAdapter = new OpenDataMatchAnnounceViewAdapter(openDataAnnounceList, oldAnnounce, openDataDistancePercentagesList, openDataDistancesList, typePlaceOldAnnounce, "transport", openDataMatchPercentagesList);
            recyclerView = findViewById(R.id.match_announce_reyclerview);
            recyclerView.setAdapter(openDataAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            if(!openDataAdapter.getListAnnounce().isEmpty()) {
                openDataAdapter.getListAnnounce().clear();
                listElements = 0;
                recyclerView.setAdapter(openDataAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
            String announceType = getIntent().getStringExtra("announceType");
            new getOpenDataAnnounces().execute(oldAnnounce.announceCategorie, oldAnnounce.announceDateText, announceType);
        } else {
            adapter = new MatchAnnounceViewAdapter(announceList, userEmail, oldAnnounce, atributoDeterminante, colorPercentagesList, distancePercentagesList, distancesList, typePlaceOldAnnounce, typePlaceMatchAnnounce, matchPercentagesList);
            recyclerView = findViewById(R.id.match_announce_reyclerview);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            if(!adapter.getListAnnounce().isEmpty()) {
                adapter.getListAnnounce().clear();
                listElements = 0;
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
            if(a != null) {
                new getNumberObjectAnnouncesDB().execute(userEmail, a.announceCategorie, a.announceType, a.announceDateText, String.valueOf(a.getIdAnuncio()), atributoDeterminante);
            }
        }
    }

    private class getOpenDataAnnounces extends AsyncTask<String, Void, List<OpenDataAnnounce>> {

        private ProgressDialog dialog = new ProgressDialog(MatchAnnounce.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected List<OpenDataAnnounce> doInBackground(String... strings) {
            if(strings[2].equals("Perdida")) {
                return DB_announce.getMatchOpenDataFoundAnnounces(strings[0], strings[1]);
            } else {
                return DB_announce.getMatchOpenDataLostAnnounces(strings[0], strings[1]);
            }
        }

        @Override
        protected void onPostExecute(List<OpenDataAnnounce> announces) {
            processOpenDataAnnounceScreen(announces);
            this.dialog.dismiss();
        }
    }

    public void processOpenDataAnnounceScreen(List<OpenDataAnnounce> announces) {
        Integer numAnnouncesOpenData = announces.size();
        if (numAnnouncesOpenData == 0) {
            TextView noannounces = findViewById(R.id.without_match);
            noannounces.setText(noannounces.getResources().getString(R.string.info_txt2));
        } else {
            TextView noannounces = findViewById(R.id.without_match);
            noannounces.setText("");
            String coordinates1, coordinates2 = "";
            Announce oldAnnounce = (Announce) getIntent().getSerializableExtra("match");
            if(oldAnnounce != null) {
                coordinates2 = oldAnnounce.getPlace();
            }
            double distanceDouble;
            openDataDistancePercentagesList = new ArrayList<>();
            openDataDistancesList = new ArrayList<>();
            String distanceMetres, distancePercentage = "";
            openDataMatchPercentagesList = new ArrayList<>();
            try {
                if(oldAnnounce != null) {
                    Integer idOldAnnounce = oldAnnounce.getIdAnuncio(); // Obtenemos el idAnuncio
                    placeIdOldAnnounce = new getPlaceIdByAnnounceIdDB().execute(idOldAnnounce).get(); // Funcion que devuelve el idLugar dado el idAnuncio
                    typePlaceOldAnnounce = new getTypePlaceByIdDB().execute(placeIdOldAnnounce).get(); // Funcion que devuelve el tipo de lugar (map, concrete o transport) dado el idLugar
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            typePlaceMatchAnnounce = "transport";
            for(int i = 0; i < numAnnouncesOpenData; i++) { // Para cada uno de los anuncios que hacen match:
                if(typePlaceOldAnnounce != null) { // typePlaceMatchAnnounce SIEMPRE es transport
                    /* Posibilidades segun el tipo de lugar:
                    Announce:       Cálculo
                    Map             (Cálculo Distancia)
                    != Map          (Cálculo LatLng, Cálculo Distancia)*/
                    // Calculo LatLng para matchAnnounce
                    if(!announces.get(i).getPlace().equals(" ")) { // Distancia disponible
                        try {
                            Double[] tmpArray = new getLatitudeAndLongitudeByAdress().execute(announces.get(i).getPlace(), typePlaceMatchAnnounce).get(); // Conseguimos lat y long a partir de address
                            matchAnnounceLatLng = Arrays.copyOf(tmpArray, tmpArray.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        coordinates1 = matchAnnounceLatLng[0] + "," + matchAnnounceLatLng[1];
                        if(typePlaceOldAnnounce.equals("map")) { // Si oldAnnounce es map
                            if(oldAnnounce != null)
                                coordinates2 = oldAnnounce.getPlace();
                        } else { // Si oldAnnounce no es map
                            // Calculo LatLng para oldAnnounce
                            try {
                                if(oldAnnounce != null) {
                                    Double[] tmpArray = new getLatitudeAndLongitudeByAdress().execute(oldAnnounce.getPlace(), typePlaceOldAnnounce).get(); // Conseguimos lat y long a partir de address
                                    oldAnnounceLatLng = Arrays.copyOf(tmpArray, tmpArray.length);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            coordinates2 = oldAnnounceLatLng[0] + "," + oldAnnounceLatLng[1];
                        }
                        // Calculo distancia
                        if(coordinates2 != null) {
                            distanceDouble = getDistance(coordinates1, coordinates2);
                            distanceMetres = String.valueOf(distanceDouble);
                            distancePercentage = getDistancePercentage(distanceMetres);
                            openDataDistancePercentagesList.add(i, distancePercentage);
                            openDataDistancesList.add(i, distanceMetres);
                        }
                        // Calculo del match final a partir de las caracteristicas especificas de los dos objetos y el porcentaje de la distancia:
                        Double distancePercentageInt = Double.valueOf(distancePercentage);
                        String matchPercentaje = getOpenDataMatchPercentage(distancePercentageInt, oldAnnounce, announces.get(i));
                        String matchPercentajeArray[] = matchPercentaje.split("\\.");
                        if(matchPercentajeArray[1].length() == 1) {
                            matchPercentajeArray[1] = matchPercentajeArray[1] + "0";
                        }
                        matchPercentaje = matchPercentajeArray[0] + "." + matchPercentajeArray[1].substring(0, 2);
                        Double matchPercentajeDouble = Double.valueOf(matchPercentaje);
                        if(matchPercentajeDouble != null)
                            announces.get(i).setMatchPercentage(matchPercentajeDouble);
                        openDataMatchPercentagesList.add(i, matchPercentaje);
                        openDataAdapter.insert(openDataListElements, announces.get(i));
                        openDataListElements++;
                        recyclerView.setAdapter(openDataAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    } else { // Distancia no disponible
                        openDataDistancePercentagesList.add(i, " ");
                        openDataDistancesList.add(i, " ");
                        String matchPercentage = getOpenDataMatchPercentage(0.0, oldAnnounce, announces.get(i));
                        String matchPercentageArray[] = matchPercentage.split("\\.");
                        if(matchPercentageArray[1].length() == 1) {
                            matchPercentageArray[1] = matchPercentageArray[1] + "0";
                        }
                        matchPercentage = matchPercentageArray[0] + "." + matchPercentageArray[1].substring(0, 2);
                        Double matchPercentajeDouble = Double.valueOf(matchPercentage);
                        if(matchPercentajeDouble != null)
                            announces.get(i).setMatchPercentage(matchPercentajeDouble);

                        openDataMatchPercentagesList.add(i, matchPercentage);
                        openDataAdapter.insert(openDataListElements, announces.get(i));
                        openDataListElements++;
                        recyclerView.setAdapter(openDataAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    }
                }
            }

            Collections.sort(openDataAnnounceList, (o1, o2) -> {
                Double o1d = o1.getMatchPercentage();
                Double o2d = o2.getMatchPercentage();
                return Double.compare(o2d, o1d);
            });
            openDataAdapter.setAnnounceList(openDataAnnounceList);
            openDataAdapter.notifyDataSetChanged();

            Collections.sort(openDataMatchPercentagesList, (s1, s2) -> {
                Double o1d = Double.valueOf(s1);
                Double o2d = Double.valueOf(s2);
                return Double.compare(o2d, o1d);
            });

            openDataAdapter.setListPercentageDistance(openDataDistancePercentagesList);
            openDataAdapter.setListDistance(openDataDistancesList);
            openDataAdapter.setTypePlaceOldAnnounce(typePlaceOldAnnounce);
            openDataAdapter.setTypePlaceMatchAnnounce(); // typePlaceMatchAnnounce siempre es transport
            openDataAdapter.setListPercentageMatch(openDataMatchPercentagesList);
        }
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
            processAnnounceScreen(numAnnounce);
            this.dialog.dismiss();
        }
    }

    public void processAnnounceScreen(Integer numAnnounces) {
        if (numAnnounces == 0) {
            TextView noAnnounces = findViewById(R.id.without_match);
            noAnnounces.setText(noAnnounces.getResources().getString(R.string.info_txt2));
        } else {
            TextView noAnnounces = findViewById(R.id.without_match);
            noAnnounces.setText("");
            numberAnnounces = numAnnounces;
            SharedPreferences spref = getApplicationContext().getSharedPreferences("Login", 0);
            String userEmail = spref.getString("email", "");
            new getObjectAnnouncesDB().execute(userEmail, a.announceCategorie, a.announceType, String.valueOf(numberAnnounces), a.announceDateText, atributoDeterminante);
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
            return DB_announce.getAnnouncesMatch(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5]);
        }

        @Override
        protected void onPostExecute(Announce[] announces) {
            updateAdapter(announces, numberAnnounces);
            this.dialog.dismiss();
        }
    }

    public void updateAdapter(Announce[] announces, Integer numAnnounces) {
        int color1, color2 = Color.TRANSPARENT;
        String coordinates1, coordinates2 = "";
        Announce oldAnnounce = (Announce) getIntent().getSerializableExtra("match");
        if(oldAnnounce != null) {
            color2 = oldAnnounce.getColor();
            coordinates2 = oldAnnounce.getPlace();
        }
        double colorPercentage, distanceDouble;
        String colorPercentageDouble, colorPercentageText;
        distancePercentagesList = new ArrayList<>();
        distancesList = new ArrayList<>();
        String distanceMetres, distancePercentage = "";
        colorPercentagesList = new ArrayList<>();
        matchPercentagesList = new ArrayList<>();
        try {
            if(oldAnnounce != null) {
                Integer idOldAnnounce = oldAnnounce.getIdAnuncio(); // Obtenemos el idAnuncio
                placeIdOldAnnounce = new getPlaceIdByAnnounceIdDB().execute(idOldAnnounce).get(); // Funcion que devuelve el idLugar dado el idAnuncio
                typePlaceOldAnnounce = new getTypePlaceByIdDB().execute(placeIdOldAnnounce).get(); // Funcion que devuelve el tipo de lugar (map, concrete o transport) dado el idLugar
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0; i < numAnnounces; i++) { // Para cada uno de los anuncios que hacen match:
            color1 = announces[i].getColor();
            colorPercentage = getColorPercentage(color1, color2); // Funcion que obtiene el porcentaje de proximidad de dos colores según la distancia euclidea entre ambos
            colorPercentageDouble = String.valueOf(colorPercentage);
            colorPercentageText = colorPercentageDouble.substring(0, 5);
            colorPercentagesList.add(i, colorPercentageText);
            try {
                int placeIdMatchAnnounce;
                placeIdMatchAnnounce = new getPlaceIdByAnnounceIdDB().execute(announces[i].getIdAnuncio()).get(); // Funcion que devuelve el idLugar dado el idAnuncio
                typePlaceMatchAnnounce = new getTypePlaceByIdDB().execute(placeIdMatchAnnounce).get(); // Funcion que devuelve el tipo de lugar (map, concrete o transport) dado el idLugar
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(typePlaceOldAnnounce != null && typePlaceMatchAnnounce != null) {
                /* Posibilidades segun el tipo de lugar:
                    Announce:       Cálculo
                    Map             (Cálculo Distancia)
                    != Map          (Cálculo LatLng, Cálculo Distancia)
                */
                if (typePlaceOldAnnounce.equals("map") && typePlaceMatchAnnounce.equals("map")) { // Ambos map:
                    // Calculo distancia
                    coordinates1 = announces[i].getPlace();
                    distanceDouble = getDistance(coordinates1, coordinates2);
                    distanceMetres = String.valueOf(distanceDouble);
                    distancePercentage = getDistancePercentage(distanceMetres);
                    distancePercentagesList.add(i, distancePercentage);
                    distancesList.add(i, distanceMetres);
                } else { // Cualquier otro caso menos ambos map:
                    if(typePlaceOldAnnounce.equals("map")) { // Si oldAnnounce es map
                        if(oldAnnounce != null)
                        coordinates2 = oldAnnounce.getPlace();
                    } else { // Si oldAnnounce no es map
                        // Calculo LatLng para oldAnnounce
                        try {
                            if(oldAnnounce != null) {
                                Double[] tmpArray = new getLatitudeAndLongitudeByAdress().execute(oldAnnounce.getPlace(), typePlaceOldAnnounce).get(); // Conseguimos lat y long a partir de address
                                oldAnnounceLatLng = Arrays.copyOf(tmpArray, tmpArray.length);
                            }
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
                    if(coordinates1 != null && coordinates2 != null) {
                        distanceDouble = getDistance(coordinates1, coordinates2);
                        distanceMetres = String.valueOf(distanceDouble);
                        distancePercentage = getDistancePercentage(distanceMetres);
                        distancePercentagesList.add(i, distancePercentage);
                        distancesList.add(i, distanceMetres);
                    }
                }
            }

            // Calculo del match final a partir de las caracteristicas especificas de los dos objetos, el porcentaje del color y el porcentaje de la distancia:
            Double distancePercentageInt = Double.valueOf(distancePercentage);
            String matchPercentaje = getMatchPercentage(distancePercentageInt, colorPercentage, oldAnnounce, announces[i]);
            String matchPercentajeArray[] = matchPercentaje.split("\\.");
            if(matchPercentajeArray[1].length() == 1) {
                matchPercentajeArray[1] = matchPercentajeArray[1] + "0";
            }
            matchPercentaje = matchPercentajeArray[0] + "." + matchPercentajeArray[1].substring(0, 2);

            announces[i].setMatchPercentage(Double.valueOf(matchPercentaje));

            matchPercentagesList.add(i, matchPercentaje);

            adapter.insert(listElements, announces[i]);
            listElements++;
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        Collections.sort(announceList, (o1, o2) -> {
            Double o1d = o1.getMatchPercentage();
            Double o2d = o2.getMatchPercentage();
            return Double.compare(o2d, o1d);
        });
        adapter.setAnnounceList(announceList);
        adapter.notifyDataSetChanged();

        Collections.sort(matchPercentagesList, (s1, s2) -> {
            Double o1d = Double.valueOf(s1);
            Double o2d = Double.valueOf(s2);
            return Double.compare(o2d, o1d);
        });

        Collections.reverse(colorPercentagesList);
        Collections.reverse(distancePercentagesList);
        Collections.reverse(distancesList);

        adapter.setListPercentageColor(colorPercentagesList);
        adapter.setListPercentageDistance(distancePercentagesList);
        adapter.setListDistance(distancesList);
        adapter.setTypePlaceOldAnnounce(typePlaceOldAnnounce);
        adapter.setTypePlaceMatchAnnounce(typePlaceMatchAnnounce);
        adapter.setListPercentageMatch(matchPercentagesList);
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
        double percentageTmp;
        double percentage;

        double red1 = (matchAnnounceColorInt >> 16) & 0xFF;
        double green1 = (matchAnnounceColorInt >> 8) & 0xFF;
        double blue1 = (matchAnnounceColorInt >> matchAnnounceColorInt) & 0xFF; //

        double red2 = (oldAnnounceColorInt >> 16) & 0xFF;
        double green2 = (oldAnnounceColorInt >> 8) & 0xFF;
        double blue2 = (oldAnnounceColorInt >> oldAnnounceColorInt) & 0xFF; //

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

        return d; // Nos da la distancia
    }

    public double radius(double num) {
        return num * Math.PI / 180;
    }

    public String getDistancePercentage(String distance) {
        String percentageText = "";
        Double percentage;
        Double Ddistance = Double.parseDouble(distance);
        if(Ddistance >= 2000.00) { // >= 1km
            percentage = 0.00;
            percentageText = String.valueOf(percentage);
        } else { // < 1 km
            if (Ddistance <= 100) {
                percentage = 100.00;
                percentageText = String.valueOf(percentage);
                percentageText = percentageText.substring(0, 4);
            } else if (Ddistance > 100 && Ddistance < 999) {
                Double tmp;
                tmp = Ddistance / 100;
                percentage = 100 - (tmp * 10);
                //percentage = 1000 - Ddistance;
                //percentage /= 10;
                percentageText = String.valueOf(percentage);
                percentageText = percentageText.substring(0, 4);
            }
        }
        return percentageText; // Nos da el porcentaje de aproximación
    }

    public String getMatchPercentage(Double distancePercentage, Double colorPercentage, Announce oldAnnounce, Announce matchAnnounce) {
        Double matchPercentageDouble;

        // Formula del match...
        Double colorMultiplier = 0.225;
        Double distanceMultiplier = 0.725;

        matchPercentageDouble = colorPercentage * colorMultiplier + distancePercentage * distanceMultiplier;

        String oldAnnounceDay = oldAnnounce.getAnnounceDateText().substring(oldAnnounce.getAnnounceDateText().length()-2);
        Integer oldAnnounceDayInt = Integer.valueOf(oldAnnounceDay);
        String matchAnnounceDay = matchAnnounce.getAnnounceDateText().substring(matchAnnounce.getAnnounceDateText().length()-2);
        Integer matchAnnounceDayInt = Integer.valueOf(matchAnnounceDay);
        // Por cada día más de diferencia se le resta al porcentaje...
        if(oldAnnounce.getAnnounceType().equals("Perdida")) { // oldAnnounceDayInt <= matchAnnounceDayInt
            if(oldAnnounceDayInt - matchAnnounceDayInt <= 3) {
                matchPercentageDouble += 5.0; // Le sumamos 10 al porcentaje si los anuncios tienen 3 dias o menos de diferencia
            }
        } else { // oldAnnounceDayInt >= matchAnnounceDayInt
            if(matchAnnounceDayInt - oldAnnounceDayInt <= 3) {
                matchPercentageDouble += 5.0; // Le sumamos 10 al porcentaje si los anuncios tienen 3 dias o menos de diferencia
            }
        }

        return String.valueOf(matchPercentageDouble); // Nos da el porcentaje de match entre los dos anuncios
    }

    public String getOpenDataMatchPercentage(Double distancePercentage, Announce oldAnnounce, OpenDataAnnounce matchAnnounce) {
        Double matchPercentageDouble;

        // Formula del match...
        Double distanceMultiplier = 0.7;

        matchPercentageDouble = distancePercentage * distanceMultiplier;

        if(distancePercentage != 0) {
            String oldAnnounceDay = oldAnnounce.getAnnounceDateText().substring(oldAnnounce.getAnnounceDateText().length()-2);
            Integer oldAnnounceDayInt = Integer.valueOf(oldAnnounceDay);
            String matchAnnounceDay = matchAnnounce.getAnnounceDateText().substring(matchAnnounce.getAnnounceDateText().length()-2);
            Integer matchAnnounceDayInt = Integer.valueOf(matchAnnounceDay);
            // Por cada día más de diferencia se le resta al porcentaje...
            if(oldAnnounce.getAnnounceType().equals("Perdida")) { // oldAnnounceDayInt <= matchAnnounceDayInt
                if(oldAnnounceDayInt - matchAnnounceDayInt <= 1) {
                    matchPercentageDouble += 30.0; // Le sumamos 10 al porcentaje si los anuncios tienen 3 dias o menos de diferencia
                } else if(oldAnnounceDayInt - matchAnnounceDayInt == 2) {
                    matchPercentageDouble += 20.0;
                } else if(oldAnnounceDayInt - matchAnnounceDayInt >= 3 && oldAnnounceDayInt - matchAnnounceDayInt < 5) {
                    matchPercentageDouble += 10.0;
                }
            } else { // oldAnnounceDayInt >= matchAnnounceDayInt
                if(matchAnnounceDayInt - oldAnnounceDayInt <= 1) {
                    matchPercentageDouble += 30.0; // Le sumamos 10 al porcentaje si los anuncios tienen 3 dias o menos de diferencia
                } else if(matchAnnounceDayInt - oldAnnounceDayInt == 2) {
                    matchPercentageDouble += 20.0;
                } else if(matchAnnounceDayInt - oldAnnounceDayInt >= 3 && matchAnnounceDayInt - oldAnnounceDayInt < 5) {
                    matchPercentageDouble += 10.0;
                }
            }
        }

        return String.valueOf(matchPercentageDouble); // Nos da el porcentaje de match entre los dos anuncios
    }
}
