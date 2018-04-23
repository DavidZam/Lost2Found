package es.lost2found.lost2foundUI.seekerUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Announce;

public class SeekerAnnounceInfoActivity extends AppCompatActivity {

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

        String s = "<h4> <font color=#699CFC> Categoría del objeto </font></h4>"+ a.announceCategorie +"<br><br>" +
                "<h4> <font color=#699CFC> Tipo de anuncio </font></h4>" + a.announceType + "<br><br>" +
                "<h4> <font color=#699CFC> Fecha </font></h4>" + a.announceDateText+ "<br><b>" +
                "<h4> <font color=#699CFC> Hora en la que ocurrió </font></h4>" + a.currentTime + "<br><br>" +
                "<h4> <font color=#699CFC> Hora en la que se registró el anuncio </font></h4>" + a.announceHourText + "<br><br>" +
                "<h4> <font color=#699CFC> Color </font></h4>" + a.color + "<br><br>";
        TextView texto = (TextView)findViewById(R.id.textinfoannounce);
        texto.setText(Html.fromHtml(s));
        texto.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent seeker = new Intent(this, SeekerActivity.class);
        startActivity(seeker);
        finish();
        return true;
    }

}
