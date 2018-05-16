package es.lost2found.entities;

import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {

    private Integer id;
    private String name;
    private String email;
    private String password;

    public User(Integer id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User(String json) {
        try {
            JSONObject jObject = new JSONObject(json);
            this.email = jObject.getString("email");
            this.name = jObject.getString("nombre");
            this.password = "";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
