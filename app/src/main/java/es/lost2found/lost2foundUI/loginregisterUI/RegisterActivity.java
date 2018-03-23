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

public class RegisterActivity extends AppCompatActivity {

    private String msgerror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.msgerror = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     *
     * @param view
     */
    public void register(View view) {
        EditText editText = (EditText) findViewById(R.id.email);
        String name = editText.getText().toString();

        editText = (EditText) findViewById(R.id.name);
        String email= editText.getText().toString();

        editText = (EditText) findViewById(R.id.password);
        String pass = editText.getText().toString();

        editText = (EditText) findViewById(R.id.repassword);
        String confirmPass = editText.getText().toString();


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
        else
            new RegisterDB().execute(email, pass, name);
    }



    /**
     * Verify the sign up process after atempt to intert into the database.
     * @param user
     */
    private void processRegister(User user) {
        Intent intent = new Intent(this, AnnounceActivity.class);

        if(user != null) {    // Insert correct.
            SharedPreferences sp = getSharedPreferences("Login", 0);
            SharedPreferences.Editor ed = sp.edit();            // Saved the user login credencials.
            ed.putString("email", user.getEmail());
            ed.putString("nombre", user.getName());
            ed.commit();

            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
        else {
            this.msgerror = "Email already exist in the database.";
            TextView textView = (TextView) findViewById(R.id.user_already_exists);
            textView.setText(this.msgerror);
        }
    }


    /**
     * Classroom to perform database access async.
     */

    private class RegisterDB extends AsyncTask<String, Void, User> {
        private ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Sign in, please wait.");
            this.dialog.show();
        }


        @Override
        protected User doInBackground(String... strings) {
            return DB_user.insertUser(strings[0], strings[1], strings[2]);
        }

        @Override
        protected void onPostExecute(User result) {
            this.dialog.dismiss();
            processRegister(result);
        }
    }
}
