package es.lost2found.lost2foundUI.seekerUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Announce;

public class SeekerAnnounceInfoActivity extends AppCompatActivity {

    public String announceType;
    public String announceDateText;
    public String currentTime;
    public String announceHourText;
    public String announceCategorie;
    public String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekerannounce_info);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        


        String s = "<h2> <font color=#699CFC> Categoría del objeto </font></h2>"+ announceCategorie +"<br><br>" +
                "<h2> <font color=#699CFC> Tipo de anuncio </font></h2>" + announceType + "<br><br>" +
                "<h2> <font color=#699CFC> Fecha </font></h2>" + announceDateText+ "<br><b>" +
                "<h2> <font color=#699CFC> Hora de  </font></h2>" + announceType + "<br><br>" +
                "<h2> <font color=#699CFC> Tipo de anuncio </font></h2>" + announceType + "<br><br>" +
                "<h2> <font color=#699CFC> Tipo de anuncio </font></h2>" + announceType + "<br><br>";
        TextView texto = (TextView)findViewById(R.id.textinfoannounce);
        texto.setText(Html.fromHtml(s));

    }





    /*public void datosAnuncio(String tipo, String dia, String hora, String horaCreacionAnuncio, String categoria, String color){
        announceType = tipo;
        announceDateText = dia;
        currentTime = hora;
        announceHourText = horaCreacionAnuncio;
        announceCategorie = categoria;
        this.color = color;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent seeker = new Intent(this, SeekerActivity.class);
        startActivity(seeker);
        finish();
        return true;
    }

}
