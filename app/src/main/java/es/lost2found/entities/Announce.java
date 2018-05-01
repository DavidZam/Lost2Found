package es.lost2found.entities;

import java.io.Serializable;

public class Announce implements Serializable {

    public String announceType;
    public String announceDateText;
    public String currentTime;
    public String announceHourText;
    public String announceCategorie;
    public int color;
    public String place;
    public String userOwner;
    private Integer idAnuncio;

    public Announce(String announceType, String currentTime, String announceDateText, String announceHourText, int color, String announceCategorie, String place, String userOwner) {
        this.announceType = announceType;
        this.announceDateText = announceDateText;
        this.currentTime = currentTime;
        this.announceHourText = announceHourText;
        this.announceCategorie = announceCategorie;
        this.color = color;
        this.place = place;
        this.userOwner = userOwner;
    }

    public Announce(String announce, String userOwner, String place, Integer id) {
        String announceArray[] = announce.split("\"");
        this.announceType = announceArray[5];
        this.currentTime = announceArray[9];
        this.announceDateText = announceArray[13];
        this.announceHourText = announceArray[17];
        String color = announceArray[21];
        this.color = Integer.valueOf(color);
        this.announceCategorie = announceArray[29];
        this.userOwner = userOwner;
        this.place = place;
        idAnuncio = id;
    }

    public Integer getIdAnuncio() {
        return idAnuncio;
    }

    public String getAnnounceType() {
        return this.announceType;
    }

    public String DDMMYYYY(){
        String anio = "", mes = "", dia = "";

        int ini = 0;

        while(announceDateText.charAt(ini) != '/'){
            anio += announceDateText.charAt(ini);
            ++ini;
        }
        ++ini;
        while(announceDateText.charAt(ini) != '/'){
            mes += announceDateText.charAt(ini);
            ++ini;
        }
        ++ini;
        while(ini <= announceDateText.length()-1){
            dia += announceDateText.charAt(ini);
            ++ini;
        }

        /*announceDateText = (dia + "/" + mes + "/" + anio);
        return announceDateText;*/
        return (dia + "/" + mes + "/" + anio);
    }

    public String getAnnounceDateText() {
        return announceDateText;
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

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public String getPlace() {
        return this.place;
    }

    public String getUserOwner() {
        return this.userOwner;
    }
}
