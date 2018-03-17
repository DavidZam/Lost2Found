package es.lost2found.lost2foundUI.seekerUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;


//import com.jaredrummler.materialspinner.MaterialSpinner;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.announceUI.AnnounceViewAdapter;
import es.lost2found.lost2foundUI.announceUI.AnnounceActivity;
import es.lost2found.lost2foundUI.chatUI.ChatActivity;


public class SeekerActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker);

        String[] categorias = {"Tarjetas Bancarias", "Tarjetas Transporte Público", "Carteras/Monederos", "Teléfonos", "Otros"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categorias);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner) findViewById(R.id.listaCategorias);
        materialDesignSpinner.setAdapter(arrayAdapter);

        //Menú
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nav_view);

        final Intent menu = new Intent(this, AnnounceActivity.class);
        final Intent chat = new Intent(this, ChatActivity.class);

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected

                        if(menuItem.getItemId() == R.id.nav_home) {
                            startActivity(menu);
                        }else if(menuItem.getItemId() == R.id.nav_chat) {
                            startActivity(chat);
                        }

                        return true;
                    }
                }
        );
        navView.setCheckedItem(R.id.nav_search);

        // In this example we fill announceList with a function fill_with_data(), in the future we'll do it with the database info
        List<Announce> announceList = new ArrayList<>();
        Announce announce = new Announce();
        announce.fill_with_data(announceList);

        RecyclerView recyclerView = findViewById(R.id.search_recyclerview);
        AnnounceViewAdapter adapter = new AnnounceViewAdapter(announceList, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adding a ItemAnimator to the RecyclerView (Optional)
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        /*recyclerView.setItemAnimator(itemAnimator);*/
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

    /**
     *
     * @param view
     */
    public void seeker(View view) {

    }
}