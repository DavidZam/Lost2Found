package es.lost2found.lost2foundUI.otherUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import es.lost2found.R;
import es.lost2found.database.DB_user;
import es.lost2found.entities.User;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;
import es.lost2found.lost2foundUI.chatUI.ChatActivity;
import es.lost2found.lost2foundUI.loginUI.LoginActivity;
import es.lost2found.lost2foundUI.openDataUI.OpenDataActivity;
import es.lost2found.lost2foundUI.seekerUI.SeekerActivity;

public class SettingsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private String nombreActual, emailActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        findViewById(R.id.cambiarNombre).setOnClickListener(view -> createAndDisplayDialogNombre());
        findViewById(R.id.cambiarEmail).setOnClickListener(view -> createAndDisplayDialogEmail());
        findViewById(R.id.cambiarPass).setOnClickListener(view -> createAndDisplayDialogPass());

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));

        NavigationView navView = findViewById(R.id.nav_view);

        View headerLayout = navView.getHeaderView(0);
        TextView emailUser = headerLayout.findViewById(R.id.user_mail);
        TextView nameUser = headerLayout.findViewById(R.id.user_name);
        SharedPreferences spref = getApplicationContext().getSharedPreferences("Login", 0);
        if(spref != null) {
            if (spref.contains("email")) {
                String userEmail = spref.getString("email", "");
                emailUser.setText(userEmail);
                emailActual = emailUser.getText().toString();
            }
            if (spref.contains("nombre")) {
                String userName = spref.getString("nombre", "");
                nameUser.setText(userName);
                nombreActual = nameUser.getText().toString();
            }
        }

        final Intent home = new Intent(this, AnnounceActivity.class);
        final Intent buscar = new Intent(this, SeekerActivity.class);
        final Intent contact = new Intent(this, ContactActivity.class);
        final Intent aboutus = new Intent(this, AboutUsActivity.class);
        final Intent chat = new Intent(this, ChatActivity.class);
        final Intent help = new Intent(this, HelpActivity.class);
        final Intent rate = new Intent(this, RateActivity.class);
        final Intent openData = new Intent(this, OpenDataActivity.class);

        navView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();

                    if(menuItem.getItemId()== R.id.nav_home) {
                        startActivity(home);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_search) {
                        startActivity(buscar);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_contact) {
                        startActivity(contact);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_chat) {
                        startActivity(chat);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_open_data) {
                        startActivity(openData);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_help) {
                        startActivity(help);
                        finish();
                    }else if(menuItem.getItemId()== R.id.nav_feedback) {
                        startActivity(rate);
                        finish();
                    } else if(menuItem.getItemId() == R.id.nav_info) {
                        startActivity(aboutus);
                        finish();
                    } else if(menuItem.getItemId()== R.id.nav_logout) {
                        logoutUser();
                    }
                    return true;
                }
        );
        navView.setCheckedItem(R.id.nav_settings);

    }

    private void createAndDisplayDialogNombre() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout       = new LinearLayout(this);
        TextView tvMessage        = new TextView(this);
        TextView tvMessage2        = new TextView(this);
        final EditText etInput    = new EditText(this);

        tvMessage2.setText(String.format("Nombre actual: %s", nombreActual));
        tvMessage.setText(getResources().getText(R.string.new_name));
        tvMessage2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        etInput.setSingleLine();
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(tvMessage2);
        layout.addView(tvMessage);
        layout.addView(etInput);
        layout.setPadding(50, 40, 50, 10);

        builder.setView(layout);

        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            Toast.makeText(this, "No has hecho cambios", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });

        builder.setPositiveButton("Cambiar", (dialog, which) -> {
            String nombre = etInput.getText().toString();
            new updateName().execute(nombre, emailActual);

            Toast.makeText(this, "Nuevo nombre: "+nombre, Toast.LENGTH_SHORT).show();

            nombreActual = nombre;
        });

        builder.create().show();

    }

    private void createAndDisplayDialogEmail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout       = new LinearLayout(this);
        TextView tvMessage        = new TextView(this);
        TextView tvMessage2        = new TextView(this);
        final EditText etInput    = new EditText(this);

        tvMessage2.setText(String.format("Email actual: %s", emailActual));
        tvMessage.setText(getResources().getText(R.string.new_email));
        tvMessage2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        etInput.setSingleLine();
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(tvMessage2);
        layout.addView(tvMessage);
        layout.addView(etInput);
        layout.setPadding(50, 40, 50, 10);

        builder.setView(layout);

        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            Toast.makeText(this, "No has hecho cambios", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });

        builder.setPositiveButton("Cambiar", (dialog, which) -> {
            String email = etInput.getText().toString();

            new updateEmail().execute(nombreActual, email, emailActual);
        });

        builder.create().show();
    }

    private void createAndDisplayDialogPass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout       = new LinearLayout(this);
        TextView tvMessage        = new TextView(this);
        EditText etInput    = new EditText(this);
        EditText etInput2    = new EditText(this);

        tvMessage.setText(getResources().getText(R.string.new_pass));
        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        etInput.setSingleLine();
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(tvMessage);
        layout.addView(etInput);
        layout.addView(etInput2);
        layout.setPadding(50, 40, 50, 10);
        builder.setView(layout);

        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            Toast.makeText(this, "No has hecho cambios", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });

        builder.setPositiveButton("Cambiar", (dialog, which) -> {
            String pass = etInput.getText().toString();
            String repass = etInput2.getText().toString();

            if(pass.equals(repass))
                new updatePassword().execute(pass, emailActual, nombreActual);
            else
                Toast.makeText(this, "Las contraseñas no coinciden. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show();
        });

        builder.create().show();
    }

    private class updateName extends AsyncTask<String, Void, User> {

        @Override
        protected User doInBackground(String... strings) {
            return DB_user.updateNameUser(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(User result) {
            processUpdate(result);
        }
    }

    private class updateEmail extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... strings) {

            boolean userExists = DB_user.checkIfEmailAlreadyExists(strings[1]);

            if(!userExists){
                return DB_user.updateEmailUser(strings[0], strings[1], strings[2]);

            }
            return null;
        }

        @Override
        protected void onPostExecute(User result) {
            processUpdate(result);
        }
    }

    private class updatePassword extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... strings) {

            return DB_user.updatePasswordUser(strings[0], strings[1], strings[2]);
        }

        @Override
        protected void onPostExecute(User result) {
            processUpdate(result);
        }
    }

    private void processUpdate(User user) {
        Intent intent = new Intent(this, SettingsActivity.class);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Login", 0);

        if(user != null) {
            SharedPreferences.Editor ed = sp.edit();
            Integer userId = user.getId();
            ed.putInt("userId", userId);
            ed.putString("email", user.getEmail());
            ed.putString("nombre", user.getName());
            ed.apply();

            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        SharedPreferences sp = getSharedPreferences("Login", 0);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("email", null);
        ed.putString("nombre", null);
        ed.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
