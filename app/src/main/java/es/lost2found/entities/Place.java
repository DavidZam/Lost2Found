package es.lost2found.entities;

import org.json.JSONObject;

import es.lost2found.database.DB_place;

public class Place {

    private Integer id;

    public Place(Integer id) {
        this.id = id;
    }

    public Place() {

    }

    /*public Place(String json) {
        try {
            JSONObject jObject = new JSONObject(json);
            this.id = jObject.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public Integer getId() {
        return DB_place.getId();
    }
}
