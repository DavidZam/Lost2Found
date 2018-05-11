package es.lost2found.lost2foundUI.chatUI.chatConcreteUI;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;
import es.lost2found.database.DB_message;
import es.lost2found.database.DB_user;
import es.lost2found.entities.Chat;
import es.lost2found.entities.Message;

public class ChatConcreteViewAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private List<Message> listMsg = new ArrayList<>();
    private String actualUser;
    private String userOwner;
    private Context context;

    public ChatConcreteViewAdapter(Context context, List<Message> listMsg, String actualUser) {
        this.context = context;
        this.listMsg = listMsg;
        this.actualUser = actualUser;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            userOwner = new getUserIdOwnerOfMsg().execute(listMsg.get(position)).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(userOwner != null) {
            if(!userOwner.equals(actualUser)) {
                // If the current user is the sender of the message
                return VIEW_TYPE_MESSAGE_SENT;
            } else {
                // If some other user sent the message
                return VIEW_TYPE_MESSAGE_RECEIVED;
            }
        } else {
            return VIEW_TYPE_MESSAGE_SENT; // 0
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_blue, parent, false);
            return new ChatConcreteSentMsgHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_green, parent, false);
            return new ChatConcreteRecievedMsgHolder(view);
        }
        return null;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message msg = (Message) listMsg.get(position);
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