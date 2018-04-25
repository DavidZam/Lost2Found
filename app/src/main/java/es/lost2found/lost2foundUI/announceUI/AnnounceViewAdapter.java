package es.lost2found.lost2foundUI.announceUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.announceUI.matchingAnnounceUI.MatchAnnounce;

public class AnnounceViewAdapter extends RecyclerView.Adapter<AnnounceViewHolder>{
    List<Announce> listAnnounce;
    Context context;
    String actualUser;

    public AnnounceViewAdapter(List<Announce> listAnnounce, Context context, String actualUser) {
        this.listAnnounce = listAnnounce;
        this.context = context;
        this.actualUser = actualUser;
    }

    @Override
    public AnnounceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout and initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_announce, parent, false);
        AnnounceViewHolder holder = new AnnounceViewHolder(v);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View itemView = v.findViewById(R.id.activity_concrete_chat);
                Context context = v.getContext();
                Intent intent = new Intent(context, MatchAnnounce.class);
                context.startActivity(intent);
            }
        });


        /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = holder .getAdapterPosition();
            // This is org.greenrobot.eventbus
            Application.getInstance().getEventBus().post(new OnHistoryClickEvent(position));
        }
        });
        holder .itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position = holder .getAdapterPosition();
            // This is org.greenrobot.eventbus
            Application.getInstance().getEventBus().post(new OnHistoryLongClickEvent(position));
            return true;
        }
        });
         */

        return holder;
    }

    @Override
    public void onBindViewHolder(AnnounceViewHolder holder, int position) {
        // Use the provided ChatView_Holder on the onCreateViewHolder method to populate the current row on the RecycleView
        holder.announceType.setText(listAnnounce.get(position).getAnnounceType());
        holder.announceDateText.setText(listAnnounce.get(position).getAnnounceDateText());
        holder.announceHourText.setText(listAnnounce.get(position).getAnnounceHourText());
        holder.announceCategorie.setText(listAnnounce.get(position).getAnnounceCategorie());
        holder.color.setBackgroundColor(listAnnounce.get(position).getColor());

        String userAnnounceOwnerName = listAnnounce.get(position).getUserOwner();
        if(userAnnounceOwnerName.equals(actualUser)) {
        //if(listAnnounce.get(position).userOwner.equals(actualUser)) {
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

        //animate(holder);
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

    /*public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }*/
}
