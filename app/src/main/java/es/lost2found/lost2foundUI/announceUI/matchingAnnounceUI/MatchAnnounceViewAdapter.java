package es.lost2found.lost2foundUI.announceUI.matchingAnnounceUI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.announceUI.AnnounceViewHolder;
import es.lost2found.lost2foundUI.seekerUI.SeekerAnnounceInfoActivity;

public class MatchAnnounceViewAdapter extends RecyclerView.Adapter<AnnounceViewHolder> {

    private List<Announce> listAnnounce = Collections.emptyList();
    private Context context;
    private String actualUser;

    public MatchAnnounceViewAdapter(List<Announce> listAnnounce, Context context, String actualUser) {
        this.listAnnounce = listAnnounce;
        this.context = context;
        this.actualUser = actualUser;
    }

    @Override
    public AnnounceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout and initialize the View Holder


        //Comentamos esta linea porque no está bien la vista row_matching_announce y usamos row_announce de momento
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_matching_announce, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_announce, parent, false);
        AnnounceViewHolder holder = new AnnounceViewHolder(v);


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

        holder.getAnnounceType().setText(listAnnounce.get(position).getAnnounceType());
        holder.getAnnounceDateText().setText(listAnnounce.get(position).getAnnounceDateText());
        holder.getAnnounceHourText().setText(listAnnounce.get(position).getAnnounceHourText());
        holder.getAnnounceCategorie().setText(listAnnounce.get(position).getAnnounceCategorie());
        holder.getColor().setBackgroundColor(listAnnounce.get(position).getColor());

        String userAnnounceOwnerName = listAnnounce.get(position).getUserOwner();
        if(userAnnounceOwnerName.equals(actualUser)) {
            //if(listAnnounce.get(position).userOwner.equals(actualUser)) {
            holder.getOwner().setText("Yo");
        } else {
            holder.getOwner().setText(listAnnounce.get(position).getUserOwner());
        }

        if(listAnnounce.get(position).announceCategorie.equals("Telefono")) {
            holder.getCategorieIcon().setImageResource(R.drawable.ic_phone_android);
        } else if(listAnnounce.get(position).announceCategorie.equals("Cartera")) {
            holder.getCategorieIcon().setImageResource(R.drawable.ic_wallet);
        } else if(listAnnounce.get(position).announceCategorie.equals("Tarjeta bancaria") || listAnnounce.get(position).announceCategorie.equals("Tarjeta transporte")) {
            holder.getCategorieIcon().setImageResource(R.drawable.ic_card);
        } else if(listAnnounce.get(position).announceCategorie.equals("Otro")) {
            holder.getCategorieIcon().setImageResource(R.drawable.ic_other);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, SeekerAnnounceInfoActivity.class);

                Announce announce = listAnnounce.get(position);
                intent.putExtra("myAnnounce", announce);
                context.startActivity(intent);
            }
        });



        // Use the provided ChatView_Holder on the onCreateViewHolder method to populate the current row on the RecycleView
        /*TextView title = holder.getTitle();
        TextView descr = holder.getDescription();
        ImageView image = holder.getImageView();

        title.setText(listAnnounce.get(position).title);
        descr.setText(listAnnounce.get(position).description);
        image.setImageResource(listAnnounce.get(position).imageId);*/
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

    public List<Announce> getListAnnounce() {
        return this.listAnnounce;
    }

    /*public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }*/

}
