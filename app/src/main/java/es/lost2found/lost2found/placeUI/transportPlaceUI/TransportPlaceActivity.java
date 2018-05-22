package es.lost2found.lost2found.placeUI.transportPlaceUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import es.lost2found.R;
import es.lost2found.lost2found.placeUI.PlaceActivity;

public class TransportPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_place);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.color700));
    }

    public void saveMetroClicked(View view) {
        SharedPreferences sp = getSharedPreferences("transportButton", 0);
        SharedPreferences.Editor ed = sp.edit();            // Saved the user login credencials.
        ed.putBoolean("metro", true);
        ed.putBoolean("bus", false);
        ed.putBoolean("tren", false);
        ed.apply();
        Intent intent = new Intent(this, FillTransportPlaceActivity.class);
        startActivity(intent);
    }

    public void saveBusClicked(View view) {
        SharedPreferences sp = getSharedPreferences("transportButton", 0);
        SharedPreferences.Editor ed = sp.edit();            // Saved the user login credencials.
        ed.putBoolean("bus", true);
        ed.putBoolean("metro", false);
        ed.putBoolean("tren", false);
        ed.apply();
        Intent intent = new Intent(this, FillTransportPlaceActivity.class);
        startActivity(intent);
    }

    public void saveTrainClicked(View view) {
        SharedPreferences sp = getSharedPreferences("transportButton", 0);
        SharedPreferences.Editor ed = sp.edit();            // Saved the user login credencials.
        ed.putBoolean("tren", true);
        ed.putBoolean("bus", false);
        ed.putBoolean("metro", false);
        ed.apply();
        Intent intent = new Intent(this, FillTransportTrainPlaceActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent place = new Intent(this, PlaceActivity.class);
        startActivity(place);
        finish();
        return true;
    }
}
