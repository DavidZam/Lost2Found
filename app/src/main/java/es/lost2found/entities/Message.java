package es.lost2found.entities;

import java.io.Serializable;
import java.util.List;

import es.lost2found.R;

public class Message implements Serializable {

    public String textMsg;
    public String hourMsg;
    public boolean read;

    public Message(String textMsg, String hourMsg, boolean read) {
        this.textMsg = textMsg;
        this.hourMsg = hourMsg;
        this.read = read;
    }

    public String getTextMsg() {
        return textMsg;
    }

    public String getHourMsg() {
        return hourMsg;
    }
}
