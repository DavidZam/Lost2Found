package es.lost2found.entities;

import java.util.List;

import es.lost2found.R;

public class Message {

    public String userSender;
    public String textMsg;
    public String hourMsg;
    public boolean read;

    public Message() {
        this.userSender = "";
        this.textMsg = "";
        this.hourMsg = "";
        this.read = false;
    }

    public Message(String userSender, String textMsg, String hourMsg, boolean read) {
        this.userSender = userSender;
        this.textMsg = textMsg;
        this.hourMsg = hourMsg;
        this.read = read;
    }

    public String getUserSender() {
        return userSender;
    }

    public String getTextMsg() {
        return textMsg;
    }

    public String getHourMsg() {
        return hourMsg;
    }

    public List<Message> fill_with_data(List<Message> msg) {

        msg.add(new Message("Paco", "Hola!", "11:10", true));
        msg.add(new Message("Luis","Buenas", "11:10", true));
        msg.add(new Message("Paco","¿Encontraste un movil hace poco no?", "11:10",true));
        msg.add(new Message("Luis","Si", "11:10", true));

        return msg;
    }
}
