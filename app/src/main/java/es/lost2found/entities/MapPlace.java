package es.lost2found.entities;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.Serializable;

public class MapPlace extends Place implements Serializable {
    private Integer id;
    private Double latitud;
    private Double longitud;

    public MapPlace(Integer id, Double latitud, Double longitud) {
        super(id);
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public MapPlace(String json) {
        try {
            JSONObject jObject = new JSONObject(json);
            this.id = jObject.getInt("id");
            this.latitud = jObject.getDouble("latitud");
            this.longitud = jObject.getDouble("longitud");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLatitud() {
        return this.latitud;
    }

    public Double getLongitud() {
        return this.longitud;
    }
}
