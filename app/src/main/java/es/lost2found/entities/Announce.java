package es.lost2found.entities;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;

public class Announce {

    /* public String title;
    public String description;
    public int imageId;*/

    // String announceType, String announceDateText, String currentTime, String announceHourText, String announceCategorie, String brand, String model, String color
    public String announceType;
    public String announceDateText;
    public String currentTime;
    public String announceHourText;
    public String announceCategorie;
    public String brand;
    public String model;
    public String color;

    /*public Announce(String title, String description, int imageId)  {
        this.title = title;
        this.description = description;
        this.imageId = imageId;
    }*/

    public Announce(String announceType, String currentTime, String announceDateText, String announceHourText, String model, String brand, String color, String announceCategorie) {
        this.announceType = announceType;
        this.announceDateText = announceDateText;
        this.currentTime = currentTime;
        this.announceHourText = announceHourText;
        this.announceCategorie = announceCategorie;
        this.brand = brand;
        this.model = model;
        this.color = color;
    }

    public Announce(String json) {
        try {
            JSONObject jObject = new JSONObject(json);
            this.announceType = jObject.getString("announceType");
            this.currentTime = jObject.getString("currentTime");
            this.announceDateText = jObject.getString("announceDateText");
            this.announceHourText = jObject.getString("announceHourText");
            this.model = jObject.getString("model");
            this.brand = jObject.getString("brand");
            this.color = jObject.getString("color");
            this.announceCategorie = jObject.getString("announceCategorie");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
