package es.lost2found.lost2foundUI.chatUI.chatConcreteUI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import es.lost2found.lost2foundUI.typeObjectUI.OtherObjectActivity;

public class ChatConcrete extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatConcreteViewAdapter chatConcreteViewAdapter;
    private DrawerLayout mDrawerLayout;
    private Chat concreteChat;
    private Integer listElements = 0;
    private List<Message> listMsg;
    private Integer chatNumberMsgs;
    private Button sendButton;
    private String chatTitle;
    private Integer chatId;
    private Integer userId;
    private Message msg;
    private String userName;
    private EditText chatbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concrete_chat);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        mDrawerLayout = findViewById(R.id.chat_concrete_layout);

        Intent intent = getIntent();
        if(intent != null) {
            concreteChat = (Chat) intent.getSerializableExtra("chat");
            chatTitle = intent.getStringExtra("chatTitle");
            userName = intent.getStringExtra("userName");
        }

        getSupportActionBar().setTitle(chatTitle);

        sendButton = findViewById(R.id.button_chatbox_send);
        chatbox = findViewById(R.id.edittext_chatbox);

        listMsg = new ArrayList<>();

        if(concreteChat != null) {
            new getNumberChatMsgsDB().execute(concreteChat); // Devuelve el numero de msgs del chat en cuestion
            // Tambien hay que hacer una funcion que me devuelva un array de msgs
        }

        chatConcreteViewAdapter = new ChatConcreteViewAdapter(this, concreteChat, listMsg, sendButton); // otherUserName
        recyclerView = findViewById(R.id.reyclerview_message_list);
        recyclerView.setAdapter(chatConcreteViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Creamos un nuevo mensaje // Falta guardar los msgs en la bd
                try {
                    String msgText = chatbox.getText().toString();
                    if(!msgText.isEmpty()) { // Si no esta vacio:
                    // FALTA COMPROBAR QUE EL MENSAJE NO ESTE VACIO!
                        msg = new createNewChatMsgOnDB().execute(msgText).get(); // Obtenemos una instancia del nuevo chat
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class createNewChatMsgOnDB extends AsyncTask<String, Void, Message> {

        @Override
        protected Message doInBackground(String... strings) {
            String actualHour = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()); // Hora del msg
            chatId = DB_chat.getChatId(concreteChat); // Funcion que dado un chat devuelve su id
            userId = Integer.valueOf(DB_user.getIdByName(userName)); // Funcion que dado el nombre del usuario devuelve su id
            return DB_message.createNewMsg(strings[0], actualHour, false, chatId, userId);
        }

        @Override
        protected void onPostExecute(Message msg) {
            chatConcreteViewAdapter.insert(listElements, msg);
            listElements++;
            recyclerView.setAdapter(chatConcreteViewAdapter);
            //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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

    /**
     *
     * @param view
     */
    public void chat_concrete(View view) {
        /*EditText editText = (EditText) findViewById(R.id.email);
        String name = editText.getText().toString();

        editText = (EditText) findViewById(R.id.name);
        String email= editText.getText().toString();

        editText = (EditText) findViewById(R.id.password);
        String pass = editText.getText().toString();

        editText = (EditText) findViewById(R.id.repassword);
        String confirmPass = editText.getText().toString();

        String role = "Student";

        if(name.equalsIgnoreCase("") || email.equalsIgnoreCase("") || pass.equalsIgnoreCase("") || confirmPass.equalsIgnoreCase("")) {
            this.msgerror = "Please, complete all the fields.";
            TextView textView = (TextView) findViewById(R.id.user_already_exists);
            textView.setText(this.msgerror);
        }
        else if(!pass.equals(confirmPass)) {
            this.msgerror = "Passwords doesn't match.";
            TextView textView = (TextView) findViewById(R.id.user_already_exists);
            textView.setText(this.msgerror);
        }
        //else
        //new RegisterDB().execute(email, pass, name, role);*/
    }

}
