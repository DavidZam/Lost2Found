package es.lost2found.lost2foundUI.seekerUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.typeObjectUI.WalletActivity;

public class SeekerAnnounceViewAdapter extends RecyclerView.Adapter<SeekerAnnounceViewHolder> implements Serializable {
    List<Announce> listAnnounce;
    Context context;

    public SeekerAnnounceViewAdapter(List<Announce> listAnnounce, Context context) {
        this.listAnnounce = listAnnounce;
        this.context = context;
    }

    @Override
    public SeekerAnnounceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout and initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_announce, parent, false);
        SeekerAnnounceViewHolder holder = new SeekerAnnounceViewHolder(v);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //View itemView = v.findViewById(R.id.activity_concrete_chat);
                Context context = v.getContext();
                Intent intent = new Intent(context, SeekerAnnounceInfoActivity.class);
                int clickedposition = (int) v.getTag();
                Announce announce = listAnnounce.get(clickedposition);
                intent.putExtra("myAnnounce", announce);
                context.startActivity(intent);


            }
        });

        /*holder.itemView.setOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

        return holder;
    }

    @Override
    public void onBindViewHolder(SeekerAnnounceViewHolder holder, int position) {

        // Use the provided ChatView_Holder on the onCreateViewHolder method to populate the current row on the RecycleView
        /*holder.title.setText(listAnnounce.get(position).title);
        holder.description.setText(listAnnounce.get(position).description);
        holder.imageView.setImageResource(listAnnounce.get(position).imageId);*/
        //animate(holder);

        holder.announceType.setText(listAnnounce.get(position).getAnnounceType());
        holder.announceDateText.setText(listAnnounce.get(position).getAnnounceDateText());
        holder.announceHourText.setText(listAnnounce.get(position).getAnnounceHourText());
        //holder.currentTime.setText(listAnnounce.get(position).getCurrentTime());
        holder.announceCategorie.setText(listAnnounce.get(position).getAnnounceCategorie());

        if(listAnnounce.get(position).announceCategorie.equals("Telefono")) {
            holder.categorieIcon.setImageResource(R.drawable.ic_phone_android);
        } else if(listAnnounce.get(position).announceCategorie.equals("Cartera")) {
            holder.categorieIcon.setImageResource(R.drawable.ic_wallet);
        } else if(listAnnounce.get(position).announceCategorie.equals("Tarjeta bancaria") || listAnnounce.get(position).announceCategorie.equals("Tarjeta transporte")) {
            holder.categorieIcon.setImageResource(R.drawable.ic_card);
        } else if(listAnnounce.get(position).announceCategorie.equals("Otro")) {
            holder.categorieIcon.setImageResource(R.drawable.ic_other);
        }
    }

    @Override
    public int getItemCount() {
        return listAnnounce.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item (announce) to the RecyclerView on a predefined position
    public void insert(int position, Announce annonuce) {
        listAnnounce.add(position, annonuce);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified announce Object
    public void remove(Announce announce) {
        int position = listAnnounce.indexOf(announce);
        listAnnounce.remove(position);
        notifyItemRemoved(position);
    }
}
