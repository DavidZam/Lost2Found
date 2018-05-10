package es.lost2found.lost2foundUI.chatUI.chatConcreteUI;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.database.DB_chat;
import es.lost2found.database.DB_user;
import es.lost2found.entities.Chat;
import es.lost2found.entities.Message;

public class ChatConcreteViewAdapter extends RecyclerView.Adapter<ChatConcreteViewHolder> {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private List<Message> listMsg = new ArrayList<>();
    private Context context;
    private Chat chat;
    private Button sendButton;
    private String actualUser;
    //private String otherUserName;

    public ChatConcreteViewAdapter(Context context, Chat chat, List<Message> listMsg, Button sendButton, String actualUser) { // , String otherUserName
        this.context = context;
        this.chat = chat;
        this.listMsg = listMsg;
        this.sendButton = sendButton;
        this.actualUser = actualUser;
        //this.otherUserName = otherUserName;
    }

    @Override
    public ChatConcreteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_announce, parent, false);
        //ChatConcreteViewHolder holder = new ChatConcreteViewHolder(view); // COMPROBAR
        ChatConcreteViewHolder holder = new ChatConcreteViewHolder(view);

        //for(int position = 0; position < listMsg.size(); position++) {
            // ActualUser: David SenderUser: David --> Green
            // ActualUser: Luis  SenderUser: David --> Blue
        // ActualUser: Luis  SenderUser: Luis --> Green

        if(listMsg.size() >= 1) {
            viewType = getItemViewType(listMsg.size() - 1);
        }
        if(viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_blue, parent, false);
            holder = new ChatConcreteViewHolder(view);
            bind(holder, listMsg.size() - 1);
            return holder;
        } else if(viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_green, parent, false);
            holder = new ChatConcreteViewHolder(view);
            bind(holder, listMsg.size() - 1);
            return holder;
        }
        return holder;

        /*int position = listMsg.size() - 1;
        String userSender = listMsg.get(position).getUserSender(); // NO FUNCIONA

        if(!userSender.equals(actualUser)) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_blue, parent, false);
            holder = new ChatConcreteViewHolder(view);
            bind(holder, position);
            return holder;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_green, parent, false);
            holder = new ChatConcreteViewHolder(view);
            bind(holder, position);
            return holder;
        }*/

        /*
        // Inflate the layout and initialize the View Holder
        View view;
        if(viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_blue, parent, false);
            View_Holder holder = new View_Holder(view);
            return holder;
        } else if(viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_green, parent, false);
            View_Holder holder = new View_Holder(view);
            return holder;
        }
        return null
         */
    }

    @Override
    public void onBindViewHolder(ChatConcreteViewHolder holder, int position) {

    }

    public void bind(ChatConcreteViewHolder holder, int position) {
        Message msg = (Message) listMsg.get(position);
        holder.userSender.setText(msg.getUserSender());
        holder.textMsg.setText(msg.getTextMsg());
        holder.hourMsg.setText(msg.getHourMsg());
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        String userSender = listMsg.get(position).getUserSender();
        if(!userSender.equals(actualUser)) { // Arreglar esto en su día y repensarlo.
            viewType = VIEW_TYPE_MESSAGE_RECEIVED;
        } else {
            viewType = VIEW_TYPE_MESSAGE_SENT;
        }
        return viewType;
    }

    @Override
    public int getItemCount() {
        return listMsg.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item (announce) to the RecyclerView on a predefined position
    public void insert(int position, Message chat) {
        listMsg.add(position, chat);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified announce Object
    public void remove(Message chat) {
        int position = listMsg.indexOf(chat);
        listMsg.remove(position);
        notifyItemRemoved(position);
    }

}