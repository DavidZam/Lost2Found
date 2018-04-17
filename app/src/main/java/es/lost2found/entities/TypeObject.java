package es.lost2found.entities;

import android.support.v7.app.AppCompatActivity;

public class TypeObject {

    private String nombreTabla;
    private String descripcion;

    public TypeObject(String nombreTabla, String descripcion) {
        this.nombreTabla = nombreTabla;
        this.descripcion = descripcion;
    }

    public TypeObject() {

    }

    public String getNombreTabla() {
        return this.nombreTabla;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

}
