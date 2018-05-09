package es.lost2found.lost2foundUI.chatUI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Chat;
import es.lost2found.lost2foundUI.chatUI.chatConcreteUI.ChatConcrete;

public class ChatViewAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    List<Chat> listChat = Collections.emptyList();
    Context context;

    public ChatViewAdapter(List<Chat> listChat, Context context) {
        this.listChat = listChat;
        this.context = context;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout and initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat, parent, false);
        ChatViewHolder holder = new ChatViewHolder(v);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //View itemView = v.findViewById(R.id.activity_concrete_chat);
            Context context = v.getContext();
            Intent intent = new Intent(context, ChatConcrete.class);
            context.startActivity(intent);

            /*
            int position = holder .getAdapterPosition();
            // This is org.greenrobot.eventbus
            Application.getInstance().getEventBus().post(new OnHistoryClickEvent(position));*/
        }
        });
        /*holder .itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position = holder .getAdapterPosition();
            // This is org.greenrobot.eventbus
            Application.getInstance().getEventBus().post(new OnHistoryLongClickEvent(position));
            return true;
        }
        });*/

        return holder;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.chatTitle.setText(listChat.get(position).getChattitle());
        holder.chaticon.setImageResource(R.drawable.ic_chaticon);

        //animate(holder);
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

}
