package es.lost2found.entities;

import java.io.Serializable;

public class TransportPlace extends Place implements Serializable {

    private Integer id;
    private String line;
    private String station;

    public TransportPlace(Integer id, String line, String station) {
        super(id);
        this.line = line;
        this.station = station;
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
