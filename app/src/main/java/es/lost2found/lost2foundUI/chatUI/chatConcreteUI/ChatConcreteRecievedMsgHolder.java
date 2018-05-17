package es.lost2found.lost2foundUI.chatUI.chatConcreteUI;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

import es.lost2found.R;
import es.lost2found.entities.Message;

class ChatConcreteRecievedMsgHolder extends RecyclerView.ViewHolder {

    private TextView userSender;
    private TextView textMsg;
    private TextView hourMsg;

    ChatConcreteRecievedMsgHolder(View itemView) {
        super(itemView);
        userSender = itemView.findViewById(R.id.userSender);
        textMsg = itemView.findViewById(R.id.textMsg);
        hourMsg = itemView.findViewById(R.id.hourMsg);
    }

    void bind(Message msg, String userOwner) {
        String utf8Text = "";
        try {
            utf8Text = new String(msg.getTextMsg().getBytes(), "UTF-8");
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //textMsg.setText(utf8Text);
        textMsg.setText(Html.fromHtml(msg.getTextMsg()));
        //textMsg.setText(msg.getTextMsg());
        hourMsg.setText(msg.getHourMsg());
        userSender.setText(userOwner);
    }
}
