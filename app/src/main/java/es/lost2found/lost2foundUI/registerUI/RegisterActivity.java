package es.lost2found.lost2foundUI.registerUI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import es.lost2found.R;
import es.lost2found.database.DB_user;
import es.lost2found.entities.User;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;
import es.lost2found.lost2foundUI.loginUI.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private String msgerror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void register(View view) {
        EditText editText = findViewById(R.id.email);
        String email = editText.getText().toString();

        editText = findViewById(R.id.name);
        String name = editText.getText().toString();

        editText = findViewById(R.id.password);
        String pass = editText.getText().toString();

        editText = findViewById(R.id.repassword);
        String confirmPass = editText.getText().toString();


        if(email.equalsIgnoreCase("") || name.equalsIgnoreCase("") || pass.equalsIgnoreCase("") || confirmPass.equalsIgnoreCase("")) {
            this.msgerror = "Completa todos los campos";
            TextView textView = findViewById(R.id.user_already_exists);
            textView.setText(this.msgerror);
        } else if(!pass.equals(confirmPass)) {
            this.msgerror = "Las contraseñas no coinciden";
            TextView textView = findViewById(R.id.user_already_exists);
            textView.setText(this.msgerror);
        } else
            new RegisterDB().execute(name, email, pass);
    }

    private class RegisterDB extends AsyncTask<String, Void, User> {
        private ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Comprobando, espera");
            this.dialog.show();
        }

        @Override
        protected User doInBackground(String... strings) {
            boolean userExists = DB_user.checkIfUserAlreadyExists(strings[0], strings[1]);
            if(userExists) { // User exists
                return null;
            } else {
                return DB_user.insertUser(strings[0], strings[1], strings[2]);
            }
        }

        @Override
        protected void onPostExecute(User result) {
            this.dialog.dismiss();
            processRegister(result);
        }
    }

    /**
     * Verify the sign up process after atempt to insert into the database.
     * @param user
     */
    private void processRegister(User user) {
        if(user == null) { // User already exists
            this.msgerror = "Ese usuario ya existe";
            TextView textView = findViewById(R.id.user_already_exists);
            textView.setText(this.msgerror);
        } else { // User doesn't exists
            Intent intent = new Intent(this, AnnounceActivity.class);
            SharedPreferences sp = getSharedPreferences("Login", 0);
            SharedPreferences.Editor ed = sp.edit();            // Saved the user login credencials.
            ed.putString("email", user.getEmail());
            ed.putString("nombre", user.getName());
            Integer userId = user.getId();
            ed.putInt("userId", userId);
            ed.apply(); //ed.commit()

            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
    }
}
