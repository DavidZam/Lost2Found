package es.lost2found.lost2found.chatUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.database.DB_chat;
import es.lost2found.database.DB_user;
import es.lost2found.entities.Chat;
import es.lost2found.lost2found.announceUI.AnnounceActivity;
import es.lost2found.lost2found.loginUI.LoginActivity;
import es.lost2found.lost2found.openDataUI.OpenDataActivity;
import es.lost2found.lost2found.otherUI.AboutUsActivity;
import es.lost2found.lost2found.otherUI.SettingsActivity;
import es.lost2found.lost2found.otherUI.ContactActivity;
import es.lost2found.lost2found.otherUI.HelpActivity;
import es.lost2found.lost2found.otherUI.RateActivity;
import es.lost2found.lost2found.seekerUI.SeekerActivity;

public class ChatActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Integer userNumberChats;
    private Integer userId = 0;
    private Integer listElements = 0;
    private ChatViewAdapter chatAdapter;
    private RecyclerView recyclerView;
    private String userName;
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        mDrawerLayout = findViewById(R.id.chat_layout);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));

        NavigationView navView = findViewById(R.id.nav_view);

        try {
            connected = new checkIfDeviceIsConnected().execute().get(); // Check if device is connected
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                userName = spref.getString("nombre", "");
                nameUser.setText(userName);
            }
        }

        final Intent home = new Intent(this, AnnounceActivity.class);
        final Intent buscar = new Intent(this, SeekerActivity.class);
        final Intent contact = new Intent(this, ContactActivity.class);
        final Intent aboutus = new Intent(this, AboutUsActivity.class);
        final Intent help = new Intent(this, HelpActivity.class);
        final Intent rate = new Intent(this, RateActivity.class);
        final Intent config = new Intent(this, SettingsActivity.class);
        final Intent openData = new Intent(this, OpenDataActivity.class);
        final Intent chat = new Intent(this, ChatActivity.class);
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
                    } else if(menuItem.getItemId()== R.id.nav_open_data) {
                        startActivity(openData);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_info) {
                        startActivity(aboutus);
                        finish();
                    } else if(menuItem.getItemId() == R.id.nav_settings){
                        startActivity(config);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_help) {
                        startActivity(help);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_feedback) {
                        startActivity(rate);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_logout) {
                        logoutUser();
                    } else if(menuItem.getItemId()== R.id.nav_chat) {
                        startActivity(chat);
                        finish();
                    }
                    return true;
                }
        );
        navView.setCheckedItem(R.id.nav_chat);

        List<Chat> chatList = new ArrayList<>();

        if(userName != null && connected) {
            new getNumberChatsDB().execute(userName); // Devuelve el numero de chats del usuario en cuestion
        } else {
            TextView noannounces = findViewById(R.id.without_chats);
            noannounces.setText(noannounces.getResources().getString(R.string.info_txt4));
        }

        TextView noChats = findViewById(R.id.without_chats);

        chatAdapter = new ChatViewAdapter(chatList, userName, noChats);
        recyclerView = findViewById(R.id.chat_recyclerview);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class getNumberChatsDB extends AsyncTask<String, Void, Integer> {

        private ProgressDialog dialog = new ProgressDialog(ChatActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            String userIdTxt = DB_user.getIdByName(strings[0]); // Funcion que dado el nombre del usuario devuelve su id
            if(!userIdTxt.equals("")) {
                userId = Integer.valueOf(userIdTxt);
            }

            return DB_chat.getNumberChats(userId);
        }

        @Override
        protected void onPostExecute(Integer numChats) {
            processChatScreen(numChats);
            this.dialog.dismiss();
        }
    }

    public void processChatScreen(Integer numChats) {
        if(numChats != null) {
            if (numChats == 0) {
                TextView noannounces = findViewById(R.id.without_chats);
                noannounces.setText(noannounces.getResources().getString(R.string.info_txt3));
            } else {
                TextView noannounces = findViewById(R.id.without_chats);
                noannounces.setText("");
                userNumberChats = numChats;
                new getChatsDB().execute(userId, userNumberChats); // Devuelve una lista con los chats del usuario en cuestion
            }
        }
    }

    private class getChatsDB extends AsyncTask<Integer, Void, Chat[]> {

        private ProgressDialog dialog = new ProgressDialog(ChatActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected Chat[] doInBackground(Integer... params) {
            return DB_chat.getChats(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Chat[] chats) {
            updateAdapter(chats, userNumberChats);
            this.dialog.dismiss();
        }
    }

    public void updateAdapter(Chat[] chats, Integer numChats) {
        for(int i = 0; i < numChats; i++) {
            chatAdapter.insert(listElements, chats[i]);
            listElements++;
        }
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        private ProgressDialog dialog = new ProgressDialog(ChatActivity.this);

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
