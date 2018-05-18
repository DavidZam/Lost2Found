package es.lost2found.lost2foundUI.chatUI.chatConcreteUI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.lost2found.R;
import es.lost2found.database.DB_chat;
import es.lost2found.database.DB_message;
import es.lost2found.database.DB_user;
import es.lost2found.entities.Chat;
import es.lost2found.entities.Message;
import es.lost2found.lost2foundUI.chatUI.ChatActivity;
import es.lost2found.lost2foundUI.openDataUI.OpenDataActivity;
import es.lost2found.lost2foundUI.typeObjectUI.OtherObjectActivity;

public class ChatConcrete extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatConcreteViewAdapter chatConcreteViewAdapter;
    private Chat concreteChat;
    private Integer listElements = 0;
    private Integer chatNumberMsgs;
    private String chatTitle;
    private Integer chatId;
    private String userName;
    private EditText chatBox;
    private boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concrete_chat);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent = getIntent();
        if(intent != null) {
            concreteChat = (Chat) intent.getSerializableExtra("chat");
            chatTitle = intent.getStringExtra("chatTitle");
            userName = intent.getStringExtra("userName");
        }

        getSupportActionBar().setTitle(chatTitle);

        chatBox = findViewById(R.id.edittext_chatbox);
        chatBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                active = false; // Si el usuario esta escribiendo evitamos que se recarge la actividad
            }

            @Override
            public void afterTextChanged(Editable s) {
                //active = false; // Si el usuario esta escribiendo evitamos que se recarge la actividad
            }
        });

        List<Message> listMsg = new ArrayList<>();

        if(concreteChat != null) {
            new getNumberChatMsgsDB().execute(concreteChat); // Devuelve el numero de msgs del chat en cuestion
        }

        FloatingActionButton createMsg = findViewById(R.id.button_chatbox_send);
        createMsg.setOnClickListener(v -> {
            try {
                String msgText = chatBox.getText().toString();
                if(!msgText.isEmpty()) { // Si no esta vacio: // Comprobar
                    boolean validMsg = false;
                    for(int i = 0; i < msgText.length(); i++) {
                        char character = msgText.charAt(i);
                        if(character != ' ') {
                            validMsg = true;
                        }
                    }
                    if(validMsg)
                        active = true;
                        new createNewChatMsgOnDB().execute(msgText); // Obtenemos una instancia del nuevo msg
                        refresh(); // Refrescamos la activity para mostrar el nuevo msg
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            chatBox.setText("");
        });

        chatConcreteViewAdapter = new ChatConcreteViewAdapter(listMsg, userName); // otherUserName
        recyclerView = findViewById(R.id.reyclerview_message_list);
        recyclerView.setAdapter(chatConcreteViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Recargamos el chat por si hay mensajes nuevos
        reloadChat();
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        active = true;
        reloadChat();
    }

    @Override
    public void onPause() {
        super.onPause();
        active = false;
    }

    public void reloadChat() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(15000); // Actualizamos el chat cada 15 seg por si hay mensajes nuevos...
                        runOnUiThread(() -> {
                            if(active)
                                refresh();
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    public void refresh() {
        Toast t = Toast.makeText(ChatConcrete.this, "Cargando...", Toast.LENGTH_SHORT);
        t.setDuration(Toast.LENGTH_SHORT);
        t.show();

        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    private class createNewChatMsgOnDB extends AsyncTask<String, Void, Message> {

        @Override
        protected Message doInBackground(String... strings) {
            String actualHour = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()); // Hora del msg
            chatId = DB_chat.getChatId(concreteChat); // Funcion que dado un chat devuelve su id
            Integer userId = Integer.valueOf(DB_user.getIdByName(userName)); // Funcion que dado el nombre del usuario devuelve su id
            return DB_message.createNewMsg(strings[0], actualHour, false, chatId, userId);
        }
    }

    private class getNumberChatMsgsDB extends AsyncTask<Chat, Void, Integer> {

        @Override
        protected Integer doInBackground(Chat... params) {
            chatId = DB_chat.getChatId(params[0]); // Funcion que dado un chat devuelve su id
            return DB_message.getNumberChatMsgs(chatId);
        }

        @Override
        protected void onPostExecute(Integer numMsgs) {
            processChatConcreteScreen(numMsgs);
        }
    }

    public void processChatConcreteScreen(Integer numMsgs) {
        if (numMsgs > 0) {
            chatNumberMsgs = numMsgs;
            new getMsgsDB().execute(chatId, chatNumberMsgs); // Devuelve una lista con los chats del usuario en cuestion
        }
    }

    private class getMsgsDB extends AsyncTask<Integer, Void, Message[]> {

        @Override
        protected Message[] doInBackground(Integer... params) {
            return DB_message.getMsgs(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Message[] msgs) {
            updateAdapter(msgs, chatNumberMsgs);
        }
    }

    public void updateAdapter(Message[] msgs, Integer numMsgs) {
        for(int i = 0; i < numMsgs; i++) {
            chatConcreteViewAdapter.insert(listElements, msgs[i]);
            listElements++;
        }
        recyclerView.setAdapter(chatConcreteViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent chat = new Intent(this, ChatActivity.class);
        startActivity(chat);
        finish();
        return true;
    }
}
