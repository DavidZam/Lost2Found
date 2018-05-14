package es.lost2found.lost2foundUI.announceUI;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.lost2found.R;

public class OpenDataAnnounceViewHolder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView announceType;
    TextView announceDateText;
    TextView announceHourText;
    ImageView categorieIcon;
    TextView announceCategorie;
    TextView matchPercentage;

    public OpenDataAnnounceViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        categorieIcon = (ImageView) itemView.findViewById(R.id.imageView);
        announceType = (TextView) itemView.findViewById(R.id.announceType);
        announceDateText = (TextView) itemView.findViewById(R.id.announceDateText);
        announceHourText = (TextView) itemView.findViewById(R.id.announceHourText);
        announceCategorie = (TextView) itemView.findViewById(R.id.categorieAnnounce);
        matchPercentage = (TextView) itemView.findViewById(R.id.matchPercentage);
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
