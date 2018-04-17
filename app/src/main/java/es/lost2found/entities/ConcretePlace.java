package es.lost2found.entities;


import org.json.JSONObject;

public class ConcretePlace extends Place {

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

    public ConcretePlace(String json) {
        try {
            JSONObject jObject = new JSONObject(json);
            this.id = jObject.getInt("id");
            this.calle = jObject.getString("calle");
            this.number = jObject.getString("number");
            this.postalCode = jObject.getString("postalCode");
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
}
