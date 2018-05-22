package es.lost2found.lost2found.otherUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import es.lost2found.R;
import es.lost2found.lost2found.announceUI.AnnounceActivity;
import es.lost2found.lost2found.chatUI.ChatActivity;
import es.lost2found.lost2found.loginUI.LoginActivity;
import es.lost2found.lost2found.openDataUI.OpenDataActivity;
import es.lost2found.lost2found.seekerUI.SeekerActivity;

public class RateActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);

        try {
            connected = isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));

        NavigationView navView = findViewById(R.id.nav_view);

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
        final Intent help = new Intent(this, HelpActivity.class);
        final Intent config = new Intent(this, SettingsActivity.class);
        final Intent openData = new Intent(this, OpenDataActivity.class);
        final Intent rate = new Intent(this, RateActivity.class);

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
                    } else if(menuItem.getItemId() == R.id.nav_settings){
                        startActivity(config);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_contact) {
                        startActivity(contact);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_open_data) {
                        startActivity(openData);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_help) {
                        startActivity(help);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_logout) {
                        logoutUser();
                    } else if(menuItem.getItemId()== R.id.nav_feedback) {
                        startActivity(rate);
                        finish();
                    }
                    return true;
                }
        );
        navView.setCheckedItem(R.id.nav_feedback);

        if(connected) {
            TextView withoutConnection = findViewById(R.id.without_connection);
            withoutConnection.setText("");
            String s = "<h2> <font color=#1976D2> ¡Tu valoración cuenta! </font></h2> Valorándonos contribuyes a mantener y mejorar el sistema para que siga ayudando a muchas personas.<br><br>" +
                    "Además colaboras en la difusión de la aplicación para así llegar a más gente, es una forma de agradecer lo que te ofrece Lost2Found.<br><br>" +
                    "<h2> <font color=#1976D2> ¿Cómo nos puedes puntuar? </font></h2> Pincha en el siguiente enlace, busca nuestra aplicación y puntúa:<br><br>";
            TextView texto = findViewById(R.id.textinfo);
            texto.setText(Html.fromHtml(s));
        } else {
            TextView withoutConnection = findViewById(R.id.without_connection);
            withoutConnection.setText(withoutConnection.getResources().getString(R.string.info_txt4));
        }

        Button ratebutton = findViewById(R.id.rate);
        ratebutton.setOnClickListener(v -> {
            final String appPackageName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        });
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
        ed.putString("name", null);
        ed.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec (command).waitFor() == 0);
    }
}
