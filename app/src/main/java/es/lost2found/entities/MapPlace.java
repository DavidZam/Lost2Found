package es.lost2found.entities;

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
