package es.lost2found.entities;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;

public class Chat {
    public String chattitle;
    public String lastmsg;
    public int chaticon;

    public Chat() {
        this.chattitle = "";
        this.lastmsg = "";
        this.chaticon = 0;
    }

    Chat(String chattitle, String lastmsg, int chaticon)  {
        this.chattitle = chattitle;
        this.lastmsg = lastmsg;
        this.chaticon = chaticon;
    }

    public List<Chat> fill_with_data(List<Chat> chat) {

        chat.add(new Chat("Chat1", "Mensaje1", R.drawable.ic_chaticon));
        chat.add(new Chat("Chat2", "Mensaje2", R.drawable.ic_chaticon));
        chat.add(new Chat("Chat3", "Mensaje3", R.drawable.ic_chaticon));
        chat.add(new Chat("Chat4", "Mensaje4", R.drawable.ic_chaticon));
        chat.add(new Chat("Chat5", "Mensaje5", R.drawable.ic_chaticon));
        chat.add(new Chat("Chat6", "Mensaje6", R.drawable.ic_chaticon));

        return chat;
    }
}
