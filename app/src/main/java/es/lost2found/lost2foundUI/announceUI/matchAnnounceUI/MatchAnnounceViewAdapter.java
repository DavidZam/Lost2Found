package es.lost2found.lost2foundUI.announceUI.matchAnnounceUI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Announce;
import es.lost2found.lost2foundUI.announceUI.AnnounceViewHolder;
import es.lost2found.lost2foundUI.chatUI.chatConcreteUI.ChatConcreteViewAdapter;

public class MatchAnnounceViewAdapter extends RecyclerView.Adapter<AnnounceViewHolder> {

    private List<Announce> listAnnounce = Collections.emptyList();
    private Context context;
    private String actualUser;
    private Announce oldAnnounce;
    private String atrDeterminante;
    private List<String>  colorPercentagesList;
    private List<String> distancePercentagesList;
    private List<String> matchPercentagesList;
    private List<String> distancesList;
    private String typePlaceOldAnnounce;
    private String typePlaceMatchAnnounce;

    public MatchAnnounceViewAdapter(List<Announce> listAnnounce, Context context, String actualUser, Announce oldAnnounce, String atrDeterminante, List<String>  colorPercentagesList, List<String>  distancePercentagesList, List<String>  distancesList, String typePlaceOldAnnounce, String typePlaceMatchAnnounce, List<String>  matchPercentagesList) {
        this.listAnnounce = listAnnounce;
        this.context = context;
        this.actualUser = actualUser;
        this.oldAnnounce = oldAnnounce;
        this.atrDeterminante = atrDeterminante;
        this.colorPercentagesList = colorPercentagesList;
        this.distancePercentagesList = distancePercentagesList;
        this.distancesList = distancesList;
        this.typePlaceOldAnnounce = typePlaceOldAnnounce;
        this.typePlaceMatchAnnounce = typePlaceMatchAnnounce;
        this.matchPercentagesList = matchPercentagesList;
    }

    @Override
    public AnnounceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_announce, parent, false);
        AnnounceViewHolder holder = new AnnounceViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(AnnounceViewHolder holder, int position) {

        holder.getAnnounceType().setText(listAnnounce.get(position).getAnnounceType());
        holder.getAnnounceDateText().setText(listAnnounce.get(position).DDMMYYYY());
        holder.getAnnounceHourText().setText(listAnnounce.get(position).getAnnounceHourText());
        holder.getAnnounceCategorie().setText(listAnnounce.get(position).getAnnounceCategorie());
        holder.getColor().setBackgroundColor(listAnnounce.get(position).getColor());

        holder.getMatchPercentage().setText(matchPercentagesList.get(position) + "%"); // ¿?
        double matchPercentageValue = Double.valueOf(matchPercentagesList.get(position));
        if(matchPercentageValue >= 70) {
            holder.getMatchPercentage().setTextColor(holder.getMatchPercentage().getResources().getColor(R.color.ForestGreen));
        } else if(matchPercentageValue < 70 && matchPercentageValue >= 20) {
            holder.getMatchPercentage().setTextColor(holder.getMatchPercentage().getResources().getColor(R.color.Coral));
        } else if(matchPercentageValue < 20) {
            holder.getMatchPercentage().setTextColor(holder.getMatchPercentage().getResources().getColor(R.color.FireBrick));
        }

        String userAnnounceOwnerName = listAnnounce.get(position).getUserOwner();
        if(userAnnounceOwnerName.equals(actualUser)) {
            holder.getOwner().setText("Yo");
        } else {
            holder.getOwner().setText(listAnnounce.get(position).getUserOwner());
        }

        if(listAnnounce.get(position).announceCategorie.equals("Telefono")) {
            holder.getCategorieIcon().setImageResource(R.drawable.ic_smartphone);
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
                Intent intent = new Intent(context, MatchAnnounceInfoActivity.class);

                Announce announce = listAnnounce.get(position);
                intent.putExtra("myAnnounce", announce);
                intent.putExtra("oldAnnounce", oldAnnounce);
                String percentageColor = colorPercentagesList.get(position);
                if(typePlaceOldAnnounce != null && typePlaceMatchAnnounce != null) {
                        String distance = distancesList.get(position);
                        String percentageDistance = distancePercentagesList.get(position);
                        intent.putExtra("typePlaceOldAnnounce", typePlaceOldAnnounce);
                        intent.putExtra("typePlaceMatchAnnounce", typePlaceMatchAnnounce);
                        intent.putExtra("distance", distance);
                        intent.putExtra("percentageDistance", percentageDistance);
                }
                intent.putExtra("percentageColor", percentageColor);
                intent.putExtra("atributoDeterminante", atrDeterminante);
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

    public void setListPercentageColor(List<String> listPercentageColor) {
        this.colorPercentagesList = listPercentageColor;
    }

    public void setListPercentageDistance(List<String> listPercentageDistance) {
        this.distancePercentagesList = listPercentageDistance;
    }

    public void setListDistance(List<String> listDistance) {
        this.distancesList = listDistance;
    }

    public void setTypePlaceOldAnnounce(String typePlaceOldAnnounce) {
        this.typePlaceOldAnnounce = typePlaceOldAnnounce;
    }

    public void setTypePlaceMatchAnnounce(String typePlaceMatchAnnounce) {
        this.typePlaceMatchAnnounce = typePlaceMatchAnnounce;
    }

    public void setListPercentageMatch(List<String> listPercentageMatch) {
        this.matchPercentagesList = listPercentageMatch;
    }

    /*public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }*/

}
