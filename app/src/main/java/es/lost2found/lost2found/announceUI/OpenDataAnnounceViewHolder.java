package es.lost2found.lost2found.announceUI;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.lost2found.R;

public class OpenDataAnnounceViewHolder extends RecyclerView.ViewHolder {

    //private CardView cv;
    private TextView announceType;
    private TextView announceDateText;
    private TextView announceHourText;
    private ImageView categorieIcon;
    private TextView announceCategorie;
    private TextView matchPercentage;

    public OpenDataAnnounceViewHolder(View itemView) {
        super(itemView);
        //cv = itemView.findViewById(R.id.cardView);
        categorieIcon = itemView.findViewById(R.id.imageView);
        announceType = itemView.findViewById(R.id.announceType);
        announceDateText = itemView.findViewById(R.id.announceDateText);
        announceHourText = itemView.findViewById(R.id.announceHourText);
        announceCategorie = itemView.findViewById(R.id.categorieAnnounce);
        matchPercentage = itemView.findViewById(R.id.matchPercentage);
    }

    public TextView getOpenDataAnnounceType() {
        return announceType;
    }

    public TextView getOpenDataAnnounceDateText() {
        return announceDateText;
    }

    public TextView getOpenDataAnnounceHourText() {
        return announceHourText;
    }

    public ImageView getOpenDataCategorieIcon() {
        return categorieIcon;
    }

    public TextView getOpenDataAnnounceCategorie() {
        return announceCategorie;
    }

    public TextView getOpenDataMatchPercentage() {
        return matchPercentage;
    }

}
