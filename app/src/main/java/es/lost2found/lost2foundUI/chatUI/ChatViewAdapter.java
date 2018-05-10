package es.lost2found.lost2foundUI.chatUI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Chat;
import es.lost2found.lost2foundUI.chatUI.chatConcreteUI.ChatConcrete;

public class ChatViewAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private List<Chat> listChat;
    private Context context;
    private String chatTitle;
    private String userName;

    public ChatViewAdapter(List<Chat> listChat, Context context, String userName) {
        this.listChat = listChat;
        this.context = context;
        this.userName = userName;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout and initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat, parent, false);
        ChatViewHolder holder = new ChatViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.chatTitle.setText(listChat.get(position).getChattitle());
        holder.chaticon.setImageResource(R.drawable.ic_comment);
        chatTitle = listChat.get(position).getChattitle();
        //animate(holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View itemView = v.findViewById(R.id.activity_concrete_chat);
                Context context = v.getContext();
                Intent intent = new Intent(context, ChatConcrete.class);
                Chat concreteChat = listChat.get(position);
                intent.putExtra("userName", userName);
                intent.putExtra("chat", concreteChat);
                intent.putExtra("chatTitle", listChat.get(position).getChattitle());
                context.startActivity(intent);

            /*
            int position = holder .getAdapterPosition();
            // This is org.greenrobot.eventbus
            Application.getInstance().getEventBus().post(new OnHistoryClickEvent(position));*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return listChat.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item (announce) to the RecyclerView on a predefined position
    public void insert(int position, Chat chat) {
        listChat.add(position, chat);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified announce Object
    public void remove(Chat chat) {
        int position = listChat.indexOf(chat);
        listChat.remove(position);
        notifyItemRemoved(position);
    }

    public List<Chat> getListChats() {
        return this.listChat;
    }

}
