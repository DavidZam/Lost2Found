package es.lost2found.loginregisterUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import es.lost2found.R;
import es.lost2found.homeUI.HomeActivity;

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
    public void home(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     *
     * @param view
     */
    public void login(View view) {
        EditText editText = (EditText) findViewById(R.id.email);
        String email = editText.getText().toString();

        EditText passwordEditText = (EditText) findViewById(R.id.password);
        String password = passwordEditText.getText().toString();

        //new LoginDB().execute(email, password);
    }


}
