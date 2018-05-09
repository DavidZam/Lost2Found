package es.lost2found.lost2foundUI.chatUI.chatConcreteUI;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import es.lost2found.R;
import es.lost2found.entities.Message;

public class ChatConcreteViewAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    List<Message> listMsg = Collections.emptyList();
    Context context;

    public ChatConcreteViewAdapter(Context context, List<Message> listMsg) {
        this.context = context;
        this.listMsg = listMsg;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        View_Holder vh = (View_Holder) holder;
        vh.bind(msg);
        // Use the provided ChatViewHolder on the onCreateViewHolder method to populate the current row on the RecycleView
        /*switch(holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ChatConcreteViewHolder vh = (ChatConcreteViewHolder) holder;
                vh.bind(msg);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ChatConcreteViewHolder vh = (ChatConcreteViewHolder) holder;
                vh.bind(msg);
        }*/

        //animate(holder);
    }

    private class View_Holder extends RecyclerView.ViewHolder {

        ConstraintLayout cl;
        TextView userSender;
        TextView textMsg;
        TextView hourMsg;

        View_Holder(View itemView) {
            super(itemView);
            cl = (ConstraintLayout) itemView.findViewById(R.id.concrete_chat_layout);
            userSender = (TextView) itemView.findViewById(R.id.userSender);
            textMsg = (TextView) itemView.findViewById(R.id.textMsg);
            hourMsg = (TextView) itemView.findViewById(R.id.hourMsg);
        }

        void bind(Message msg) {
            userSender.setText(msg.getUserSender());
            textMsg.setText(msg.getTextMsg());
            hourMsg.setText(msg.getHourMsg());
        }
    }

    @Override
    public int getItemCount() {
        return listMsg.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message msg = (Message) listMsg.get(position);

        /*if(msg.getUserSender().equals("Yo")) { // Arreglar esto en su día y repensarlo.
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }*/
        if(!msg.getUserSender().equals("Yo")) { // Arreglar esto en su día y repensarlo.
            return VIEW_TYPE_MESSAGE_RECEIVED;
        } else {
            return VIEW_TYPE_MESSAGE_SENT;
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