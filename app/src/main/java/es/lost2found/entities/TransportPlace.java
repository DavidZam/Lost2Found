package es.lost2found.entities;

import org.json.JSONObject;

import java.io.Serializable;

public class TransportPlace extends Place implements Serializable {

    private Integer id;
    private String tipoTte;
    private String line;
    private String station;

    public TransportPlace(Integer id, String tipoTte, String line, String station) {
        super(id);
        this.tipoTte = tipoTte;
        this.line = line;
        this.station = station;
    }

    public TransportPlace(String json) {
        try {
            JSONObject jObject = new JSONObject(json);
            this.id = jObject.getInt("id");
            this.tipoTte = jObject.getString("tipoTte");
            this.line = jObject.getString("linea");
            this.station = jObject.getString("estacion");
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

    public String getLine() {
        return this.line;
    }

    public String getStation() {
        return this.station;
    }
}
