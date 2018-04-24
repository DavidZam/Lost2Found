package es.lost2found.lost2foundUI.seekerUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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

        Announce a = (Announce) getIntent().getSerializableExtra("myAnnounce");

        TextView infoCat = (TextView) findViewById(R.id.infoCat);
        TextView cat = (TextView) findViewById(R.id.textinfoannounce);
        TextView infoType = (TextView) findViewById(R.id.infoType);
        TextView type = (TextView) findViewById(R.id.type);
        TextView infoDia = (TextView) findViewById(R.id.infoDia);
        TextView dia = (TextView) findViewById(R.id.dia);
        TextView infoCurrent = (TextView) findViewById(R.id.infoCurrent);
        TextView current = (TextView) findViewById(R.id.current);
        TextView infoHora = (TextView) findViewById(R.id.infoHoraAnuncio);
        TextView hora = (TextView) findViewById(R.id.horaAnuncio);
        TextView infoColor = (TextView) findViewById(R.id.infoColor);
        TextView color = (TextView) findViewById(R.id.color);

        String c = "<h4> <font color=#699CFC>"+ a.announceCategorie +" </font></h4><br>";
        cat.setText(Html.fromHtml(c));

        String t = "<h4> <font color=#699CFC>"+ a.announceType +" </font></h4><br>";
        type.setText(Html.fromHtml(t));


        String d = "<h4> <font color=#699CFC>"+ a.announceDateText +" </font></h4><br>";
        dia.setText(Html.fromHtml(d));

        String curr = "<h4> <font color=#699CFC>"+ a.currentTime +" </font></h4><br>";
        current.setText(Html.fromHtml(curr));

        String h = "<h4> <font color=#699CFC>"+ a.announceHourText +" </font></h4><br>";
        hora.setText(Html.fromHtml(h));

       /* String col = "<h4> <font color=#699CFC>"+ a.color +" </font></h4><br>";
        color.setText(Html.fromHtml(col));*/


       /* String s = "<h4> <font color=#699CFC> Categoría del objeto </font></h4>"+ a.announceCategorie +"<br><br>" +
                    "<h4> <font color=#699CFC> Tipo de anuncio </font></h4>" + a.announceType + "<br><br>" +
                    "<h4> <font color=#699CFC> Fecha </font></h4>" + a.announceDateText+ "<br><b>" +
                    "<h4> <font color=#699CFC> Hora en la que ocurrió </font></h4>" + a.currentTime + "<br><br>" +
                    "<h4> <font color=#699CFC> Hora en la que se registró el anuncio </font></h4>" + a.announceHourText + "<br><br>" +
                    "<h4> <font color=#699CFC> Color </font></h4>" + a.color + "<br><br>";

        TextView texto = (TextView)findViewById(R.id.textinfoannounce);
        texto.setText(Html.fromHtml(s));
        texto.setMovementMethod(new ScrollingMovementMethod());*/

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
        Intent seeker = new Intent(this, SeekerActivity.class);
        startActivity(seeker);
        finish();
        return true;
    }

}
