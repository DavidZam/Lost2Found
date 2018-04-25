package es.lost2found.lost2foundUI.announceUI;

import android.graphics.Color;
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
    TextView announceType;
    TextView announceDateText;
    TextView announceHourText;
    ImageView categorieIcon;
    TextView announceCategorie;
    TextView owner;
    View color;

    public AnnounceViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        categorieIcon = (ImageView) itemView.findViewById(R.id.imageView);
        announceType = (TextView) itemView.findViewById(R.id.announceType);
        announceDateText = (TextView) itemView.findViewById(R.id.announceDateText);
        announceHourText = (TextView) itemView.findViewById(R.id.announceHourText);
        announceCategorie = (TextView) itemView.findViewById(R.id.categorieAnnounce);
        owner = (TextView) itemView.findViewById(R.id.announceOwner);
        color = (View) itemView.findViewById(R.id.color_view);
    }
}
