package es.lost2found.lost2foundUI.chatUI.chatConcreteUI;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import es.lost2found.R;
import es.lost2found.entities.Message;

class ChatConcreteRecievedMsgHolder extends RecyclerView.ViewHolder {

    //private ConstraintLayout cl;
    private TextView userSender;
    private TextView textMsg;
    private TextView hourMsg;

    ChatConcreteRecievedMsgHolder(View itemView) {
        super(itemView);
        //cl = (ConstraintLayout) itemView.findViewById(R.id.concrete_chat_layout);
        userSender = itemView.findViewById(R.id.userSender);
        textMsg = itemView.findViewById(R.id.textMsg);
        hourMsg = itemView.findViewById(R.id.hourMsg);
    }

    void bind(Message msg, String userOwner) {
        textMsg.setText(msg.getTextMsg());
        hourMsg.setText(msg.getHourMsg());
        userSender.setText(userOwner);
    }
}
