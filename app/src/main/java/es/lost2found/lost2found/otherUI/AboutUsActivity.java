package es.lost2found.lost2found.otherUI;

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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import es.lost2found.R;
import es.lost2found.lost2found.announceUI.AnnounceActivity;
import es.lost2found.lost2found.chatUI.ChatActivity;
import es.lost2found.lost2found.loginUI.LoginActivity;
import es.lost2found.lost2found.openDataUI.OpenDataActivity;
import es.lost2found.lost2found.seekerUI.SeekerActivity;

public class AboutUsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abouts_us);

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

        String s = "<h2> <font color=#1976D2>¿Cuál es nuestra misión? </font></h2> Lost2Found ha sido creado con el objetivo de poner en contacto a personas que han perdido algún tipo de objeto con aquellas que lo han encontrado, de forma sencilla y rápida.<br><br><br>" +
                    "<h2> <font color=#1976D2>¿Qué visión tiene Lost2Found? </font></h2> Intentamos hacer un mundo mejor poniendo en contacto a personas para recuperar sus objetos perdidos, y cumplir así nuestro objetivo.<br><br><br>" +
                    "<h2> <font color=#1976D2>¿Cuáles son nuestros valores? </font></h2> Nuestro proyecto se basa en creer en las personas y en sus colaboraciones altruistas, de esta manera Lost2Found se retroalimenta.<br><br><b>";
        TextView texto = findViewById(R.id.textinfoannounce);
        texto.setText(Html.fromHtml(s));

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
        final Intent contact = new Intent(this, ContactActivity.class);
        final Intent chat = new Intent(this, ChatActivity.class);
        final Intent help = new Intent(this, HelpActivity.class);
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
                    }else if(menuItem.getItemId()== R.id.nav_contact) {
                        startActivity(contact);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_chat) {
                        startActivity(chat);
                        finish();
                    }else if(menuItem.getItemId() == R.id.nav_settings){
                        startActivity(config);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_help) {
                        startActivity(help);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_feedback) {
                        startActivity(rate);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_open_data) {
                        startActivity(openData);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_logout) {
                        logoutUser();
                    }
                    return true;
                }
        );
        navView.setCheckedItem(R.id.nav_info);

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
