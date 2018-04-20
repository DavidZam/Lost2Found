package es.lost2found.lost2foundUI.announceUI;

import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import es.lost2found.R;

public class AnnounceViewHolder extends RecyclerView.ViewHolder {

    CardView cv;
    //TextView title;
    //TextView description;
    //ImageView imageView;

    TextView announceType;
    TextView announceDateText;
    TextView announceHourText;
    TextView currentTime;
    ImageView categorieIcon;
    TextView announceCategorie;
    TextView brand;
    TextView model;
    TextView color;

    public AnnounceViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        categorieIcon = (ImageView) itemView.findViewById(R.id.imageView);
        announceType = (TextView) itemView.findViewById(R.id.announceType);
        announceDateText = (TextView) itemView.findViewById(R.id.announceDateText);
        announceHourText = (TextView) itemView.findViewById(R.id.announceHourText);
        currentTime = (TextView) itemView.findViewById(R.id.currentTime);
        announceCategorie = (TextView) itemView.findViewById(R.id.categorieAnnounce);
        //brand = (TextView) itemView.findViewById(R.id.brand);
        //model = (TextView) itemView.findViewById(R.id.model);
        //color = (TextView) itemView.findViewById(R.id.color);
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

    public TextView getCurrentTime() {
        return currentTime;
    }

    public TextView getAnnounceCategorie() {
        return announceCategorie;
    }

    public TextView getBrand() {
        return brand;
    }

    public TextView getModel() {
        return model;
    }

    public TextView getColor() {
        return color;
    }
}
