package es.lost2found.lost2foundUI.announceUI.matchAnnounceUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import es.lost2found.R;
import es.lost2found.entities.Announce;
import es.lost2found.entities.OpenDataAnnounce;

public class OpenDataMatchAnnounceInfoActivity extends AppCompatActivity {

    private OpenDataAnnounce a;
    private String distancePercentageText;
    private String distanceText;
    private Integer user1Id;
    private Integer user2Id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opendata_matchannounce_info);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));

        TextView cat = (TextView) findViewById(R.id.categoria);
        TextView type = (TextView) findViewById(R.id.tipo);
        TextView dia = (TextView) findViewById(R.id.dia);
        TextView lugar = (TextView) findViewById(R.id.lugar);
        TextView hora = (TextView) findViewById(R.id.hora);
        TextView distanceMeters = (TextView) findViewById(R.id.distance);
        TextView distancePercentage = (TextView) findViewById(R.id.distancePercentage);

        a = (OpenDataAnnounce) getIntent().getSerializableExtra("myAnnounce");

        distancePercentageText = getIntent().getStringExtra("percentageDistance");
        distanceText = getIntent().getStringExtra("distance");

        String c = "<h4> <font color=#699CFC> Categoría: </font>"+ a.announceCategorie +" </h4><br>";
        cat.setText(Html.fromHtml(c));

        String t = "<h4> <font color=#699CFC> Tipo: </font>"+ a.announceType +" </h4><br>";
        type.setText(Html.fromHtml(t));

        String d = "<h4> <font color=#699CFC> Día: </font>"+ a.DDMMYYYY() +" </h4><br>";
        dia.setText(Html.fromHtml(d));

        String h = "<h4> <font color=#699CFC> Hora: </font>"+ a.lostFoundHourText +" </h4><br>";
        hora.setText(Html.fromHtml(h));

        if(a.place.equals(" ")) { // Lugar no disponible
            String l = "<h4> <font color=#699CFC> Lugar: </font>" + "<font color=#FF0000> Lugar no disponible </font>";
            lugar.setText(Html.fromHtml(l));
        } else {
            String l = "<h4> <font color=#699CFC> Lugar: </font>"+ a.place +" </h4><br>";
            lugar.setText(Html.fromHtml(l));
        }

        if(distancePercentageText.equals(" ") && distanceText.equals(" ")) { // Distancia no disponible
            String distance = "<h4><font color=#699CFC> Cercanía: </font>" + "<font color=#FF0000> Distancia no disponible </font>";
            distanceMeters.setText(Html.fromHtml(distance));
        } else { // Distancia disponible
            String distancePerc = "<h4>" + distancePercentageText + "%" + "</h4><br>";
            distancePercentage.setText(Html.fromHtml(distancePerc));
            double distancePercentageValue = Double.valueOf(distancePercentageText);
            if(distancePercentageValue >= 70) {
                distancePercentage.setTextColor(getResources().getColor(R.color.ForestGreen));
            } else if(distancePercentageValue < 70 && distancePercentageValue >= 20) {
                distancePercentage.setTextColor(getResources().getColor(R.color.Coral));
            } else if(distancePercentageValue < 20) {
                distancePercentage.setTextColor(getResources().getColor(R.color.FireBrick));
            }

            Double distanceDouble = Double.valueOf(distanceText);
            if(distanceDouble > 1000.00) { // >= 1km
                distanceDouble /= 1000; // Lo expresamos en km
                String distanceTextKm = String.valueOf(distanceDouble);
                String[] distanceTextPrint = distanceTextKm.split("\\.");
                String distance = "<h4><font color=#699CFC> Cercanía: </font>" + distanceTextPrint[0] + " kilometros" + "</h4><br>";
                distanceMeters.setText(Html.fromHtml(distance));
                distanceMeters.setTextColor(getResources().getColor(R.color.FireBrick));
            } else { // < 1 km
                String[] distanceTextPrint = distanceText.split("\\.");
                String distance = "<h4><font color=#699CFC> Cercanía: </font>" + distanceTextPrint[0] + " metros" + "</h4><br>";
                distanceMeters.setText(Html.fromHtml(distance));
                double distanceMeterValue = Double.valueOf(distanceText);
                if (distanceMeterValue <= 400) {
                    distanceMeters.setTextColor(getResources().getColor(R.color.ForestGreen));
                } else if (distanceMeterValue > 400 && distanceMeterValue <= 700) {
                    distanceMeters.setTextColor(getResources().getColor(R.color.Coral));
                } else if (distanceMeterValue > 700) {
                    distanceMeters.setTextColor(getResources().getColor(R.color.FireBrick));
                }
            }
        }

        ImageView image = findViewById(R.id.imageinfoannounce);

        if(a.announceCategorie.equals("Telefono")){
            image.setImageResource(R.drawable.ic_phone_android);
        }else if(a.announceCategorie.equals("Cartera")){
            image.setImageResource(R.drawable.ic_wallet);
        }else if(a.announceCategorie.equals("Otro")){
            image.setImageResource(R.drawable.ic_other);
        }else{
            image.setImageResource(R.drawable.ic_card);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent matchannounce = new Intent(this, MatchAnnounce.class);
        matchannounce.putExtra("openDataMatching", true);
        matchannounce.putExtra("back", true);
        Announce oldAnnounce = (Announce) getIntent().getSerializableExtra("oldAnnounce");
        matchannounce.putExtra("match", oldAnnounce);
        matchannounce.putExtra("oldAnnounceSet", true);
        String atrDeterminante = getIntent().getStringExtra("atributoDeterminante");
        matchannounce.putExtra("atributoDeterminante", atrDeterminante);
        startActivity(matchannounce);
        finish();
        return true;
    }
}
