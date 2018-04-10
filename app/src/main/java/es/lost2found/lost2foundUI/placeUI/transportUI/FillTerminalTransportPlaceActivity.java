package es.lost2found.lost2foundUI.placeUI.transportUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import es.lost2found.R;
import es.lost2found.lost2foundUI.announceUI.NewAnnounceActivity;

public class FillTerminalTransportPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_transport_place);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent place = new Intent(this, TransportPlaceActivity.class);
        startActivity(place);
        finish();
        return true;
    }

    public void saveTerminalTransportPlaceData(View view) {
        // Terminal
        EditText terminal = findViewById(R.id.terminal);
        String terminalText = terminal.getText().toString();

        SharedPreferences sp = getApplicationContext().getSharedPreferences("transportTerminalPlace", 0);
        SharedPreferences.Editor ed = sp.edit();            // Saved the user color choice.
        ed.putString("terminalChoice", terminalText);
        ed.apply();

        Intent intent = new Intent(this, NewAnnounceActivity.class);
        startActivity(intent);
        finish();
    }

}
