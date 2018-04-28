package es.lost2found.lost2foundUI.seekerUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.lost2found.R;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.announceUI.AnnounceViewAdapter;
import es.lost2found.lost2foundUI.announceUI.matchingAnnounceUI.MatchAnnounce;

public class SeekerAnnounceInfoActivity extends AppCompatActivity {
    Announce a;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekerannounce_info);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        TextView cat = (TextView) findViewById(R.id.categoria);
        TextView type = (TextView) findViewById(R.id.tipo);
        TextView dia = (TextView) findViewById(R.id.dia);
        TextView lugar = (TextView) findViewById(R.id.lugar);
        TextView usuario = (TextView) findViewById(R.id.usuario);
        TextView hora = (TextView) findViewById(R.id.hora);
        TextView textoColor = (TextView) findViewById(R.id.colorTexto);
        View color = (View) findViewById(R.id.color_view);

        a = (Announce) getIntent().getSerializableExtra("myAnnounce");

        String c = "<h4> <font color=#699CFC> Categoría: </font>"+ a.announceCategorie +" </h4><br>";
        cat.setText(Html.fromHtml(c));

        String t = "<h4> <font color=#699CFC> Tipo: </font>"+ a.announceType +" </h4><br>";
        type.setText(Html.fromHtml(t));

        String d = "<h4> <font color=#699CFC> Día: </font>"+ a.announceDateText +" </h4><br>";
        dia.setText(Html.fromHtml(d));

        String h = "<h4> <font color=#699CFC> Hora: </font>"+ a.announceHourText +" </h4><br>";
        hora.setText(Html.fromHtml(h));

        String l = "<h4> <font color=#699CFC> Lugar: </font>"+ a.place +" </h4><br>";
        lugar.setText(Html.fromHtml(l));

        String u = "<h4> <font color=#699CFC> Creador del anuncio: </font>"+ a.userOwner +" </h4><br>";
        usuario.setText(Html.fromHtml(u));

        String col = "<h4> <font color=#699CFC> Color: </font> </h4><br>";
        textoColor.setText(Html.fromHtml(col));

        color.setBackgroundColor(a.color);

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



         /* String col = "<h4> <font color=#699CFC>"+ a.color +" </font></h4><br>";
        color.setText(Html.fromHtml(col));*/


        /*String s = "<h4> <font color=#699CFC> Categoría del objeto: </font>"+ a.announceCategorie +"</h4><br>" +
                "<h4> <font color=#699CFC> Tipo de anuncio: </font>" + a.announceType + "</h4><br>" +
                "<h4> <font color=#699CFC> Fecha: </font>" + a.announceDateText+ "</h4><br>" +
                "<h4> <font color=#699CFC> Hora en la que ocurrió: </font>" + a.currentTime + "</h4><br>" +
                "<h4> <font color=#699CFC> Lugar: </font>" + a.place + "</h4><br>" +
                "<h4> <font color=#699CFC> Autor anuncio: </font>" + a.userOwner + "</h4><br>" +
                "<h4> <font color=#699CFC> Color </font> </h4><br>";*/

        //Aparece la hora de la creación del anuncio
        /*String s = "<h4> <font color=#699CFC> Categoría del objeto </font>"+ a.announceCategorie +"</h4><br>" +
                    "<h4> <font color=#699CFC> Tipo de anuncio </font>" + a.announceType + "</h4><br>" +
                    "<h4> <font color=#699CFC> Fecha </font>" + a.announceDateText+ "</h4><br>" +
                    "<h4> <font color=#699CFC> Hora en la que ocurrió </font>" + a.currentTime + "</h4><br>" +
                    "<h4> <font color=#699CFC> Hora creación anuncio </font>" + a.announceHourText + "</h4><br>" +
                    "<h4> <font color=#699CFC> Color </font>" + a.color + "</h4><br>";*/


        //Color azul en el lado derecho
        /*String s = "<h4> Hora creación anuncio:   <font color=#699CFC>" + a.announceHourText + "</font></h4><br>" +
                    "<h4>  Categoría del objeto:   <font color=#699CFC> "+  a.announceCategorie +"</font></h4><br>" +
                    "<h4> Tipo de anuncio:  <font color=#699CFC>" + a.announceType + " </font></h4><br>" +
                    "<h4> Fecha:  <font color=#699CFC> " + a.announceDateText+ "</font></h4><br>" +
                    "<h4> Hora en la que ocurrió:  <font color=#699CFC> " + a.currentTime + "</font></h4><br>" +
                    "<h4> <font color=#699CFC> Color </font></h4>" + a.color + "<br><br>";*/

        /*TextView texto = (TextView)findViewById(R.id.textinfoannounce);
        texto.setText(Html.fromHtml(s));
        texto.setMovementMethod(new ScrollingMovementMethod());
*/



    }

    public void matching(View v) {
        final Intent match = new Intent(this, MatchAnnounce.class);
        match.putExtra("match", a);
        startActivity(match);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent seeker = new Intent(this, AnnounceViewAdapter.class);
        startActivity(seeker);
        finish();
        return true;
    }

}
