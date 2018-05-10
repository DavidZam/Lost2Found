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
import es.lost2found.entities.Chat;
import es.lost2found.entities.Message;

public class ChatConcreteViewAdapter extends RecyclerView.Adapter<ChatConcreteViewHolder> {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private List<Message> listMsg = new ArrayList<>();
    private Context context;
    private Chat chat;
    private Button sendButton;
    //private String otherUserName;

    public ChatConcreteViewAdapter(Context context, Chat chat, List<Message> listMsg, Button sendButton) { // , String otherUserName
        this.context = context;
        this.chat = chat;
        this.listMsg = listMsg;
        this.sendButton = sendButton;
        //this.otherUserName = otherUserName;
    }

    @Override
    public ChatConcreteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ChatConcreteViewHolder holder = new ChatConcreteViewHolder(parent); // COMPROBAR
        if(viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_blue, parent, false);
            holder = new ChatConcreteViewHolder(view);
            return holder;
        } else if(viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_green, parent, false);
            holder = new ChatConcreteViewHolder(view);
            return holder;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ChatConcreteViewHolder holder, int position) {
        Message msg = (Message) listMsg.get(position);
        //holder.userSender.setText(msg.getUserSender());
        holder.textMsg.setText(msg.getTextMsg());
        holder.hourMsg.setText(msg.getHourMsg());

        /*sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Creamos un nuevo mensaje // Falta guardar los msgs en la bd
                Message msg = new Message(listMsg.get(position).getUserSender(), listMsg.get(position).getTextMsg(), listMsg.get(position).getHourMsg(), false);
                //listMsg.add(msg);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listMsg.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message msg = (Message) listMsg.get(position);
        /*if(!msg.getUserSender().equals("Yo")) { // Arreglar esto en su día y repensarlo.
            return VIEW_TYPE_MESSAGE_RECEIVED;
        } else {
            return VIEW_TYPE_MESSAGE_SENT;
        }*/
        return 0;
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