package es.lost2found.lost2foundUI.chatUI.chatConcreteUI;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import es.lost2found.R;
import es.lost2found.entities.Message;

public class SendMsgView_Holder extends RecyclerView.ViewHolder {

    ConstraintLayout cl;
    TextView textMsg;
    TextView hourMsg;

    SendMsgView_Holder(View itemView) {
        super(itemView);
        cl = (ConstraintLayout) itemView.findViewById(R.id.concrete_chat_layout);
        textMsg = (TextView) itemView.findViewById(R.id.textMsg);
        hourMsg = (TextView) itemView.findViewById(R.id.hourMsg);
    }

    void bind(Message msg) {
        textMsg.setText(msg.getTextMsg());
        hourMsg.setText(msg.getHourMsg());
    }
}
