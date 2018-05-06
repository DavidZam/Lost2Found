package es.lost2found.lost2foundUI.placeUI;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import es.lost2found.R;
import es.lost2found.database.DB_mapPlace;
import es.lost2found.entities.ConcretePlace;
import es.lost2found.entities.MapPlace;
import es.lost2found.lost2foundUI.announceUI.NewAnnounceActivity;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMapClickListener { // , LocationListener

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient googleApiClient;
    public static final String TAG = MapActivity.class.getSimpleName();
    private LatLng currentLocationMark = new LatLng(40.417101, -3.703283); // Madrid
    private GoogleMap gMap;
    private LatLng objectLastLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    LocationManager locationManager;
    //String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);

        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //provider = locationManager.getBestProvider(new Criteria(), false);
        checkLocationPermission();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { // Si no tenemos permiso de localizacion
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) { // Solicitamos el permiso con una explicacion
                // Show an explanation to the user *asynchronously* -- don't block this thread waiting for the user's response! After the user sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Petición") // R.string.title_location_permission
                        .setMessage("Activa el GPS si quieres hacer uso de los servicios de localización") // R.string.text_location_permission
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() { // R.string.ok
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown  // Al solicitar el permiso se ejecuta el metodo onRequestPermissionsResult()
                                ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else { // Solicitamos el permiso sin explicacion
                // No explanation needed, we can request the permission. // Al solicitar el permiso se ejecuta el metodo onRequestPermissionsResult()
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else { // Si tenemos permiso de localizacion
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    double lat = location.getLatitude();
                                    double longit = location.getLongitude();
                                    currentLocationMark = new LatLng(lat, longit);
                                    gMap.addMarker(new MarkerOptions().position(currentLocationMark)).setVisible(true);
                                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationMark, 15));
                                    gMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
                                    if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                        gMap.setMyLocationEnabled(true);
                                    }
                                }
                            }
                        });
            }
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the location-related task you need to do.
                    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mFusedLocationClient.getLastLocation()
                                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        if (location != null) {
                                            double lat = location.getLatitude();
                                            double longit = location.getLongitude();
                                            currentLocationMark = new LatLng(lat, longit);
                                            gMap.addMarker(new MarkerOptions().position(currentLocationMark)).setVisible(true);
                                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationMark, 15));
                                            gMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
                                            if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                                gMap.setMyLocationEnabled(true);
                                            }
                                        }
                                    }
                                });
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();

        Log.i("Location info: Lat", lat.toString());
        Log.i("Location info: Lng", lng.toString());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        //gMap.addMarker(new MarkerOptions().position(currentLocationMark)).setVisible(true);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationMark, 15));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
        gMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        objectLastLocation = new LatLng(latLng.latitude, latLng.longitude);
        gMap.clear();
        gMap.addMarker(new MarkerOptions().position(objectLastLocation)).setVisible(true);
    }

    public void savePositionChosed(View v) {
        Double latitud = objectLastLocation.latitude;
        Double longitud = objectLastLocation.longitude;
        new insertMapPlaceDB().execute(latitud, longitud);
    }

    private class insertMapPlaceDB extends AsyncTask<Double, Void, MapPlace> {

        private ProgressDialog dialog = new ProgressDialog(MapActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Cargando...");
            this.dialog.show();
        }

        @Override
        protected MapPlace doInBackground(Double... params) {
            return DB_mapPlace.insertMapPlace(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(MapPlace result) {
            this.dialog.dismiss();
            processNewMaplace(result);
        }
    }

    private void processNewMaplace(MapPlace mapPlace) {
        Intent intent = new Intent(this, NewAnnounceActivity.class);
        SharedPreferences sp = getSharedPreferences("placeId", 0);
        SharedPreferences.Editor ed = sp.edit();  // Saved the place data filled by the user.
        Integer placeId = mapPlace.getId(); // transportPlace.getId();

        ed.putInt("idLugar", placeId); // Comprobar
        ed.apply();

        intent.putExtra("concretePlace", mapPlace);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent place = new Intent(this, PlaceActivity.class);
        startActivity(place);
        finish();
        return true;
    }
}
