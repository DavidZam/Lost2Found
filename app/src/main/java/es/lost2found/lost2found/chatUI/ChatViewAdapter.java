package es.lost2found.lost2found.chatUI;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Chat;
import es.lost2found.lost2found.chatUI.chatConcreteUI.ChatConcrete;

public class ChatViewAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private List<Chat> listChat;
    private String userName;
    private TextView noChats;
    private boolean connected;

    ChatViewAdapter(List<Chat> listChat, String userName, TextView noChats) {
        this.listChat = listChat;
        this.userName = userName;
        this.noChats = noChats;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat, parent, false);
        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.chatTitle.setText(listChat.get(position).getChattitle());
        holder.chaticon.setImageResource(R.drawable.ic_comment);
        //chatTitle = listChat.get(position).getChattitle();

        holder.itemView.setOnClickListener(v -> {
            try {
                connected = isConnected();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(!connected) {
                this.getListChat().clear();
                noChats.setText(noChats.getResources().getString(R.string.info_txt4));
            } else {
                noChats.setText("");
                Context context = v.getContext();
                Intent intent = new Intent(context, ChatConcrete.class);
                Chat concreteChat = listChat.get(position);
                intent.putExtra("userName", userName);
                intent.putExtra("chat", concreteChat);
                intent.putExtra("chatTitle", listChat.get(position).getChattitle());
                context.startActivity(intent);
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

    public void insert(int position, Chat chat) {
        listChat.add(position, chat);
        notifyItemInserted(position);
    }

    public List<Chat> getListChat() {
        return this.listChat;
    }

    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec (command).waitFor() == 0);
    }
}
