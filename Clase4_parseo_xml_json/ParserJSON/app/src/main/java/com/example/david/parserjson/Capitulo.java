package com.example.david.parserjson;

/**
 * Created by david on 7/3/17.
 */

public class Capitulo {
    Long id;
    String titulo;
    String num;
    String fecha;
    String puntos;
    String imdbID;

    public Capitulo(){

    }

    public Capitulo(String titulo, String num, String fecha, String puntos,String imdbID) {
        this.titulo = titulo;
        this.num = num;
        this.fecha = fecha;
        this.puntos = puntos;
        this.imdbID=imdbID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
}
