package es.lost2found.entities;

import java.io.Serializable;

public class ConcretePlace extends Place implements Serializable {

    private Integer id;
    private String calle;
    private String number;
    private String postalCode;

    public ConcretePlace(Integer id, String calle, String number, String postalCode) {
        super(id);
        this.calle = calle;
        this.number = number;
        this.postalCode = postalCode;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCalle() {
        return this.calle;
    }

    public String getNumber() {
        return this.number;
    }

    public String getPostalCode() {
        return this.postalCode;
    }
}
