package es.lost2found.entities;

import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String email;
    private String password;

    public User(String email, String name, String password) {
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", nombre='" + name + '\'' +
                ", contrasena='" + password + '\'' +
                '}';
    }
}
