package com.example.pgutierrezd.becaidspracticav2.models;

/**
 * Created by pgutierrezd on 10/08/2016.
 */
public class Recurso {

    private int id;
    private String nombre;
    private String aPaterno;
    private String aMaterno;
    private String email;

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setaPaterno(String aPaterno) {
        this.aPaterno = aPaterno;
    }

    public void setaMaterno(String aMaterno) {
        this.aMaterno = aMaterno;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getaPaterno() {
        return aPaterno;
    }

    public String getaMaterno() {
        return aMaterno;
    }

    public String getEmail() {
        return email;
    }
}
