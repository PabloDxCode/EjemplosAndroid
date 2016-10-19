package com.example.pgutierrezd.becaidspracticav2.models;

/**
 * Created by pgutierrezd on 10/08/2016.
 */
public class DiaSemana {

    private int idRegistroDiaSemana;
    private String fecha;
    private String hrs;
    private String comentario;
    private int idRecursoFK;
    private int idProyectoFK;
    private int idTipoActividadFK;

    public void setIdRegistroDiaSemana(int idRegistroDiaSemana) {
        this.idRegistroDiaSemana = idRegistroDiaSemana;
    }

    public void setHrs(String hrs) {
        this.hrs = hrs;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setIdRecursoFK(int idRecursoFK) {
        this.idRecursoFK = idRecursoFK;
    }

    public void setIdProyectoFK(int idProyectoFK) {
        this.idProyectoFK = idProyectoFK;
    }

    public void setIdTipoActividadFK(int idTipoActividadFK) {
        this.idTipoActividadFK = idTipoActividadFK;
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

    public int getIdProyectoFK() {
        return idProyectoFK;
    }

    public int getIdTipoActividadFK() {
        return idTipoActividadFK;
    }
}
