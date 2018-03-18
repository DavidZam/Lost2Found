package es.lost2found.lost2foundUI.announceUI.matchingAnnounceUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.chatUI.chatConcreteUI.ChatConcrete;

public class MatchAnnounce extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_announce);

        Toolbar tb = findViewById(R.id.toolbar_center);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);

        // In this example we fill announceList with a function fill_with_data(), in the future we'll do it with the database info
        List<Announce> announceList = new ArrayList<>();
        Announce announce = new Announce();
        announce.fill_with_data(announceList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.match_announce_reyclerview);
        MatchAnnounceViewAdapter adapter = new MatchAnnounceViewAdapter(announceList, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adding a ItemAnimator to the RecyclerView (Optional)
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        /*recyclerView.setItemAnimator(itemAnimator);*/
    }

    public void moreinfoannounce(View view) {
        Intent intent = new Intent(this, MatchAnnounceInfoActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     *
     * @param view
     */
    public void matchingAnnounce(View view) {

    }

}
