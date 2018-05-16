package es.lost2found.lost2foundUI.chatUI;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.lost2found.R;

class ChatViewHolder extends RecyclerView.ViewHolder {

    //CardView cv2;
    TextView chatTitle;
    ImageView chaticon;

    ChatViewHolder(View itemView) {
        super(itemView);
        //cv2 = itemView.findViewById(R.id.chat_cardView);
        chatTitle = itemView.findViewById(R.id.chattitle);
        chaticon = itemView.findViewById(R.id.chaticon);
    }
}
