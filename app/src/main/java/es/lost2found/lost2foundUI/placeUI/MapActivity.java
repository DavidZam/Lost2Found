package es.lost2found.lost2foundUI.placeUI;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import es.lost2found.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener { // , LocationListener

    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    //private LocationRequest mLocationRequest;
    private GoogleApiClient googleApiClient;
    public static final String TAG = MapActivity.class.getSimpleName();
    private LatLng currentLocationMark = new LatLng(40.417101, -3.703283); // Madrid
    private boolean permissionGranted = false;
    private GoogleMap gMap;
    private View mapView;

    private FusedLocationProviderClient mFusedLocationClient;
    //private boolean locationSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.map);

        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
            String requiredPermission = "android.permission.ACCESS_FINE_LOCATION";
            int checkVal = getApplicationContext().checkCallingOrSelfPermission(requiredPermission);
            if(checkVal == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true;
            }
        }

        //googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        // Create the LocationRequest object
        /*mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds*/

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        //new askForLocation().execute(); // Asigna un valor a un booleano para comprobar si se tiene el permiso de localizacion
        //permissionGranted = getIntent().getExtras().getBoolean("permissionGranted");
        /*if(permissionGranted) {
            setCurrentLocation(gMap);
            gMap.addMarker(new MarkerOptions().position(currentLocationMark)).setVisible(true);
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationMark, 15));
            gMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
        }*/
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //gMap.addMarker(new MarkerOptions().position(currentLocationMark)).setVisible(true);
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationMark, 15));
            gMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
            gMap.setMyLocationEnabled(true);
        }

            //ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
            //String requiredPermission = "android.permission.ACCESS_FINE_LOCATION";
            //int checkVal = getApplicationContext().checkCallingOrSelfPermission(requiredPermission);
            //if(checkVal == PackageManager.PERMISSION_GRANTED) {
                //permissionGranted = true;
                //gMap.setMyLocationEnabled(true);
            //}
        //} else {
            //permissionGranted = true;
            //gMap.setMyLocationEnabled(true);
        //}

        if(permissionGranted) {
            setCurrentLocation(gMap);


            //gMap.addMarker(new MarkerOptions().position(currentLocationMark)).setVisible(true);
            //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationMark, 15));
            //gMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
        }

        //new askForLocation().execute();
    }

    public void setCurrentLocation(GoogleMap googleMap) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                double lat = location.getLatitude();
                                double longit = location.getLongitude();
                                currentLocationMark = new LatLng(lat, longit);
                                gMap.addMarker(new MarkerOptions().position(currentLocationMark)).setVisible(true);
                                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationMark, 15));
                                gMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
                            }
                        }
                    });
        }
    }

    /*private class askForLocation extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
                String requiredPermission = "android.permission.ACCESS_FINE_LOCATION";
                int checkVal = getApplicationContext().checkCallingOrSelfPermission(requiredPermission);
                if(checkVal == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    gMap.setMyLocationEnabled(true);
                }
            } else {
                permissionGranted = true;
                gMap.setMyLocationEnabled(true);
            }
            return permissionGranted;
        }

        @Override
        protected void onPostExecute(Boolean permission) {
                gMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        if(permission) {
                            setCurrentLocation(gMap);
                            //gMap.addMarker(new MarkerOptions().position(currentLocationMark)).setVisible(true);
                            //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationMark, 15));
                            //gMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
                        }
                        return true;
                    }
                });
        }
    }*/

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_FINE_LOCATION:
                Toast.makeText(this, "Selecciona en el mapa el lugar de la perdida o hallazgo", Toast.LENGTH_SHORT).show();
                break;
        }
    }*/

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

    /*@Override
    protected void onResume() {
        super.onResume();
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }*/

    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                double lat = location.getLatitude(), lon = location.getLongitude();
                currentLocationMark = new LatLng(lat, lon);
                gMap.addMarker(new MarkerOptions().position(currentLocationMark)).setVisible(true);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationMark, 15));
                gMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
                gMap.setMyLocationEnabled(true);
            }
        }
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
