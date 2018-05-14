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
import es.lost2found.entities.OpenDataAnnounce;
import es.lost2found.lost2foundUI.announceUI.OpenDataAnnounceViewHolder;

public class OpenDataMatchAnnounceViewAdapter extends RecyclerView.Adapter<OpenDataAnnounceViewHolder> {

    private List<OpenDataAnnounce> listAnnounce = Collections.emptyList();
    private Context context;
    private Announce oldAnnounce;
    private List<String> distancePercentagesList;
    private List<String> matchPercentagesList;
    private List<String> distancesList;
    private String typePlaceOldAnnounce;
    private String typePlaceMatchAnnounce;

    public OpenDataMatchAnnounceViewAdapter(List<OpenDataAnnounce> listAnnounce, Context context, Announce oldAnnounce, List<String>  distancePercentagesList, List<String>  distancesList, String typePlaceOldAnnounce, String typePlaceMatchAnnounce, List<String>  matchPercentagesList) {
        this.listAnnounce = listAnnounce;
        this.context = context;
        this.oldAnnounce = oldAnnounce;
        this.distancePercentagesList = distancePercentagesList;
        this.distancesList = distancesList;
        this.typePlaceOldAnnounce = typePlaceOldAnnounce;
        this.typePlaceMatchAnnounce = typePlaceMatchAnnounce;
        this.matchPercentagesList = matchPercentagesList;
    }

    @Override
    public OpenDataAnnounceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_opendata_announce, parent, false);
        OpenDataAnnounceViewHolder holder = new OpenDataAnnounceViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(OpenDataAnnounceViewHolder holder, int position) {
        holder.getOpenDataAnnounceType().setText(listAnnounce.get(position).getAnnounceType());
        holder.getOpenDataAnnounceDateText().setText(listAnnounce.get(position).DDMMYYYY());
        holder.getOpenDataAnnounceHourText().setText(listAnnounce.get(position).getAnnounceHourText());
        holder.getOpenDataAnnounceCategorie().setText(listAnnounce.get(position).getAnnounceCategorie());
        holder.getOpenDataMatchPercentage().setText(matchPercentagesList.get(position) + "%");
        double matchPercentageValue = Double.valueOf(matchPercentagesList.get(position));
        if(matchPercentageValue >= 70) {
            holder.getOpenDataMatchPercentage().setTextColor(holder.getOpenDataMatchPercentage().getResources().getColor(R.color.ForestGreen));
        } else if(matchPercentageValue < 70 && matchPercentageValue >= 20) {
            holder.getOpenDataMatchPercentage().setTextColor(holder.getOpenDataMatchPercentage().getResources().getColor(R.color.Coral));
        } else if(matchPercentageValue < 20) {
            holder.getOpenDataMatchPercentage().setTextColor(holder.getOpenDataMatchPercentage().getResources().getColor(R.color.FireBrick));
        }

        if(listAnnounce.get(position).announceCategorie.equals("Telefono")) {
            holder.getOpenDataCategorieIcon().setImageResource(R.drawable.ic_phone_android);
        } else if(listAnnounce.get(position).announceCategorie.equals("Cartera")) {
            holder.getOpenDataCategorieIcon().setImageResource(R.drawable.ic_wallet);
        } else if(listAnnounce.get(position).announceCategorie.equals("Tarjeta bancaria") || listAnnounce.get(position).announceCategorie.equals("Tarjeta transporte")) {
            holder.getOpenDataCategorieIcon().setImageResource(R.drawable.ic_card);
        } else if(listAnnounce.get(position).announceCategorie.equals("Otro")) {
            holder.getOpenDataCategorieIcon().setImageResource(R.drawable.ic_other);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, OpenDataMatchAnnounceInfoActivity.class);

                OpenDataAnnounce announce = listAnnounce.get(position);
                intent.putExtra("myAnnounce", announce);
                intent.putExtra("oldAnnounce", oldAnnounce);
                if(typePlaceOldAnnounce != null && typePlaceMatchAnnounce != null) {
                    String distance = distancesList.get(position);
                    String percentageDistance = distancePercentagesList.get(position);
                    intent.putExtra("typePlaceOldAnnounce", typePlaceOldAnnounce);
                    intent.putExtra("typePlaceMatchAnnounce", typePlaceMatchAnnounce);
                    intent.putExtra("distance", distance);
                    intent.putExtra("percentageDistance", percentageDistance);
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
    public void insert(int position, OpenDataAnnounce annonuce) {
        listAnnounce.add(position, annonuce);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified announce Object
    public void remove(OpenDataAnnounce announce) {
        int position = listAnnounce.indexOf(announce);
        listAnnounce.remove(position);
        notifyItemRemoved(position);
    }

    public List<OpenDataAnnounce> getListAnnounce() {
        return this.listAnnounce;
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
}
