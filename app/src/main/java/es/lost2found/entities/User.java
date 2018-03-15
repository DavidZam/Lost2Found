package es.lost2found.entities;

public class User {

    public String name;
    public String email;

    public User() {
        this.name = "";
        this.email = "";
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
