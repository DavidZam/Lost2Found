package es.lost2found.entities;

import org.json.JSONObject;

import es.lost2found.database.DB_transportPlace;

public class TransportPlace { // extends Place

    //private String nombre;
    //private String codigoEstacion;
    //private String movilidad;
    private String line;
    private String station;

    public TransportPlace(String line, String station) { // Integer id, String nombre,
        //super(id, nombre);
        this.line = line;
        this.station = station;
    }

    public TransportPlace(String json) {
        //super(json);
        try {
            JSONObject jObject = new JSONObject(json);
            this.line = jObject.getString("line");
            this.station = jObject.getString("station");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return DB_transportPlace.getId(line, station);
    }

    public String getLine() {
        return this.line;
    }

    public String getStation() {
        return this.station;
    }
}
