package es.lost2found.entities;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.lost2found.R;

public class Announce implements Serializable {

    public String announceType;
    //public Date announceDateText;
    public String announceDateText;
    //public String currentTime;
    public String currentTime;
    //public Date announceHourText;
    public String announceHourText;
    public String announceCategorie;
    //public String brand;
    //public String model;
    public String color;

    public Announce(String announceType, String currentTime, String announceDateText, String announceHourText, String color, String announceCategorie) { // String model, String brand,
        this.announceType = announceType;
        this.announceDateText = announceDateText;
        this.currentTime = currentTime;
        this.announceHourText = announceHourText;
        this.announceCategorie = announceCategorie;
        //this.brand = brand;
        //this.model = model;
        this.color = color;
    }


    /*public Announce(Announce announce) {
        this.announceType = announce.announceType;
        this.announceDateText = announce.announceDateText;
        this.currentTime = announce.currentTime;
        this.announceHourText = announce.announceHourText;
        this.announceCategorie = announce.announceCategorie;
        this.brand = announce.brand;
        this.model = announce.model;
        this.color = announce.color;
    }*/

    public Announce(String announce) {
        //String announceArray[] = announce.split(",");
        String announceArray[] = announce.split("\"");
        this.announceType = announceArray[5];
        this.currentTime = announceArray[9];
        this.announceDateText = announceArray[13];
        this.announceHourText = announceArray[17];
        //this.model = announceArray[21];
        //this.brand = announceArray[25];
        //this.color = announceArray[29];
        this.announceCategorie = announceArray[29];
    }

    public String getAnnounceType() {
        return this.announceType;
    }

    public String getAnnounceDateText() {
        return this.announceDateText;
    }

    public String getAnnounceHourText() {
        return this.announceHourText;
    }

    public String getCurrentTime() { return this.currentTime; }

    public String getAnnounceCategorie() {
        return this.announceCategorie;
    }

    public void setAnnounceType(String announceType) {
        this.announceType = announceType;
    }

    public void setAnnounceDateText(String announceDateText) {
        this.announceDateText = announceDateText;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public void setAnnounceHourText(String announceHourText) {
        this.announceHourText = announceHourText;
    }

    public void setAnnounceCategorie(String announceCategorie) {
        this.announceCategorie = announceCategorie;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /*public String getBrand() {
        return this.brand;
    }

    public String getModel() {
        return this.model;
    }*/

    public String getColor() {
        return this.color;
    }

    // Faltan los getters y setters
    /*
    public List<Announce> fill_with_data(List<Announce> announce) {

        announce.add(new Announce("Anuncio1", "Descripcion1", R.drawable.ic_phone_android));
        announce.add(new Announce("Anuncio2", "Descripcion2", R.drawable.ic_phone_android));
        announce.add(new Announce("Anuncio3", "Descripcion3", R.drawable.ic_phone_android));
        announce.add(new Announce("Anuncio4", "Descripcion4", R.drawable.ic_phone_android));
        announce.add(new Announce("Anuncio5", "Descripcion5", R.drawable.ic_phone_android));
        announce.add(new Announce("Anuncio6", "Descripcion6", R.drawable.ic_phone_android));

        return announce;
    }*/
}
