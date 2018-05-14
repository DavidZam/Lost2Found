package es.lost2found.entities;

import java.io.Serializable;

public class OpenDataAnnounce implements Serializable {

    public String announceType;
    public String announceDateText;
    public String lostFoundHourText;
    public String announceCategorie;
    public String place;
    private Integer idAnuncio;
    private String typePlace;

    public OpenDataAnnounce(String announceType, String announceDateText, String lostFoundHourText, String announceCategorie, String place) {
        this.announceType = announceType;
        this.announceDateText = announceDateText;
        this.lostFoundHourText = lostFoundHourText;
        this.announceCategorie = announceCategorie;
        this.place = place;
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

    public String getAnnounceCategorie() {
        return this.announceCategorie;
    }

    public void setAnnounceType(String announceType) {
        this.announceType = announceType;
    }

    public void setAnnounceDateText(String announceDateText) {
        this.announceDateText = announceDateText;
    }

    public String getAnnounceHourText() {
        return this.lostFoundHourText;
    }

    public void setAnnounceHourText(String lostFoundHourText) {
        this.lostFoundHourText = lostFoundHourText;
    }

    public void setAnnounceCategorie(String announceCategorie) {
        this.announceCategorie = announceCategorie;
    }

    public String getPlace() {
        return this.place;
    }

    public Integer getAnnounceId() {
        return this.idAnuncio;
    }

}
