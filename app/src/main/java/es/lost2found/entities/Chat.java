package es.lost2found.entities;

import java.io.Serializable;

public class Chat implements Serializable {
    public String chattitle;
    public int idUsuario1;
    public int idUsuario2;
    public String lastMsg;

    public Chat(String chattitle, int idUsuario1, int idUsuario2, String lastMsg)  {
        this.chattitle = chattitle;
        this.idUsuario1 = idUsuario1;
        this.idUsuario2 = idUsuario2;
        this.lastMsg = lastMsg;
    }

    public Chat(Chat chat) {
        this.chattitle = chat.getChattitle();
        this.idUsuario1 = chat.getIdUsuario1();
        this.idUsuario2 = chat.getIdUsuario2();
        this.lastMsg = chat.getLastMsg();
    }

    public String getChattitle() {
        return chattitle;
    }

    public int getIdUsuario1() {
        return idUsuario1;
    }

    public int getIdUsuario2() {
        return idUsuario2;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    /*public List<Chat> fill_with_data(List<Chat> chat) {

        chat.add(new Chat("Chat1", "Mensaje1", R.drawable.ic_chaticon));
        chat.add(new Chat("Chat2", "Mensaje2", R.drawable.ic_chaticon));
        chat.add(new Chat("Chat3", "Mensaje3", R.drawable.ic_chaticon));
        chat.add(new Chat("Chat4", "Mensaje4", R.drawable.ic_chaticon));
        chat.add(new Chat("Chat5", "Mensaje5", R.drawable.ic_chaticon));
        chat.add(new Chat("Chat6", "Mensaje6", R.drawable.ic_chaticon));

        return chat;
    }*/
}
