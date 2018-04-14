package es.lost2found.lost2foundUI.otherUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import es.lost2found.R;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;
import es.lost2found.lost2foundUI.chatUI.ChatActivity;
import es.lost2found.lost2foundUI.loginregisterUI.LoginActivity;
import es.lost2found.lost2foundUI.seekerUI.SeekerActivity;

/**
 * Created by Usuario on 13/04/2018.
 */

public class ConfigurationActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private View headerLayout;
    private String nombreActual, emailActual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        ////
        findViewById(R.id.cambiarNombre).setOnClickListener(view -> createAndDisplayDialogNombre());
        findViewById(R.id.cambiarEmail).setOnClickListener(view -> createAndDisplayDialogEmail());
        findViewById(R.id.cambiarPass).setOnClickListener(view -> createAndDisplayDialogPass());
        ////
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nav_view);

        headerLayout = navView.getHeaderView(0);
        TextView emailUser = headerLayout.findViewById(R.id.user_mail);
        TextView nameUser = headerLayout.findViewById(R.id.user_name);
        SharedPreferences spref = getApplicationContext().getSharedPreferences("Login", 0);
        if(spref != null) {
            if (spref.contains("email")) {
                String userEmail = spref.getString("email", "");
                emailUser.setText(userEmail);
                emailActual = emailUser.getText().toString();
            }
            if (spref.contains("name")) {
                String userName = spref.getString("name", "");
                nameUser.setText(userName);
                nombreActual = nameUser.getText().toString();
            }
        }

        final Intent home = new Intent(this, AnnounceActivity.class);
        final Intent buscar = new Intent(this, SeekerActivity.class);
        final Intent contact = new Intent(this, ContactActivity.class);
        final Intent chat = new Intent(this, ChatActivity.class);
        final Intent help = new Intent(this, HelpActivity.class);
        final Intent rate = new Intent(this, RateActivity.class);

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected

                        if(menuItem.getItemId()== R.id.nav_home) {
                            startActivity(home);
                        }else if(menuItem.getItemId()== R.id.nav_search) {
                            startActivity(buscar);
                        }else if(menuItem.getItemId()== R.id.nav_contact) {
                            startActivity(contact);
                        } else if(menuItem.getItemId()== R.id.nav_chat) {
                            startActivity(chat);
                        } else if(menuItem.getItemId()== R.id.nav_help) {
                            startActivity(help);
                        }else if(menuItem.getItemId()== R.id.nav_feedback) {
                            startActivity(rate);
                        } else if(menuItem.getItemId()== R.id.nav_logout) {
                            logoutUser();
                        }
                        return true;
                    }
                }
        );
        navView.setCheckedItem(R.id.nav_info);

    }

    private void createAndDisplayDialogNombre() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout       = new LinearLayout(this);
        TextView tvMessage        = new TextView(this);
        TextView tvMessage2        = new TextView(this);
        final EditText etInput    = new EditText(this);

        tvMessage2.setText("Nombre actual: "+ nombreActual);
        tvMessage.setText("Introduce tu nuevo nombre:");
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
            Toast.makeText(this, "Nuevo nombre: "+nombre, Toast.LENGTH_SHORT).show();
        });

        builder.create().show();
    }

    private void createAndDisplayDialogEmail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout       = new LinearLayout(this);
        TextView tvMessage        = new TextView(this);
        TextView tvMessage2        = new TextView(this);
        final EditText etInput    = new EditText(this);

        tvMessage2.setText("Email actual: "+ emailActual);
        tvMessage.setText("Introduce tu nuevo email:");
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
            Toast.makeText(this, "Nuevo email: "+email, Toast.LENGTH_SHORT).show();
        });

        builder.create().show();
    }

    private void createAndDisplayDialogPass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout       = new LinearLayout(this);
        TextView tvMessage        = new TextView(this);
        final EditText etInput    = new EditText(this);

        tvMessage.setText("Introduce tu nueva contraseña:");
        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        etInput.setSingleLine();
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(tvMessage);
        layout.addView(etInput);
        layout.setPadding(50, 40, 50, 10);

        builder.setView(layout);

        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            Toast.makeText(this, "No has hecho cambios", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });

        builder.setPositiveButton("Cambiar", (dialog, which) -> {
            Toast.makeText(this, "Contraseña cambiada", Toast.LENGTH_SHORT).show();
        });

        builder.create().show();
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
        ed.putString("name", null);
        ed.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
