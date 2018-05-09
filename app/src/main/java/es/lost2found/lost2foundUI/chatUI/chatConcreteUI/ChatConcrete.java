package es.lost2found.lost2foundUI.chatUI.chatConcreteUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Chat;
import es.lost2found.entities.Message;
import es.lost2found.lost2foundUI.chatUI.ChatActivity;

public class ChatConcrete extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatConcreteViewAdapter chatConcreteViewAdapter;
    private DrawerLayout mDrawerLayout;
    private Chat chat;

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
            chat = (Chat) intent.getSerializableExtra("chat");
        }

        // In this example we fill listMsg with a function fill_with_data(), in the future we'll do it with the database info
        /*List<Message> listMsg = new ArrayList<>();
        Message chat = new Message();
        chat.fill_with_data(listMsg);

        recyclerView = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        chatConcreteViewAdapter = new ChatConcreteViewAdapter(this, chat);
        recyclerView.setAdapter(chatConcreteViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        List<Message> listMsg = new ArrayList<>();
        chatConcreteViewAdapter = new ChatConcreteViewAdapter(this, chat, listMsg);
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
