package es.lost2found.entities;

import java.util.ArrayList;
import java.util.List;

import es.lost2found.R;

public class Announce {

    public String title;
    public String description;
    public int imageId;

    public Announce() {
        this.title = "";
        this.description = "";
        this.imageId = 0;
    }

    Announce(String title, String description, int imageId)  {
        this.title = title;
        this.description = description;
        this.imageId = imageId;
    }

    public List<Announce> fill_with_data(List<Announce> announce) {

        announce.add(new Announce("Anuncio1", "Descripcion1", R.drawable.ic_phone_android));
        announce.add(new Announce("Anuncio2", "Descripcion2", R.drawable.ic_phone_android));
        announce.add(new Announce("Anuncio3", "Descripcion3", R.drawable.ic_phone_android));
        announce.add(new Announce("Anuncio4", "Descripcion4", R.drawable.ic_phone_android));
        announce.add(new Announce("Anuncio5", "Descripcion5", R.drawable.ic_phone_android));
        announce.add(new Announce("Anuncio6", "Descripcion6", R.drawable.ic_phone_android));

        return announce;
    }

}
