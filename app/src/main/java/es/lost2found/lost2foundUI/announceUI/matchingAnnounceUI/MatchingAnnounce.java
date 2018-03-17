package es.lost2found.lost2foundUI.announceUI.matchingAnnounceUI;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.announceUI.AnnounceViewAdapter;

public class MatchingAnnounce extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_announce);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        // In this example we fill announceList with a function fill_with_data(), in the future we'll do it with the database info
        List<Announce> announceList = new ArrayList<>();
        Announce announce = new Announce();
        announce.fill_with_data(announceList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.match_announce_reyclerview);
        MatchingAnnounceViewAdapter adapter = new MatchingAnnounceViewAdapter(announceList, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adding a ItemAnimator to the RecyclerView (Optional)
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        /*recyclerView.setItemAnimator(itemAnimator);*/
    }

    /**
     *
     * @param view
     */
    public void matchingAnnounce(View view) {

    }

}
