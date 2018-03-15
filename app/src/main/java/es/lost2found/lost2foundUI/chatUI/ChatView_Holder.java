package es.lost2found.lost2foundUI.chatUI;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.lost2found.R;

public class ChatView_Holder extends RecyclerView.ViewHolder {

    CardView cv2;
    TextView chattitle;
    TextView lastmsg;
    ImageView chaticon;

    ChatView_Holder(View itemView) {
        super(itemView);
        cv2 = (CardView) itemView.findViewById(R.id.chat_cardView);
        chattitle = (TextView) itemView.findViewById(R.id.chattitle);
        lastmsg = (TextView) itemView.findViewById(R.id.lastmsg);
        chaticon = (ImageView) itemView.findViewById(R.id.chaticon);
    }
}
