package es.lost2found.lost2foundUI.announceUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Collections;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.announceUI.matchingAnnounceUI.MatchAnnounce;
import es.lost2found.lost2foundUI.seekerUI.SeekerActivity;
import es.lost2found.lost2foundUI.seekerUI.SeekerAnnounceInfoActivity;

public class AnnounceViewAdapter extends RecyclerView.Adapter<AnnounceViewHolder>{
    private List<Announce> listAnnounce;
    private Context context;
    private String actualUser;
    private String parentAct;
    private String typePlace;

    public AnnounceViewAdapter(List<Announce> listAnnounce, Context context, String actualUser, String parentAct, String typePlace) {
        this.listAnnounce = listAnnounce;
        this.context = context;
        this.actualUser = actualUser;
        this.parentAct = parentAct;
        this.typePlace = typePlace;
    }

    @Override
    public AnnounceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout and initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_announce, parent, false);
        AnnounceViewHolder holder = new AnnounceViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(AnnounceViewHolder holder, int position) {
        holder.announceType.setText(listAnnounce.get(position).getAnnounceType());
        holder.announceDateText.setText(listAnnounce.get(position).DDMMYYYY());
        holder.announceHourText.setText(listAnnounce.get(position).getAnnounceHourText());
        holder.announceCategorie.setText(listAnnounce.get(position).getAnnounceCategorie());
        holder.color.setBackgroundColor(listAnnounce.get(position).getColor());

        String userAnnounceOwnerName = listAnnounce.get(position).getUserOwner();
        if(userAnnounceOwnerName.equals(actualUser)) {
            holder.owner.setText("Yo");
        } else {
            holder.owner.setText(listAnnounce.get(position).getUserOwner());
        }

        if(listAnnounce.get(position).announceCategorie.equals("Telefono")) {
            holder.categorieIcon.setImageResource(R.drawable.ic_phone_android);
        } else if(listAnnounce.get(position).announceCategorie.equals("Cartera")) {
            holder.categorieIcon.setImageResource(R.drawable.ic_wallet);
        } else if(listAnnounce.get(position).announceCategorie.equals("Tarjeta bancaria") || listAnnounce.get(position).announceCategorie.equals("Tarjeta transporte")) {
            holder.categorieIcon.setImageResource(R.drawable.ic_card);
        } else if(listAnnounce.get(position).announceCategorie.equals("Otro")) {
            holder.categorieIcon.setImageResource(R.drawable.ic_other);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, SeekerAnnounceInfoActivity.class);

                Announce announce = listAnnounce.get(position);
                intent.putExtra("myAnnounce", announce);

                if(parentAct.equals(AnnounceActivity.class.getSimpleName())) {
                    intent.putExtra("parentAct", "announce");
                    intent.putExtra("actualUser", actualUser);
                    intent.putExtra("typePlace", typePlace);
                } else if(parentAct.equals(SeekerActivity.class.getSimpleName())) {
                    intent.putExtra("parentAct", "seeker");
                }
                context.startActivity(intent);
            }
        });
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
    public void insert(int position, Announce announce) {
        listAnnounce.add(position, announce);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified announce Object
    public void remove(Announce announce) {
        int position = listAnnounce.indexOf(announce);
        listAnnounce.remove(position);
        notifyItemRemoved(position);
    }

    public List<Announce> getListAnnounce() {
        return this.listAnnounce;
    }
}
