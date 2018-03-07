package es.lost2found.lost2found.loginregisterUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import es.lost2found.R;

public class RegisterActivity extends AppCompatActivity {
    private String msgerror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.msgerror = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    /**
     *
     * @param view
     */
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
            //new RegisterDB().execute(email, pass, name, role);
    }
}
