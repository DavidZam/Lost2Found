package es.lost2found.lost2foundUI.placeUI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import es.lost2found.R;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;
import es.lost2found.lost2foundUI.placeUI.concretePlaceUI.FillConcretePlaceActivity;
import es.lost2found.lost2foundUI.placeUI.transportUI.TransportPlaceActivity;

public class PlaceActivity extends AppCompatActivity {

    //private boolean permissionGranted = false;
    //private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    public void transportPlace(View view) {
        Intent intent = new Intent(this, TransportPlaceActivity.class);
        startActivity(intent);
    }

    public void map(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void concretePlace(View view) {
        Intent intent = new Intent(this, FillConcretePlaceActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent newannounce = new Intent(this, AnnounceActivity.class);
        startActivity(newannounce);
        finish();
        return true;
    }
}
