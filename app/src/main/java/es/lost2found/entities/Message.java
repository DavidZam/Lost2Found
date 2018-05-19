package es.lost2found.entities;

import java.io.Serializable;
import java.util.List;

import es.lost2found.R;

public class Message implements Serializable {

    private String textMsg;
    private String hourMsg;

    public Message(String textMsg, String hourMsg) {
        this.textMsg = textMsg;
        this.hourMsg = hourMsg;
    }

    public String getTextMsg() {
        return textMsg;
    }

    public String getHourMsg() {
        return hourMsg;
    }
}
