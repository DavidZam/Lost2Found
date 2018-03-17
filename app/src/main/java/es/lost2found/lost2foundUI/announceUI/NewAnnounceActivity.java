package es.lost2found.lost2foundUI.announceUI;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import es.lost2found.DatePickerUI;
import es.lost2found.R;
import es.lost2found.lost2foundUI.TimePickerUI;

public class NewAnnounceActivity extends AppCompatActivity {
    //private DrawerLayout mDrawerLayout;
    private EditText dateText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_announce);

        String[] announceType = {"Pérdida", "Hallazgo"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, announceType);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner) findViewById(R.id.announce_type);
        materialDesignSpinner.setAdapter(arrayAdapter);

        String[] categories = {"Tarjetas Bancarias", "Tarjetas Transporte Público", "Carteras/Monederos", "Teléfonos", "Otros"};
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categories);
        MaterialBetterSpinner materialDesignSpinner2 = (MaterialBetterSpinner) findViewById(R.id.listCategories);
        materialDesignSpinner2.setAdapter(arrayAdapter2);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerUI();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerUI();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
