package es.lost2found.lost2foundUI.loginregisterUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import es.lost2found.R;
import es.lost2found.database.DB_user;
import es.lost2found.entities.User;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences("Login", 0);
        String email = sp.getString("email", null);
        if(email != null) {
            String name = sp.getString("nombre", null);
            this.processLogin(new User(email, name, null));
        }
    }

    public void login(View view) {
        EditText editText = findViewById(R.id.email);
        String email = editText.getText().toString();

        EditText passwordEditText = findViewById(R.id.password);
        String password = passwordEditText.getText().toString();

        new LoginDB().execute(email, password);
    }


    private class LoginDB extends AsyncTask<String, Void, User> {

        private ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Entrando, espera");
            this.dialog.show();
        }

        @Override
        protected User doInBackground(String... strings) {
            return DB_user.findUserByEmail(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(User result) {
            this.dialog.dismiss();
            processLogin(result);
        }
    }

    private void processLogin(User user) {
        Intent intent = new Intent(this, AnnounceActivity.class);

        SharedPreferences sp = getSharedPreferences("Login", 0);
        String email = sp.getString("email", null);
        if(email != null) { // Login perform with SharedPreferences credentials.
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
        else {              // Login perform without SharedPreferences credentials.
            if(user != null) {
                SharedPreferences.Editor ed = sp.edit();            // Saved the user login credencials.
                ed.putString("email", user.getEmail());
                ed.commit();

                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
            else {
                TextView textView = (TextView) findViewById(R.id.wrong_user);
                textView.setText("Email o contraseña incorrectos");
            }
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    /*public void announce(View view) {
        Intent intent = new Intent(this, AnnounceActivity.class);
        startActivity(intent);
        finish();
    }*/

}
