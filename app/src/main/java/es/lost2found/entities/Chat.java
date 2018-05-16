package es.lost2found.entities;

import org.json.JSONObject;

import java.io.Serializable;

public class Chat implements Serializable {
    public String chattitle;
    public int idUsuario1;
    public int idUsuario2;

    public Chat(String chattitle, int idUsuario1, int idUsuario2)  {
        this.chattitle = chattitle;
        this.idUsuario1 = idUsuario1;
        this.idUsuario2 = idUsuario2;
    }

    public Chat(Chat chat) {
        this.chattitle = chat.getChattitle();
        this.idUsuario1 = chat.getIdUsuario1();
        this.idUsuario2 = chat.getIdUsuario2();
    }

    public Chat(String json) {
        try {
            JSONObject jObject = new JSONObject(json);
            this.chattitle = jObject.getString("nombreChat");
            this.idUsuario1 = jObject.getInt("idUser1");
            this.idUsuario2 = jObject.getInt("idUser2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Chat() {

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
}
