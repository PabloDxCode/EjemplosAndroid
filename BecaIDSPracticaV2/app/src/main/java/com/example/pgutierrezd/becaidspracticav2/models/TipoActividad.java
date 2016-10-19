package com.example.pgutierrezd.becaidspracticav2.models;

/**
 * Created by pgutierrezd on 16/08/2016.
 */
public class TipoActividad {

    private int idTipoActividad;
    private String clave;
    private String descripcion;

    public int getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(int idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
