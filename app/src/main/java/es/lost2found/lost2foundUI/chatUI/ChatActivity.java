package es.lost2found.lost2foundUI.chatUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.database.DB_chat;
import es.lost2found.database.DB_user;
import es.lost2found.entities.Chat;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;
import es.lost2found.lost2foundUI.loginregisterUI.LoginActivity;
import es.lost2found.lost2foundUI.openDataUI.OpenDataActivity;
import es.lost2found.lost2foundUI.otherUI.AboutUsActivity;
import es.lost2found.lost2foundUI.otherUI.ConfigurationActivity;
import es.lost2found.lost2foundUI.otherUI.ContactActivity;
import es.lost2found.lost2foundUI.otherUI.HelpActivity;
import es.lost2found.lost2foundUI.otherUI.RateActivity;
import es.lost2found.lost2foundUI.seekerUI.SeekerActivity;

public class ChatActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Integer userNumberChats;
    private Integer userId = 0;
    private Integer listElements = 0;
    private ChatViewAdapter chatAdapter;
    private RecyclerView recyclerView;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerLayout = findViewById(R.id.chat_layout);

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
        final Intent config = new Intent(this, ConfigurationActivity.class);
        final Intent openData = new Intent(this, OpenDataActivity.class);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        if(menuItem.getItemId()== R.id.nav_home) {
                            startActivity(home);
                        }else if(menuItem.getItemId()== R.id.nav_search) {
                            startActivity(buscar);
                        }else if(menuItem.getItemId()== R.id.nav_contact) {
                            startActivity(contact);
                        } else if(menuItem.getItemId()== R.id.nav_open_data) {
                            startActivity(openData);
                        } else if(menuItem.getItemId()== R.id.nav_info) {
                            startActivity(aboutus);
                        } else if(menuItem.getItemId() == R.id.nav_settings){
                            startActivity(config);
                        } else if(menuItem.getItemId()== R.id.nav_help) {
                            startActivity(help);
                        }else if(menuItem.getItemId()== R.id.nav_feedback) {
                            startActivity(rate);
                        } else if(menuItem.getItemId()== R.id.nav_logout) {
                            logoutUser();
                        }
                        return true;
                    }
                }
        );
        navView.setCheckedItem(R.id.nav_chat);

        List<Chat> chatList = new ArrayList<>();

        if(userName != null) {
            new getNumberChatsDB().execute(userName); // Devuelve el numero de chats del usuario en cuestion
        }

        chatAdapter = new ChatViewAdapter(chatList, getApplication(), userName);
        recyclerView = findViewById(R.id.chat_recyclerview);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class getNumberChatsDB extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            userId = Integer.valueOf(DB_user.getIdByName(strings[0])); // Funcion que dado el nombre del usuario devuelve su id
            return DB_chat.getNumberChats(userId);
        }

        @Override
        protected void onPostExecute(Integer numChats) {
            processChatScreen(numChats);
        }
    }

    public void processChatScreen(Integer numChats) {
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

    private class getChatsDB extends AsyncTask<Integer, Void, Chat[]> {

        @Override
        protected Chat[] doInBackground(Integer... params) {
            return DB_chat.getChats(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Chat[] chats) {
            updateAdapter(chats, userNumberChats);
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
}
