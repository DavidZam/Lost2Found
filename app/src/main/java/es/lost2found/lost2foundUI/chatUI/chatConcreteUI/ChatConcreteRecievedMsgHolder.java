package es.lost2found.lost2foundUI.chatUI.chatConcreteUI;


import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import es.lost2found.R;
import es.lost2found.entities.Message;

public class ChatConcreteRecievedMsgHolder extends RecyclerView.ViewHolder {

    ConstraintLayout cl;
    TextView userSender;
    TextView textMsg;
    TextView hourMsg;

    public ChatConcreteRecievedMsgHolder(View itemView) {
        super(itemView);
        cl = (ConstraintLayout) itemView.findViewById(R.id.concrete_chat_layout);
        userSender = (TextView) itemView.findViewById(R.id.userSender);
        textMsg = (TextView) itemView.findViewById(R.id.textMsg);
        hourMsg = (TextView) itemView.findViewById(R.id.hourMsg);
    }

    void bind(Message msg, String userOwner) {
        textMsg.setText(msg.getTextMsg());
        hourMsg.setText(msg.getHourMsg());
        userSender.setText(userOwner);
    }
}
