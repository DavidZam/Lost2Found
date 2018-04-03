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

import org.json.JSONObject;

import es.lost2found.R;
import es.lost2found.database.DB_user;
import es.lost2found.entities.User;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;

public class RegisterActivity extends AppCompatActivity {

    private String msgerror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
            /*String emailUserDB = "";
            String nameUserDB = "";
            User user = DB_user.findUserByEmail(strings[1], strings[2]);
            if(user != null) {
                emailUserDB = user.getEmail();
                nameUserDB = user.getName();
                if (!strings[1].equalsIgnoreCase(emailUserDB) || !strings[0].equalsIgnoreCase(nameUserDB)) { // || strings[0].equalsIgnoreCase(passHashUserDB)
                    return DB_user.insertUser(strings[0], strings[1], strings[2]);
                }
            }
            // Usuario nulo: no existe o algun campo no coincide
            // Usuario no nulo: TODO coincide, email, name, passHash*/

            //User user = DB_user.checkIfUserAlreadyExists(strings[0], strings[1]);
            //JSONObject object = DB_user.checkIfUserAlreadyExists(strings[0], strings[1]);
            boolean userExists = DB_user.checkIfUserAlreadyExists(strings[0], strings[1]);
            if(userExists) { // User exists
                /*String emailUserDB = user.getEmail();
                String nameUserDB = user.getName();
                if(!strings[1].equalsIgnoreCase(emailUserDB) && !strings[0].equalsIgnoreCase(nameUserDB)) {

                } else {*/
                    return null;
                //}
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
            ed.apply(); //ed.commit()

            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
    }
}
