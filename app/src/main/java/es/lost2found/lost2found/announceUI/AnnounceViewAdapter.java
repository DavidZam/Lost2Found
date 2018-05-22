package es.lost2found.lost2found.announceUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Announce;
import es.lost2found.lost2found.seekerUI.SeekerActivity;
import es.lost2found.lost2found.seekerUI.SeekerAnnounceInfoActivity;

public class AnnounceViewAdapter extends RecyclerView.Adapter<AnnounceViewHolder>{
    private List<Announce> listAnnounce;
    private String actualUser;
    private String parentAct;
    private String typePlace;
    private TextView noAnnounces;
    private boolean connected;

    public AnnounceViewAdapter(List<Announce> listAnnounce, String actualUser, String parentAct, String typePlace, TextView noAnnounces) {
        this.listAnnounce = listAnnounce;
        this.actualUser = actualUser;
        this.parentAct = parentAct;
        this.typePlace = typePlace;
        this.noAnnounces = noAnnounces;
    }

    @NonNull
    @Override
    public AnnounceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_announce, parent, false);
        return new AnnounceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnounceViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
            holder.categorieIcon.setImageResource(R.drawable.ic_smartphone);
        } else if(listAnnounce.get(position).announceCategorie.equals("Cartera")) {
            holder.categorieIcon.setImageResource(R.drawable.ic_wallet);
        } else if(listAnnounce.get(position).announceCategorie.equals("Tarjeta bancaria") || listAnnounce.get(position).announceCategorie.equals("Tarjeta transporte")) {
            holder.categorieIcon.setImageResource(R.drawable.ic_card);
        } else if(listAnnounce.get(position).announceCategorie.equals("Otro")) {
            holder.categorieIcon.setImageResource(R.drawable.ic_other);
        }

        holder.itemView.setOnClickListener(v -> {
            try {
                connected = isConnected();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(!connected) {
                this.getListAnnounce().clear();
                noAnnounces.setText(noAnnounces.getResources().getString(R.string.info_txt4));
            } else {
                noAnnounces.setText("");
                Context context = v.getContext();
                Intent intent = new Intent(context, SeekerAnnounceInfoActivity.class);

                Announce announce = listAnnounce.get(position);
                intent.putExtra("myAnnounce", announce);

                if (parentAct.equals(AnnounceActivity.class.getSimpleName())) {
                    intent.putExtra("parentAct", "announce");
                    intent.putExtra("actualUser", actualUser);
                    intent.putExtra("typePlace", typePlace);
                } else if (parentAct.equals(SeekerActivity.class.getSimpleName())) {
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
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, Announce announce) {
        listAnnounce.add(position, announce);
        notifyItemInserted(position);
    }

    public List<Announce> getListAnnounce() {
        return this.listAnnounce;
    }

    private boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec (command).waitFor() == 0);
    }
}
