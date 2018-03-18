package es.lost2found.lost2foundUI.loginregisterUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import es.lost2found.R;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    /**
     *
     * @param view
     */
    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     *
     * @param view
     */
    public void announce(View view) {
        Intent intent = new Intent(this, AnnounceActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     *
     * @param view
     */
    public void login(View view) {
        /*EditText editText = (EditText) findViewById(R.id.email);
        String email = editText.getText().toString();

        EditText passwordEditText = (EditText) findViewById(R.id.password);
        String password = passwordEditText.getText().toString();^*/

        //new LoginDB().execute(email, password);
    }


}
