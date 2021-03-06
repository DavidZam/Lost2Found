package es.lost2found.lost2found.openDataUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import es.lost2found.R;
import es.lost2found.lost2found.announceUI.AnnounceActivity;
import es.lost2found.lost2found.chatUI.ChatActivity;
import es.lost2found.lost2found.loginUI.LoginActivity;
import es.lost2found.lost2found.otherUI.AboutUsActivity;
import es.lost2found.lost2found.otherUI.SettingsActivity;
import es.lost2found.lost2found.otherUI.ContactActivity;
import es.lost2found.lost2found.otherUI.HelpActivity;
import es.lost2found.lost2found.otherUI.RateActivity;
import es.lost2found.lost2found.seekerUI.SeekerActivity;

import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class OpenDataActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private boolean connected;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_data);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);

        try {
            connected = new checkIfDeviceIsConnected().execute().get(); // Check if device is connected
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        final Intent buscar = new Intent(this, SeekerActivity.class);
        final Intent chat = new Intent(this, ChatActivity.class);
        final Intent contact = new Intent(this, ContactActivity.class);
        final Intent aboutus = new Intent(this, AboutUsActivity.class);
        final Intent help = new Intent(this, HelpActivity.class);
        final Intent rate = new Intent(this, RateActivity.class);
        final Intent config = new Intent(this, SettingsActivity.class);
        final Intent home = new Intent(this, AnnounceActivity.class);
        final Intent openData = new Intent(this, OpenDataActivity.class);

        navView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();

                    if(menuItem.getItemId()== R.id.nav_search) {
                        startActivity(buscar);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_chat) {
                        startActivity(chat);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_home) {
                        startActivity(home);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_contact) {
                        startActivity(contact);
                        finish();
                    } else if(menuItem.getItemId() == R.id.nav_settings){
                        startActivity(config);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_info) {
                        startActivity(aboutus);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_help) {
                        startActivity(help);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_feedback) {
                        startActivity(rate);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_logout) {
                        logoutUser();
                    } else if(menuItem.getItemId()== R.id.nav_open_data) {
                        startActivity(openData);
                        finish();
                    }
                    return true;
                }
        );
        navView.setCheckedItem(R.id.nav_open_data);

        if(connected) {
            TextView withoutConnection = findViewById(R.id.without_connection);
            withoutConnection.setText("");
            WebView webView = findViewById(R.id.webview);
            String OPEN_DATA_URL = "https://ressources.data.sncf.com/explore/embed/dataset/objets-trouves-gares/?sort=date&static=false&datasetcard=false";
            webView.loadUrl(OPEN_DATA_URL);

            Toast t = Toast.makeText(OpenDataActivity.this, "Cargando...", Toast.LENGTH_SHORT);
            t.setDuration(Toast.LENGTH_LONG);
            t.show();

            webView.setWebViewClient(new WebViewClient());
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
        } else {
            TextView withoutConnection = findViewById(R.id.without_connection);
            withoutConnection.setText(withoutConnection.getResources().getString(R.string.info_txt4));
        }
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

    public boolean isInternetAvailable(String address, int port, int timeoutMs) {
        try {
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress(address, port);

            sock.connect(sockaddr, timeoutMs); // This will block no more than timeoutMs
            sock.close();

            return true;

        } catch (IOException e) { return false; }
    }

    private class checkIfDeviceIsConnected extends AsyncTask<Void, Void, Boolean> {

        private ProgressDialog dialog = new ProgressDialog(OpenDataActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Comprobando conexion...");
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (isInternetAvailable("8.8.8.8", 53, 1000)) {
                // Internet available, do something
                connected = true;
            } else {
                // Internet not available
                connected = false;
            }
            return connected;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            this.dialog.dismiss();
        }
    }
}