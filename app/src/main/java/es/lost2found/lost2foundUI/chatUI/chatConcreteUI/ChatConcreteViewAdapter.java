package es.lost2found.lost2foundUI.chatUI.chatConcreteUI;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.database.DB_message;
import es.lost2found.database.DB_user;
import es.lost2found.entities.Message;

public class ChatConcreteViewAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private List<Message> listMsg = new ArrayList<>();
    private String actualUser;
    private String userOwner;

    ChatConcreteViewAdapter(List<Message> listMsg, String actualUser) {
        this.listMsg = listMsg;
        this.actualUser = actualUser;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 0;
        try {
            userOwner = new getUserIdOwnerOfMsg().execute(listMsg.get(position)).get();
            if(userOwner != null) {
                if(!userOwner.equals(actualUser)) {
                    // If the current user is the sender of the message
                    viewType = VIEW_TYPE_MESSAGE_SENT;
                } else {
                    // If some other user sent the message
                    viewType = VIEW_TYPE_MESSAGE_RECEIVED;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view;
        if(viewType != 0) {
            if (viewType == VIEW_TYPE_MESSAGE_SENT) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_blue, parent, false);
                holder = new ChatConcreteSentMsgHolder(view);
            } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_green, parent, false);
                holder = new ChatConcreteRecievedMsgHolder(view);
            }
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_green, parent, false);
            holder = new ChatConcreteRecievedMsgHolder(view);
        }
        return holder;
    }

    private class getUserIdOwnerOfMsg extends AsyncTask<Message, Void, String> {
        @Override
        protected String doInBackground(Message... params) {
            Integer idUser = DB_message.getUserIdOwnerOfMsg(params[0]);
            if(idUser != null) {
                return DB_user.getNameById(idUser);
            } else {
                return null;
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message msg = listMsg.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((ChatConcreteSentMsgHolder) holder).bind(msg, userOwner);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ChatConcreteRecievedMsgHolder) holder).bind(msg, userOwner);
        }
    }

    @Override
    public int getItemCount() {
        return listMsg.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, Message chat) {
        listMsg.add(position, chat);
        notifyItemInserted(position);
    }
}