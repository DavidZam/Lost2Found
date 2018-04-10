package es.lost2found.entities;

import org.json.JSONObject;

public class Place {

    //private Integer id;
    private String nombre;

    /*public Place(String nombre) { // Integer id,
        //this.id = id;
        this.nombre = nombre;
    }*/

    public Place(String json) {
        try {
            JSONObject jObject = new JSONObject(json);
            //this.id = jObject.getInt("id");
            this.nombre = jObject.getString("nombre");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNombre() {
        return this.nombre;
    }

    /*public Integer getId() {
        return this.id;
    }*/

    public void setName(String nombre) {
        this.nombre = nombre;
    }
}
