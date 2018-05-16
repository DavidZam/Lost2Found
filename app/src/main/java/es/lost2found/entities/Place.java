package es.lost2found.entities;

import es.lost2found.database.DB_place;

public class Place {

    private Integer id;

    public Place(Integer id) {
        this.id = id;
    }

    public Place() {

    }

    public Integer getId() {
        return DB_place.getId();
    }
}
