package es.lost2found.lost2foundUI.otherUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import es.lost2found.R;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;
import es.lost2found.lost2foundUI.chatUI.ChatActivity;
import es.lost2found.lost2foundUI.loginUI.LoginActivity;
import es.lost2found.lost2foundUI.openDataUI.OpenDataActivity;
import es.lost2found.lost2foundUI.seekerUI.SeekerActivity;

public class HelpActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));

        NavigationView navView = findViewById(R.id.nav_view);

        String s = "<h2> <font color=#1976D2>¿Cómo funciona Lost2Found? </font></h2> &bull; Cuando una persona pierde un objeto lo notifica creando un anuncio de pérdida y rellenando los máximos campos posibles.<br><br>" +
                "&bull; Otra persona encuentra ese mismo objeto y crea un anuncio de hallazgo, completando todos los campos que le sea posible. <br><br>" +
                "&bull; Lost2Found cuenta con un algoritmo que nos proporciona un porcentaje de matching calculado en función de cuánto tengan en común las características de los objetos de ambos anuncios" +
                ", de esta manera se le muestra al usuario una lista de posibles anuncios que son 'compatibles' con su anuncio de pérdida y qué podrían ser su objeto perdido o encontrado.<br><br>" +
                "&bull; En caso de que el usuario piense que un anuncio con el que se ha hecho match corresponse a su objeto solo tiene que pulsar en contactar para comunicarse con el dueño de dicho anuncio. <br><br>" +
                "<h2> <font color=#1976D2>¿Puede haber anuncios cuyos procesos coincidan simultáneamente? </font></h2> &bull; Perfectamente. Varios objetos pueden reunir las condiciones necesarias para que sus coincidencias sean similares " +
                "(objetos parecidos, mismo lugar, fecha y hora). Por eso es imprescindible que los usuarios hablen entre ellos y se aseguren de que el objeto es el correcto.<br>" +
                "<h2> <font color=#1976D2>¿En qué se basa nuestro algoritmo? </font></h2> &bull; Cuando estamos comparando dos objetos, uno de pérdida y otro de hallazgo, dentro de la aplicación, el algoritmo usa factores como el color y la " +
                "distancia de ambos objetos. Cuanto más parecidos y más cerca estén, el porcentaje de matching será más alto. <br><br>" +
                "&bull; Cuando se realiza el match con el portal de open data en cambio, el matching se basa sobretodo en la distancia, ya que en este portal no se publican determinadas caracteristicas de los objetos perdidos o encontrados, " +
                "como por ejemplo el color, además de esto en determinadas ocasiones tampoco se dispone del lugar donde se ha perdido o encontrado el objeto (no todos los objetos publicados en el portal abierto tienen registrado un lugar), " +
                "por lo que el cálculo de la distancia resulta imposible, por esto recomendamos siempre a nuestros usuarios que creen anuncios en nuestra aplicación y especifiquen todos los datos posibles acerca de su objeto.<br>"+
                "<h2> <font color=#1976D2>¿Tengo que entrar en la aplicación para saber si tengo algún mensaje?</font></h2> &bull; ¡Claro que no! Nuestro chat, fácil e intuitivo de usar, se actualiza cada 15 segundos para que nuestros usuarios " +
                "no estén pendientes de meterse cada poco tiempo, sino que sea la aplicación quien se actualize por si hay nuevos mensajes disponibles.<br>";
        TextView texto = findViewById(R.id.textComoFunciona);
        texto.setText(Html.fromHtml(s));

        texto.setMovementMethod(new ScrollingMovementMethod());

        View headerLayout = navView.getHeaderView(0);
        TextView emailUser = headerLayout.findViewById(R.id.user_mail);
        TextView nameUser = headerLayout.findViewById(R.id.user_name);
        SharedPreferences spref = getApplicationContext().getSharedPreferences("Login", 0);
        if(spref != null) {
            if (spref.contains("email")) {
                String userEmail = spref.getString("email", "");
                emailUser.setText(userEmail);
            }
            if (spref.contains("nombre")) {
                String userName = spref.getString("nombre", "");
                nameUser.setText(userName);
            }
        }

        final Intent home = new Intent(this, AnnounceActivity.class);
        final Intent buscar = new Intent(this, SeekerActivity.class);
        final Intent aboutus = new Intent(this, AboutUsActivity.class);
        final Intent chat = new Intent(this, ChatActivity.class);
        final Intent contact = new Intent(this, ContactActivity.class);
        final Intent rate = new Intent(this, RateActivity.class);
        final Intent config = new Intent(this, SettingsActivity.class);
        final Intent openData = new Intent(this, OpenDataActivity.class);

        navView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();

                    if(menuItem.getItemId()== R.id.nav_home) {
                        startActivity(home);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_search) {
                        startActivity(buscar);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_info) {
                        startActivity(aboutus);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_chat) {
                        startActivity(chat);
                        finish();
                    } else if(menuItem.getItemId() == R.id.nav_settings) {
                        startActivity(config);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_contact) {
                        startActivity(contact);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_open_data) {
                        startActivity(openData);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_feedback) {
                        startActivity(rate);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_logout) {
                        logoutUser();
                    }
                    return true;
                }
        );
        navView.setCheckedItem(R.id.nav_help);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        SharedPreferences sp = getSharedPreferences("Login", 0);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("email", null);
        ed.putString("nombre", null);
        ed.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
