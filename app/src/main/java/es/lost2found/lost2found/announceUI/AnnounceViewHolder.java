package es.lost2found.lost2found.announceUI;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import es.lost2found.R;

public class AnnounceViewHolder extends RecyclerView.ViewHolder {

    //private CardView cv;
    TextView announceType;
    TextView announceDateText;
    TextView announceHourText;
    ImageView categorieIcon;
    TextView announceCategorie;
    TextView owner;
    View color;
    private TextView matchPercentage;

    public AnnounceViewHolder(View itemView) {
        super(itemView);
        //CardView cv =  itemView.findViewById(R.id.cardView);
        categorieIcon =  itemView.findViewById(R.id.imageView);
        announceType =  itemView.findViewById(R.id.announceType);
        announceDateText =  itemView.findViewById(R.id.announceDateText);
        announceHourText =  itemView.findViewById(R.id.announceHourText);
        announceCategorie = itemView.findViewById(R.id.categorieAnnounce);
        owner =  itemView.findViewById(R.id.announceOwner);
        color =  itemView.findViewById(R.id.color_view);
        matchPercentage =  itemView.findViewById(R.id.matchPercentage);
    }

    public TextView getAnnounceType() {
        return announceType;
    }

    public TextView getAnnounceDateText() {
        return announceDateText;
    }

    public TextView getAnnounceHourText() {
        return announceHourText;
    }

    public ImageView getCategorieIcon() {
        return categorieIcon;
    }

    public TextView getAnnounceCategorie() {
        return announceCategorie;
    }

    public TextView getOwner() {
        return owner;
    }

    public View getColor() {
        return color;
    }

    public TextView getMatchPercentage() {
        return matchPercentage;
    }
}
