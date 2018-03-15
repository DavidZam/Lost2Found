package es.lost2found.lost2foundUI.chatUI.chatConcreteUI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Message;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    List<Message> listMsg = Collections.emptyList();
    Context context;

    public RecyclerViewAdapter(Context context, List<Message> listMsg) {
        this.context = context;
        this.listMsg = listMsg;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout and initialize the View Holder
        View view;
        if(viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_blue, parent, false);
            ReceivedMsgView_Holder holder = new ReceivedMsgView_Holder(view);
            return holder;
        } else if(viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_msg_green, parent, false);
            ReceivedMsgView_Holder holder = new ReceivedMsgView_Holder(view);
            return holder;
        }

        /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View itemView = v.findViewById(R.id.activity_concrete_chat);
                Context context = v.getContext();
                Intent intent = new Intent(context, ChatConcrete.class);
                context.startActivity(intent);


            int position = holder .getAdapterPosition();
            // This is org.greenrobot.eventbus
            Application.getInstance().getEventBus().post(new OnHistoryClickEvent(position));
            }
        });
        holder .itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position = holder .getAdapterPosition();
            // This is org.greenrobot.eventbus
            Application.getInstance().getEventBus().post(new OnHistoryLongClickEvent(position));
            return true;
        }
        });*/
        return null;
        //return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message msg = (Message) listMsg.get(position);
        // Use the provided ChatView_Holder on the onCreateViewHolder method to populate the current row on the RecycleView
        switch(holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SendMsgView_Holder) holder).bind(msg);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMsgView_Holder) holder).bind(msg);
        }

        //animate(holder);
    }

    private class SendMsgView_Holder extends RecyclerView.ViewHolder {
        TextView textMsg, hourMsg;

        SendMsgView_Holder(View itemView) {
            super(itemView);

            textMsg = (TextView) itemView.findViewById(R.id.textMsg);
            hourMsg = (TextView) itemView.findViewById(R.id.hourMsg);
        }

        void bind(Message message) {
            textMsg.setText(message.getTextMsg());
            hourMsg.setText(message.getHourMsg());
        }
    }

    private class ReceivedMsgView_Holder extends RecyclerView.ViewHolder {
        TextView userSender, textMsg, hourMsg;

        ReceivedMsgView_Holder(View itemView) {
            super(itemView);

            userSender = (TextView) itemView.findViewById(R.id.userSender);
            textMsg = (TextView) itemView.findViewById(R.id.textMsg);
            hourMsg = (TextView) itemView.findViewById(R.id.hourMsg);
        }

        void bind(Message message) {

            userSender.setText(message.getUserSender());
            textMsg.setText(message.getTextMsg());
            hourMsg.setText(message.getHourMsg());
        }
    }

    @Override
    public int getItemCount() {
        return listMsg.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message msg = (Message) listMsg.get(position);

        if(msg.getUserSender().equals("Paco")) { // Arreglar esto en su día y repensarlo.
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
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