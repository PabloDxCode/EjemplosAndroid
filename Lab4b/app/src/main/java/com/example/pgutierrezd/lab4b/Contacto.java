package com.example.pgutierrezd.lab4b;

/**
 * Created by pgutierrezd on 26/07/2016.
 */
public class Contacto {

    private String nombre;
    private int edad;

    public Contacto(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    @Override
    public String toString() {
        return this.nombre + " " + this.edad;
    }
}
