package com.example.pgutierrezd.proyectoids.models;

/**
 * Created by pgutierrezd on 10/08/2016.
 */
public class DiaSemana {

    private int idRegistroDiaSemana;
    private String fecha;
    private String hrs;
    private String comentario;
    private int idRecursoFK;

    public void setIdRegistroDiaSemana(int idRegistroDiaSemana) {
        this.idRegistroDiaSemana = idRegistroDiaSemana;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHrs(String hrs) {
        this.hrs = hrs;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setIdRecursoFK(int idRecursoFK) {
        this.idRecursoFK = idRecursoFK;
    }

    public int getIdRegistroDiaSemana() {
        return idRegistroDiaSemana;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHrs() {
        return hrs;
    }

    public String getComentario() {
        return comentario;
    }

    public int getIdRecursoFK() {
        return idRecursoFK;
    }
}
