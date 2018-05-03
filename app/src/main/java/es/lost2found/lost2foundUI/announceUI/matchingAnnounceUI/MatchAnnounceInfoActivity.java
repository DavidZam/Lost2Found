package es.lost2found.lost2foundUI.announceUI.matchingAnnounceUI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.lost2found.R;
import es.lost2found.database.DB_typeObject;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.chatUI.chatConcreteUI.ChatConcrete;
import es.lost2found.lost2foundUI.seekerUI.SeekerAnnounceInfoActivity;

public class MatchAnnounceInfoActivity extends AppCompatActivity {
    private Announce a;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchannounce_info);

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
        TextView param = (TextView) findViewById(R.id.param);
        TextView textoColor = (TextView) findViewById(R.id.colorTexto);
        View color = (View) findViewById(R.id.color_view);

        a = (Announce) getIntent().getSerializableExtra("myAnnounce");

        String idText = String.valueOf(a.getAnnounceId());
        new getObjectDataFromDB().execute(idText, a.announceCategorie);

        String c = "<h4> <font color=#699CFC> Categoría: </font>"+ a.announceCategorie +" </h4><br>";
        cat.setText(Html.fromHtml(c));

        String t = "<h4> <font color=#699CFC> Tipo: </font>"+ a.announceType +" </h4><br>";
        type.setText(Html.fromHtml(t));

        String d = "<h4> <font color=#699CFC> Día: </font>"+ a.DDMMYYYY() +" </h4><br>";
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
    }

    private class getObjectDataFromDB extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return DB_typeObject.getDataObjectById(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(String dataObject) {
            if(dataObject != null) {
                TextView param = (TextView) findViewById(R.id.param);
                String params[] = dataObject.split(",");
                if(a.announceCategorie.equals("Telefono")){
                    String o = "<h4> <font color=#699CFC> Datos: </font><br>"+ "Marca: " + params[0] + ", Modelo: " + params[1] + "<br>" +  "tara: " + params[2] +" </h4><br>";
                    param.setText(Html.fromHtml(o));
                }else if(a.announceCategorie.equals("Cartera")){
                    String o = "<h4> <font color=#699CFC> Datos: </font><br>"+ "Marca: " + params[0] + ", Documentacion: " + params[1] +" </h4><br>";
                    param.setText(Html.fromHtml(o));
                }else if(a.announceCategorie.equals("Otro")){
                    String o = "<h4> <font color=#699CFC> Datos: </font><br>"+ "Nombre: " + params[0] + ", Descripcion: " + params[1] +" </h4><br>";
                    param.setText(Html.fromHtml(o));
                }else if(a.announceCategorie.equals("Tarjeta bancaria")){
                    String o = "<h4> <font color=#699CFC> Datos: </font><br>"+ "Banco: " + params[0] + ", Propietario: " + params[1] +" </h4><br>";
                    param.setText(Html.fromHtml(o));
                }else if(a.announceCategorie.equals("Tarjeta transporte")){
                    String o = "<h4> <font color=#699CFC> Datos: </font><br>"+ "Propietario: " + params[0] +" </h4><br>";
                    param.setText(Html.fromHtml(o));
                }
            }
        }
    }

    public void contactar(View v) {
        final Intent contacto = new Intent(this, ChatConcrete.class); ////C: he puesto esta clase por poner
        startActivity(contacto);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent matchannounce = new Intent(this, MatchAnnounce.class);
        matchannounce.putExtra("match", a);
        matchannounce.putExtra("back", true);
        Announce oldAnnounce = (Announce) getIntent().getSerializableExtra("oldAnnounce");
        matchannounce.putExtra("oldAnnounce", oldAnnounce);
        startActivity(matchannounce);
        finish();
        return true;
    }
}
